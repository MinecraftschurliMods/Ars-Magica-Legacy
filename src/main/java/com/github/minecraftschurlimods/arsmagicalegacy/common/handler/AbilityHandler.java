package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
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
import net.minecraftforge.event.entity.living.PotionEvent;
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
        if (event.getEntityLiving().isInvertedHealAndHarm()) return;
        if (event.getSource().getEntity() instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var helper = api.getAffinityHelper();
            IAbilityData ability = api.getAbilityManager().get(AMAbilities.NAUSEA.getId());
            if (ability.test(player)) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (600 * helper.getAffinityDepth(player, ability.affinity()))));
            }
        }
    }

    private static void livingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getSource().getEntity() instanceof Player player) {
            var api = ArsMagicaAPI.get();
            var manager = api.getAbilityManager();
            var helper = api.getAffinityHelper();
            IAbilityData ability = manager.get(AMAbilities.FIRE_PUNCH.getId());
            if (ability.test(player) && !entity.fireImmune()) {
                entity.setSecondsOnFire((int) (5 * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = manager.get(AMAbilities.FROST_PUNCH.getId());
            if (ability.test(player) && entity.canFreeze()) {
                entity.addEffect(new MobEffectInstance(AMMobEffects.FROST.get(), (int) (100 * helper.getAffinityDepth(player, ability.affinity()))));
            }
            ability = manager.get(AMAbilities.SMITE.getId());
            if (ability.test(player) && entity.getMobType() == MobType.UNDEAD) {
                event.setAmount((float) (event.getAmount() + helper.getAffinityDepth(player, ability.affinity()) * 4));
            }
        }
        if (entity instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var manager = api.getAbilityManager();
            var helper = api.getAffinityHelper();
            IAbilityData ability = manager.get(AMAbilities.THORNS.getId());
            if (ability.test(player) && event.getSource().getEntity() != null) {
                event.getSource().getEntity().hurt(event.getSource(), (float) (event.getAmount() * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = manager.get(AMAbilities.ENDERMAN_THORNS.getId());
            if (ability.test(player) && event.getSource().getEntity() instanceof EnderMan enderMan) {
                enderMan.hurt(event.getSource(), (float) (event.getAmount() * helper.getAffinityDepth(player, ability.affinity())));
            }
            ability = manager.get(AMAbilities.RESISTANCE.getId());
            if (ability.test(player)) {
                event.setAmount((float) (event.getAmount() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = manager.get(AMAbilities.FIRE_RESISTANCE.getId());
            if (ability.test(player) && event.getSource().isFire()) {
                event.setAmount((float) (event.getAmount() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = manager.get(AMAbilities.MAGIC_DAMAGE.getId());
            if (ability.test(player) && event.getSource().isMagic()) {
                event.setAmount((float) (event.getAmount() * (1 + helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
        }
    }

    private static void livingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isDeadOrDying()) return;
        if (entity instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var manager = api.getAbilityManager();
            var helper = api.getAffinityHelper();
            IAbilityData ability = manager.get(AMAbilities.JUMP_BOOST.getId());
            if (ability.test(player)) {
                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.5f * helper.getAffinityDepth(player, ability.affinity()), 0));
            }
        }
    }

    private static void livingFall(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.isDeadOrDying()) return;
        if (entity instanceof Player player) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(player)) return;
            var manager = api.getAbilityManager();
            var helper = api.getAffinityHelper();
            IAbilityData ability = manager.get(AMAbilities.FEATHER_FALLING.getId());
            if (ability.test(player)) {
                event.setDistance((float) (event.getDistance() * (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
            ability = manager.get(AMAbilities.FALL_DAMAGE.getId());
            if (ability.test(player)) {
                event.setDistance((float) (event.getDistance() / (1 - helper.getAffinityDepth(player, ability.affinity()) / 2)));
            }
        }
    }

    private static void enderManAnger(EnderManAngerEvent event) {
        Player player = event.getPlayer();
        var api = ArsMagicaAPI.get();
        if (!api.getMagicHelper().knowsMagic(player)) return;
        IAbilityData ability = api.getAbilityManager().get(AMAbilities.ENDERMAN_PUMPKIN.getId());
        if (ability.test(player)) {
            event.setCanceled(true);
        }
    }

    private static void potionApplicable(PotionEvent.PotionApplicableEvent event) {
        var api = ArsMagicaAPI.get();
        IAbilityData ability = api.getAbilityManager().get(AMAbilities.POISON_RESISTANCE.getId());
        if (event.getEntityLiving() instanceof Player player && event.getPotionEffect().getEffect() == MobEffects.POISON && ability.test(player)) {
            event.setResult(Event.Result.DENY);
        }
    }

    private static void manaCostPre(SpellEvent.ManaCost.Pre event) {
        LivingEntity caster = event.getEntityLiving();
        if (caster instanceof Player player) {
            var api = ArsMagicaAPI.get();
            IAbilityData ability = api.getAbilityManager().get(AMAbilities.MANA_REDUCTION.getId());
            if (ability.test(player)) {
                event.setBase(event.getBase() * (float) (1 - (api.getAffinityHelper().getAffinityDepth(player, ability.affinity())) * 0.5f));
            }
        }
    }

    private static void affinityChangingPost(AffinityChangingEvent.Post event) {
        var api = ArsMagicaAPI.get();
        var manager = api.getAbilityManager();
        var helper = api.getAffinityHelper();
        IAffinity affinity = event.affinity;
        Player player = event.getPlayer();
        AttributeMap attributes = player.getAttributes();
        attributes.getInstance(ForgeMod.SWIM_SPEED.get()).removeModifier(AbilityUUIDs.SWIM_SPEED);
        attributes.getInstance(Attributes.ATTACK_SPEED).removeModifier(AbilityUUIDs.HASTE);
        attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).removeModifier(AbilityUUIDs.GRAVITY);
        attributes.getInstance(Attributes.MOVEMENT_SPEED).removeModifier(AbilityUUIDs.SLOWNESS);
        attributes.getInstance(Attributes.MOVEMENT_SPEED).removeModifier(AbilityUUIDs.SPEED);
        IAbilityData ability = manager.get(AMAbilities.SWIM_SPEED.getId());
        if (affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(ForgeMod.SWIM_SPEED.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SWIM_SPEED, "Swim Speed Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.HASTE.getId());
        if (affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.ATTACK_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.HASTE, "Haste Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.GRAVITY.getId());
        if (affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.GRAVITY, "Gravity Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.SLOWNESS.getId());
        if (affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SLOWNESS, "Slowness Ability", -(helper.getAffinityDepth(player, affinity) - Objects.requireNonNullElse(ability.bounds().getMin(), 0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = manager.get(AMAbilities.SPEED.getId());
        if (affinity == ability.affinity()) {
            if (ability.test(player)) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SPEED, "Speed Ability", (helper.getAffinityDepth(player, affinity) - Objects.requireNonNullElse(ability.bounds().getMin(), 0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = manager.get(AMAbilities.STEP_ASSIST.getId());
        if (affinity == ability.affinity()) {
            float stepHeight = ability.test(player) ? 1f : 0.6f;
            player.maxUpStep = stepHeight;
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(stepHeight), player);
        }
        ability = manager.get(AMAbilities.POISON_RESISTANCE.getId());
        if (affinity == ability.affinity() && ability.test(player) && player.hasEffect(MobEffects.POISON)) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    private static void spellCastPost(SpellEvent.Cast.Post event) {
        if (event.getEntityLiving() instanceof Player player) {
            IAbilityData ability = ArsMagicaAPI.get().getAbilityManager().get(AMAbilities.CLARITY.getId());
            if (ability.test(player) && player.getLevel().getRandom().nextBoolean()) {
                player.addEffect(new MobEffectInstance(AMMobEffects.CLARITY.get(), 1200));
            }
        }
    }
}
