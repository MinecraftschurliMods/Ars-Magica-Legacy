package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.network.UpdateStepHeightPacket;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class AgilityEffect extends AMMobEffect {
    private static final Map<LivingEntity, Float> CACHE = new HashMap<>();

    public AgilityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xade000);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity.maxUpStep >= 1) return;
        CACHE.put(entity, entity.maxUpStep);
        entity.maxUpStep = 1;
        if (entity instanceof Player) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(1), (Player) entity);
        }
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        if (!CACHE.containsKey(entity)) return;
        entity.maxUpStep = CACHE.get(entity);
        if (entity instanceof Player) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(CACHE.get(entity)), (Player) entity);
        }
        CACHE.remove(entity);
    }
}
