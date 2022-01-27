package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class AffinityCommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_AFFINITIES = AffinityCommand::suggestAffinities;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_AFFINITY = new DynamicCommandExceptionType(message -> new TranslatableComponent(TranslationConstants.COMMAND_AFFINITY_UNKNOWN, message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("affinity")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("get")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .executes(AffinityCommand::get)))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .executes(AffinityCommand::getSelf)))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(AffinityCommand::set))))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                        .executes(AffinityCommand::setSelf))))
                .then(Commands.literal("reset")
                        .executes(AffinityCommand::resetSelf)
                        .then(Commands.argument("target", EntityArgument.players())
                                .executes(AffinityCommand::reset)))
        );
    }

    private static int getSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return get(context.getSource().getPlayerOrException(), context);
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return get(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int get(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IAffinity affinity = getAffinity(context);
        return players.stream().mapToInt(player -> get(player, affinity, context)).sum();
    }

    private static int get(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return get(player, getAffinity(context), context);
    }

    private static int get(ServerPlayer player, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_AFFINITY_GET_SUCCESS, affinity.getDisplayName(), player.getDisplayName(), (float) ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity)), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int setSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return set(context.getSource().getPlayerOrException(), context);
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return set(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int set(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        IAffinity affinity = getAffinity(context);
        return players.stream().mapToInt(player -> set(player, affinity, context)).sum();
    }

    private static int set(ServerPlayer player, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return set(player, getAffinity(context), context);
    }

    private static int set(ServerPlayer player, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        ArsMagicaAPI.get().getAffinityHelper().setAffinityDepth(player, affinity, (float) amount);
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_AFFINITY_SET_SUCCESS, affinity.getDisplayName(), player.getDisplayName(), (float) amount), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int resetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(context.getSource().getPlayerOrException(), context);
    }

    private static int reset(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int reset(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        return players.stream().mapToInt(player -> reset(player, context)).sum();
    }

    private static int reset(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var helper = api.getAffinityHelper();
        for (IAffinity affinity : api.getAffinityRegistry()) {
            helper.setAffinityDepth(player, affinity, 0f);
        }
        context.getSource().sendSuccess(new TranslatableComponent(TranslationConstants.COMMAND_AFFINITY_RESET_SUCCESS, player.getDisplayName()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static IAffinity getAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "affinity");
        IAffinity affinity = ArsMagicaAPI.get().getAffinityRegistry().getValue(rl);
        if (affinity == null) throw ERROR_UNKNOWN_AFFINITY.create(rl);
        return affinity;
    }

    private static CompletableFuture<Suggestions> suggestAffinities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getAffinityRegistry().getKeys(), builder);
    }
}
