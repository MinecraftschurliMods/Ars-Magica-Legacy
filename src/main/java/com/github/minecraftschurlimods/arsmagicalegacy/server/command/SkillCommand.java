package com.github.minecraftschurlimods.arsmagicalegacy.server.command;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
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

import java.util.List;

public final class SkillCommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder, CommandBuildContext context) {
        builder.then(Commands.literal("skill")
            .then(Commands.literal("learn")
                .then(Commands.literal("*")
                    .executes(SkillCommand::learnAllSelf))
                .then(Commands.argument("skill", ResourceArgument.resource(context, AMRegistries.Keys.SKILL))
                    .executes(SkillCommand::learnSelf))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.literal("*")
                        .executes(SkillCommand::learnAll))
                    .then(Commands.argument("skill", ResourceArgument.resource(context, AMRegistries.Keys.SKILL))
                        .executes(SkillCommand::learn))))
            .then(Commands.literal("forget")
                .then(Commands.literal("*")
                    .executes(SkillCommand::forgetAllSelf))
                .then(Commands.argument("skill", ResourceArgument.resource(context, AMRegistries.Keys.SKILL))
                    .executes(SkillCommand::forgetSelf))
                .then(Commands.argument("target", EntityArgument.players())
                    .then(Commands.literal("*")
                        .executes(SkillCommand::forgetAll))
                    .then(Commands.argument("skill", ResourceArgument.resource(context, AMRegistries.Keys.SKILL))
                        .executes(SkillCommand::forget))))
            .then(Commands.literal("list")
                .executes(SkillCommand::listKnownSelf)
                .then(Commands.literal("all")
                    .executes(SkillCommand::listAll))
                .then(Commands.literal("known")
                    .executes(SkillCommand::listKnownSelf))
                .then(Commands.literal("unknown")
                    .executes(SkillCommand::listUnknownSelf))
                .then(Commands.argument("target", EntityArgument.player())
                    .executes(SkillCommand::listKnown)
                    .then(Commands.literal("known")
                        .executes(SkillCommand::listKnown))
                    .then(Commands.literal("unknown")
                        .executes(SkillCommand::listUnknown)))));
    }

    private static int learnAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.runCommandSelf(context, ArsMagicaApi.magicHelper()::learnAll, name -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_ALL_SINGLE_KEY, name));
    }

    private static int learnSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Skill> holder = ResourceArgument.getResource(context, "skill", AMRegistries.Keys.SKILL);
        return AMUtil.runCommandSelf(context, player -> ArsMagicaApi.magicHelper().learn(player, holder), name -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_SINGLE_KEY, Skill.getName(holder), name));
    }

    private static int learnAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.runCommand(context, ArsMagicaApi.magicHelper()::learnAll, name -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_ALL_SINGLE_KEY, name), size -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_ALL_MULTIPLE_KEY, size));
    }

    private static int learn(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Skill> holder = ResourceArgument.getResource(context, "skill", AMRegistries.Keys.SKILL);
        return AMUtil.runCommand(context, player -> ArsMagicaApi.magicHelper().learn(player, holder), name -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_SINGLE_KEY, Skill.getName(holder), name), size -> Component.translatable(AMTranslations.COMMAND_SKILL_LEARN_MULTIPLE_KEY, Skill.getName(holder), size));
    }

    private static int forgetAllSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.runCommandSelf(context, ArsMagicaApi.magicHelper()::forgetAll, name -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_ALL_SINGLE_KEY, name));
    }

    private static int forgetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Skill> holder = ResourceArgument.getResource(context, "skill", AMRegistries.Keys.SKILL);
        return AMUtil.runCommandSelf(context, player -> ArsMagicaApi.magicHelper().forget(player, holder), name -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_SINGLE_KEY, Skill.getName(holder), name));
    }

    private static int forgetAll(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.runCommand(context, ArsMagicaApi.magicHelper()::forgetAll, name -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_ALL_SINGLE_KEY, name), size -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_ALL_MULTIPLE_KEY, size));
    }

    private static int forget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Holder<Skill> holder = ResourceArgument.getResource(context, "skill", AMRegistries.Keys.SKILL);
        return AMUtil.runCommand(context, player -> ArsMagicaApi.magicHelper().forget(player, holder), name -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_SINGLE_KEY, Skill.getName(holder), name), size -> Component.translatable(AMTranslations.COMMAND_SKILL_FORGET_MULTIPLE_KEY, Skill.getName(holder), size));
    }

    private static int listAll(CommandContext<CommandSourceStack> context) {
        List<? extends Holder<Skill>> holders = AMRegistries.skills(context.getSource().registryAccess()).listElements().toList();
        context.getSource().sendSuccess(() -> Component.translatable(AMTranslations.COMMAND_SKILL_LIST_ALL_KEY, skillsComponent(holders)), true);
        return holders.size();
    }

    private static int listKnownSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommandSelf(context, ArsMagicaApi.magicHelper()::getKnown, List::size, (player, list) -> Component.translatable(AMTranslations.COMMAND_SKILL_LIST_KNOWN_KEY, player, skillsComponent(list)));
    }

    private static int listUnknownSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommandSelf(context, ArsMagicaApi.magicHelper()::getUnknown, List::size, (player, list) -> Component.translatable(AMTranslations.COMMAND_SKILL_LIST_UNKNOWN_KEY, player, skillsComponent(list)));
    }

    private static int listKnown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommand(context, ArsMagicaApi.magicHelper()::getKnown, List::size, (player, list) -> Component.translatable(AMTranslations.COMMAND_SKILL_LIST_KNOWN_KEY, player, skillsComponent(list)));
    }

    private static int listUnknown(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return AMUtil.getCommand(context, ArsMagicaApi.magicHelper()::getUnknown, List::size, (player, list) -> Component.translatable(AMTranslations.COMMAND_SKILL_LIST_UNKNOWN_KEY, player, skillsComponent(list)));
    }

    private static Component skillsComponent(List<? extends Holder<Skill>> list) {
        return list.stream()
            .map(Skill::getName)
            .reduce((a, b) -> a.copy().append(AMTranslations.COMMAND_SKILL_LIST_SEPARATOR).append(b))
            .orElse(Component.literal(""));
    }
}
