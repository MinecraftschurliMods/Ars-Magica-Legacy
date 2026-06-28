package at.minecraftschurli.mods.arsmagicalegacy.spell.component;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class Heal extends SpellComponent.CastEntity {
    public static final Identifier UNDEAD_PARTICLES = ArsMagicaApi.id("heal_undead");

    public Heal() {
        super(AMSpells.HEALING_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity living)) return SpellComponentCastResult.pass(spell);
        float healing = (float) ArsMagicaApi.spellHelper().getModifiedStat(2, AMSpells.HEALING_STAT, modifiers, context);
        if (!living.isInvertedHealAndHarm()) {
            living.heal(healing);
        } else if (context.level() instanceof ServerLevel level) {
            LivingEntity caster = context.caster();
            living.hurtServer(level, caster != null ? level.damageSources().indirectMagic(caster, context.directEntity()) : level.damageSources().magic(), healing);
        }
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public void spawnParticles(List<SpellModifier> modifiers, SpellCastContext context) {
        Entity directEntity = context.directEntity();
        HitResult hitResult = context.hitResult();
        if (directEntity == null || !(hitResult instanceof EntityHitResult entityHitResult) || !(entityHitResult.getEntity() instanceof LivingEntity living)) return;
        if (living.isInvertedHealAndHarm()) {
            AMClientUtil.spawnParticles(UNDEAD_PARTICLES, directEntity.position(), ArsMagicaApi.spellHelper().getColor(modifiers, context.spell(), -1), context.caster(), directEntity, hitResult);
        } else {
            super.spawnParticles(modifiers, context);
        }
    }
}
