package com.github.minecraftschurli.arsmagicalegacy.common.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.*;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class SpellHelper implements ISpellHelper {
    private static final Lazy<SpellHelper> INSTANCE = Lazy.concurrentOf(SpellHelper::new);

    private SpellHelper() {
    }

    /**
     * @return The only instance of this class.
     */
    public static SpellHelper instance() {
        return INSTANCE.get();
    }

    @Override
    public boolean hasReagents(LivingEntity caster, Collection<Either<Ingredient, ItemStack>> reagentsIn) {
        if (!(caster instanceof Player player)) return true;
        var reagents = new ArrayList<>(reagentsIn);
        for (ItemStack item : player.getInventory().items) {
            if (item.isEmpty()) continue;
            for (Iterator<Either<Ingredient, ItemStack>> iterator = reagents.iterator(); iterator.hasNext(); ) {
                iterator.next().ifLeft(ingredient1 -> {
                    if (ingredient1.test(item)) {
                        iterator.remove();
                    }
                }).ifRight(itemStack -> {
                    if (ItemStack.isSame(itemStack, item) && itemStack.getCount() <= item.getCount()) {
                        iterator.remove();
                    }
                });
            }
            if (reagents.isEmpty()) break;
        }
        return reagents.isEmpty();
    }

    @Override
    public void consumeReagents(LivingEntity caster, Collection<Either<Ingredient, ItemStack>> reagents) {
        if (!(caster instanceof Player player)) return;
        for (ItemStack item : player.getInventory().items) {
            if (item.isEmpty()) continue;
            for (Iterator<Either<Ingredient, ItemStack>> iterator = reagents.iterator(); iterator.hasNext(); ) {
                iterator.next().ifLeft(ingredient1 -> {
                    if (ingredient1.test(item)) {
                        item.shrink(1);
                        iterator.remove();
                    }
                }).ifRight(itemStack -> {
                    if (ItemStack.isSame(itemStack, item) && itemStack.getCount() <= item.getCount()) {
                        item.shrink(itemStack.getCount());
                        iterator.remove();
                    }
                });
            }
            if (reagents.isEmpty()) break;
        }
    }

    @Override
    public float getXpForSpellCast(float mana, float burnout, Collection<Either<Ingredient, ItemStack>> reagents, ISpell spell, Player player) {
        return 0;
    }

    @Override
    public int countModifiers(List<ISpellModifier> modifiers, ResourceLocation modifier) {
        return modifiers.stream().map(IForgeRegistryEntry::getRegistryName).filter(modifier::equals).toList().size();
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        Pair<? extends ISpellPart, List<ISpellModifier>> part = spell.partsWithModifiers().get(index);
        switch (part.getFirst().getType()) {
            case COMPONENT -> {
                SpellCastResult result = SpellCastResult.FAIL;
                var component = (ISpellComponent) part.getFirst();
                if (target instanceof EntityHitResult entityHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), entityHitResult, index + 1, castingTicks);
                }
                if (target instanceof BlockHitResult blockHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), blockHitResult, index + 1, castingTicks);
                }
                return result;
            }
            case SHAPE -> {
                var shape = (ISpellShape) part.getFirst();
                return shape.invoke(spell, caster, level, part.getSecond(), target, castingTicks, index + 1, awardXp);
            }
            default -> {
                return SpellCastResult.FAIL;
            }
        }
    }
}
