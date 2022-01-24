package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellCustomizationScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class ClientHelper {
    @Nullable
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    public static void openOcculusGui() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }

    public static void openSpellCustomizationGui(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SpellCustomizationScreen(stack));
    }

    public static boolean showAdvancedTooltips() {
        return Screen.hasShiftDown();
    }

    public static void updateStepHeight(Player player, float stepHeight) {
        player.maxUpStep = stepHeight;
    }
}
