package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AbilityUUIDs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMTalents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.LightLayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Objects;

/**
 * Holds the living and player tick event handlers. These get special treatment as these events should not have more than one event handler per mod.
 */
final class TickHandler {
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(TickHandler::livingUpdate);
        forgeBus.addListener(TickHandler::playerTick);
    }

    private static void livingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(AMMobEffects.WATERY_GRAVE.value()) && (entity.isInWaterOrBubble() || entity.getPose() == Pose.SWIMMING)) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), entity.getPose() == Pose.SWIMMING ? 0 : Math.min(0, entity.getDeltaMovement().y()), entity.getDeltaMovement().z());
        }
        if (event.getEntity().isOnFire()) {
            ArsMagicaAPI.get().getContingencyHelper().triggerContingency(event.getEntity(), ContingencyType.FIRE);
        }
    }

    private static void playerTick(TickEvent.PlayerTickEvent event) {
        switch (event.side) {
            case CLIENT -> {
                switch (event.phase) {
                    case START -> playerTickClientStart(event.player);
                    case END -> playerTickClientEnd(event.player);
                }
            }
            case SERVER -> {
                switch (event.phase) {
                    case START -> playerTickServerStart(event.player);
                    case END -> playerTickServerEnd(event.player);
                }
            }
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
        var magicHelper = api.getMagicHelper();
        var skillHelper = api.getSkillHelper();
        if (player.isDeadOrDying() || !magicHelper.knowsMagic(player)) return;
        if (!magicHelper.knowsMagic(player)) return;
        float factor = 1f;
        if (skillHelper.knows(player, AMTalents.MANA_REGEN_BOOST_3)) {
            factor = 1.15f;
        } else if (skillHelper.knows(player, AMTalents.MANA_REGEN_BOOST_2)) {
            factor = 1.1f;
        } else if (skillHelper.knows(player, AMTalents.MANA_REGEN_BOOST_1)) {
            factor = 1.05f;
        }
        api.getManaHelper().increaseMana(player, (float) player.getAttributeValue(AMAttributes.MANA_REGEN.value()) * factor);
        api.getBurnoutHelper().decreaseBurnout(player, (float) player.getAttributeValue(AMAttributes.BURNOUT_REGEN.value()) * factor);
    }

    private static void handleAbilities(final Player player) {
        if (player.isDeadOrDying()) return;
        var api = ArsMagicaAPI.get();
        var manager = player.level().registryAccess().registryOrThrow(Ability.REGISTRY_KEY);
        var helper = api.getAffinityHelper();
        Ability ability;
        if (!player.isCreative()) {
            // todo use custom damage types
            ability = manager.get(AMAbilities.WATER_DAMAGE_FIRE);
            if (ability != null && ability.test(player) && player.isInWater() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(player.damageSources().fellOutOfWorld(), 1);
            }
            ability = manager.get(AMAbilities.WATER_DAMAGE_LIGHTNING);
            if (ability != null && ability.test(player) && player.isInWater() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(player.damageSources().fellOutOfWorld(), 1);
            }
            ability = manager.get(AMAbilities.NETHER_DAMAGE_WATER);
            if (ability != null && ability.test(player) && player.level().dimensionType().ultraWarm() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(player.damageSources().fellOutOfWorld(), 1);
            }
            ability = manager.get(AMAbilities.NETHER_DAMAGE_NATURE);
            if (ability != null && ability.test(player) && player.level().dimensionType().ultraWarm() && player.tickCount % 20 == 0 && (player.getHealth() - 1) / player.getMaxHealth() >= 0.75) {
                player.hurt(player.damageSources().fellOutOfWorld(), 1);
            }
        }
        ability = manager.get(AMAbilities.SATURATION);
        if (ability != null && ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)), 0, false, false));
        }
        ability = manager.get(AMAbilities.REGENERATION);
        if (ability != null && ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)), 0, false, false));
        }
        ability = manager.get(AMAbilities.NIGHT_VISION);
        if (ability != null && ability.test(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, (int) (20 * helper.getAffinityDepthOrElse(player, ability.affinity(), 0)) + 200, 0, false, false));
        }
        ability = manager.get(AMAbilities.FROST_WALKER);
        if (ability != null && ability.test(player)) {
            FrostWalkerEnchantment.onEntityMoved(player, player.level(), player.blockPosition(), 1);
        }
        AttributeMap attributes = player.getAttributes();
        AttributeInstance maxHealth = attributes.getInstance(Attributes.MAX_HEALTH);
        assert maxHealth != null;
        maxHealth.removeModifier(AbilityUUIDs.HEALTH_REDUCTION);
        Ability lightHealthReduction = manager.get(AMAbilities.LIGHT_HEALTH_REDUCTION);
        Ability waterHealthReduction = manager.get(AMAbilities.WATER_HEALTH_REDUCTION);
        if ((lightHealthReduction != null
             && player.level().getBrightness(LightLayer.SKY, player.blockPosition()) == 15
             && lightHealthReduction.test(player))
            || (waterHealthReduction != null
                && player.isInWaterOrBubble()
                && waterHealthReduction.test(player))) {
            double depth;
            if (waterHealthReduction != null && lightHealthReduction != null) {
                depth = Math.max(helper.getAffinityDepth(player, lightHealthReduction.affinity()), helper.getAffinityDepth(player, waterHealthReduction.affinity()));
            } else {
                depth = helper.getAffinityDepthOrElse(player, Objects.requireNonNullElse(waterHealthReduction, lightHealthReduction).affinity(), 0);
            }
            maxHealth.addPermanentModifier(new AttributeModifier(AbilityUUIDs.HEALTH_REDUCTION, "Health Reduction Ability", -depth * 4, AttributeModifier.Operation.ADDITION));
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }
}
