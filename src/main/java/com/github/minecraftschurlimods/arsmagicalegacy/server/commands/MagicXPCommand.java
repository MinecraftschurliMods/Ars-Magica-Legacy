package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ToFloatFunction;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public class MagicXPCommand {
    public static final String ADD_LEVELS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.add.levels.multiple.success";
    public static final String ADD_LEVELS_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.add.levels.single.success";
    public static final String ADD_POINTS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.add.points.multiple.success";
    public static final String ADD_POINTS_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.add.points.single.success";
    public static final String GET_LEVELS          = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.get.levels.success";
    public static final String GET_POINTS          = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.get.points.success";
    public static final String SET_LEVELS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.set.levels.multiple.success";
    public static final String SET_LEVELS_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.set.levels.single.success";
    public static final String SET_POINTS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.set.points.multiple.success";
    public static final String SET_POINTS_SINGLE   = "commands." + ArsMagicaAPI.MOD_ID + ".magicxp.set.points.single.success";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("magicxp")
                .requires(p -> p.hasPermission(2))
                .then(Commands.literal("add")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(MagicXPCommand::addMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXPCommand::addMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXPCommand::addMagicXpLevels))))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(MagicXPCommand::addMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXPCommand::addMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXPCommand::addMagicXpLevelsSelf))))
                .then(Commands.literal("set")
                        .then(Commands.argument("targets", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(MagicXPCommand::setMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXPCommand::setMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXPCommand::setMagicXpLevels))))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(MagicXPCommand::setMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXPCommand::setMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXPCommand::setMagicXpLevelsSelf))))
                .then(Commands.literal("get")
                        .executes(MagicXPCommand::getMagicXpPointsSelf)
                        .then(Commands.argument("targets", EntityArgument.player())
                                .then(Commands.literal("points")
                                        .executes(MagicXPCommand::getMagicXpPoints))
                                .then(Commands.literal("levels")
                                        .executes(MagicXPCommand::getMagicXpLevels)))
                        .then(Commands.literal("points")
                                .executes(MagicXPCommand::getMagicXpPointsSelf))
                        .then(Commands.literal("levels")
                                .executes(MagicXPCommand::getMagicXpLevelsSelf)))
        );
    }

    private static int addMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, int amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.add.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? ADD_POINTS_SINGLE : ADD_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? ADD_POINTS_MULTIPLE : ADD_LEVELS_MULTIPLE, amount, players.size()), true);
        }
        return players.size();
    }

    private static int setMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int setMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int setMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int setMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "targets"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int setMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, int amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.set.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? SET_POINTS_SINGLE : SET_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? SET_POINTS_MULTIPLE : SET_LEVELS_MULTIPLE, amount, players.size()), true);
        }
        return players.size();
    }

    private static int getMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getMagicXp(context.getSource(), context.getSource().getPlayerOrException(), MagicXpCommandType.POINTS);
    }

    private static int getMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getMagicXp(context.getSource(), context.getSource().getPlayerOrException(), MagicXpCommandType.LEVELS);
    }

    private static int getMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getMagicXp(context.getSource(), EntityArgument.getPlayer(context, "targets"), MagicXpCommandType.POINTS);
    }

    private static int getMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getMagicXp(context.getSource(), EntityArgument.getPlayer(context, "targets"), MagicXpCommandType.LEVELS);
    }

    private static int getMagicXp(CommandSourceStack source, ServerPlayer player, MagicXpCommandType type) {
        int i = (int) type.get.apply(player);
        source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? GET_POINTS : GET_LEVELS, player.getDisplayName(), i), true);
        return i;
    }

    private enum MagicXpCommandType {
        POINTS((player, value) -> ArsMagicaAPI.get().getMagicHelper().awardXp(player, value), (player, value) -> ArsMagicaAPI.get().getMagicHelper().setXp(player, value), player -> ArsMagicaAPI.get().getMagicHelper().getXp(player)),
        LEVELS((player, value) -> ArsMagicaAPI.get().getMagicHelper().awardLevel(player, value), (player, value) -> ArsMagicaAPI.get().getMagicHelper().setLevel(player, value), player -> ArsMagicaAPI.get().getMagicHelper().getLevel(player));

        final BiConsumer<ServerPlayer, Integer> add;
        final BiConsumer<ServerPlayer, Integer> set;
        final ToFloatFunction<ServerPlayer> get;

        MagicXpCommandType(BiConsumer<ServerPlayer, Integer> add, BiConsumer<ServerPlayer, Integer> set, ToFloatFunction<ServerPlayer> get) {
            this.add = add;
            this.set = set;
            this.get = get;
        }
    }
}
