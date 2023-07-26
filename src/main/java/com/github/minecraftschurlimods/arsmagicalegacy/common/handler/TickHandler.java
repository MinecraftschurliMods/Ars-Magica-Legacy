package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbilityUUIDs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.ManaHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

/**
 * Holds the living and player tick event handlers. These get special treatment as these events should not have more than one event handler per mod.
 */
final class TickHandler {
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(TickHandler::livingUpdate);
        forgeBus.addListener(TickHandler::playerTick);
    }

    private static final Field CAP_PROVIDER_VALID = ObfuscationReflectionHelper.findField(CapabilityProvider.class, "valid");

    private static void livingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.hasEffect(AMMobEffects.WATERY_GRAVE.get()) && (entity.isInWaterOrBubble() || entity.getPose() == Pose.SWIMMING)) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getPose() == Pose.SWIMMING ? 0 : Math.min(0, entity.getDeltaMovement().y()), entity.getDeltaMovement().z());
        }
        if (event.getEntityLiving().isOnFire()) {
            ArsMagicaAPI.get().getContingencyHelper().triggerContingency(event.getEntityLiving(), ContingencyType.FIRE);
        }
    }

    private static void playerTick(TickEvent.PlayerTickEvent event) {
        switch (event.side) {
            case CLIENT:
                switch (event.phase) {
                    case START -> playerTickClientStart(event.player);
                    case END -> playerTickClientEnd(event.player);
                }
                break;
            case SERVER:
                switch (event.phase) {
                    case START -> playerTickServerStart(event.player);
                    case END -> playerTickServerEnd(event.player);
                }
                break;
        }
    }

    private static void playerTickServerStart(final Player player) {
        manaAndBurnoutRegen(player);
        handleAbilities(player);
    }

    private static void playerTickServerEnd(final Player player) {
        // NOOP
    }

    private static void playerTickClientStart(final Player player) {
        // NOOP
    }

    private static void playerTickClientEnd(final Player player) {
        // NOOP
    }

    private static void manaAndBurnoutRegen(final Player player) {
        var api = ArsMagicaAPI.get();
        if (player.isDeadOrDying() || !api.getMagicHelper().knowsMagic(player)) return;
        api.getManaHelper().increaseMana(player, (float) player.getAttributeValue(AMAttributes.MANA_REGEN.get()));
        api.getBurnoutHelper().decreaseBurnout(player, (float) player.getAttributeValue(AMAttributes.BURNOUT_REGEN.get()));
    }

    private static void handleAbilities(final Player player) {
        if (player.isDeadOrDying()) return;
        var api = ArsMagicaAPI.get();
        var manager = api.getAbilityManager();
        var helper = api.getAffinityHelper();
        try {
            if (!(boolean) CAP_PROVIDER_VALID.get(player)) return;
        } catch (IllegalAccessException ignored) {
            return;
        }
        IAbilityData ability;
        if (!player.isCreative()) {
            if (manager.hasAbility(player, AMAbilities.WATER_DAMAGE_FIRE.getId()) && player.isInWater() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(DamageSource.OUT_OF_WORLD, 1);
            }
            if (manager.hasAbility(player, AMAbilities.WATER_DAMAGE_LIGHTNING.getId()) && player.isInWater() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(DamageSource.OUT_OF_WORLD, 1);
            }
            if (manager.hasAbility(player, AMAbilities.NETHER_DAMAGE_WATER.getId()) && player.getLevel().dimensionType().ultraWarm() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(DamageSource.OUT_OF_WORLD, 1);
            }
            if (manager.hasAbility(player, AMAbilities.NETHER_DAMAGE_NATURE.getId()) && player.getLevel().dimensionType().ultraWarm() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(DamageSource.OUT_OF_WORLD, 1);
            }
        }
        ability = manager.get(AMAbilities.SATURATION.getId());
        if (ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)), 0, false, false));
        }
        ability = manager.get(AMAbilities.REGENERATION.getId());
        if (ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)), 0, false, false));
        }
        ability = manager.get(AMAbilities.NIGHT_VISION.getId());
        if (ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)) + 200, 0, false, false));
        }
        ability = manager.get(AMAbilities.FROST_WALKER.getId());
        if (ability.test(player)) {
            FrostWalkerEnchantment.onEntityMoved(player, player.getLevel(), player.blockPosition(), 1);
        }
        AttributeMap attributes = player.getAttributes();
        if (attributes.hasAttribute(Attributes.MAX_HEALTH)) {
            //noinspection ConstantConditions
            attributes.getInstance(Attributes.MAX_HEALTH).removeModifier(AbilityUUIDs.HEALTH_REDUCTION);
        }
        boolean shouldReapplyHealthReduction = false;
        ability = manager.get(AMAbilities.LIGHT_HEALTH_REDUCTION.getId());
        if (player.getLevel().getBrightness(LightLayer.SKY, player.blockPosition()) == 15 && ability.test(player)) {
            shouldReapplyHealthReduction = true;
        }
        ability = manager.get(AMAbilities.WATER_HEALTH_REDUCTION.getId());
        if (player.isInWaterOrBubble() && ability.test(player)) {
            shouldReapplyHealthReduction = true;
        }
        if (shouldReapplyHealthReduction && attributes.hasAttribute(Attributes.MAX_HEALTH)) {
            //noinspection ConstantConditions
            attributes.getInstance(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(AbilityUUIDs.HEALTH_REDUCTION, "Health Reduction Ability", -helper.getAffinityDepthOrElse(player, ability.affinity(), 0) * 4, AttributeModifier.Operation.ADDITION));
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
}
