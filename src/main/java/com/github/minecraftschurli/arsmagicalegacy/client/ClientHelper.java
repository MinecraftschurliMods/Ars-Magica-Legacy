package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.client.gui.SpellIconPickScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class ClientHelper {
    public static void openOcculusGui() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }

    public static void openSpellCustomizationGui(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SpellIconPickScreen(stack));
    }

    @Nullable
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static boolean showAdvancedTooltips() {
        return Screen.hasShiftDown();
    }
}
