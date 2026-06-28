package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
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
