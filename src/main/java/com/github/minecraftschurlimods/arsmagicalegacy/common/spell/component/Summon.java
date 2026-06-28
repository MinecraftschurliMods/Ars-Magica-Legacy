package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.BurnoutHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.attachment.SummonMinionsAttachment;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

import java.util.List;

public class Summon extends SpellComponent {
    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        HitResult hitResult = context.hitResult();
        if (hitResult == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        EntityType<?> type = spell.dataComponents().grammar().get(AMDataComponents.SPELL_SUMMON.get());
        if (type == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_SUMMON_NO_SELECTION);
        SummonMinionsAttachment attachment = caster.getData(AMAttachments.SUMMON_MINIONS);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (attachment.size() >= helper.getMaxSummons(caster)) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_SUMMON_TOO_MANY);
        if (!(type.create(level, EntitySpawnReason.MOB_SUMMONED) instanceof Mob mob)) return SpellComponentCastResult.pass(spell);
        mob.setPos(hitResult.getLocation());
        EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(mob.blockPosition()), EntitySpawnReason.MOB_SUMMONED, null);
        if (context.consume() && !(caster instanceof Player player && player.isCreative())) {
            ManaHelper manaHelper = ArsMagicaApi.manaHelper();
            BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
            double manaCost = mob.getMaxHealth() * AMServerConfig.SUMMON_MANA_COST.get();
            double mana = manaHelper.getMana(caster);
            if (mana <= manaCost) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NOT_ENOUGH_MANA);
            if (mana <= manaCost + burnoutHelper.getBurnout(caster)) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_BURNED_OUT);
            manaHelper.decreaseMana(caster, manaCost + manaCost * helper.getManaToBurnoutRatio());
            burnoutHelper.increaseBurnout(caster, manaCost);
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            mob.setDropChance(slot, 0);
        }
        mob.setCanPickUpLoot(true);
        mob.setData(AMAttachments.SUMMON_OWNER, caster.getUUID());
        caster.setData(AMAttachments.SUMMON_MINIONS, attachment.add(mob.getUUID()));
        if (mob instanceof TamableAnimal animal) {
            animal.setOwner(caster);
        }
        level.addFreshEntity(mob);
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_SUMMON.get();
    }
}
