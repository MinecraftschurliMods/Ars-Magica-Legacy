package com.github.minecraftschurli.arsmagicalegacy.client.hud;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;

public final class BurnoutHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + 121;
        int yStart = height - 13;
        final Player player = Minecraft.getInstance().player;
        if (!ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return;
        double burnout = 0;
        double maxBurnout = 0;
        if (!player.isDeadOrDying()) {
            var magicHelper = ArsMagicaAPI.get().getMagicHelper();
            burnout = magicHelper.getBurnout(player);
            maxBurnout = magicHelper.getMaxBurnout(player);
        }
        renderBar(mStack, xStart, yStart, 80, 10, burnout, maxBurnout, 0x880000);
    }
}
