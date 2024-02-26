package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;

public final class BurnoutHUD extends AbstractHUD {
    public BurnoutHUD() {
        super(Config.CLIENT.BURNOUT_ANCHOR_X, Config.CLIENT.BURNOUT_ANCHOR_Y, Config.CLIENT.BURNOUT_X, Config.CLIENT.BURNOUT_Y, 80, 10);
    }

    @Override
    public void draw(ExtendedGui forgeGui, GuiGraphics graphics, float partialTick) {
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
            renderBar(graphics, 0, 0, getWidth(), getHeight(), burnout, maxBurnout, 0x880000);
        }
    }
}
