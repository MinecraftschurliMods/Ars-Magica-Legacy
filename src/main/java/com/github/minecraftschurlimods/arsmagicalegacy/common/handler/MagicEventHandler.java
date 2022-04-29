package com.github.minecraftschurlimods.arsmagicalegacy.common.handler;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.AffinityChangingEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.PlayerLevelUpEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellEvent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IBurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.IManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AbilityUUIDs;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSkillPoints;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.network.UpdateStepHeightPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.Set;

final class MagicEventHandler {
    static void init(IEventBus forgeBus, IEventBus modBus) {
        forgeBus.addListener(MagicEventHandler::playerLevelUp);
        forgeBus.addListener(MagicEventHandler::spellCastPost);
        forgeBus.addListener(MagicEventHandler::affinityChangingPost);
        forgeBus.addListener(MagicEventHandler::manaCostPre);
    }

    private static void playerLevelUp(PlayerLevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();
        var api = ArsMagicaAPI.get();
        if (level == 1) {
            api.getSkillHelper().addSkillPoint(player, AMSkillPoints.BLUE.getId(), Config.SERVER.EXTRA_BLUE_SKILL_POINTS.get());
        }
        for (ISkillPoint iSkillPoint : api.getSkillPointRegistry()) {
            int minEarnLevel = iSkillPoint.getMinEarnLevel();
            if (level >= minEarnLevel && (level - minEarnLevel) % iSkillPoint.getLevelsForPoint() == 0) {
                api.getSkillHelper().addSkillPoint(player, iSkillPoint);
                event.getPlayer().getLevel().playSound(null, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), AMSounds.GET_KNOWLEDGE_POINT.get(), SoundSource.PLAYERS, 1f, 1f);
            }
        }
        float newMaxMana = Config.SERVER.MANA_BASE.get().floatValue() + Config.SERVER.MANA_MULTIPLIER.get().floatValue() * (level - 1);
        AttributeInstance maxManaAttr = player.getAttribute(AMAttributes.MAX_MANA.get());
        if (maxManaAttr != null) {
            IManaHelper manaHelper = api.getManaHelper();
            maxManaAttr.setBaseValue(newMaxMana);
            manaHelper.increaseMana(player, (newMaxMana - manaHelper.getMana(player)) / 2);
        }
        float newMaxBurnout = Config.SERVER.BURNOUT_BASE.get().floatValue() + Config.SERVER.BURNOUT_MULTIPLIER.get().floatValue() * (level - 1);
        AttributeInstance maxBurnoutAttr = player.getAttribute(AMAttributes.MAX_BURNOUT.get());
        if (maxBurnoutAttr != null) {
            IBurnoutHelper burnoutHelper = api.getBurnoutHelper();
            maxBurnoutAttr.setBaseValue(newMaxBurnout);
            burnoutHelper.decreaseBurnout(player, burnoutHelper.getBurnout(player) / 2);
        }
        event.getPlayer().getLevel().playSound(null, event.getPlayer().getX(), event.getPlayer().getY(), event.getPlayer().getZ(), AMSounds.MAGIC_LEVEL_UP.get(), SoundSource.PLAYERS, 1f, 1f);
    }

    private static void manaCostPre(SpellEvent.ManaCost.Pre event) {
        LivingEntity caster = event.getEntityLiving();
        float cost = event.getBase();
        if (caster instanceof Player player) {
            var api = ArsMagicaAPI.get();
            for (ISpellPart iSpellPart : event.getSpell().parts()) {
                if (iSpellPart.getType() != ISpellPart.SpellPartType.COMPONENT) continue;
                ISpellPartData dataForPart = api.getSpellDataManager().getDataForPart(iSpellPart);
                if (dataForPart == null) continue;
                Set<IAffinity> affinities = dataForPart.affinities();
                for (IAffinity aff : affinities) {
                    double value = api.getAffinityHelper().getAffinityDepth(player, aff);
                    if (value > 0) {
                        cost -= (float) (cost * (0.5f * value / 100f));
                    }
                }
            }
            var helper = api.getAffinityHelper();
            IAbilityData ability = api.getAbilityManager().get(AMAbilities.MANA_REDUCTION.getId());
            if (ability.test(player)) {
                cost *= 1 - (helper.getAffinityDepth(player, ability.affinity())) * 0.5f;
            }
        }
        event.setBase(cost);
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
            if (ability.range().test(helper.getAffinityDepth(player, affinity))) {
                attributes.getInstance(ForgeMod.SWIM_SPEED.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SWIM_SPEED, "Swim Speed Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.HASTE.getId());
        if (affinity == ability.affinity()) {
            if (ability.range().test(helper.getAffinityDepth(player, affinity))) {
                attributes.getInstance(Attributes.ATTACK_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.HASTE, "Haste Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.GRAVITY.getId());
        if (affinity == ability.affinity()) {
            if (ability.range().test(helper.getAffinityDepth(player, affinity))) {
                attributes.getInstance(ForgeMod.ENTITY_GRAVITY.get()).addPermanentModifier(new AttributeModifier(AbilityUUIDs.GRAVITY, "Gravity Ability", helper.getAffinityDepth(player, affinity) * 0.5f, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        ability = manager.get(AMAbilities.SLOWNESS.getId());
        if (affinity == ability.affinity()) {
            if (ability.range().test(helper.getAffinityDepth(player, affinity))) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SLOWNESS, "Slowness Ability", -(helper.getAffinityDepth(player, affinity) - ability.range().min().orElse(0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = manager.get(AMAbilities.SPEED.getId());
        if (affinity == ability.affinity()) {
            if (ability.range().test(helper.getAffinityDepth(player, affinity))) {
                attributes.getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(new AttributeModifier(AbilityUUIDs.SPEED, "Speed Ability", (helper.getAffinityDepth(player, affinity) - ability.range().min().orElse(0d)) * 0.1f, AttributeModifier.Operation.ADDITION));
            }
        }
        ability = manager.get(AMAbilities.STEP_ASSIST.getId());
        if (affinity == ability.affinity()) {
            float stepHeight = ability.range().test(helper.getAffinityDepth(player, affinity)) ? 1f : 0.6f;
            player.maxUpStep = stepHeight;
            ArsMagicaLegacy.NETWORK_HANDLER.sendToPlayer(new UpdateStepHeightPacket(stepHeight), player);
        }
        ability = manager.get(AMAbilities.POISON_RESISTANCE.getId());
        if (affinity == ability.affinity() && ability.range().test(helper.getAffinityDepth(player, affinity)) && player.hasEffect(MobEffects.POISON)) {
            player.removeEffect(MobEffects.POISON);
        }
    }

    private static void spellCastPost(SpellEvent.Cast.Post event) {
        if (event.getEntityLiving() instanceof Player player) {
            var api = ArsMagicaAPI.get();
            var helper = api.getAffinityHelper();
            IAbilityData ability = api.getAbilityManager().get(AMAbilities.CLARITY.getId());
            if (ability.test(player) && player.getLevel().getRandom().nextBoolean()) {
                player.addEffect(new MobEffectInstance(AMMobEffects.CLARITY.get(), 1200));
            }
        }
    }
}
