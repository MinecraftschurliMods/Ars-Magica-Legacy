package com.github.minecraftschurli.arsmagicalegacy.server.commands;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SkillCommand {
    public static final String LANG_KEY_PREFIX = "commands.%s.skill".formatted(ArsMagicaAPI.MOD_ID);
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLS = SkillCommand::suggestSkills;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL = new DynamicCommandExceptionType(message -> new TranslatableComponent(LANG_KEY_PREFIX + ".skillNotFound", message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        var skill = dispatcher.register(Commands.literal("skill")
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
        dispatcher.register(Commands.literal("%s:skill".formatted(ArsMagicaAPI.MOD_ID)).redirect(skill));
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
        context.getSource().sendSuccess(new TranslatableComponent(LANG_KEY_PREFIX + ".forgetAll.success", player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(context.getSource().getPlayerOrException(), context);
    }

    private static int forget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forget(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var skill = getSkill(context);
        return players.stream().mapToInt(player -> forget(player, skill, context)).sum();
    }

    private static int forget(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forget(player, getSkill(context), context);
    }

    private static int forget(ServerPlayer player, ISkill skill, CommandContext<CommandSourceStack> context) {
        var knowledgeHelper = ArsMagicaAPI.get().getSkillHelper();
        if (!knowledgeHelper.knows(player, skill)) {
            context.getSource().sendFailure(new TranslatableComponent(LANG_KEY_PREFIX + ".skillNotKnown", skill.getDisplayName(), player.getDisplayName()));
            return 0;
        }
        knowledgeHelper.forget(player, skill);
        context.getSource().sendSuccess(new TranslatableComponent(LANG_KEY_PREFIX + ".forget.success", skill.getDisplayName(), player.getDisplayName()), true);
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
        var knowledgeHelper = ArsMagicaAPI.get().getSkillHelper();
        ArsMagicaAPI.get().getSkillManager().getSkills().forEach(iSkill -> knowledgeHelper.learn(player, iSkill));
        context.getSource().sendSuccess(new TranslatableComponent(LANG_KEY_PREFIX + ".learnAll.success", player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int learnSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(context.getSource().getPlayerOrException(), context);
    }

    private static int learn(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learn(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var skill = getSkill(context);
        return players.stream().mapToInt(player -> learn(player, skill, context)).sum();
    }

    private static int learn(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learn(player, getSkill(context), context);
    }

    private static int learn(ServerPlayer player, ISkill skill, CommandContext<CommandSourceStack> context) {
        var knowledgeHelper = ArsMagicaAPI.get().getSkillHelper();
        if (knowledgeHelper.knows(player, skill)) {
            context.getSource().sendFailure(new TranslatableComponent(LANG_KEY_PREFIX + ".skillAlreadyKnown", skill.getDisplayName(), player.getDisplayName()));
            return 0;
        }
        knowledgeHelper.learn(player, skill);
        context.getSource().sendSuccess(new TranslatableComponent(LANG_KEY_PREFIX + ".learn.success", skill.getDisplayName(), player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static ISkill getSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var skill = ResourceLocationArgument.getId(context, "skill");
        var api = ArsMagicaAPI.get();
        var optional = api.getSkillManager().getOptional(skill);
        if (optional.isEmpty()) throw ERROR_UNKNOWN_SKILL.create(skill);
        return optional.get();
    }

    private static Component createListComponent(Stream<ISkill> resourceLocationStream) {
        return resourceLocationStream.map(ITranslatable::getDisplayName).reduce((component, component2) -> component.append("\n").append(component2)).orElse(new TranslatableComponent(LANG_KEY_PREFIX + ".empty"));
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillManager().getSkills().stream().map(ISkill::getId), builder);
    }
}
