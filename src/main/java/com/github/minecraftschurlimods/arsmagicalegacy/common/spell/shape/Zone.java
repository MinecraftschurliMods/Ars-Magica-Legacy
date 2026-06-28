package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SecondarySpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStat;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;

import java.util.List;

public class Zone extends SecondarySpellShape {
    public Zone() {
        super(SpellStat.COLOR, AMSpells.DURATION_STAT, AMSpells.GRAVITY_STAT, AMSpells.RANGE_STAT, AMSpells.TARGET_NON_SOLID_STAT);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        Level level = context.level();
        Entity directEntity = context.directEntity();
        if (level.isClientSide() || directEntity == null) return new SpellCastResult(spell);
        var zone = AMEntities.ZONE.get().create(level, EntitySpawnReason.MOB_SUMMONED);
        zone.setPos(directEntity.getEyePosition());
        zone.setXRot(directEntity.getXRot());
        zone.setYRot(directEntity.getYRot());
        zone.setOwner(context.caster());
        zone.setSpell(spell);
        zone.setConsume(context.consume());
        zone.setAwardXp(context.awardXp());
        SpellHelper helper = ArsMagicaApi.spellHelper();
        zone.setColor(helper.getColor(modifiers, spell, spell.activeShapeGroup()));
        zone.setTargetNonSolid(helper.getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0);
        zone.setDuration((int) helper.getModifiedStat(AMServerConfig.ZONE_DURATION.get(), AMSpells.DURATION_STAT, modifiers, context));
        zone.setGravity((float) (helper.getModifiedStat(0, AMSpells.GRAVITY_STAT, modifiers, context) * AMServerConfig.ZONE_GRAVITY.get()));
        zone.setRange((float) helper.getModifiedStat(AMServerConfig.ZONE_RANGE.get(), AMSpells.RANGE_STAT, modifiers, context));
        level.addFreshEntity(zone);
        return new SpellCastResult(spell).setSuccess();
    }
}
