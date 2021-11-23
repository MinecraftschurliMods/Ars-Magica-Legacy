package com.github.minecraftschurli.arsmagicalegacy.client.hud;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.magic.IManaHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ManaHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + 121;
        int yStart = height - 23;
        final Player player = Minecraft.getInstance().player;
        if (player == null || !ArsMagicaAPI.get().getMagicHelper().knowsMagic(player)) return;
        double mana = 0;
        double maxMana = 0;
        if (!player.isDeadOrDying()) {
            IManaHelper manaHelper = ArsMagicaAPI.get().getManaHelper();
            mana = manaHelper.getMana(player);
            maxMana = manaHelper.getMaxMana(player);
        }
        renderBar(mStack, xStart, yStart, 80, 10, mana, maxMana, 0x99FFFF);
    }
}
