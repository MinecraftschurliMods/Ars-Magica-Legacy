package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;

import java.util.List;

/**
 * Base interface for spell components
 */
public interface ISpellComponent extends ISpellPart {

    /**
     * Invoke this spell component for an entity.
     *
     * @param spell          the spell being cast
     * @param caster         the caster of the spell
     * @param level          the level the spell is being cast in
     * @param modifiers      the modifiers modifying this component
     * @param target         the target
     * @param index          the index of this spell component in the spell execution stack
     * @param ticksUsed      the amount of ticks the spell is being cast for
     * @return the casting result (success if anything was affected)
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed);

    /**
     * Invoke this spell component for a block.
     *
     * @param spell          the spell being cast
     * @param caster         the caster of the spell
     * @param level          the level the spell is being cast in
     * @param modifiers      the modifiers modifying this component
     * @param target         the target
     * @param index          the index of this spell component in the spell execution stack
     * @param ticksUsed      the amount of ticks the spell is being cast for
     * @return the casting result (success if anything was affected)
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed);

    @Override
    default SpellPartType getType() {
        return SpellPartType.COMPONENT;
    }
}
