package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrimarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Self extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        return caster != null
            ? ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, new EntityHitResult(caster)))
            : new SpellCastResult(context.spell()).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER);
    }
}
