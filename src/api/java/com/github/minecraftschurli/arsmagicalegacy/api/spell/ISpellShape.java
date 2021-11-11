package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ISpellShape extends ISpellPart {
    /**
     * @return Whether the shape is continuous or not.
     */
    default boolean isContinuous() {
        return false;
    }

    /**
     * Casts the spell.
     *
     * @param spell        The spell to cast.
     * @param caster       The entity casting the spell.
     * @param level        The level the spell is cast in.
     * @param targetEntity The target entity for this spell.
     * @param targetBlock  The target block for this spell.
     * @param targetPos    The target position for this spell.
     * @param ticksUsed    How long the spell has already been cast.
     * @param index        The index.
     * @param awardXp      The magic xp awarded for casting this spell.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable Entity targetEntity, @Nullable BlockPos targetBlock, Vec3 targetPos, int ticksUsed, int index, boolean awardXp);

    @Override
    default SpellPartType getType() {
        return SpellPartType.SHAPE;
    }
}
