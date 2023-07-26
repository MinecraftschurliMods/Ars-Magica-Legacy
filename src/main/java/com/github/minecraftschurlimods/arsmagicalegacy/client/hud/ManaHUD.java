package com.github.minecraftschurlimods.arsmagicalegacy.client.hud;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public final class ManaHUD extends AbstractHUD {
    public ManaHUD() {
        super(Config.CLIENT.MANA_ANCHOR_X, Config.CLIENT.MANA_ANCHOR_Y, Config.CLIENT.MANA_X, Config.CLIENT.MANA_Y, 80, 10);
    }

    @Override
    public void draw(ForgeGui forgeGui, PoseStack poseStack, float partialTick) {
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
            renderBar(poseStack, 0, 0, getWidth(), getHeight(), mana, maxMana, 0x99FFFF);
        }
    }
}
