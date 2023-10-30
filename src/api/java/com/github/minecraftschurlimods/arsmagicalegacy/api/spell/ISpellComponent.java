package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.ApiStatus.OverrideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

/**
 * Base interface for spell components
 */
public interface ISpellComponent extends ISpellPart {
    /**
     * Invoke this spell component for an entity. Called only on the server.
     *
     * @param spell     The spell being cast.
     * @param caster    The caster of the spell.
     * @param level     The level the spell is being cast in.
     * @param modifiers The modifiers modifying this component.
     * @param target    The target.
     * @param index     The index of this spell component in the spell execution stack.
     * @param ticksUsed The amount of ticks the spell is being cast for.
     * @return The spell cast result (success if anything was affected).
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed);

    /**
     * Invoke this spell component for a block. Called only on the server.
     *
     * @param spell     The spell being cast.
     * @param caster    The caster of the spell.
     * @param level     The level the spell is being cast in.
     * @param modifiers The modifiers modifying this component.
     * @param target    The target.
     * @param index     The index of this spell component in the spell execution stack.
     * @param ticksUsed The amount of ticks the spell is being cast for.
     * @return The spell cast result (success if anything was affected).
     */
    @OverrideOnly
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed);

    /**
     * Spawn particle effects for this spell. Called only on the client.
     *
     * @param spell     The spell being cast.
     * @param caster    The caster of the spell.
     * @param level     The level the spell is being cast in.
     * @param modifiers The modifiers modifying this component.
     * @param target    The target.
     * @param index     The index of this spell component in the spell execution stack.
     * @param ticksUsed The amount of ticks the spell is being cast for.
     */
    @OverrideOnly
    default void spawnParticles(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, EntityHitResult target, int index, int ticksUsed) {}

    /**
     * Spawn particle effects for this spell. Called only on the client.
     *
     * @param spell     The spell being cast.
     * @param caster    The caster of the spell.
     * @param level     The level the spell is being cast in.
     * @param modifiers The modifiers modifying this component.
     * @param target    The target.
     * @param index     The index of this spell component in the spell execution stack.
     * @param ticksUsed The amount of ticks the spell is being cast for.
     */
    @OverrideOnly
    default void spawnParticles(ISpell spell, LivingEntity caster, Level level, List<ISpellModifier> modifiers, BlockHitResult target, int index, int ticksUsed) {}

    /**
     * Returns the default color to use for this component's visual effects.
     * If null, particles should be used without any color modifier applied.
     *
     * @return The default color to use for this component's visual effects.
     */
    @Nullable
    default Integer defaultColor() {
        return null;
    }

    /**
     * Returns the color for this component's visual effects. Uses the color modifier, or the result of {@link ISpellComponent#defaultColor()} if no color modifier is present.
     * If null, particles should be used without any color modifier applied.
     *
     * @param modifiers The modifier list for this component.
     * @return The color modifier for this component.
     */
    @Nullable
    default Integer getColor(List<ISpellModifier> modifiers) {
        return defaultColor(); // TODO: color modifier
    }

    /**
     * @return The stats used by this spell part.
     */
    Set<ISpellPartStat> getStatsUsed();

    @Override
    default SpellPartType getType() {
        return SpellPartType.COMPONENT;
    }
}
