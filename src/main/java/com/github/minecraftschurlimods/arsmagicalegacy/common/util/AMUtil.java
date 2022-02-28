package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbilityData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.Range;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

import java.util.stream.Collector;

public final class AMUtil {
    /**
     * @param player The player to check this for.
     * @return Whether an enderman can get angry at the given player.
     */
    public static boolean canEndermanGetAngryAt(Player player) {
        return true;
    }

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
     * @param fortune   The fortune level to enchant the stack with.
     * @param silkTouch The silk touch level to enchant the stack with.
     * @return A dummy item stack, enchanted with the given levels of fortune and silk touch.
     */
    public static ItemStack createDummyStack(int fortune, int silkTouch) {
        ItemStack stack = new ItemStack(null);
        stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
        stack.enchant(Enchantments.SILK_TOUCH, silkTouch);
        return stack;
    }

    /**
     * Calls {@link AMUtil#translateToScale} with the min and max values of the ability's affinity, the player's value of the ability's affinity, and the given resultMin and resultMax values.
     *
     * @param ability   The ability to get the values from.
     * @param player    The player to get the affinity value from.
     * @param resultMin The result min value.
     * @param resultMax The result max value.
     * @return The result of {@link AMUtil#translateToScale}, with the values described above.
     */
    public static double getAbilityValue(IAbilityData ability, Player player, double resultMin, double resultMax) {
        Range range = ability.range();
        return translateToScale(range.min().orElse(0d), range.max().orElse(1d), ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, ability.affinity()), resultMin, resultMax);
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
     * @param from Starting point of the ray trace.
     * @param to   Ending point of the ray trace.
     * @param entity The entity causing this ray trace.
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
        if (SpellItem.getSpell(stack) != Spell.EMPTY) return stack;
        stack = entity.getOffhandItem();
        return SpellItem.getSpell(stack) != Spell.EMPTY ? stack : ItemStack.EMPTY;
    }

    /**
     * @param delimiter The delimiter to use.
     * @return A collector that joins multiple components together, using the given delimiter.
     */
    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(String delimiter) {
        TextComponent del = new TextComponent(delimiter);
        return Collector.of(TextComponent.EMPTY::copy, (c1, c2) -> (c1.getContents().isEmpty() ? c1 : c1.append(del)).append(c2), (c1, c2) -> c1.append(del).append(c2));
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
     * Two scales, a1-b1 and a2-b2, are given. A value p on the scale a1-b1 is given.
     * The method returns a value. The location of that value on the scale a2-b2 is the same as the location of p on the scale a1-b1.
     *
     * a1---p---------b1
     *      |
     *      |
     * a2---?---------b2
     *
     * @param a1 The min value of the original scale.
     * @param b1 The max value of the original scale.
     * @param p  The p value on the original scale.
     * @param a2 The min value of the target scale.
     * @param b2 The max value of the target scale.
     * @return The value of p, scaled from the scale a1-b1 to the scale a2-b2.
     */
    public static double translateToScale(double a1, double b1, double p, double a2, double b2) {
        return (p - a1) / (b1 - a1) * (b2 - a2) + a2;
    }
}
