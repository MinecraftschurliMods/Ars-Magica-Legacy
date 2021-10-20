package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ISpellShape extends ISpellPart {
    default boolean isContinuous() { return false; }

    SpellCastResult invoke(Spell spell,
                           LivingEntity caster,
                           Level level,
                           List<ISpellModifier> modifiers,
                           @Nullable Entity targetEntity,
                           @Nullable BlockPos targetBlock,
                           Vec3 targetPos,
                           int ticksUsed,
                           int index,
                           boolean awardXp);

    @Override
    default SpellPartType getType() {
        return SpellPartType.SHAPE;
    }
}
