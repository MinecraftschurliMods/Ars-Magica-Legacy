package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
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

public class ManaBlast extends SpellComponent.CastEntity {
    public ManaBlast() {
        super(AMSpells.DAMAGE_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        ManaHelper helper = ArsMagicaApi.manaHelper();
        double mana = helper.getMana(caster);
        if (context.level() instanceof ServerLevel level) {
            entity.hurtServer(level, level.damageSources().indirectMagic(caster, context.directEntity()), (float) ArsMagicaApi.spellHelper().getModifiedStat(mana * AMServerConfig.MANA_BLAST_FACTOR.get(), AMSpells.DAMAGE_STAT, modifiers, context));
        }
        helper.decreaseMana(caster, mana);
        return SpellComponentCastResult.success(spell);
    }
}
