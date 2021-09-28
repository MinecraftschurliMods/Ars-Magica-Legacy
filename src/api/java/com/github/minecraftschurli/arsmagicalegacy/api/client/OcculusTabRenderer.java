package com.github.minecraftschurli.arsmagicalegacy.api.client;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Base class for all occulus tab renderers<br>
 * <p>To register a tab renderer you need to call <br>
 * {@code ArsMagicaAPI.get().registerOcculusTabRenderer(<registered occulus tab>, (occulusTab, player) -> <create your tab renderer>)}</p>
 */
public abstract class OcculusTabRenderer extends Screen implements NarratableEntry {
    protected final IOcculusTab occulusTab;
    protected final int textureHeight;
    protected final int textureWidth;
    protected int screenWidth;
    protected int screenHeight;
    protected int posX;
    protected int posY;

    /**
     * Constructor for a {@link OcculusTabRenderer}.
     *
     * @param occulusTab the occulus tab of this renderer
     * @param player the player that has the gui open
     */
    protected OcculusTabRenderer(IOcculusTab occulusTab) {
        super(occulusTab.getDisplayName());
        this.occulusTab = occulusTab;
        this.textureHeight = occulusTab.getHeigth();
        this.textureWidth = occulusTab.getWidth();
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
    public final void initValues(int screenWidth, int screenHeight, int posX, int posY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.posX = posX;
        this.posY = posY;
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
    @NotNull
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    /**
     * Override this method if you want to provide narrations.
     */
    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {}

    public void renderComponentToolTip(PoseStack stack, List<? extends net.minecraft.network.chat.FormattedText> tooltips, int mouseX, int mouseY, Font font) {
        GuiUtils.drawHoveringText(stack, tooltips, mouseX, mouseY, screenWidth, screenHeight, -1, font);
    }
}
