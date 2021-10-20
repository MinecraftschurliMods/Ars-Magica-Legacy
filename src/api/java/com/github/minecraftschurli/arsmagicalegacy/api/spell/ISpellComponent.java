package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface ISpellComponent extends ISpellPart {
    SpellCastResult invoke(Spell spell,
                           LivingEntity caster,
                           Level level,
                           List<ISpellModifier> modifiers,
                           Entity target,
                           Vec3 targetPosition,
                           int index,
                           int ticksUsed);

    SpellCastResult invoke(Spell spell,
                           LivingEntity caster,
                           Level level,
                           List<ISpellModifier> modifiers,
                           BlockPos target,
                           Vec3 targetPosition,
                           int index,
                           int ticksUsed);

    @Override
    default SpellPartType getType() {
        return SpellPartType.COMPONENT;
    }

}
