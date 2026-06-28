package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

public class Projectile extends PrimarySpellShape {
    public Projectile() {
        super(SpellStat.COLOR, AMSpells.BOUNCE_STAT, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.PIERCING_STAT, AMSpells.SPEED_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        LivingEntity caster = context.caster();
        if (level.isClientSide() || caster == null) return new SpellCastResult(spell);
        var projectile = AMEntities.PROJECTILE.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        projectile.setPos(caster.getEyePosition());
        projectile.setXRot(caster.getXRot());
        projectile.setYRot(caster.getYRot());
        projectile.setOwner(caster);
        projectile.setSpell(spell);
        projectile.setConsume(context.consume());
        projectile.setAwardXp(context.awardXp());
        SpellHelper helper = ArsMagicaApi.spellHelper();
        projectile.setDeltaMovement(caster.getLookAngle().scale(helper.getModifiedStat(AMServerConfig.PROJECTILE_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context)));
        projectile.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        projectile.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0);
        projectile.setDuration((int) helper.getModifiedStat(AMServerConfig.PROJECTILE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        projectile.setBounces((int) helper.getModifiedStat(0, AMSpells.BOUNCE_STAT, modifiers, context));
        projectile.setPierces((int) helper.getModifiedStat(0, AMSpells.PIERCING_STAT, modifiers, context));
        projectile.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, context) * AMServerConfig.PROJECTILE_GRAVITY.get()));
        level.addFreshEntity(projectile);
        return new SpellCastResult(spell).setSuccess();
    }
}
