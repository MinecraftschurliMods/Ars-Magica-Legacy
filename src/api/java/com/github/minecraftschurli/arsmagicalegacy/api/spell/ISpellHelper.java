package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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
     * @param mana     The spell's mana cost.
     * @param burnout  The spell'S burnout cost.
     * @param reagents The reagents this spell requires.
     * @param spell    The spell item.
     * @param player   The player that casts the spell.
     * @return How much magic xp the spell usage grants, specific to the player.
     */
    float getXpForSpellCast(float mana, float burnout, Collection<Either<Ingredient, ItemStack>> reagents, ISpell spell, Player player);

    int countModifiers(List<ISpellModifier> modifiers, ResourceLocation modifier);

    HitResult trace(LivingEntity caster, Level world, double range, boolean includeEntities, boolean targetWater);

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
}
