package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import java.util.List;

/**
 * Base interface for spell components
 */
public interface ISpellComponent extends ISpellPart {
    /**
     * Invoke this spell component for an entity.
     *
     * @param spell the spell being cast
     * @param caster the caster of the spell
     * @param level the level the spell is being cast in
     * @param modifiers the modifiers modifying this component
     * @param target the target entity
     * @param targetPosition the position that is targeted
     * @param index the index of this spell component in the spell execution stack
     * @param ticksUsed the amount of ticks the spell is being cast for
     * @return the casting result (success if anything was affected)
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell,
                           LivingEntity caster,
                           Level level,
                           List<ISpellModifier> modifiers,
                           Entity target,
                           Vec3 targetPosition,
                           int index,
                           int ticksUsed);

    /**
     * Invoke this spell component for a block.
     *
     * @param spell the spell being cast
     * @param caster the caster of the spell
     * @param level the level the spell is being cast in
     * @param modifiers the modifiers modifying this component
     * @param target the target block
     * @param targetPosition the position that is targeted
     * @param index the index of this spell component in the spell execution stack
     * @param ticksUsed the amount of ticks the spell is being cast for
     * @return the casting result (success if anything was affected)
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell,
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
