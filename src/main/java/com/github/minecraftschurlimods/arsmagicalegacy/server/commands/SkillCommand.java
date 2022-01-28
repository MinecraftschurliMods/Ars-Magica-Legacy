package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class SkillCommand {
    public static final String ALREADY_KNOWN       = "commands." + ArsMagicaAPI.MOD_ID + ".skill.already_known";
    public static final String FORGET_ALL_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget_all.multiple.success";
    public static final String FORGET_ALL_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget_all.single.success";
    public static final String FORGET_MULTIPLE     = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget.multiple.success";
    public static final String FORGET_SINGLE       = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget.single.success";
    public static final String LEARN_ALL_MULTIPLE  = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn_all.multiple.success";
    public static final String LEARN_ALL_SINGLE    = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn_all.single.success";
    public static final String LEARN_MULTIPLE      = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn.multiple.success";
    public static final String LEARN_SINGLE        = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn.single.success";
    public static final String NOT_YET_KNOWN       = "commands." + ArsMagicaAPI.MOD_ID + ".skill.not_yet_known";
    public static final String UNKNOWN             = "commands." + ArsMagicaAPI.MOD_ID + ".skill.unknown";
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLS = SkillCommand::suggestSkills;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL = new DynamicCommandExceptionType(message -> new TranslatableComponent(UNKNOWN, message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("skill")
                .requires(p -> p.hasPermission(2))
                .then(Commands.literal("learn")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::learn))
                                .then(Commands.literal("*")
                                        .executes(SkillCommand::learnAll)))
                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::learnSelf))
                        .then(Commands.literal("*")
                                .executes(SkillCommand::learnAllSelf)))
                .then(Commands.literal("forget")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::forget))
                                .then(Commands.literal("*")
                                        .executes(SkillCommand::forgetAll)))
                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::forgetSelf))
                        .then(Commands.literal("*")
                                .executes(SkillCommand::forgetAllSelf)))
                .then(Commands.literal("list")
                        .executes(SkillCommand::listKnownSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(SkillCommand::listKnown)
                                .then(Commands.literal("known")
                                        .executes(SkillCommand::listKnown))
                                .then(Commands.literal("unknown")
                                        .executes(SkillCommand::listUnknown)))
                        .then(Commands.literal("known")
                                .executes(SkillCommand::listKnownSelf))
                        .then(Commands.literal("unknown")
                                .executes(SkillCommand::listUnknownSelf))
                        .then(Commands.literal("all")
                                .executes(SkillCommand::listAll)))
        );
    }

    private static int listAll(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(createSkillListComponent(ArsMagicaAPI.get().getSkillManager().getSkills().stream()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listUnknownSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknown(context.getSource().getPlayerOrException(), context);
    }

    private static int listUnknown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknown(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listKnownSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnown(context.getSource().getPlayerOrException(), context);
    }

    private static int listKnown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnown(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listUnknown(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var knowledgeHelper = api.getSkillHelper();
        var skillManager = api.getSkillManager();
        context.getSource().sendSuccess(createSkillListComponent(skillManager.getSkills().stream().filter(skill -> knowledgeHelper.knows(player, skill))), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listKnown(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var skillManager = api.getSkillManager();
        var knowledgeHelper = api.getSkillHelper();
        context.getSource().sendSuccess(createSkillListComponent(knowledgeHelper.getKnownSkills(player).stream().flatMap(skill -> skillManager.getOptional(skill).stream())), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAll(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forgetAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAll(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetAll(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().forgetAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(FORGET_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(FORGET_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int forgetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forget(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkill(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (!helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(NOT_YET_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.forget(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(FORGET_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(FORGET_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static int learnAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAll(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learnAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAll(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnAll(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().learnAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(LEARN_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(LEARN_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int learnSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learn(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learn(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkill(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(ALREADY_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.learn(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(LEARN_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(LEARN_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static ISkill getSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "skill");
        Optional<ISkill> skill = ArsMagicaAPI.get().getSkillManager().getOptional(rl);
        if (skill.isEmpty()) throw ERROR_UNKNOWN_SKILL.create(rl);
        return skill.get();
    }

    private static Component createSkillListComponent(Stream<ISkill> stream) {
        return stream.map(ITranslatable::getDisplayName).reduce((component, component2) -> component.copy().append("\n").append(component2)).orElse(new TranslatableComponent(""));
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillManager().getSkills().stream().map(ISkill::getId), builder);
    }
}
