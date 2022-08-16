package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.common.effect.AMMobEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

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
        forgeBus.addListener(EffectHandler::entitySize);
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
        if (entity.hasEffect(AMMobEffects.AGILITY.get())) {
            entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.1f * (Objects.requireNonNull(entity.getEffect(AMMobEffects.AGILITY.get())).getAmplifier() + 1), 0));
        }
    }

    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.AGILITY.get())) {
            event.setDistance(event.getDistance() / (1.1f * (Objects.requireNonNull(entity.getEffect(AMMobEffects.AGILITY.get())).getAmplifier() + 1)));
        }
        if (entity.hasEffect(AMMobEffects.GRAVITY_WELL.get())) {
            event.setDistance(event.getDistance() * (Objects.requireNonNull(entity.getEffect(AMMobEffects.GRAVITY_WELL.get())).getAmplifier() + 1));
        }
        if (entity.getAttributes().hasAttribute(AMAttributes.SCALE.get())) {
            event.setDistance(event.getDistance() * ((float) Objects.requireNonNull(entity.getAttribute(AMAttributes.SCALE.get())).getValue()));
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getSource() != DamageSource.OUT_OF_WORLD && entity.hasEffect(AMMobEffects.MAGIC_SHIELD.get())) {
            event.setAmount(event.getAmount() / (float) Objects.requireNonNull(entity.getEffect(AMMobEffects.MAGIC_SHIELD.get())).getAmplifier());
        }
        if (event.getSource().getEntity() instanceof LivingEntity living && living.getAttributes().hasAttribute(AMAttributes.SCALE.get())) {
            event.setAmount(event.getAmount() * ((float) Objects.requireNonNull(living.getAttribute(AMAttributes.SCALE.get())).getValue()));
        }
    }

    private static void livingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.TEMPORAL_ANCHOR.get())) {
            entity.removeEffect(AMMobEffects.TEMPORAL_ANCHOR.get());
            event.setCanceled(true);
        }
    }

    private static void enderEntityTeleport(EntityTeleportEvent.EnderEntity event) {
        if (event.getEntityLiving().hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) {
            event.setCanceled(true);
        }
    }

    private static void enderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        if (event.getPlayer().hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) {
            event.setCanceled(true);
        }
    }

    private static void chorusFruitTeleport(EntityTeleportEvent.ChorusFruit event) {
        if (event.getEntityLiving().hasEffect(AMMobEffects.ASTRAL_DISTORTION.get())) {
            event.setCanceled(true);
        }
    }

    private static void potionAdded(MobEffectEvent.Added event) {
        if (!event.getEntity().level.isClientSide() && event.getEffectInstance().getEffect() instanceof AMMobEffect effect) {
            effect.startEffect(event.getEntity(), event.getEffectInstance());
        }
    }

    private static void potionExpiry(MobEffectEvent.Expired event) {
        if (!event.getEntity().level.isClientSide() && !(event.getEffectInstance() == null) && event.getEffectInstance().getEffect() instanceof AMMobEffect effect) {
            effect.stopEffect(event.getEntity(), event.getEffectInstance());
        }
    }

    private static void potionRemove(MobEffectEvent.Remove event) {
        if (!event.getEntity().level.isClientSide() && !(event.getEffectInstance() == null) && event.getEffectInstance().getEffect() instanceof AMMobEffect effect) {
            effect.stopEffect(event.getEntity(), event.getEffectInstance());
        }
    }

    private static void entitySize(EntityEvent.Size event) {
        if (!(event.getEntity() instanceof LivingEntity living) || !living.isAddedToWorld()) return;
        if (living.getAttribute(AMAttributes.SCALE.get()) == null) return;
        float factor = (float) living.getAttributeValue(AMAttributes.SCALE.get());
        if (factor == 1) return;
        EntityDimensions newSize = event.getNewSize();
        EntityDimensions scaledSize = newSize.scale(factor);
        float eyeHeight = living.getEyeHeightAccess(event.getPose(), newSize);
        float scaledEyeHeight = living.getEyeHeightAccess(event.getPose(), scaledSize);
        boolean manuallyUpdateEyeHeight = eyeHeight == scaledEyeHeight;
        event.setNewSize(scaledSize, !manuallyUpdateEyeHeight);
        if (manuallyUpdateEyeHeight) {
            event.setNewEyeHeight(event.getNewEyeHeight() * factor);
        }
    }
}
