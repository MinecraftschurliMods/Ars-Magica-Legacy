package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbilityUUIDs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.network.UpdateStepHeightPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Objects;

final class AbilityHandler {
    static void init(IEventBus forgeBus) {
        forgeBus.addListener(EventPriority.HIGHEST, AbilityHandler::livingDeath);
        forgeBus.addListener(EventPriority.LOWEST, AbilityHandler::livingHurt);
        forgeBus.addListener(AbilityHandler::livingJump);
        forgeBus.addListener(AbilityHandler::livingFall);
        forgeBus.addListener(AbilityHandler::enderManAnger);
        forgeBus.addListener(AbilityHandler::potionApplicable);
        forgeBus.addListener(EventPriority.LOW, AbilityHandler::manaCostPre);
        forgeBus.addListener(AbilityHandler::affinityChangingPost);
        forgeBus.addListener(AbilityHandler::spellCastPost);
    }

    private static void livingDeath(LivingDeathEvent event) {
        if (event.getEntity().isInvertedHealAndHarm()) return;
        if (event.getSource().getEntity() instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var helper = api.getAffinityHelper();
            Ability ability = player.getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY).get(AMAbilities.NAUSEA);
            if (ability != null && ability.test(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (600 * helper.getAffinityDepth(player, ability.affinity()))));
            }
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();
        var api = ArsMagicaAPI.get();
        var helper = api.getAffinityHelper();
        var abilityRegistry = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY);
        if (event.getSource().getEntity() instanceof Player player) {
            Ability ability = abilityRegistry.get(AMAbilities.FIRE_PUNCH);
            if (ability != null && ability.test(player) && !entity.fireImmune()) {
                entity.setSecondsOnFire((int) (5 * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = abilityRegistry.get(AMAbilities.FROST_PUNCH);
            if (ability != null && ability.test(player) && entity.canFreeze()) {
                entity.addEffect(new MobEffectInstance(AMMobEffects.FROST.get(), (int) (100 * helper.getAffinityDepth(player, ability.affinity()))));
            }
            ability = abilityRegistry.get(AMAbilities.SMITE);
            if (ability != null && ability.test(player) && entity.getMobType() == MobType.UNDEAD) {
                event.setAmount((float) (event.getAmount() + helper.getAffinityDepth(player, ability.affinity()) * 4));
            }
        }
        if (entity instanceof Player player) {
            if (!api.getMagicHelper().knowsMagic(player)) return;
            Ability ability = abilityRegistry.get(AMAbilities.THORNS);
            if (ability != null && ability.test(player) && event.getSource().getEntity() != null) {
                event.getSource().getEntity().hurt(event.getSource(), (float) (event.getAmount() * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = abilityRegistry.get(AMAbilities.ENDERMAN_THORNS);
            if (ability != null && ability.test(player) && event.getSource().getEntity() instanceof EnderMan enderMan) {
                enderMan.hurt(event.getSource(), (float) (event.getAmount() * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = abilityRegistry.get(AMAbilities.RESISTANCE);
            if (ability != null && ability.test(player)) {
                event.setAmount((float) (event.getAmount() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = abilityRegistry.get(AMAbilities.FIRE_RESISTANCE);
            if (ability != null && ability.test(player) && event.getSource().isFire()) {
                event.setAmount((float) (event.getAmount() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = abilityRegistry.get(AMAbilities.MAGIC_DAMAGE);
            if (ability != null && ability.test(player) && event.getSource().isMagic()) {
                event.setAmount((float) (event.getAmount() * (1 + helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
        }
    }

    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isDeadOrDying()) return;
        if (entity instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var abilityRegistry = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY);
            var helper = api.getAffinityHelper();
            Ability ability = abilityRegistry.get(AMAbilities.JUMP_BOOST);
            if (ability != null && ability.test(player)) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5f * helper.getAffinityDepth(player, ability.affinity()), 0));
            }
        }
    }

    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.isDeadOrDying()) return;
        if (entity instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var abilityRegistry = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY);
            var helper = api.getAffinityHelper();
            Ability ability = abilityRegistry.get(AMAbilities.FEATHER_FALLING);
            if (ability != null && ability.test(player)) {
                event.setDistance((float) (event.getDistance() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = abilityRegistry.get(AMAbilities.FALL_DAMAGE);
            if (ability != null && ability.test(player)) {
                event.setDistance((float) (event.getDistance() / (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
        }
    }

    private static void enderManAnger(EnderManAngerEvent event) {
        Player player = event.getPlayer();
        var api = ArsMagicaAPI.get();
        if (!api.getMagicHelper().knowsMagic(player)) return;
        Ability ability = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY).get(AMAbilities.ENDERMAN_PUMPKIN);
        if (ability != null && ability.test(player)) {
            event.setCanceled(true);
        }
    }

    private static void potionApplicable(MobEffectEvent.Applicable event) {
        Ability ability = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY).get(AMAbilities.POISON_RESISTANCE);
        if (ability != null && event.getEntity() instanceof Player player && event.getEffectInstance().getEffect() == MobEffects.POISON && ability.test(player)) {
            event.setResult(Event.Result.DENY);
        }
    }

    private static void manaCostPre(SpellEvent.ManaCost.Pre event) {
        LivingEntity caster = event.getEntity();
        if (caster instanceof Player player) {
            var api = ArsMagicaAPI.get();
            Ability ability = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY).get(AMAbilities.MANA_REDUCTION);
            if (ability != null && ability.test(player)) {
                event.setBase(event.getBase() * (float) (1 - (api.getAffinityHelper().getAffinityDepth(player, ability.affinity())) * 0.5f));
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void affinityChangingPost(AffinityChangingEvent.Post event) {
        var api = ArsMagicaAPI.get();
        var abilityRegistry = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY);
        var helper = api.getAffinityHelper();
        Affinity affinity = event.affinity;
        Player player = event.getEntity();
        AttributeMap attributes = player.getAttributes();
        attributes.getInstance(ForgeMod.SWIM_SPEED.get()).removeModifier(AbilityUUIDs.SWIM_SPEED);
        attributes.getInstance(Attributes.ATTACK_SPEED).removeModifier(AbilityUUIDs.HASTE);
        attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).removeModifier(AbilityUUIDs.GRAVITY);
        attributes.getInstance(Attributes.MOVEMENT_SPEED).removeModifier(AbilityUUIDs.SLOWNESS);
        attributes.getInstance(Attributes.MOVEMENT_SPEED).removeModifier(AbilityUUIDs.SPEED);
        Ability ability = abilityRegistry.get(AMAbilities.SWIM_SPEED);
        if (ability != null && affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(ForgeMod.SWIM_SPEED.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SWIM_SPEED, "Swim Speed Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = abilityRegistry.get(AMAbilities.HASTE);
        if (ability != null && affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.ATTACK_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.HASTE, "Haste Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = abilityRegistry.get(AMAbilities.GRAVITY);
        if (ability != null && affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.GRAVITY, "Gravity Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = abilityRegistry.get(AMAbilities.SLOWNESS);
        if (ability != null && affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SLOWNESS, "Slowness Ability", -(helper.getAffinityDepth(player, affinity) - Objects.requireNonNullElse(ability.bounds().getMin(), 0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = abilityRegistry.get(AMAbilities.SPEED);
        if (ability != null && affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SPEED, "Speed Ability", (helper.getAffinityDepth(player, affinity) - Objects.requireNonNullElse(ability.bounds().getMin(), 0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = abilityRegistry.get(AMAbilities.STEP_ASSIST);
        if (ability != null && affinity == ability.affinity()) {
            float stepHeight = ability.test(player) ? 1f : 0.6f;
            player.maxUpStep = stepHeight;//TODO move to attribute modifier
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(stepHeight), player);
        }
        ability = abilityRegistry.get(AMAbilities.POISON_RESISTANCE);
        if (ability != null && affinity == ability.affinity() && ability.test(player) && player.hasEffect(MobEffects.POISON)) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    private static void spellCastPost(SpellEvent.Cast.Post event) {
        if (event.getEntity() instanceof Player player) {
            Ability ability = event.getEntity().getLevel().registryAccess().registryOrThrow(Ability.REGISTRY_KEY).get(AMAbilities.CLARITY);
            if (ability != null && ability.test(player) && player.getLevel().getRandom().nextBoolean()) {
                player.addEffect(new MobEffectInstance(AMMobEffects.CLARITY.get(), 1200));
            }
        }
    }
}
