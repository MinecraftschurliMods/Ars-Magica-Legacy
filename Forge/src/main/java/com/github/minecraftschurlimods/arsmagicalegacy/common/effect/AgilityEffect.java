package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.network.UpdateStepHeightPacket;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AgilityEffect extends AMMobEffect {
    public AgilityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xade000);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.maxUpStep = 1f;
        if (entity instanceof Player) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(1f), (Player) entity);
        }
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.maxUpStep = 0.6f;
        if (entity instanceof Player) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(0.6f), (Player) entity);
        }
    }
}
