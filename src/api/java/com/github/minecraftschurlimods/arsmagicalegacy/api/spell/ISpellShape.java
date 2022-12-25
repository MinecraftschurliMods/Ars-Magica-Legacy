package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public interface ISpellShape extends ISpellPart {
    /**
     * Casts the spell.
     *
     * @param spell     The spell that is cast.
     * @param caster    The player that casts the spell.
     * @param level     The level that the caster is in.
     * @param modifiers A list of modifiers that affect this spell cast.
     * @param hit       The target of the spell.
     * @param ticksUsed The amount of ticks this spell has been cast already.
     * @param index     The index of the next component.
     * @param awardXp   Whether to grant the player magic xp or not.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, @Nullable HitResult hit, int ticksUsed, int index, boolean awardXp);

    /**
     * @return A set containing all spell part stats that affect this shape.
     */
    Set<ISpellPartStat> getStatsUsed();

    /**
     * @return Whether the shape is continuous or not.
     */
    default boolean isContinuous() {
        return false;
    }

    /**
     * @return True if this shape can only be at the end, false otherwise.
     */
    default boolean isEndShape() {
        return false;
    }

    /**
     * @return True if this shape can not be at the beginning, false otherwise.
     */
    default boolean needsPrecedingShape() {
        return false;
    }

    /**
     * @return True if this shape can only be at the beginning, false otherwise.
     */
    default boolean needsToComeFirst() {
        return false;
    }

    @Override
    default SpellPartType getType() {
        return SpellPartType.SHAPE;
    }
}
