package com.github.minecraftschurlimods.arsmagicalegacy.server.command;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.apache.commons.lang3.function.TriConsumer;
import org.apache.commons.lang3.function.TriFunction;

public final class AffinityCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder, CommandBuildContext context) {
        builder.then(Commands.literal("affinity")
            .then(Commands.literal("add")
                .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg(-1, 1))
                        .executes(AffinityCommand::addSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(-1, 1))
                            .executes(AffinityCommand::add)))))
            .then(Commands.literal("set")
                .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                    .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0, 1))
                        .executes(AffinityCommand::setSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg(0, 1))
                            .executes(AffinityCommand::set)))))
            .then(Commands.literal("get")
                .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                    .executes(AffinityCommand::getSelf))
                .then(Commands.argument("target", EntityArgument.player())
                    .then(Commands.argument("affinity", ResourceArgument.resource(context, AMRegistries.Keys.AFFINITY))
                        .executes(AffinityCommand::get)))));
    }

    private static int addSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, DoubleArgumentType.getDouble(context, "amount"), (player, affinity, depth) -> ArsMagicaApi.magicHelper().addAffinityDepth(player, affinity, depth, true, true), (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_ADD_SINGLE_KEY, amount, name, Affinity.getName(holder)));
    }

    private static int add(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runMultiple(context, DoubleArgumentType.getDouble(context, "amount"), (player, affinity, depth) -> ArsMagicaApi.magicHelper().addAffinityDepth(player, affinity, depth, true, true), (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_ADD_SINGLE_KEY, amount, name, Affinity.getName(holder)), (size, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_ADD_MULTIPLE_KEY, amount, size, Affinity.getName(holder)));
    }

    private static int setSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, DoubleArgumentType.getDouble(context, "amount"), (player, affinity, depth) -> ArsMagicaApi.magicHelper().setAffinityDepth(player, affinity, depth, true, true), (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_SET_SINGLE_KEY, amount, name, Affinity.getName(holder)));
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runMultiple(context, DoubleArgumentType.getDouble(context, "amount"), (player, affinity, depth) -> ArsMagicaApi.magicHelper().setAffinityDepth(player, affinity, depth, true, true), (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_SET_SINGLE_KEY, amount, name, Affinity.getName(holder)), (size, holder, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_SET_MULTIPLE_KEY, amount, size, Affinity.getName(holder)));
    }

    private static int getSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Affinity> holder = ResourceArgument.getResource(context, "affinity", AMRegistries.Keys.AFFINITY);
        return AMUtil.getCommandSelf(context, sp -> ArsMagicaApi.magicHelper().getAffinityDepth(sp, holder), Double::intValue, (sp, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_GET_KEY, Affinity.getName(holder), sp, amount));
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Affinity> holder = ResourceArgument.getResource(context, "affinity", AMRegistries.Keys.AFFINITY);
        return AMUtil.getCommand(context, sp -> ArsMagicaApi.magicHelper().getAffinityDepth(sp, holder), Double::intValue, (sp, amount) -> Component.translatable(AMTranslations.COMMAND_AFFINITY_GET_KEY, Affinity.getName(holder), sp, amount));
    }

    private static int runSelf(CommandContext<CommandSourceStack> context, double amount, TriConsumer<ServerPlayer, Holder<Affinity>, Double> consumer, TriFunction<Component, Holder<Affinity>, Double, Component> messageFactory) throws CommandSyntaxException {
        Holder<Affinity> holder = ResourceArgument.getResource(context, "affinity", AMRegistries.Keys.AFFINITY);
        return AMUtil.runCommandSelf(context, sp -> consumer.accept(sp, holder, amount), sp -> messageFactory.apply(sp, holder, amount));
    }

    private static int runMultiple(CommandContext<CommandSourceStack> context, double amount, TriConsumer<ServerPlayer, Holder<Affinity>, Double> consumer, TriFunction<Component, Holder<Affinity>, Double, Component> singleMessageFactory, TriFunction<Integer, Holder<Affinity>, Double, Component> multipleMessageFactory) throws CommandSyntaxException {
        Holder<Affinity> holder = ResourceArgument.getResource(context, "affinity", AMRegistries.Keys.AFFINITY);
        return AMUtil.runCommand(context, sp -> consumer.accept(sp, holder, amount), name -> singleMessageFactory.apply(name, holder, amount), size -> multipleMessageFactory.apply(size, holder, amount));
    }
}
