package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Interface to modify a stat of a spell part.
 */
@FunctionalInterface
public interface ISpellPartStatModifier {
    float modify(float base, float modified, ISpell spell, LivingEntity caster, @Nullable HitResult target);
}
