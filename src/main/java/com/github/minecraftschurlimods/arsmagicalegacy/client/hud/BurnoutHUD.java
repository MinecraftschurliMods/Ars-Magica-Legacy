package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class BurnoutHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + Config.CLIENT.HUD_HORIZONTAL_OFFSET.get();
        int yStart = height - 10 - Config.CLIENT.HUD_VERTICAL_OFFSET.get();
        Player player = Minecraft.getInstance().player;
        var api = ArsMagicaAPI.get();
        if (player == null || !api.getMagicHelper().knowsMagic(player)) return;
        double burnout = 0;
        double maxBurnout = 0;
        if (!player.isDeadOrDying()) {
            var burnoutHelper = api.getBurnoutHelper();
            burnout = burnoutHelper.getBurnout(player);
            maxBurnout = burnoutHelper.getMaxBurnout(player);
        }
        if (maxBurnout > 0) {
            renderBar(mStack, xStart, yStart, 80, 10, burnout, maxBurnout, 0x880000);
        }
    }
}
