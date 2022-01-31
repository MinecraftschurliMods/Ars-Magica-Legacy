package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.DoubleArgumentType;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.ArsMagicaLegacyCommandTranslations.*;

public class AffinitySubcommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_AFFINITIES = AffinitySubcommand::suggestAffinities;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_AFFINITY = new DynamicCommandExceptionType(message -> new TranslatableComponent(AFFINITY_UNKNOWN, message));
    
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("affinity")
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(AffinitySubcommand::setAffinity))))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                        .executes(AffinitySubcommand::setAffinitySelf))))
                .then(Commands.literal("reset")
                        .executes(AffinitySubcommand::resetAffinitiesSelf)
                        .then(Commands.argument("target", EntityArgument.players())
                                .executes(AffinitySubcommand::resetAffinities)))
                .then(Commands.literal("get")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .executes(AffinitySubcommand::getAffinity)))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .executes(AffinitySubcommand::getAffinitySelf))));
    }

    private static int setAffinitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setAffinity(List.of(context.getSource().getPlayerOrException()), getAffinityFromRegistry(context), context);
    }

    private static int setAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setAffinity(EntityArgument.getPlayers(context, "target"), getAffinityFromRegistry(context), context);
    }

    private static int setAffinity(Collection<ServerPlayer> players, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getAffinityHelper().setAffinityDepth(player, affinity, (float) amount);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(AFFINITY_SET_SINGLE, affinity.getDisplayName(), players.iterator().next().getDisplayName(), amount), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(AFFINITY_SET_MULTIPLE, affinity.getDisplayName(), players.size(), amount), true);
        }
        return players.size();
    }

    private static int resetAffinitiesSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return resetAffinities(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int resetAffinities(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return resetAffinities(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int resetAffinities(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        for (ServerPlayer player : players) {
            for (IAffinity affinity : api.getAffinityRegistry()) {
                api.getAffinityHelper().setAffinityDepth(player, affinity, 0f);
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(AFFINITY_RESET_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(AFFINITY_RESET_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int getAffinitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getAffinity(context.getSource().getPlayerOrException(), getAffinityFromRegistry(context), context);
    }

    private static int getAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getAffinity(EntityArgument.getPlayer(context, "target"), getAffinityFromRegistry(context), context);
    }

    private static int getAffinity(ServerPlayer player, IAffinity affinity, CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(new TranslatableComponent(AFFINITY_GET, affinity.getDisplayName(), player.getDisplayName(), ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity)), true);
        return Command.SINGLE_SUCCESS;
    }
    
    private static IAffinity getAffinityFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "affinity");
        IAffinity affinity = ArsMagicaAPI.get().getAffinityRegistry().getValue(rl);
        if (affinity == null) throw ERROR_UNKNOWN_AFFINITY.create(rl);
        return affinity;
    }

    private static CompletableFuture<Suggestions> suggestAffinities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getAffinityRegistry().getKeys(), builder);
    }
}
