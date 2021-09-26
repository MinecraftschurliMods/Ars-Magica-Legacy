package com.github.minecraftschurli.arsmagicalegacy.api.client;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmlclient.gui.GuiUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class OcculusTabRenderer extends Screen implements NarratableEntry {
    protected final IOcculusTab occulusTab;
    protected final Player player;
    protected int screenWidth;
    protected int screenHeight;
    protected int posX;
    protected int posY;

    protected OcculusTabRenderer(IOcculusTab occulusTab, Player player) {
        super(occulusTab.getDisplayName());
        this.occulusTab = occulusTab;
        this.player = player;
    }

    @Override
    public void render(@NotNull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        pMatrixStack.pushPose();
        pMatrixStack.translate(7, 7, 0);
        pMouseX -= posX;
        pMouseY -= posY;
        renderBg(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        renderFg(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        pMatrixStack.popPose();
    }

    public void initValues(int screenWidth, int screenHeight, int posX, int posY) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.posX = posX;
        this.posY = posY;
    }

    protected abstract void renderBg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks);

    protected abstract void renderFg(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks);

    @NotNull
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}

    public void renderComponentTooltip(@NotNull PoseStack stack, @NotNull List<Component> tooltips, int mouseX, int mouseY) {
        GuiUtils.drawHoveringText(stack, tooltips, mouseX, mouseY, screenWidth, screenHeight, -1, font);
    }
}
