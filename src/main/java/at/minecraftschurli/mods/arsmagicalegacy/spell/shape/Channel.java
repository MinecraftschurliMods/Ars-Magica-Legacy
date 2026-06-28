package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Channel extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        return caster != null
            ? ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, new EntityHitResult(caster)))
            : new SpellCastResult(context.spell()).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER);
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
