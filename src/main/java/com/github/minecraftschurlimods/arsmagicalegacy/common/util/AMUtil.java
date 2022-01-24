package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

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

import java.util.function.Predicate;
import java.util.stream.Collector;

public final class AMUtil {
    public static boolean canEndermanGetAngryAt(Player player) {
        return true;
    }

    public static Vec3 closestPointOnLine(Vec3 view, Vec3 a, Vec3 b) {
        Vec3 c = view.subtract(a);
        Vec3 d = b.subtract(a).normalize();
        double p = d.dot(c);
        return p <= 0 ? a : p >= a.distanceTo(b) ? b : a.add(d.scale(p));
    }

    public static ItemStack createDummyStack(int fortune, int silk_touch) {
        ItemStack stack = new ItemStack(null);
        stack.enchant(Enchantments.BLOCK_FORTUNE, fortune);
        stack.enchant(Enchantments.SILK_TOUCH, silk_touch);
        return stack;
    }

    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }

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

    public static ItemStack getSpellStack(LivingEntity entity) {
        ItemStack stack = entity.getMainHandItem();
        if (SpellItem.getSpell(stack) != Spell.EMPTY) return stack;
        stack = entity.getOffhandItem();
        return SpellItem.getSpell(stack) != Spell.EMPTY ? stack : ItemStack.EMPTY;
    }

    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(String delimiter) {
        TextComponent del = new TextComponent(delimiter);
        return Collector.of(TextComponent.EMPTY::copy, (c1, c2) -> (c1.getContents().isEmpty() ? c1 : c1.append(del)).append(c2), (c1, c2) -> c1.append(del).append(c2));
    }

    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
