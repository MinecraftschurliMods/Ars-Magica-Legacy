package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.arsmagicalegacy.server.AMPermissions;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.*;

public class SkillCommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLS = SkillCommand::suggestSkills;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL = new DynamicCommandExceptionType(message -> new TranslatableComponent(SKILL_UNKNOWN, message));

    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("skill")
                .requires(p -> p.getEntity() instanceof ServerPlayer player ? PermissionAPI.getPermission(player, AMPermissions.CAN_EXECUTE_AFFINITY_COMMAND) : p.hasPermission(2))
                .then(Commands.literal("learn")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::learnSkill))
                                .then(Commands.literal("*")
                                        .executes(SkillCommand::learnAllSkills)))
                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::learnSkillSelf))
                        .then(Commands.literal("*")
                                .executes(SkillCommand::learnAllSkillsSelf)))
                .then(Commands.literal("forget")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::forgetSkill))
                                .then(Commands.literal("*")
                                        .executes(SkillCommand::forgetAllSkills)))
                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::forgetSkillSelf))
                        .then(Commands.literal("*")
                                .executes(SkillCommand::forgetAllSkillsSelf)))
                .then(Commands.literal("list")
                        .executes(SkillCommand::listKnownSkillsSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(SkillCommand::listKnownSkills)
                                .then(Commands.literal("known")
                                        .executes(SkillCommand::listKnownSkills))
                                .then(Commands.literal("unknown")
                                        .executes(SkillCommand::listUnknownSkills)))
                        .then(Commands.literal("known")
                                .executes(SkillCommand::listKnownSkillsSelf))
                        .then(Commands.literal("unknown")
                                .executes(SkillCommand::listUnknownSkillsSelf))
                        .then(Commands.literal("all")
                                .executes(SkillCommand::listAllSkills))));
    }

    private static int listAllSkills(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(createSkillListComponent(ArsMagicaAPI.get().getSkillManager().getSkills().stream()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listUnknownSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknownSkills(context.getSource().getPlayerOrException(), context);
    }

    private static int listUnknownSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknownSkills(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listKnownSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnownSkills(context.getSource().getPlayerOrException(), context);
    }

    private static int listKnownSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnownSkills(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listUnknownSkills(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var knowledgeHelper = api.getSkillHelper();
        var skillManager = api.getSkillManager();
        context.getSource().sendSuccess(createSkillListComponent(skillManager.getSkills().stream().filter(skill -> knowledgeHelper.knows(player, skill))), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listKnownSkills(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var skillManager = api.getSkillManager();
        var knowledgeHelper = api.getSkillHelper();
        context.getSource().sendSuccess(createSkillListComponent(knowledgeHelper.getKnownSkills(player).stream().flatMap(skill -> skillManager.getOptional(skill).stream())), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetAllSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAllSkills(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forgetAllSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAllSkills(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetAllSkills(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().forgetAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int forgetSkillSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetSkill(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forgetSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetSkill(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetSkill(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkillFromRegistry(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (!helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(SKILL_NOT_YET_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.forget(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static int learnAllSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAllSkills(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learnAllSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAllSkills(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnAllSkills(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().learnAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int learnSkillSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnSkill(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learnSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnSkill(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnSkill(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkillFromRegistry(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(SKILL_ALREADY_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.learn(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static ISkill getSkillFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "skill");
        Optional<ISkill> skill = ArsMagicaAPI.get().getSkillManager().getOptional(rl);
        if (skill.isEmpty()) throw ERROR_UNKNOWN_SKILL.create(rl);
        return skill.get();
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillManager().getSkills().stream().map(ISkill::getId), builder);
    }

    private static Component createSkillListComponent(Stream<ISkill> stream) {
        return stream.map(ITranslatable::getDisplayName).reduce((component, component2) -> component.copy().append("\n").append(component2)).orElse(new TranslatableComponent(""));
    }
}
