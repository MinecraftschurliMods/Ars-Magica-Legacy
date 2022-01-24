package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class SkillCommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLS = SkillCommand::suggestSkills;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL = new DynamicCommandExceptionType(message -> new TranslatableComponent(TranslationConstants.COMMAND_SKILL_UNKNOWN, message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> skill = dispatcher.register(Commands.literal("skill")
                .then(Commands.literal("learn").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::learn))
                                .then(Commands.literal("*").executes(SkillCommand::learnAll))
                        ).then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::learnSelf))
                        .then(Commands.literal("*").executes(SkillCommand::learnAllSelf))
                ).then(Commands.literal("forget").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(SkillCommand::forget))
                                .then(Commands.literal("*")
                                        .executes(SkillCommand::forgetAll))
                        ).then(Commands.argument("skill", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILLS)
                                .executes(SkillCommand::forgetSelf))
                        .then(Commands.literal("*").executes(SkillCommand::forgetAllSelf))
                ).then(Commands.literal("list")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.literal("known").executes(SkillCommand::listKnown))
                                .then(Commands.literal("unknown").executes(SkillCommand::listUnknown))
                        ).then(Commands.literal("known").executes(SkillCommand::listKnownSelf))
                        .then(Commands.literal("unknown").executes(SkillCommand::listUnknownSelf))
                        .then(Commands.literal("all").executes(SkillCommand::listAll))
                )
        );
        dispatcher.register(Commands.literal(ArsMagicaAPI.MOD_ID + ":skill").redirect(skill));
    }

    private static int listAll(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(createListComponent(ArsMagicaAPI.get().getSkillManager().getSkills().stream()), false);
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
        context.getSource().sendSuccess(createListComponent(skillManager.getSkills().stream().filter(iSkill -> knowledgeHelper.knows(player, iSkill))), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int listKnown(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var skillManager = api.getSkillManager();
        var knowledgeHelper = api.getSkillHelper();
        context.getSource().sendSuccess(createListComponent(knowledgeHelper.getKnownSkills(player).stream().flatMap(location -> skillManager.getOptional(location).stream())), false);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAll(context.getSource().getPlayerOrException(), context);
    }

    private static int forgetAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAll(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetAll(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        return players.stream().mapToInt(player -> forgetAll(player, context)).sum();
    }

    private static int forgetAll(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        ArsMagicaAPI.get().getSkillHelper().forgetAll(player);
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_FORGET_ALL_SUCCESS, player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(context.getSource().getPlayerOrException(), context);
    }

    private static int forget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forget(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ISkill skill = getSkill(context);
        return players.stream().mapToInt(player -> forget(player, skill, context)).sum();
    }

    private static int forget(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(player, getSkill(context), context);
    }

    private static int forget(ServerPlayer player, ISkill skill, CommandContext<CommandSourceStack> context) {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        if (!helper.knows(player, skill)) {
            context.getSource().sendFailure(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_NOT_YET_KNOWN, skill.getDisplayName(), player.getDisplayName()));
            return 0;
        }
        helper.forget(player, skill);
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_FORGET_SUCCESS, skill.getDisplayName(), player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int learnAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAll(context.getSource().getPlayerOrException(), context);
    }

    private static int learnAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAll(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnAll(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        return players.stream().mapToInt(player -> learnAll(player, context)).sum();
    }

    private static int learnAll(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        ArsMagicaAPI.get().getSkillHelper().learnAll(player);
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_LEARN_ALL_SUCCESS, player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int learnSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(context.getSource().getPlayerOrException(), context);
    }

    private static int learn(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learn(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ISkill skill = getSkill(context);
        return players.stream().mapToInt(player -> learn(player, skill, context)).sum();
    }

    private static int learn(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(player, getSkill(context), context);
    }

    private static int learn(ServerPlayer player, ISkill skill, CommandContext<CommandSourceStack> context) {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        if (helper.knows(player, skill)) {
            context.getSource().sendFailure(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_ALREADY_KNOWN, skill.getDisplayName(), player.getDisplayName()));
            return 0;
        }
        helper.learn(player, skill);
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_LEARN_SUCCESS, skill.getDisplayName(), player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static ISkill getSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation skill = ResourceLocationArgument.getId(context, "skill");
        Optional<ISkill> optional = ArsMagicaAPI.get().getSkillManager().getOptional(skill);
        if (optional.isEmpty()) throw ERROR_UNKNOWN_SKILL.create(skill);
        return optional.get();
    }

    private static Component createListComponent(Stream<ISkill> resourceLocationStream) {
        return resourceLocationStream.map(ITranslatable::getDisplayName).reduce((component, component2) -> component.copy().append("\n").append(component2)).orElse(new TranslatableComponent(TranslationConstants.COMMAND_SKILL_EMPTY));
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillManager().getSkills().stream().map(ISkill::getId), builder);
    }
}
