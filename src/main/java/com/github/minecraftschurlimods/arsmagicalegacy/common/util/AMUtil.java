package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

public final class AMUtil {
    /**
     * @param view The view vector to which the resulting point is the closest.
     * @param a    The first coordinate of the line.
     * @param b    The second coordinate of the line.
     * @return The point on the line a..b that is the closest to the given view vector.
     */
    public static Vec3 closestPointOnLine(Vec3 view, Vec3 a, Vec3 b) {
        Vec3 c = view.subtract(a);
        Vec3 d = b.subtract(a).normalize();
        double p = d.dot(c);
        return p <= 0 ? a : p >= a.distanceTo(b) ? b : a.add(d.scale(p));
    }

    /**
     * Combines any duplicates in the list into single elements.
     *
     * @param list The list to perform the combining on.
     * @return A list, containing all elements of the old list, with the elements combined where possible.
     */
    public static List<ISpellIngredient> combineSpellIngredients(List<ISpellIngredient> list) {
        List<ISpellIngredient> result = new ArrayList<>();
        for (ISpellIngredient ingredient : list) {
            Optional<ISpellIngredient> optional = result.stream().filter(e -> e.canCombine(ingredient)).findAny();
            if (optional.isPresent()) {
                ISpellIngredient previous = optional.get();
                int index = result.indexOf(previous);
                result.remove(previous);
                result.add(index, ingredient.combine(previous));
            } else {
                result.add(ingredient);
            }
        }
        return result;
    }

    /**
     * @param fortune   The fortune level to enchant the stack with.
     * @param silkTouch The silk touch level to enchant the stack with.
     * @return A dummy item stack, enchanted with the given levels of fortune and silk touch.
     */
    public static ItemStack createDummyStack(int fortune, int silkTouch) {
        ItemStack stack = new ItemStack((ItemLike) null);
        stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
        stack.enchant(Enchantments.SILK_TOUCH, silkTouch);
        return stack;
    }

    /**
     * @param array The array to get the element from.
     * @param tick  The tick to get the element for.
     * @param <T>   The type of the array.
     * @return The tickth element of the array, wrapping around.
     */
    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }

    /**
     * @param from         Starting point of the ray trace.
     * @param to           Ending point of the ray trace.
     * @param entity       The entity causing this ray trace.
     * @param blockContext The block clipping context.
     * @param fluidContext The fluid clipping context.
     * @return The hit result (ray trace result).
     */
    public static HitResult getHitResult(Vec3 from, Vec3 to, Entity entity, ClipContext.Block blockContext, ClipContext.Fluid fluidContext) {
        HitResult hitResult = entity.level.clip(new ClipContext(from, to, blockContext, fluidContext, entity));
        if (hitResult.getType() != HitResult.Type.MISS) {
            to = hitResult.getLocation();
        }
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity.level, entity, from, to, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1), e -> true);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        return hitResult;
    }

    /**
     * @param entity The entity to get this for.
     * @return The item stack with the spell in the given entity's main hand or, if absent, in the given entity's off hand instead.
     */
    public static ItemStack getSpellStack(LivingEntity entity) {
        ItemStack stack = entity.getMainHandItem();
        var helper = ArsMagicaAPI.get().getSpellHelper();
        if (helper.getSpell(stack) != ISpell.EMPTY) return stack;
        stack = entity.getOffhandItem();
        return helper.getSpell(stack) != ISpell.EMPTY ? stack : ItemStack.EMPTY;
    }

    /**
     * @param i1 The first ingredient to match.
     * @param i2 The second ingredient to match.
     * @return Whether the two ingredients' item stack lists match, ignoring item counts.
     */
    public static boolean ingredientMatchesIgnoreCount(Ingredient i1, Ingredient i2) {
        ItemStack[] a1 = i1.getItems();
        ItemStack[] a2 = i2.getItems();
        if (a1.length != a2.length) return false;
        for (int i = 0; i < a1.length; i++) {
            ItemStack s1 = a1[i].copy();
            s1.setCount(1);
            ItemStack s2 = a2[i].copy();
            s2.setCount(1);
            if (!ItemStack.matches(s1, s2)) return false;
        }
        return true;
    }

    /**
     * @param delimiter The delimiter to use.
     * @return A collector that joins multiple components together, using the given delimiter.
     */
    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(String delimiter) {
        MutableComponent del = Component.literal(delimiter);
        return Collector.of(Component.empty()::copy, (c1, c2) -> (c1.getContents() == ComponentContents.EMPTY ? c1 : c1.append(del)).append(c2), (c1, c2) -> c1.append(del).append(c2));
    }

    /**
     * @param first  VoxelShape #1.
     * @param second VoxelShape #2.
     * @param others All other VoxelShapes.
     * @return All given shapes, joined into a single VoxelShape.
     */
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }

    /**
     * @param list The list of reagents to convert into a list of crafting ingredients.
     * @return A list of crafting ingredients, derived from the reagent list.
     */
    public static List<ItemStack[]> reagentsToIngredients(List<ItemFilter> list) {
        return list.stream().map(ItemFilter::getMatchedStacks).filter(e -> e.length > 0).toList();
    }

    public static double nextDouble(RandomSource random, double bound) {
        double r = random.nextDouble();
        r = r * bound;
        if (r >= bound) {
            r = Double.longBitsToDouble(Double.doubleToLongBits(bound) - 1);
        }
        return r;
    }
}
