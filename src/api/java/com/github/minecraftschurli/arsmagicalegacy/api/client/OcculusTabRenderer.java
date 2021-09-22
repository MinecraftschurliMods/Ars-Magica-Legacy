package com.github.minecraftschurli.arsmagicalegacy.api.client;

import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class OcculusTabRenderer extends Screen implements NarratableEntry {
    protected final IOcculusTab occulusTab;
    protected final Player player;
    protected final int xSize = 210;
    protected final int ySize = 210;

    protected OcculusTabRenderer(IOcculusTab occulusTab, Player player) {
        super(occulusTab.getDisplayName());
        this.occulusTab = occulusTab;
        this.player = player;
    }

    @Override
    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        int posX = width / 2 - xSize / 2;
        int posY = height / 2 - ySize / 2;
        renderBg(pMatrixStack, posX, posY, pMouseX, pMouseY, pPartialTicks);
        renderFg(pMatrixStack, posX, posY, pMouseX, pMouseY, pPartialTicks);
    }

    protected abstract void renderBg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks);

    protected abstract void renderFg(PoseStack pMatrixStack, int posX, int posY, int pMouseX, int pMouseY, float pPartialTicks);

    @NotNull
    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}
}
