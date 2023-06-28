package com.github.minecraftschurlimods.arsmagicalegacy.api.client;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Base class for all occulus tab renderers.
 * To register a tab renderer, call {@code ArsMagicaAPI.get().registerOcculusTabRenderer(<registered occulus tab>, (occulusTab, player) -> <create your tab renderer>);}
 */
public abstract class OcculusTabRenderer extends AbstractContainerEventHandler implements Renderable, NarratableEntry {
    protected final int textureHeight;
    protected final int textureWidth;
    protected final OcculusTab occulusTab;
    protected final Screen parent;
    protected int screenWidth;
    protected int screenHeight;
    protected int width;
    protected int height;
    protected int posX;
    protected int posY;

    protected OcculusTabRenderer(OcculusTab occulusTab, Screen parent) {
        this.textureHeight = occulusTab.height();
        this.textureWidth = occulusTab.width();
        this.occulusTab = occulusTab;
        this.parent = parent;
    }

    /**
     * Don't call this method, use {@link OcculusTabRenderer#renderBg(GuiGraphics, int, int, float)} and {@link OcculusTabRenderer#renderFg(GuiGraphics, int, int, float)} instead.
     */
    @Internal
    @Override
    public final void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTicks) {
        graphics.pose().pushPose();
        graphics.pose().translate(7, 7, 0);
        pMouseX -= posX;
        pMouseY -= posY;
        renderBg(graphics, pMouseX, pMouseY, pPartialTicks);
        renderFg(graphics, pMouseX, pMouseY, pPartialTicks);
        graphics.pose().popPose();
    }

    /**
     * Don't call this method, it is used to initialize the values from the parent gui.
     */
    @Internal
    public final void init(int width, int height, int screenWidth, int screenHeight, int posX, int posY) {
        this.width = width;
        this.height = height;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.posX = posX;
        this.posY = posY;
        init();
    }

    /**
     * Render the background in this method.
     */
    protected abstract void renderBg(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks);

    /**
     * Render the foreground in this method.
     */
    protected abstract void renderFg(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks);

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
    }

    /**
     * @return The current player.
     */
    @Nullable
    protected Player getPlayer() {
        return getMinecraft().player;
    }

    /**
     * @return The current font.
     */
    protected Font getFont() {
        return getMinecraft().font;
    }

    /**
     * @return The current item renderer.
     */
    protected ItemRenderer getItemRenderer() {
        return getMinecraft().getItemRenderer();
    }

    /**
     * @return The current minecraft instance.
     */
    protected Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    /**
     * Called while initialization.
     */
    protected void init() {
    }
}
