package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public final class XpHUD extends AbstractHUD {
    public XpHUD() {
        super(Config.CLIENT.XP_ANCHOR_X, Config.CLIENT.XP_ANCHOR_Y, Config.CLIENT.XP_X, Config.CLIENT.XP_Y, 80, 10);
    }

    @Override
    public void draw(ForgeGui forgeGui, PoseStack poseStack, float v) {
        Player player = Minecraft.getInstance().player;
        var api = ArsMagicaAPI.get();
        if (player == null || !api.getMagicHelper().knowsMagic(player)) return;
        int level = 0;
        double xp = 0;
        double xpForNextLevel = 0;
        if (!player.isDeadOrDying()) {
            var helper = api.getMagicHelper();
            level = helper.getLevel(player);
            xp = helper.getXp(player);
            xpForNextLevel = helper.getXpForNextLevel(level);
        }
        renderBar(poseStack, 0, 0, getWidth(), getHeight(), xp, xpForNextLevel, 0x7777FF);
        String s = String.valueOf(level);
        Font font = Minecraft.getInstance().font;
        int i1 = 40 - font.width(s) / 2;
        int j1 = -10;
        font.draw(poseStack, s, (float) (i1 + 1), (float) j1, 0);
        font.draw(poseStack, s, (float) (i1 - 1), (float) j1, 0);
        font.draw(poseStack, s, (float) i1, (float) (j1 + 1), 0);
        font.draw(poseStack, s, (float) i1, (float) (j1 - 1), 0);
        font.draw(poseStack, s, (float) i1, (float) j1, 0x7777FF);
    }
}
