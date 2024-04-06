package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.server.AMPermissions;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_ADD_LEVELS_MULTIPLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_ADD_LEVELS_SINGLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_ADD_POINTS_MULTIPLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_ADD_POINTS_SINGLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_GET_LEVELS;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_GET_POINTS;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_SET_LEVELS_MULTIPLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_SET_LEVELS_SINGLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_SET_POINTS_MULTIPLE;
import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.CommandTranslations.MAGIC_XP_SET_POINTS_SINGLE;

public class MagicXpCommand {
    /**
     * Registers the command to the given builder.
     *
     * @param builder The builder to register the command to.
     */
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("magic_xp")
                .requires(p -> p.getEntity() instanceof ServerPlayer player ? PermissionAPI.getPermission(player, AMPermissions.CAN_EXECUTE_MAGIC_XP_COMMAND) : p.hasPermission(2))
                .then(Commands.literal("add")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(MagicXpCommand::addMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXpCommand::addMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXpCommand::addMagicXpLevels))))
                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                .executes(MagicXpCommand::addMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXpCommand::addMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpCommand::addMagicXpLevelsSelf))))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                        .executes(MagicXpCommand::setMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXpCommand::setMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXpCommand::setMagicXpLevels))))
                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                                .executes(MagicXpCommand::setMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXpCommand::setMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpCommand::setMagicXpLevelsSelf))))
                .then(Commands.literal("get")
                        .executes(MagicXpCommand::getMagicXpPointsSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(MagicXpCommand::getMagicXpPoints)
                                .then(Commands.literal("points")
                                        .executes(MagicXpCommand::getMagicXpPoints))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpCommand::getMagicXpLevels)))
                        .then(Commands.literal("points")
                                .executes(MagicXpCommand::getMagicXpPointsSelf))
                        .then(Commands.literal("levels")
                                .executes(MagicXpCommand::getMagicXpLevelsSelf))));
    }

    private static int addMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, double amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.add.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(Component.translatable(type == MagicXpCommandType.POINTS ? MAGIC_XP_ADD_POINTS_SINGLE : MAGIC_XP_ADD_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(Component.translatable(type == MagicXpCommandType.POINTS ? MAGIC_XP_ADD_POINTS_MULTIPLE : MAGIC_XP_ADD_LEVELS_MULTIPLE, amount, players.size()), true);
        }
        return players.size();
    }

    private static int setMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int setMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int setMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int setMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), DoubleArgumentType.getDouble(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int setMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, double amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.set.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(Component.translatable(type == MagicXpCommandType.POINTS ? MAGIC_XP_SET_POINTS_SINGLE : MAGIC_XP_SET_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(Component.translatable(type == MagicXpCommandType.POINTS ? MAGIC_XP_SET_POINTS_MULTIPLE : MAGIC_XP_SET_LEVELS_MULTIPLE, amount, players.size()), true);
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
        return getMagicXp(context.getSource(), EntityArgument.getPlayer(context, "target"), MagicXpCommandType.POINTS);
    }

    private static int getMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getMagicXp(context.getSource(), EntityArgument.getPlayer(context, "target"), MagicXpCommandType.LEVELS);
    }

    private static int getMagicXp(CommandSourceStack source, ServerPlayer player, MagicXpCommandType type) {
        Number i = type.get.apply(player);
        source.sendSuccess(Component.translatable(type == MagicXpCommandType.POINTS ? MAGIC_XP_GET_POINTS : MAGIC_XP_GET_LEVELS, player.getDisplayName(), i), true);
        return i.intValue();
    }

    private enum MagicXpCommandType  {
        POINTS((player, value) -> ArsMagicaAPI.get().getMagicHelper().awardXp(player, value.floatValue()),
               (player, value) -> ArsMagicaAPI.get().getMagicHelper().setXp(player, value.floatValue()),
               (player) -> ArsMagicaAPI.get().getMagicHelper().getXp(player)),
        LEVELS((player, value) -> ArsMagicaAPI.get().getMagicHelper().awardLevel(player, value.intValue()),
               (player, value) -> ArsMagicaAPI.get().getMagicHelper().setLevel(player, value.intValue()),
               (player) -> ArsMagicaAPI.get().getMagicHelper().getLevel(player));

        final BiConsumer<ServerPlayer, Double> add;
        final BiConsumer<ServerPlayer, Double> set;
        final Function<ServerPlayer, Number> get;

        MagicXpCommandType(BiConsumer<ServerPlayer, Double> add, BiConsumer<ServerPlayer, Double> set, Function<ServerPlayer, Number> get) {
            this.add = add;
            this.set = set;
            this.get = get;
        }
    }
}
