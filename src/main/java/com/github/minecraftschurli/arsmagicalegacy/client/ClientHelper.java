package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.client.gui.SpellIconPickScreen;
import com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ClientHelper {
    /**
     * @return The player of the current Minecraft instance.
     */
    @Nullable
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    /**
     * Opens a new OcculusScreen.
     */
    public static void openOcculusGui() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }

    /**
     * Opens a new SpellIconPickScreen.
     * @param stack The icon to pass in the SpellIconPickScreen constructor.
     */
    public static void openSpellCustomizationGui(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SpellIconPickScreen(stack));
    }

    /**
     * @return Whether to show advanced spell tooltips or not.
     */
    public static boolean showAdvancedTooltips() {
        return Screen.hasShiftDown();
    }

    /**
     * Sets the player's step height.
     * @param player     The player to set the step height for.
     * @param stepHeight The step height to set.
     */
    public static void updateStepHeight(Player player, float stepHeight) {
        player.maxUpStep = stepHeight;
    }
}
