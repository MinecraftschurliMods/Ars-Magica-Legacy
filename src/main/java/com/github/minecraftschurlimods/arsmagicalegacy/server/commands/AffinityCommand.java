package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.server.AMPermissions;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.*;

public class AffinityCommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_AFFINITIES = AffinityCommand::suggestAffinities;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_AFFINITY = new DynamicCommandExceptionType(message -> Component.translatable(AFFINITY_UNKNOWN, message));

    /**
     * Registers the command to the given builder.
     *
     * @param builder The builder to register the command to.
     */
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("affinity")
                .requires(p -> p.getEntity() instanceof ServerPlayer player ? PermissionAPI.getPermission(player, AMPermissions.CAN_EXECUTE_AFFINITY_COMMAND) : p.hasPermission(2))
                .then(Commands.literal("add")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(AffinityCommand::addAffinity))))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                        .executes(AffinityCommand::addAffinitySelf))))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(AffinityCommand::setAffinity))))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                        .executes(AffinityCommand::setAffinitySelf))))
                .then(Commands.literal("reset")
                        .executes(AffinityCommand::resetAffinitiesSelf)
                        .then(Commands.argument("target", EntityArgument.players())
                                .executes(AffinityCommand::resetAffinities)))
                .then(Commands.literal("get")
                        .then(Commands.argument("target", EntityArgument.player())
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .executes(AffinityCommand::getAffinity)))
                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                .suggests(SUGGEST_AFFINITIES)
                                .executes(AffinityCommand::getAffinitySelf))));
    }

    private static int addAffinitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addAffinity(List.of(context.getSource().getPlayerOrException()), getAffinityFromRegistry(context), context);
    }

    private static int addAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addAffinity(EntityArgument.getPlayers(context, "target"), getAffinityFromRegistry(context), context);
    }

    private static int addAffinity(Collection<ServerPlayer> players, Affinity affinity, CommandContext<CommandSourceStack> context) {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        for (ServerPlayer player : players) {
            AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(player, affinity, (float) amount, true);
            if (!MinecraftForge.EVENT_BUS.post(event)) {
                helper.setAffinityDepth(player, affinity, (float) (helper.getAffinityDepth(player, affinity) + amount));
                MinecraftForge.EVENT_BUS.post(new AffinityChangingEvent.Post(player, affinity, (float) amount, true));
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_ADD_SINGLE, affinity.getDisplayName(), players.iterator().next().getDisplayName(), amount), true);
        } else {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_ADD_MULTIPLE, affinity.getDisplayName(), players.size(), amount), true);
        }
        return players.size();
    }

    private static int setAffinitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setAffinity(List.of(context.getSource().getPlayerOrException()), getAffinityFromRegistry(context), context);
    }

    private static int setAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setAffinity(EntityArgument.getPlayers(context, "target"), getAffinityFromRegistry(context), context);
    }

    private static int setAffinity(Collection<ServerPlayer> players, Affinity affinity, CommandContext<CommandSourceStack> context) {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        var helper = ArsMagicaAPI.get().getAffinityHelper();
        for (ServerPlayer player : players) {
            AffinityChangingEvent.Pre event = new AffinityChangingEvent.Pre(player, affinity, (float) amount, true);
            if (!MinecraftForge.EVENT_BUS.post(event)) {
                helper.setAffinityDepth(player, affinity, (float) amount);
                MinecraftForge.EVENT_BUS.post(new AffinityChangingEvent.Post(player, affinity, (float) amount, true));
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_SET_SINGLE, affinity.getDisplayName(), players.iterator().next().getDisplayName(), amount), true);
        } else {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_SET_MULTIPLE, affinity.getDisplayName(), players.size(), amount), true);
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
            for (Affinity affinity : api.getAffinityRegistry()) {
                api.getAffinityHelper().setAffinityDepth(player, affinity, 0f);
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_RESET_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(Component.translatable(AFFINITY_RESET_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int getAffinitySelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getAffinity(context.getSource().getPlayerOrException(), getAffinityFromRegistry(context), context);
    }

    private static int getAffinity(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getAffinity(EntityArgument.getPlayer(context, "target"), getAffinityFromRegistry(context), context);
    }

    private static int getAffinity(ServerPlayer player, Affinity affinity, CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(Component.translatable(AFFINITY_GET, affinity.getDisplayName(), player.getDisplayName(), ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity)), true);
        return Command.SINGLE_SUCCESS;
    }

    private static Affinity getAffinityFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "affinity");
        Affinity affinity = ArsMagicaAPI.get().getAffinityRegistry().getValue(rl);
        if (affinity == null) throw ERROR_UNKNOWN_AFFINITY.create(rl);
        return affinity;
    }

    private static CompletableFuture<Suggestions> suggestAffinities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getAffinityRegistry().getKeys(), builder);
    }
}
