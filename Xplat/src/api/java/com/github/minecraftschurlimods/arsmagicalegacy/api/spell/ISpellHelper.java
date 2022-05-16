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
     * @param entity   The entity to check on.
     * @param reagents The reagents to search for.
     * @return Whether the given entity has the required reagents in their inventory or not.
     */
    boolean hasReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);

    /**
     * Consumes the reagents.
     *
     * @param entity   The entity to consume the ingredients from.
     * @param reagents The reagents to consume.
     */
    void consumeReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);

    /**
     * Get the stat value modified by the modifiers.
     *
     * @param baseValue The base value for the stat.
     * @param stat      The stat that is modified.
     * @param spell     The spell that the part belongs to.
     * @param caster    The entity casting the spell.
     * @param target    The target of the spell cast.
     * @return The modified value of the stat.
     */
    float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target);

    /**
     * Performs a ray trace.
     *
     * @param caster          The entity casting the spell.
     * @param level           The level the spell is cast in.
     * @param range           The range of the ray trace.
     * @param includeEntities Whether to include entities. If false, only checks blocks.
     * @param targetNonSolid  Whether non-solid blocks and fluids should be included or not.
     * @return A HitResult, representing the ray trace.
     */
    HitResult trace(Entity caster, Level level, double range, boolean includeEntities, boolean targetNonSolid);

    /**
     * Casts the spell.
     *
     * @param spell        The spell to cast.
     * @param caster       The entity casting the spell.
     * @param level        The level the spell is cast in.
     * @param target       The target of the spell cast.
     * @param castingTicks How long the spell has already been cast.
     * @param index        The shape group index.
     * @param awardXp      The magic xp awarded for casting this spell.
     * @return A SpellCastResult that represents the spell casting outcome.
     */
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp);

    /**
     * Selects the next shape group of the given spell item stack, wrapping around.
     *
     * @param stack The spell item stack to select the next shape group of.
     */
    void nextShapeGroup(ItemStack stack);
}
