package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrimarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class Beam extends PrimarySpellShape {
    public Beam() {
        super(AMSpells.TARGET_NON_SOLID_STAT);
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        return caster == null
            ? new SpellCastResult(context.spell()).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER)
            : ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, AMUtil.getHitResult(caster, modifiers, context, AMServerConfig.BEAM_RANGE.get(), 0)));
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
