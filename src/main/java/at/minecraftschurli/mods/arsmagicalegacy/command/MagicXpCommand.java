package at.minecraftschurli.mods.arsmagicalegacy.command;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public final class MagicXpCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("magic_xp")
            .then(Commands.literal("add")
                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                    .executes(MagicXpCommand::addPointsSelf)
                    .then(Commands.literal("points")
                        .executes(MagicXpCommand::addPointsSelf))
                    .then(Commands.literal("levels")
                        .executes(MagicXpCommand::addLevelsSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                        .executes(MagicXpCommand::addPoints)
                        .then(Commands.literal("points")
                            .executes(MagicXpCommand::addPoints))
                        .then(Commands.literal("levels")
                            .executes(MagicXpCommand::addLevels)))))
            .then(Commands.literal("set")
                .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                    .executes(MagicXpCommand::setPointsSelf)
                    .then(Commands.literal("points")
                        .executes(MagicXpCommand::setPointsSelf))
                    .then(Commands.literal("levels")
                        .executes(MagicXpCommand::setLevelsSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0))
                        .executes(MagicXpCommand::setPoints)
                        .then(Commands.literal("points")
                            .executes(MagicXpCommand::setPoints))
                        .then(Commands.literal("levels")
                            .executes(MagicXpCommand::setLevels)))))
            .then(Commands.literal("get")
                .executes(MagicXpCommand::getPointsSelf)
                .then(Commands.literal("points")
                    .executes(MagicXpCommand::getPointsSelf))
                .then(Commands.literal("levels")
                    .executes(MagicXpCommand::getLevelsSelf))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(MagicXpCommand::getPoints)
                    .then(Commands.literal("points")
                        .executes(MagicXpCommand::getPoints))
                    .then(Commands.literal("levels")
                        .executes(MagicXpCommand::getLevels)))));
    }


    private static int addPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, ArsMagicaApi.magicHelper()::addXp, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_POINTS_SINGLE_KEY, amount, name));
    }

    private static int addLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, (player, amount) -> ArsMagicaApi.magicHelper().addLevel(player, amount.intValue()), (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_LEVELS_SINGLE_KEY, amount, name));
    }

    private static int addPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return run(context, ArsMagicaApi.magicHelper()::addXp, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_POINTS_SINGLE_KEY, amount, name), (size, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_POINTS_MULTIPLE_KEY, amount, size));
    }

    private static int addLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return run(context, (player, amount) -> ArsMagicaApi.magicHelper().addLevel(player, amount.intValue()), (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_LEVELS_SINGLE_KEY, amount, name), (size, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_ADD_LEVELS_MULTIPLE_KEY, amount, size));
    }

    private static int setPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, ArsMagicaApi.magicHelper()::setXp, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_POINTS_SINGLE_KEY, amount, name));
    }

    private static int setLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, (player, amount) -> ArsMagicaApi.magicHelper().setLevel(player, amount.intValue()), (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_LEVELS_SINGLE_KEY, amount, name));
    }

    private static int setPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return run(context, ArsMagicaApi.magicHelper()::setXp, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_POINTS_SINGLE_KEY, amount, name), (size, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_POINTS_MULTIPLE_KEY, amount, size));
    }

    private static int setLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return run(context, (player, amount) -> ArsMagicaApi.magicHelper().setLevel(player, amount.intValue()), (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_LEVELS_SINGLE_KEY, amount, name), (size, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_SET_LEVELS_MULTIPLE_KEY, amount, size));
    }

    private static int getPointsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommandSelf(context, ArsMagicaApi.magicHelper()::getXp, Double::intValue, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_GET_POINTS_KEY, name, amount));
    }

    private static int getLevelsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommandSelf(context, ArsMagicaApi.magicHelper()::getLevel, Integer::intValue, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_GET_LEVELS_KEY, name, amount));
    }

    private static int getPoints(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommand(context, ArsMagicaApi.magicHelper()::getXp, Double::intValue, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_GET_POINTS_KEY, name, amount));
    }

    private static int getLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommand(context, ArsMagicaApi.magicHelper()::getLevel, Integer::intValue, (name, amount) -> Component.translatable(AMTranslations.COMMAND_MAGIC_XP_GET_LEVELS_KEY, name, amount));
    }

    private static int runSelf(CommandContext<CommandSourceStack> context, BiConsumer<ServerPlayer, Double> consumer, BiFunction<Component, Double, Component> messageFactory) throws CommandSyntaxException {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        return AMUtil.runCommandSelf(context, player -> consumer.accept(player, amount), name -> messageFactory.apply(name, amount));
    }

    private static int run(CommandContext<CommandSourceStack> context, BiConsumer<ServerPlayer, Double> consumer, BiFunction<Component, Double, Component> singleMessageFactory, BiFunction<Integer, Double, Component> multipleMessageFactory) throws CommandSyntaxException {
        double amount = DoubleArgumentType.getDouble(context, "amount");
        return AMUtil.runCommand(context, player -> consumer.accept(player, amount), name -> singleMessageFactory.apply(name, amount), size -> multipleMessageFactory.apply(size, amount));
    }
}
