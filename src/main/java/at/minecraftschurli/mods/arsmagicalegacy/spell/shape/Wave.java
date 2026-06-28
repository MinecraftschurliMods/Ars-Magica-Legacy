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

public class Wave extends PrimarySpellShape {
    public Wave() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.RANGE_STAT, AMSpells.SPEED_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        LivingEntity caster = context.caster();
        if (level.isClientSide() || caster == null) return new SpellCastResult(spell);
        var wave = AMEntities.WAVE.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        wave.setPos(caster.getEyePosition());
        wave.setXRot(caster.getXRot());
        wave.setYRot(caster.getYRot());
        wave.setOwner(caster);
        wave.setSpell(spell);
        wave.setConsume(context.consume());
        wave.setAwardXp(context.awardXp());
        SpellHelper helper = ArsMagicaApi.spellHelper();
        wave.setDeltaMovement(caster.getLookAngle().scale(helper.getModifiedStat(AMServerConfig.WAVE_SPEED.get(), AMSpells.SPEED_STAT, modifiers, context)));
        wave.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        wave.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0);
        wave.setDuration((int) helper.getModifiedStat(AMServerConfig.WAVE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        wave.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, context) * AMServerConfig.WAVE_GRAVITY.get()));
        wave.setRange((float) helper.getModifiedStat(AMServerConfig.WAVE_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(wave);
        return new SpellCastResult(spell).setSuccess();
    }
}
