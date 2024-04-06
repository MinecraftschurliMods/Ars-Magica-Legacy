package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Interface to modify a stat of a spell part.
 */
@FunctionalInterface
public interface ISpellPartStatModifier {
    /**
     * Modifies the stat value.
     *
     * @param base           The base value being modified.
     * @param modified       The value with all previous modifications.
     * @param spell          The spell this is calculated for.
     * @param caster         The entity casting the spell.
     * @param target         The target of the spell.
     * @param componentIndex
     * @return The modified value.
     */
    float modify(float base, float modified, ISpell spell, LivingEntity caster, @Nullable HitResult target, int componentIndex);
}
