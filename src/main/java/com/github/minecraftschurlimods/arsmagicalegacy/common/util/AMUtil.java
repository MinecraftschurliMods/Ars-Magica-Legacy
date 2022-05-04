package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;

public final class AMUtil {
    public static final Codec<EntityPredicate> ENTITY_PREDICATE = Codec.PASSTHROUGH.xmap(
            dynamic -> EntityPredicate.fromJson(dynamic.cast(JsonOps.INSTANCE)),
            entityPredicate -> new Dynamic<>(JsonOps.INSTANCE, sanitizeJson(entityPredicate.serializeToJson())));

    private static JsonElement sanitizeJson(final JsonElement json) {
        if (json instanceof JsonObject object) {
            object.entrySet().removeIf(entry -> entry.getValue() instanceof JsonNull);
        }
        return json;
    }

    public static final Codec<MinMaxBounds.Ints> INT_MIN_MAX_BOUNDS = Codec.PASSTHROUGH.xmap(
            dynamic -> MinMaxBounds.Ints.fromJson(dynamic.cast(JsonOps.INSTANCE)),
            minMaxBounds -> new Dynamic<>(JsonOps.INSTANCE, minMaxBounds.serializeToJson()));
    public static final Codec<MinMaxBounds.Doubles> DOUBLE_MIN_MAX_BOUNDS = Codec.PASSTHROUGH.xmap(
            dynamic -> MinMaxBounds.Doubles.fromJson(dynamic.cast(JsonOps.INSTANCE)),
            minMaxBounds -> new Dynamic<>(JsonOps.INSTANCE, minMaxBounds.serializeToJson()));

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
     * Searches for items at the given position. If all given predicates are each matched by one of those items, the entity is spawned according to the given spawner function.
     *
     * @param level      The level to perform this operation in.
     * @param pos        The position to use for this operation.
     * @param type       The entity type to spawn.
     * @param items      A vararg list of item predicates that must be met. One item can match multiple predicates.
     */
    @SafeVarargs
    public static <T extends Entity> void consumeItemsAndSpawnEntity(Level level, BlockPos pos, EntityType<T> type, Predicate<ItemStack>... items) {
        Set<ItemEntity> set = new HashSet<>();
        for (Predicate<ItemStack> predicate : items) {
            List<ItemEntity> entities = level.getEntitiesOfClass(ItemEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), e -> predicate.test(e.getItem()));
            if (!entities.isEmpty()) {
                set.add(entities.get(0));
            } else return;
        }
        for (ItemEntity item : set) {
            item.kill();
        }
        T entity = type.create(level);
        if (entity != null) {
            entity.moveTo(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            level.addFreshEntity(entity);
        }
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
        if (SpellItem.getSpell(stack) != ISpell.EMPTY) return stack;
        stack = entity.getOffhandItem();
        return SpellItem.getSpell(stack) != ISpell.EMPTY ? stack : ItemStack.EMPTY;
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
}
