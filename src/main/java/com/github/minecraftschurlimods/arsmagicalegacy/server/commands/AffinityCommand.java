package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AffinityCommand {
    public static final String GET            = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.get.success";
    public static final String SET_MULTIPLE   = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.set.multiple.success";
    public static final String SET_SINGLE     = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.set.single.success";
    public static final String RESET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.reset.multiple.success";
    public static final String RESET_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.reset.single.success";
    public static final String UNKNOWN        = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.unknown";
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_AFFINITIES = AffinityCommand::suggestAffinities;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_AFFINITY = new DynamicCommandExceptionType(message -> new TranslatableComponent(UNKNOWN, message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("affinity")
                .requires(p -> p.hasPermission(2))
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
        return get(List.of(context.getSource().getPlayerOrException()), getAffinity(context), context);
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return get(EntityArgument.getPlayers(context, "target"), getAffinity(context), context);
    }

    private static int get(Collection<ServerPlayer> players, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            context.getSource().sendSuccess(new TranslatableComponent(GET, affinity.getDisplayName(), player.getDisplayName(), (float) ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity)), true);
        }
        return players.size();
    }

    private static int setSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return set(List.of(context.getSource().getPlayerOrException()), getAffinity(context), context);
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return set(EntityArgument.getPlayers(context, "target"), getAffinity(context), context);
    }

    private static int set(Collection<ServerPlayer> players, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getAffinityHelper().setAffinityDepth(player, affinity, (float) amount);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SET_SINGLE, affinity.getDisplayName(), players.iterator().next().getDisplayName(), (float) amount), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SET_MULTIPLE, affinity.getDisplayName(), players.size(), (float) amount), true);
        }
        return players.size();
    }

    private static int resetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int reset(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int reset(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var helper = api.getAffinityHelper();
        for (ServerPlayer player : players) {
            for (IAffinity affinity : api.getAffinityRegistry()) {
                helper.setAffinityDepth(player, affinity, 0f);
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(RESET_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(RESET_MULTIPLE, players.size()), true);
        }
        return players.size();
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
