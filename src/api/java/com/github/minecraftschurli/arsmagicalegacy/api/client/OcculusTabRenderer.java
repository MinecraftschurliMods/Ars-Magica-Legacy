package com.github.minecraftschurli.arsmagicalegacy.api.client;

import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
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
 * Base class for all occulus tab renderers<br>
 * <p>To register a tab renderer you need to call <br>
 * {@code ArsMagicaAPI.get().registerOcculusTabRenderer(<registered occulus tab>, (occulusTab, player) -> <create your tab renderer>)}</p>
 */
public abstract class OcculusTabRenderer extends AbstractContainerEventHandler implements Widget, NarratableEntry {
    protected final IOcculusTab occulusTab;
    protected final int textureHeight;
    protected final int textureWidth;
    protected final Screen parent;
    protected int screenWidth;
    protected int screenHeight;
    protected int width;
    protected int height;
    protected int posX;
    protected int posY;

    /**
     * Constructor for a {@link OcculusTabRenderer}.
     *
     * @param occulusTab the occulus tab of this renderer
     * @param parent
     */
    protected OcculusTabRenderer(IOcculusTab occulusTab, final Screen parent) {
        this.occulusTab = occulusTab;
        textureHeight = occulusTab.getHeight();
        textureWidth = occulusTab.getWidth();
        this.parent = parent;
    }

    /**
     * Don't call or override this method use {@link OcculusTabRenderer#renderBg(PoseStack, int, int, float)}
     * and {@link OcculusTabRenderer#renderFg(PoseStack, int, int, float)} for rendering instead.
     */
    @Internal
    @Override
    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(7, 7, 0);
        pMouseX -= posX;
        pMouseY -= posY;
        renderBg(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderFg(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        pMatrixStack.popPose();
    }

    /**
     * Never call this method yourself it is used to initialize the values from the parent gui.
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
     * Override to do your own initialisation.
     */
    protected void init() {
    }

    /**
     * Render your background in this method.
     */
    protected abstract void renderBg(PoseStack stack, int mouseX, int mouseY, float partialTicks);

    /**
     * Render your foreground in this method.
     */
    protected abstract void renderFg(PoseStack stack, int mouseX, int mouseY, float partialTicks);

    /**
     * @see NarratableEntry#narrationPriority()
     */
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    /**
     * Get the current player.
     *
     * @return the current player
     */
    @Nullable
    protected Player getPlayer() {
        return getMinecraft().player;
    }

    /**
     * Get the current font.
     *
     * @return the current font
     */
    protected Font getFont() {
        return getMinecraft().font;
    }

    /**
     * Get the minecraft instance.
     *
     * @return the minecraft instance
     */
    protected Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    /**
     * Get the current item renderer.
     *
     * @return the current item renderer
     */
    protected ItemRenderer getItemRenderer() {
        return Minecraft.getInstance().getItemRenderer();
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return List.of();
    }

    /**
     * Override this method if you want to provide narrations.
     */
    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
    }
}
