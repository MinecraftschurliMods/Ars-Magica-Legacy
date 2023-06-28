package com.github.minecraftschurlimods.arsmagicalegacy.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellCustomizationScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.SpellRecipeScreen;
import com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.Nullable;

public final class ClientHelper {
    /**
     * Draws the given item stack at the specified position.
     *
     * @param poseStack The pose stack to use.
     * @param itemStack The item stack to draw.
     * @param x         The x coordinate to draw the part at.
     * @param y         The y coordinate to draw the part at.
     */
    public static void drawItemStack(GuiGraphics graphics, ItemStack itemStack, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        graphics.pose().pushPose();
        graphics.renderItem(itemStack, x, y);
        graphics.renderItemDecorations(minecraft.font, itemStack, x, y);
        graphics.pose().popPose();
    }

    /**
     * Draws the given spell part's icon at the specified position with the specified dimensions.
     *
     * @param poseStack The pose stack to use.
     * @param spellPart The spell part to draw the icon of.
     * @param x         The x coordinate to draw the part at.
     * @param y         The y coordinate to draw the part at.
     * @param width     The width to draw the part with.
     * @param height    The height to draw the part with.
     */
    public static void drawSpellPart(GuiGraphics graphics, ISpellPart spellPart, int x, int y, int width, int height) {
        TextureAtlasSprite sprite = SkillIconAtlas.instance().getSprite(spellPart.getId());
        graphics.pose().pushPose();
        graphics.blit(x, y, 0, width, height, sprite);
        graphics.pose().popPose();
    }

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
        return localPlayer.level();
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
     * Opens a spell customization GUI on the client.
     */
    public static void openSpellRecipeGui(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        Minecraft.getInstance().setScreen(new SpellRecipeScreen(stack, playTurnSound, startPage, lecternPos));
    }

    /**
     * @return Whether to show advanced tooltips or not.
     */
    public static boolean showAdvancedTooltips() {
        return Screen.hasShiftDown();
    }

    /**
     * Get the local registry access.
     *
     * @return the local registry access.
     */
    public static RegistryAccess getRegistryAccess() {
        if (EffectiveSide.get().isServer()) {
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
        return Minecraft.getInstance().getConnection().registryAccess();
    }

    /**
     * Get the registry for the provided key from the local registry access.
     *
     * @param key the key of the registry
     * @return the registry for the key
     * @param <T> the type of the registry
     */
    public static <T> Registry<T> getRegistry(ResourceKey<Registry<T>> key) {
        return getRegistryAccess().registryOrThrow(key);
    }
}
