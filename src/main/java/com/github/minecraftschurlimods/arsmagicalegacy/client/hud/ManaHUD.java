package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public final class ManaHUD extends AbstractHUD {
    @Override
    protected void render(PoseStack mStack, int width, int height, float partialTicks) {
        int xStart = width / 2 + Config.CLIENT.HUD_HORIZONTAL_OFFSET.get();
        int yStart = height - 20 - Config.CLIENT.HUD_VERTICAL_OFFSET.get();
        Player player = Minecraft.getInstance().player;
        var api = ArsMagicaAPI.get();
        if (player == null || !api.getMagicHelper().knowsMagic(player)) return;
        double mana = 0;
        double maxMana = 0;
        if (!player.isDeadOrDying()) {
            IManaHelper manaHelper = api.getManaHelper();
            mana = manaHelper.getMana(player);
            maxMana = manaHelper.getMaxMana(player);
        }
        if (maxMana > 0) {
            renderBar(mStack, xStart, yStart, 80, 10, mana, maxMana, 0x99FFFF);
        }
    }
}
