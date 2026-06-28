package com.github.minecraftschurlimods.arsmagicalegacy.server.command;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public final class SkillPointCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder, CommandBuildContext context) {
        builder.then(Commands.literal("skill_point")
            .then(Commands.literal("add")
                .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                    .executes(SkillPointCommand::addOneSelf)
                    .then(Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(SkillPointCommand::addSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                        .executes(SkillPointCommand::addOne)
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                            .executes(SkillPointCommand::add)))))
            .then(Commands.literal("set")
                .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                    .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(SkillPointCommand::setSelf)))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                            .executes(SkillPointCommand::set)))))
            .then(Commands.literal("get")
                .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                    .executes(SkillPointCommand::getSelf))
                .then(Commands.argument("target", EntityArgument.player())
                    .then(Commands.argument("skill_point", ResourceArgument.resource(context, AMRegistries.Keys.SKILL_POINT))
                        .executes(SkillPointCommand::get)))));
    }

    private static int addOneSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, 1, ArsMagicaApi.magicHelper()::addSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_SINGLE_KEY, amount, SkillPoint.getName(holder), name));
    }

    private static int addSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, IntegerArgumentType.getInteger(context, "amount"), ArsMagicaApi.magicHelper()::addSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_SINGLE_KEY, amount, SkillPoint.getName(holder), name));
    }

    private static int addOne(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runMultiple(context, 1, ArsMagicaApi.magicHelper()::addSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_SINGLE_KEY, amount, SkillPoint.getName(holder), name), (size, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_MULTIPLE_KEY, amount, SkillPoint.getName(holder), size));
    }

    private static int add(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runMultiple(context, IntegerArgumentType.getInteger(context, "amount"), ArsMagicaApi.magicHelper()::addSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_SINGLE_KEY, amount, SkillPoint.getName(holder), name), (size, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_ADD_MULTIPLE_KEY, amount, SkillPoint.getName(holder), size));
    }

    private static int setSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runSelf(context, IntegerArgumentType.getInteger(context, "amount"), ArsMagicaApi.magicHelper()::setSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_SET_SINGLE_KEY, amount, SkillPoint.getName(holder), name));
    }

    private static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return runMultiple(context, IntegerArgumentType.getInteger(context, "amount"), ArsMagicaApi.magicHelper()::setSkillPoint, (name, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_SET_SINGLE_KEY, amount, SkillPoint.getName(holder), name), (size, holder, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_SET_MULTIPLE_KEY, amount, SkillPoint.getName(holder), size));
    }

    private static int getSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<SkillPoint> holder = ResourceArgument.getResource(context, "skill_point", AMRegistries.Keys.SKILL_POINT);
        return AMUtil.getCommandSelf(context, sp -> ArsMagicaApi.magicHelper().getSkillPoint(sp, holder), Integer::intValue, (sp, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_GET_KEY, sp, amount, SkillPoint.getName(holder)));
    }

    private static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<SkillPoint> holder = ResourceArgument.getResource(context, "skill_point", AMRegistries.Keys.SKILL_POINT);
        return AMUtil.getCommand(context, sp -> ArsMagicaApi.magicHelper().getSkillPoint(sp, holder), Integer::intValue, (sp, amount) -> Component.translatable(AMTranslations.COMMAND_SKILL_POINT_GET_KEY, sp, amount, SkillPoint.getName(holder)));
    }

    private static int runSelf(CommandContext<CommandSourceStack> context, int amount, TriConsumer<ServerPlayer, Holder<SkillPoint>, Integer> consumer, TriFunction<Component, Holder<SkillPoint>, Integer, Component> messageFactory) throws CommandSyntaxException {
        Holder<SkillPoint> holder = ResourceArgument.getResource(context, "skill_point", AMRegistries.Keys.SKILL_POINT);
        return AMUtil.runCommandSelf(context, sp -> consumer.accept(sp, holder, amount), sp -> messageFactory.apply(sp, holder, amount));
    }

    private static int runMultiple(CommandContext<CommandSourceStack> context, int amount, TriConsumer<ServerPlayer, Holder<SkillPoint>, Integer> consumer, TriFunction<Component, Holder<SkillPoint>, Integer, Component> singleMessageFactory, TriFunction<Integer, Holder<SkillPoint>, Integer, Component> multipleMessageFactory) throws CommandSyntaxException {
        Holder<SkillPoint> holder = ResourceArgument.getResource(context, "skill_point", AMRegistries.Keys.SKILL_POINT);
        return AMUtil.runCommand(context, sp -> consumer.accept(sp, holder, amount), name -> singleMessageFactory.apply(name, holder, amount), size -> multipleMessageFactory.apply(size, holder, amount));
    }
}
