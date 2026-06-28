package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
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
