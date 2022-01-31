package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.List;

import static com.github.minecraftschurlimods.arsmagicalegacy.server.commands.ArsMagicaLegacyCommandTranslations.*;

public class ResetSubcommand {
    public static void register(LiteralArgumentBuilder<CommandSourceStack> builder) {
        builder.then(Commands.literal("reset")
                .executes(ResetSubcommand::resetSelf)
                .then(Commands.argument("target", EntityArgument.players())
                        .executes(ResetSubcommand::reset)));
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
}
