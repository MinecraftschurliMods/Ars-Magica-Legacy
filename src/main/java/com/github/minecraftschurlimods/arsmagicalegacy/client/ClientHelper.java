package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellCustomizationScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClientHelper {
    /**
     * @return The player logged into the current Minecraft instance.
     */
    @Nullable
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }

    /**
     * @return The current level of the player logged into the current Minecraft instance.
     */
    @Nullable
    public static Level getLocalLevel() {
        Player localPlayer = getLocalPlayer();
        if (localPlayer == null) return null;
        return localPlayer.level;
    }

    /**
     * Opens an occulus GUI on the client.
     */
    public static void openOcculusGui() {
        Minecraft.getInstance().setScreen(new OcculusScreen());
    }

    /**
     * Opens a spell customization GUI on the client.
     */
    public static void openSpellCustomizationGui(ItemStack stack) {
        Minecraft.getInstance().setScreen(new SpellCustomizationScreen(stack));
    }

    /**
     * @return Whether to show advanced tooltips or not.
     */
    public static boolean showAdvancedTooltips() {
        return Screen.hasShiftDown();
    }

    /**
     * Updates the player's step height.
     *
     * @param player     The player to update the step height for.
     * @param stepHeight The new step height.
     */
    public static void updateStepHeight(Player player, float stepHeight) {
        player.maxUpStep = stepHeight;
    }

    @NotNull
    public static RegistryAccess getRegistryAccess() {
        if (EffectiveSide.get().isServer()) return ServerLifecycleHooks.getCurrentServer().registryAccess();
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null) return RegistryAccess.BUILTIN.get();
        return connection.registryAccess();
    }
}
