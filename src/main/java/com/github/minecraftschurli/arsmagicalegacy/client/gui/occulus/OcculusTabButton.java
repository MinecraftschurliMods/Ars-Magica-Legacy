package com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.skill.IOcculusTab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class OcculusTabButton extends Button {
    public static final int SIZE = 22;
    private final int index;
    private final IOcculusTab tab;
    private final boolean inverted;

    public OcculusTabButton(int index, int x, int y, boolean inverted, IOcculusTab tab, OnPress pOnPress) {
        super(x, y, SIZE, SIZE, tab.getDisplayName(), pOnPress);
        this.index = index;
        this.tab = tab;
        this.inverted = inverted;
    }

    @Override
    public void renderButton(@NotNull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        if (!visible) return;
        Minecraft.getInstance().getTextureManager().bindForSetup(new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/gui/occulus/overlay.png"));
        if (inverted) {
            blit(pMatrixStack, x, y, getBlitOffset(), 0, 210, SIZE, SIZE, SIZE, SIZE);
        } else {
            blit(pMatrixStack, x, y, getBlitOffset(), 0, 210, SIZE, SIZE, SIZE, SIZE);// TODO flip
        }
        Minecraft.getInstance().getTextureManager().bindForSetup(tab.getIcon());
        blit(pMatrixStack, x+2, y+2, getBlitOffset(), 0, 0, 18, 18, 18, 18);
    }

    public int getIndex() {
        return index;
    }
}
