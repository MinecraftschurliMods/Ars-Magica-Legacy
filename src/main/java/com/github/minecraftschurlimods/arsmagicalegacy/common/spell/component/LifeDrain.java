package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class LifeDrain extends SpellComponent.CastEntity {
    public LifeDrain() {
        super(AMSpells.DAMAGE_STAT, AMSpells.HEALING_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        float damage = (float) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.LIFE_DRAIN_DAMAGE.get(), entity.isInvertedHealAndHarm() ? AMSpells.HEALING_STAT : AMSpells.DAMAGE_STAT, modifiers, context);
        if (entity.hurtServer(level, level.damageSources().indirectMagic(caster, context.directEntity()), damage)) {
            caster.heal(damage);
        }
        return SpellComponentCastResult.success(spell);
    }
}
