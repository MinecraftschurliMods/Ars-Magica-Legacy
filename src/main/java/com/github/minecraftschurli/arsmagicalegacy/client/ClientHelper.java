package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;

public class ClientHelper {
    public static void openOcculusGui() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }
}
