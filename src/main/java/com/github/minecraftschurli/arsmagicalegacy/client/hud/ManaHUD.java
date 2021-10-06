package com.github.minecraftschurli.arsmagicalegacy.client.hud;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ManaHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + 121;
        int yStart = height - 23;
        final Player player = Minecraft.getInstance().player;
        var magicHelper = ArsMagicaAPI.get().getMagicHelper();
        double mana = magicHelper.getMana(player);
        double maxMana = magicHelper.getMaxMana(player);
        renderBar(mStack, xStart, yStart, 80, 10, mana, maxMana, 0x99FFFF);
    }
}
