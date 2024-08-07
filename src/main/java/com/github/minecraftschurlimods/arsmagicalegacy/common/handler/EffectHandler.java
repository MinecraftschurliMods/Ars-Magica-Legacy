package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.Objects;

/**
 * Holds all event handlers required for the various mob effects.
 */
final class EffectHandler {
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(EffectHandler::entityJoinWorld);
        forgeBus.addListener(EffectHandler::livingJump);
        forgeBus.addListener(EffectHandler::livingFall);
        forgeBus.addListener(EventPriority.HIGHEST, EffectHandler::livingDeath);
        forgeBus.addListener(EventPriority.LOWEST, EffectHandler::livingHurt);
        forgeBus.addListener(EffectHandler::enderEntityTeleport);
        forgeBus.addListener(EffectHandler::enderPearlTeleport);
        forgeBus.addListener(EffectHandler::chorusFruitTeleport);
        forgeBus.addListener(EffectHandler::potionAdded);
        forgeBus.addListener(EffectHandler::potionExpiry);
        forgeBus.addListener(EffectHandler::potionRemove);
    }

    private static void entityJoinWorld(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) return;
        for (MobEffectInstance instance : entity.getActiveEffects()) {
            if (instance.getEffect() instanceof AMMobEffect effect) {
                effect.startEffect(entity, instance);
            }
        }
    }

    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.AGILITY)) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1f * (Objects.requireNonNull(entity.getEffect(AMMobEffects.AGILITY)).getAmplifier() + 1), 0));
        }
    }

    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.AGILITY)) {
            event.setDistance(event.getDistance() / (1.1f * (Objects.requireNonNull(entity.getEffect(AMMobEffects.AGILITY)).getAmplifier() + 1)));
        }
        if (entity.hasEffect(AMMobEffects.GRAVITY_WELL)) {
            event.setDistance(event.getDistance() * (Objects.requireNonNull(entity.getEffect(AMMobEffects.GRAVITY_WELL)).getAmplifier() + 1));
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (!event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) && entity.hasEffect(AMMobEffects.MAGIC_SHIELD)) {
            event.setAmount(event.getAmount() / (float) Objects.requireNonNull(entity.getEffect(AMMobEffects.MAGIC_SHIELD)).getAmplifier());
        }
    }

    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.TEMPORAL_ANCHOR)) {
            entity.removeEffect(AMMobEffects.TEMPORAL_ANCHOR);
            event.setCanceled(true);
        }
    }

    private static void enderEntityTeleport(EntityTeleportEvent.EnderEntity event) {
        if (event.getEntityLiving().hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            event.setCanceled(true);
        }
    }

    private static void enderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        if (event.getPlayer().hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            event.setCanceled(true);
        }
    }

    private static void chorusFruitTeleport(EntityTeleportEvent.ChorusFruit event) {
        if (event.getEntityLiving().hasEffect(AMMobEffects.ASTRAL_DISTORTION)) {
            event.setCanceled(true);
        }
    }

    private static void potionAdded(MobEffectEvent.Added event) {
        if (!event.getEntity().level().isClientSide() && !(event.getEffectInstance() == null) && event.getEffectInstance().getEffect().value() instanceof AMMobEffect effect) {
            effect.startEffect(event.getEntity(), event.getEffectInstance());
        }
    }

    private static void potionExpiry(MobEffectEvent.Expired event) {
        if (!event.getEntity().level().isClientSide() && !(event.getEffectInstance() == null) && event.getEffectInstance().getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(event.getEntity(), event.getEffectInstance());
        }
    }

    private static void potionRemove(MobEffectEvent.Remove event) {
        if (!event.getEntity().level().isClientSide() && !(event.getEffectInstance() == null) && event.getEffectInstance().getEffect().value() instanceof AMMobEffect effect) {
            effect.stopEffect(event.getEntity(), event.getEffectInstance());
        }
    }
}
