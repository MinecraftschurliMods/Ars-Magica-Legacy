package com.github.minecraftschurlimods.arsmagicalegacy.common.effect;

import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;

public class FlightEffect extends AMMobEffect {
    public FlightEffect(Identifier id) {
        super(MobEffectCategory.BENEFICIAL, 0xc6dada);
        addAttributeModifier(NeoForgeMod.CREATIVE_FLIGHT, id.withPrefix("effect."), 1, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        if (entity instanceof ServerPlayer player && !player.isCreative()) {
            player.fallDistance = 0;
        }
    }
}
