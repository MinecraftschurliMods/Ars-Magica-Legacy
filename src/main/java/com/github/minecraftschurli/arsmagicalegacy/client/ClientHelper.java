package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientHelper {
    public static void openOcculusGui(Player player) {
        Minecraft.getInstance().setScreen(new OcculusScreen(player));
    }
}
