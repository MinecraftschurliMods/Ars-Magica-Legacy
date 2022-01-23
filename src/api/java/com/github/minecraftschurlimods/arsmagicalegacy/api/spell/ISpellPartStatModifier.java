package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;

/**
 * Interface to modify a stat of a spell part.
 */
@FunctionalInterface
public interface ISpellPartStatModifier {
    /**
     * Modify the stat value.
     *
     * @param base                the base value being modified
     * @param modified            the value with all previous modifications
     * @param spell,caster,target the context
     * @return the new modified value
     */
    float modify(float base, float modified, ISpell spell, LivingEntity caster, HitResult target);
}
