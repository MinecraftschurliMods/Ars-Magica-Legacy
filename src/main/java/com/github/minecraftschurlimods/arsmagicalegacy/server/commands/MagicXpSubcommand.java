package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.ArsMagicaLegacyCommandTranslations.*;

public class MagicXpSubcommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("magic_xp")
                .then(Commands.literal("add")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(MagicXpSubcommand::addMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXpSubcommand::addMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXpSubcommand::addMagicXpLevels))))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(MagicXpSubcommand::addMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXpSubcommand::addMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpSubcommand::addMagicXpLevelsSelf))))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(MagicXpSubcommand::setMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(MagicXpSubcommand::setMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(MagicXpSubcommand::setMagicXpLevels))))
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(MagicXpSubcommand::setMagicXpPointsSelf)
                                .then(Commands.literal("points")
                                        .executes(MagicXpSubcommand::setMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpSubcommand::setMagicXpLevelsSelf))))
                .then(Commands.literal("get")
                        .executes(MagicXpSubcommand::getMagicXpPointsSelf)
                        .then(Commands.argument("target", EntityArgument.player())
                                .executes(MagicXpSubcommand::getMagicXpPoints)
                                .then(Commands.literal("points")
                                        .executes(MagicXpSubcommand::getMagicXpPoints))
                                .then(Commands.literal("levels")
                                        .executes(MagicXpSubcommand::getMagicXpLevels)))
                        .then(Commands.literal("points")
                                .executes(MagicXpSubcommand::getMagicXpPointsSelf))
                        .then(Commands.literal("levels")
                                .executes(MagicXpSubcommand::getMagicXpLevelsSelf))));
    }

    private static int addMagicXpPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), List.of(context.getSource().getPlayerOrException()), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXpPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int addMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int addMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, int amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.add.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? MAGIC_XP_ADD_POINTS_SINGLE : MAGIC_XP_ADD_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? MAGIC_XP_ADD_POINTS_MULTIPLE : MAGIC_XP_ADD_LEVELS_MULTIPLE, amount, players.size()), true);
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
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.POINTS);
    }

    private static int setMagicXpLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setMagicXp(context.getSource(), EntityArgument.getPlayers(context, "target"), IntegerArgumentType.getInteger(context, "amount"), MagicXpCommandType.LEVELS);
    }

    private static int setMagicXp(CommandSourceStack source, Collection<ServerPlayer> players, int amount, MagicXpCommandType type) {
        for (ServerPlayer sp : players) {
            type.set.accept(sp, amount);
        }
        if (players.size() == 1) {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? MAGIC_XP_SET_POINTS_SINGLE : MAGIC_XP_SET_LEVELS_SINGLE, amount, players.iterator().next().getDisplayName()), true);
        } else {
            source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? MAGIC_XP_SET_POINTS_MULTIPLE : MAGIC_XP_SET_LEVELS_MULTIPLE, amount, players.size()), true);
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
        int i = (int) type.get.apply(player);
        source.sendSuccess(new TranslatableComponent(type == MagicXpCommandType.POINTS ? MAGIC_XP_GET_POINTS : MAGIC_XP_GET_LEVELS, player.getDisplayName(), i), true);
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
