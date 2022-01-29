package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ToFloatFunction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class ArsMagicaLegacyCommand {
    public static final String AFFINITY_GET = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.get.success";
    public static final String AFFINITY_RESET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.reset.multiple.success";
    public static final String AFFINITY_RESET_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.reset.single.success";
    public static final String AFFINITY_SET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.set.multiple.success";
    public static final String AFFINITY_SET_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.set.single.success";
    public static final String AFFINITY_UNKNOWN = "commands." + ArsMagicaAPI.MOD_ID + ".affinity.unknown";
    public static final String MAGIC_XP_ADD_LEVELS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.add.levels.multiple.success";
    public static final String MAGIC_XP_ADD_LEVELS_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.add.levels.single.success";
    public static final String MAGIC_XP_ADD_POINTS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.add.points.multiple.success";
    public static final String MAGIC_XP_ADD_POINTS_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.add.points.single.success";
    public static final String MAGIC_XP_GET_LEVELS = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.get.levels.success";
    public static final String MAGIC_XP_GET_POINTS = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.get.points.success";
    public static final String MAGIC_XP_SET_LEVELS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.set.levels.multiple.success";
    public static final String MAGIC_XP_SET_LEVELS_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.set.levels.single.success";
    public static final String MAGIC_XP_SET_POINTS_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.set.points.multiple.success";
    public static final String MAGIC_XP_SET_POINTS_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".magic_xp.set.points.single.success";
    public static final String RESET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".reset.multiple.success";
    public static final String RESET_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".reset.single.success";
    public static final String SKILL_ALREADY_KNOWN = "commands." + ArsMagicaAPI.MOD_ID + ".skill.already_known";
    public static final String SKILL_FORGET_ALL_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget_all.multiple.success";
    public static final String SKILL_FORGET_ALL_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget_all.single.success";
    public static final String SKILL_FORGET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget.multiple.success";
    public static final String SKILL_FORGET_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.forget.single.success";
    public static final String SKILL_LEARN_ALL_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn_all.multiple.success";
    public static final String SKILL_LEARN_ALL_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn_all.single.success";
    public static final String SKILL_LEARN_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn.multiple.success";
    public static final String SKILL_LEARN_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill.learn.single.success";
    public static final String SKILL_NOT_YET_KNOWN = "commands." + ArsMagicaAPI.MOD_ID + ".skill.not_yet_known";
    public static final String SKILL_POINT_ADD_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.add.multiple.success";
    public static final String SKILL_POINT_ADD_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.add.single.success";
    public static final String SKILL_POINT_CONSUME_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.consume.multiple.success";
    public static final String SKILL_POINT_CONSUME_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.consume.single.success";
    public static final String SKILL_POINT_GET = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.get.success";
    public static final String SKILL_POINT_SET_MULTIPLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.set.multiple.success";
    public static final String SKILL_POINT_SET_SINGLE = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.set.single.success";
    public static final String SKILL_POINT_UNKNOWN = "commands." + ArsMagicaAPI.MOD_ID + ".skill_point.unknown";
    public static final String SKILL_UNKNOWN = "commands." + ArsMagicaAPI.MOD_ID + ".skill.unknown";
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_AFFINITIES = ArsMagicaLegacyCommand::suggestAffinities;
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILLS = ArsMagicaLegacyCommand::suggestSkills;
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_SKILL_POINTS = ArsMagicaLegacyCommand::suggestSkillPoints;
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_AFFINITY = new DynamicCommandExceptionType(message -> new TranslatableComponent(AFFINITY_UNKNOWN, message));
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL = new DynamicCommandExceptionType(message -> new TranslatableComponent(SKILL_UNKNOWN, message));
    private static final DynamicCommandExceptionType ERROR_UNKNOWN_SKILL_POINT = new DynamicCommandExceptionType(message -> new TranslatableComponent(SKILL_POINT_UNKNOWN, message));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(ArsMagicaAPI.MOD_ID)
                .requires(p -> p.hasPermission(2))
                .then(Commands.literal("affinity")
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_AFFINITIES)
                                                .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                        .executes(ArsMagicaLegacyCommand::setAffinity))))
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .then(Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(ArsMagicaLegacyCommand::setAffinitySelf))))
                        .then(Commands.literal("reset")
                                .executes(ArsMagicaLegacyCommand::resetAffinitiesSelf)
                                .then(Commands.argument("target", EntityArgument.players())
                                        .executes(ArsMagicaLegacyCommand::resetAffinities)))
                        .then(Commands.literal("get")
                                .then(Commands.argument("target", EntityArgument.player())
                                        .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_AFFINITIES)
                                                .executes(ArsMagicaLegacyCommand::getAffinity)))
                                .then(Commands.argument("affinity", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_AFFINITIES)
                                        .executes(ArsMagicaLegacyCommand::getAffinitySelf))))
                .then(Commands.literal("magic_xp")
                        .then(Commands.literal("add")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                .executes(ArsMagicaLegacyCommand::addMagicXpPoints)
                                                .then(Commands.literal("points")
                                                        .executes(ArsMagicaLegacyCommand::addMagicXpPoints))
                                                .then(Commands.literal("levels")
                                                        .executes(ArsMagicaLegacyCommand::addMagicXpLevels))))
                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                        .executes(ArsMagicaLegacyCommand::addMagicXpPointsSelf)
                                        .then(Commands.literal("points")
                                                .executes(ArsMagicaLegacyCommand::addMagicXpPointsSelf))
                                        .then(Commands.literal("levels")
                                                .executes(ArsMagicaLegacyCommand::addMagicXpLevelsSelf))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ArsMagicaLegacyCommand::setMagicXpPoints)
                                                .then(Commands.literal("points")
                                                        .executes(ArsMagicaLegacyCommand::setMagicXpPoints))
                                                .then(Commands.literal("levels")
                                                        .executes(ArsMagicaLegacyCommand::setMagicXpLevels))))
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ArsMagicaLegacyCommand::setMagicXpPointsSelf)
                                        .then(Commands.literal("points")
                                                .executes(ArsMagicaLegacyCommand::setMagicXpPointsSelf))
                                        .then(Commands.literal("levels")
                                                .executes(ArsMagicaLegacyCommand::setMagicXpLevelsSelf))))
                        .then(Commands.literal("get")
                                .executes(ArsMagicaLegacyCommand::getMagicXpPointsSelf)
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(ArsMagicaLegacyCommand::getMagicXpPoints)
                                        .then(Commands.literal("points")
                                                .executes(ArsMagicaLegacyCommand::getMagicXpPoints))
                                        .then(Commands.literal("levels")
                                                .executes(ArsMagicaLegacyCommand::getMagicXpLevels)))
                                .then(Commands.literal("points")
                                        .executes(ArsMagicaLegacyCommand::getMagicXpPointsSelf))
                                .then(Commands.literal("levels")
                                        .executes(ArsMagicaLegacyCommand::getMagicXpLevelsSelf))))
                .then(Commands.literal("reset")
                        .executes(ArsMagicaLegacyCommand::resetSelf)
                        .then(Commands.argument("target", EntityArgument.players())
                                .executes(ArsMagicaLegacyCommand::reset)))
                .then(Commands.literal("skill")
                        .then(Commands.literal("learn")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILLS)
                                                .executes(ArsMagicaLegacyCommand::learnSkill))
                                        .then(Commands.literal("*")
                                                .executes(ArsMagicaLegacyCommand::learnAllSkills)))
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(ArsMagicaLegacyCommand::learnSkillSelf))
                                .then(Commands.literal("*")
                                        .executes(ArsMagicaLegacyCommand::learnAllSkillsSelf)))
                        .then(Commands.literal("forget")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILLS)
                                                .executes(ArsMagicaLegacyCommand::forgetSkill))
                                        .then(Commands.literal("*")
                                                .executes(ArsMagicaLegacyCommand::forgetAllSkills)))
                                .then(Commands.argument("skill", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILLS)
                                        .executes(ArsMagicaLegacyCommand::forgetSkillSelf))
                                .then(Commands.literal("*")
                                        .executes(ArsMagicaLegacyCommand::forgetAllSkillsSelf)))
                        .then(Commands.literal("list")
                                .executes(ArsMagicaLegacyCommand::listKnownSkillsSelf)
                                .then(Commands.argument("target", EntityArgument.player())
                                        .executes(ArsMagicaLegacyCommand::listKnownSkills)
                                        .then(Commands.literal("known")
                                                .executes(ArsMagicaLegacyCommand::listKnownSkills))
                                        .then(Commands.literal("unknown")
                                                .executes(ArsMagicaLegacyCommand::listUnknownSkills)))
                                .then(Commands.literal("known")
                                        .executes(ArsMagicaLegacyCommand::listKnownSkillsSelf))
                                .then(Commands.literal("unknown")
                                        .executes(ArsMagicaLegacyCommand::listUnknownSkillsSelf))
                                .then(Commands.literal("all")
                                        .executes(ArsMagicaLegacyCommand::listAllSkills))))
                .then(Commands.literal("skill_point")
                        .then(Commands.literal("add")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILL_POINTS)
                                                .executes(ArsMagicaLegacyCommand::addOneSkillPoint)
                                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                        .executes(ArsMagicaLegacyCommand::addSkillPoint))))
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(ArsMagicaLegacyCommand::addOneSkillPointSelf)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ArsMagicaLegacyCommand::addSkillPointSelf))))
                        .then(Commands.literal("consume")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILL_POINTS)
                                                .executes(ArsMagicaLegacyCommand::consumeOneSkillPoint)
                                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                        .executes(ArsMagicaLegacyCommand::consumeSkillPoint))))
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(ArsMagicaLegacyCommand::consumeOneSkillPointSelf)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ArsMagicaLegacyCommand::consumeSkillPointSelf))))
                        .then(Commands.literal("set")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILL_POINTS)
                                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                        .executes(ArsMagicaLegacyCommand::setSkillPoint))))
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(ArsMagicaLegacyCommand::setSkillPointSelf))))
                        .then(Commands.literal("get")
                                .then(Commands.argument("target", EntityArgument.players())
                                        .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                                .suggests(SUGGEST_SKILL_POINTS)
                                                .executes(ArsMagicaLegacyCommand::getSkillPoint)))
                                .then(Commands.argument("skill_point", ResourceLocationArgument.id())
                                        .suggests(SUGGEST_SKILL_POINTS)
                                        .executes(ArsMagicaLegacyCommand::getSkillPointSelf))))
        );
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

    private static int resetSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(context, List.of(context.getSource().getPlayerOrException()));
    }

    private static int reset(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return reset(context, EntityArgument.getPlayers(context, "target"));
    }

    private static int reset(CommandContext<CommandSourceStack> context, Collection<ServerPlayer> players) {
        var api = ArsMagicaAPI.get();
        var helper = api.getSkillHelper();
        for (ServerPlayer player : players) {
            api.getMagicHelper().setXp(player, 0);
        }
        for (ServerPlayer player : players) {
            for (IAffinity affinity : api.getAffinityRegistry()) {
                api.getAffinityHelper().setAffinityDepth(player, affinity, 0f);
            }
        }
        for (ServerPlayer player : players) {
            helper.forgetAll(player);
        }
        for (ServerPlayer player : players) {
            for (ISkillPoint skillPoint : api.getSkillPointRegistry()) {
                helper.consumeSkillPoint(player, skillPoint, helper.getSkillPoint(player, skillPoint));
            }
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(RESET_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(RESET_MULTIPLE, players.size()), true);
        }
        return players.size();
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

    private static int listAllSkills(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(createSkillListComponent(ArsMagicaAPI.get().getSkillManager().getSkills().stream()), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listUnknownSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknownSkills(context.getSource().getPlayerOrException(), context);
    }

    private static int listUnknownSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listUnknownSkills(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listKnownSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnownSkills(context.getSource().getPlayerOrException(), context);
    }

    private static int listKnownSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return listKnownSkills(EntityArgument.getPlayer(context, "target"), context);
    }

    private static int listUnknownSkills(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var knowledgeHelper = api.getSkillHelper();
        var skillManager = api.getSkillManager();
        context.getSource().sendSuccess(createSkillListComponent(skillManager.getSkills().stream().filter(skill -> knowledgeHelper.knows(player, skill))), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int listKnownSkills(ServerPlayer player, CommandContext<CommandSourceStack> context) {
        var api = ArsMagicaAPI.get();
        var skillManager = api.getSkillManager();
        var knowledgeHelper = api.getSkillHelper();
        context.getSource().sendSuccess(createSkillListComponent(knowledgeHelper.getKnownSkills(player).stream().flatMap(skill -> skillManager.getOptional(skill).stream())), true);
        return Command.SINGLE_SUCCESS;
    }

    private static int forgetAllSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAllSkills(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forgetAllSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetAllSkills(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetAllSkills(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().forgetAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int forgetSkillSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetSkill(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int forgetSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return forgetSkill(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int forgetSkill(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkillFromRegistry(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (!helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(SKILL_NOT_YET_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.forget(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_FORGET_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
    }

    private static int learnAllSkillsSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAllSkills(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learnAllSkills(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnAllSkills(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnAllSkills(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) {
        for (ServerPlayer player : players) {
            ArsMagicaAPI.get().getSkillHelper().learnAll(player);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_ALL_SINGLE, players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_ALL_MULTIPLE, players.size()), true);
        }
        return players.size();
    }

    private static int learnSkillSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnSkill(List.of(context.getSource().getPlayerOrException()), context);
    }

    private static int learnSkill(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return learnSkill(EntityArgument.getPlayers(context, "target"), context);
    }

    private static int learnSkill(Collection<ServerPlayer> players, CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var helper = ArsMagicaAPI.get().getSkillHelper();
        ISkill skill = getSkillFromRegistry(context);
        if (players.size() == 1) {
            ServerPlayer player = players.iterator().next();
            if (helper.knows(player, skill)) {
                context.getSource().sendFailure(new TranslatableComponent(SKILL_ALREADY_KNOWN, skill.getDisplayName(), player.getDisplayName()));
                return 0;
            }
        }
        for (ServerPlayer player : players) {
            helper.learn(player, skill);
        }
        if (players.size() == 1) {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_SINGLE, skill.getDisplayName(), players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(new TranslatableComponent(SKILL_LEARN_MULTIPLE, skill.getDisplayName(), players.size()), true);
        }
        return players.size();
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

    private static IAffinity getAffinityFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "affinity");
        IAffinity affinity = ArsMagicaAPI.get().getAffinityRegistry().getValue(rl);
        if (affinity == null) throw ERROR_UNKNOWN_AFFINITY.create(rl);
        return affinity;
    }

    private static ISkill getSkillFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "skill");
        Optional<ISkill> skill = ArsMagicaAPI.get().getSkillManager().getOptional(rl);
        if (skill.isEmpty()) throw ERROR_UNKNOWN_SKILL.create(rl);
        return skill.get();
    }

    private static ISkillPoint getSkillPointFromRegistry(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ResourceLocation rl = ResourceLocationArgument.getId(context, "skill_point");
        ISkillPoint skillPoint = ArsMagicaAPI.get().getSkillPointRegistry().getValue(rl);
        if (skillPoint == null) throw ERROR_UNKNOWN_SKILL_POINT.create(rl);
        return skillPoint;
    }

    private static CompletableFuture<Suggestions> suggestAffinities(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getAffinityRegistry().getKeys(), builder);
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillManager().getSkills().stream().map(ISkill::getId), builder);
    }

    private static CompletableFuture<Suggestions> suggestSkillPoints(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggestResource(ArsMagicaAPI.get().getSkillPointRegistry().getKeys(), builder);
    }

    private static Component createSkillListComponent(Stream<ISkill> stream) {
        return stream.map(ITranslatable::getDisplayName).reduce((component, component2) -> component.copy().append("\n").append(component2)).orElse(new TranslatableComponent(""));
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
