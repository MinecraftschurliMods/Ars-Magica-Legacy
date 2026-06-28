package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class Knockback extends SpellComponent.CastEntity {
    public Knockback() {
        super(AMSpells.SPEED_STAT);
    }

    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        Entity directEntity = context.directEntity();
        Entity entity = hitResult.getEntity();
        if (entity == context.caster() || directEntity == null) return SpellComponentCastResult.pass(spell);
        double velocity = ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.SPEED_STAT, modifiers, context);
        entity.setDeltaMovement(entity.getDeltaMovement().add(velocity * Math.cos(Math.atan2(entity.getZ() - directEntity.getZ(), entity.getX() - directEntity.getX())), velocity * 0.325f, velocity * Math.sin(Math.atan2(entity.getZ() - directEntity.getZ(), entity.getX() - directEntity.getX()))));
        return SpellComponentCastResult.success(spell);
    }
}
