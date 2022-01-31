package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public class SkillPointSubcommand {
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILL_POINTS = SkillPointSubcommand::suggestSkillPoints;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL_POINT = new DynamicCommandExceptionType(message -> new TranslatableComponent(SKILL_POINT_UNKNOWN, message));

    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("skill_point")
                .then(Commands.literal("add")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(SkillPointSubcommand::addOneSkillPoint)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(SkillPointSubcommand::addSkillPoint))))
                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILL_POINTS)
                                .executes(SkillPointSubcommand::addOneSkillPointSelf)
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(SkillPointSubcommand::addSkillPointSelf))))
                .then(Commands.literal("consume")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(SkillPointSubcommand::consumeOneSkillPoint)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(SkillPointSubcommand::consumeSkillPoint))))
                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILL_POINTS)
                                .executes(SkillPointSubcommand::consumeOneSkillPointSelf)
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(SkillPointSubcommand::consumeSkillPointSelf))))
                .then(Commands.literal("set")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(SkillPointSubcommand::setSkillPoint))))
                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILL_POINTS)
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(SkillPointSubcommand::setSkillPointSelf))))
                .then(Commands.literal("get")
                        .then(Commands.argument("target", EntityArgument.players())
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(SkillPointSubcommand::getSkillPoint)))
                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                .suggests(SUGGEST_SKILL_POINTS)
                                .executes(SkillPointSubcommand::getSkillPointSelf))));
    }

    private static int addOneSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context, 1);
    }

    private static int addOneSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context, 1);
    }

    private static int addSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context, IntegerArgumentType.getInteger(context, "amount"));
    }

    private static int addSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return addSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context, IntegerArgumentType.getInteger(context, "amount"));
    }

    private static int addSkillPoint(Collection<ServerPlayer> players, ISkillPoint skillPoint, CommandContext<CommandSourceStack> context, int amount) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().addSkillPoint(player, skillPoint, amount);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_ADD_SINGLE, amount, skillPoint.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_ADD_MULTIPLE, amount, skillPoint.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static int consumeOneSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return consumeSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context, 1);
    }

    private static int consumeOneSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return consumeSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context, 1);
    }

    private static int consumeSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return consumeSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context, IntegerArgumentType.getInteger(context, "amount"));
    }

    private static int consumeSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return consumeSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context, IntegerArgumentType.getInteger(context, "amount"));
    }

    private static int consumeSkillPoint(Collection<ServerPlayer> players, ISkillPoint skillPoint, CommandContext<CommandSourceStack> context, int amount) {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        for (ServerPlayer player : players) {
            helper.consumeSkillPoint(player, skillPoint, Math.min(amount, helper.getSkillPoint(player, skillPoint)));
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_CONSUME_SINGLE, amount, skillPoint.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_CONSUME_MULTIPLE, amount, skillPoint.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static int setSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context);
    }

    private static int setSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return setSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context);
    }

    private static int setSkillPoint(Collection<ServerPlayer> players, ISkillPoint skillPoint, CommandContext<CommandSourceStack> context) {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        int amount = IntegerArgumentType.getInteger(context, "amount");
        for (ServerPlayer player : players) {
            if (helper.getSkillPoint(player, skillPoint) > amount) {
                helper.consumeSkillPoint(player, skillPoint, helper.getSkillPoint(player, skillPoint) - amount);
            } else if (helper.getSkillPoint(player, skillPoint) < amount) {
                helper.addSkillPoint(player, skillPoint, amount - helper.getSkillPoint(player, skillPoint));
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_SET_SINGLE, players.iterator().next().getDisplayName(), amount, skillPoint.getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_SET_MULTIPLE, players.size(), amount, skillPoint.getDisplayName()), true);
        }
        return players.size();
    }

    private static int getSkillPointSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getSkillPoint(List.of(context.getSource().getPlayerOrException()), getSkillPointFromRegistry(context), context);
    }

    private static int getSkillPoint(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return getSkillPoint(EntityArgument.getPlayers(context, "target"), getSkillPointFromRegistry(context), context);
    }

    private static int getSkillPoint(Collection<ServerPlayer> players, ISkillPoint skillPoint, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_POINT_GET, player.getDisplayName(), ArsMagicaAPI.get().getSkillHelper().getSkillPoint(player, skillPoint), skillPoint.getDisplayName()), true);
        }
        return players.size();
    }

    private static ISkillPoint getSkillPointFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "skill_point");
        ISkillPoint skillPoint = ArsMagicaAPI.get().getSkillPointRegistry().getValue(rl);
        if (skillPoint == null) throw ERROR_UNKNOWN_SKILL_POINT.create(rl);
        return skillPoint;
    }

    private static CompletableFuture<Suggestions> suggestSkillPoints(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillPointRegistry().getKeys(), builder);
    }
}
