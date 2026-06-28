package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Contingency extends SecondarySpellShape {
    private final Identifier contingency;

    public Contingency(Identifier contingency) {
        this.contingency = contingency;
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (context.hitResult() instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            ArsMagicaApi.spellHelper().setContingency(living, contingency, spell);
            return new SpellCastResult(spell).setSuccess();
        }
        return new SpellCastResult(spell).setMessage(AMTranslations.SPELL_FAIL_NO_ENTITY);
    }
}
