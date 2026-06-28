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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class ManaDrain extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        ManaHelper helper = ArsMagicaApi.manaHelper();
        double mana = Math.min(helper.getMana(entity), AMServerConfig.MANA_DRAIN_MAX.get());
        helper.decreaseMana(entity, mana);
        helper.increaseMana(caster, mana);
        return SpellComponentCastResult.success(spell);
    }
}
