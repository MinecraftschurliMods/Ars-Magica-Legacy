package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public interface ISpellHelper {
    /**
     * Whether the entity has the reagents in their inventory or not.
     *
     * @param entity   The entity to check on.
     * @param reagents The reagents to search for.
     * @return Whether the given entity has the required reagents in their inventory or not.
     */
    boolean hasReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);

    /**
     * Consumes the reagents.
     *
     * @param entity   The entity to check on.
     * @param reagents The reagents to consume.
     */
    void consumeReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);

    /**
     * Get the stat value modified by the modifiers.
     *
     * @param baseValue the base value for the stat
     * @param stat      the stat that is modified
     * @param spell     the spell that the part belongs to
     * @param caster    the caster of the spell
     * @param target    the target of the spell cast
     * @return          the modified value of the stat
     */
    float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target);

    HitResult trace(Entity caster, Level world, double range, boolean includeEntities, boolean targetWater);

    /**
     * Casts the spell.
     *
     * @param spell        The spell to cast.
     * @param caster       The entity casting the spell.
     * @param level        The level the spell is cast in.
     * @param target       The target for this spell.
     * @param castingTicks How long the spell has already been cast.
     * @param index        The index.
     * @param awardXp      The magic xp awarded for casting this spell.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp);

    void nextShapeGroup(ItemStack stack);
}
