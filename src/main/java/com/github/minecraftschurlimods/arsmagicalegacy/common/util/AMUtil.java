package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;
import java.util.stream.Collector;

public final class AMUtil {
    /**
     * Returns whether the player can anger endermen by looking at them, based on the ender affinity depth.
     *
     * @param player The player to check the affinity on
     * @return Whether the player can anger endermen by looking at them or not
     */
    public static boolean canEndermanGetAngryAt(Player player) {
        return true;
    }

    /**
     * Returns the position that is on the line a - b and is the closest to the view vector.
     *
     * @param view The viewpoint that is assumed.
     * @param a    The first point of the line.
     * @param b    The second point of the line.
     * @return The position that is on the line a - b and is the closest to the view vector
     */
    public static Vec3 closestPointOnLine(Vec3 view, Vec3 a, Vec3 b) {
        Vec3 c = view.subtract(a);
        Vec3 d = b.subtract(a).normalize();
        double p = d.dot(c);
        return p <= 0 ? a : p >= a.distanceTo(b) ? b : a.add(d.scale(p));
    }

    /**
     * Creates a dummy item stack for situations where you have the spell, but not the ItemStack.
     * @param fortune    The fortune level of the stack.
     * @param silk_touch The silk touch level of the stack.
     * @return A dummy item stack, enchanted with fortune and silk touch if necessary
     */
    public static ItemStack createDummyStack(int fortune, int silk_touch) {
        ItemStack stack = new ItemStack(null);
        stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
        stack.enchant(Enchantments.SILK_TOUCH, silk_touch);
        return stack;
    }

    /**
     * Returns the tick'th element from the given array. If tick is greater than the array size, a modulo operation is performed.
     *
     * @param array The array.
     * @param tick  The tick index.
     * @param <T>   The type of array. Works on any type; there are no restrictions.
     * @return the tick'th element from the array
     */
    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }

    /**
     * Performs a ray trace from "from" to "to". Modified version of {@link ProjectileUtil#getHitResult(Entity, Predicate)}
     *
     * @param entity       the entity that causes this ray trace
     * @param blockContext the block clipping context to use
     * @param fluidContext the fluid clipping context to use
     * @return A hit result, representing the ray trace.
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
     * Joins multiple components into one, returning a collector.
     *
     * @param delimiter The delimiter to use.
     * @return The collector to join multiple components into one.
     */
    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(String delimiter) {
        TextComponent del = new TextComponent(delimiter);
        return Collector.of(TextComponent.EMPTY::copy, (c1, c2) -> c1.append(del).append(c2), (c1, c2) -> c1.append(del).append(c2));
    }

    /**
     * Joins multiple VoxelShapes into one. If only two VoxelShapes need to be joined, use {@link Shapes#join} instead.
     *
     * @param first  VoxelShape #1
     * @param second VoxelShape #2
     * @param others other VoxelShapes
     * @return A new VoxelShape, consisting of all given VoxelShapes.
     */
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
