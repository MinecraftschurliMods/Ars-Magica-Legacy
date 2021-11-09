package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface ISpellHelper {
    boolean hasReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);
    void consumeReagents(LivingEntity entity, Collection<Either<Ingredient, ItemStack>> reagents);
    float getXpForSpellCast(float mana, float burnout, Collection<Either<Ingredient, ItemStack>> reagents, ISpell spell, Player player);
    SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable Entity targetEntity, @Nullable BlockPos targetBlock, Vec3 targetPosition, int castingTicks, int index, boolean awardXp);
}
