package com.github.minecraftschurlimods.arsmagicalegacy.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class MagicXPCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("magicxp"));
    }
}
