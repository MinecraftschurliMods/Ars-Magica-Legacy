package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.player.Player;

public final class XpHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + 121;
        int yStart = height - 33;
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
        renderBar(mStack, xStart, yStart, 80, 10, xp, xpForNextLevel, 0x7777FF);
        String s = String.valueOf(level);
        Font font = Minecraft.getInstance().font;
        int i1 = width / 2 + 161 - font.width(s) / 2;
        int j1 = height - 43;
        font.draw(mStack, s, (float) (i1 + 1), (float) j1, 0);
        font.draw(mStack, s, (float) (i1 - 1), (float) j1, 0);
        font.draw(mStack, s, (float) i1, (float) (j1 + 1), 0);
        font.draw(mStack, s, (float) i1, (float) (j1 - 1), 0);
        font.draw(mStack, s, (float) i1, (float) j1, 0x7777FF);
    }
}
