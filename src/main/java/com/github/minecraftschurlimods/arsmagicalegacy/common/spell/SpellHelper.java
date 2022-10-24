package com.github.minecraftschurlimods.arsmagicalegacy.common.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStat;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellShape;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
        List<Either<Ingredient, ItemStack>> reagents = new ArrayList<>(reagentsIn);
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
    @Nullable
    public Entity getPointedEntity(Entity entity, Level level, double range, boolean clipFluids) {
        Entity result = null;
        Vec3 vec = new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
        Vec3 lookVec = entity.getLookAngle();
        double d = 0;
        for (Entity e : level.getEntities(entity, entity.getBoundingBox().inflate(lookVec.x * range, lookVec.y * range, lookVec.z * range).inflate(1, 1, 1))) {
            HitResult hit = level.clip(new ClipContext(new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ()), new Vec3(e.getX(), e.getY() + e.getEyeHeight(), e.getZ()), ClipContext.Block.COLLIDER, clipFluids ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, entity));
            if (e.canBeCollidedWith() && hit.getType() == HitResult.Type.MISS) {
                float size = Math.max(0.8F, e.getBbWidth());
                AABB aabb = e.getBoundingBox().inflate(size, size, size);
                Optional<Vec3> optional = aabb.clip(vec, lookVec);
                if (aabb.contains(vec)) {
                    result = e;
                    d = 0;
                } else if (optional.isPresent()) {
                    double distance = vec.distanceTo(optional.get());
                    if ((distance < d) || (d == 0)) {
                        result = e;
                        d = distance;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public HitResult trace(Entity entity, Level level, double range, boolean entities, boolean targetNonSolid) {
        HitResult entityHit = null;
        if (entities) {
            Entity pointed = getPointedEntity(entity, level, range, targetNonSolid);
            if (pointed != null) {
                entityHit = new EntityHitResult(pointed);
            }
        }
        float interpPitch = entity.xRotO + (entity.getXRot() - entity.xRotO);
        float interpYaw = entity.yRotO + (entity.getYRot() - entity.yRotO);
        float degToRad = 0.017453292F;
        float offsetPitchCos = -Mth.cos(-interpPitch * degToRad);
        float offsetPitchSin = Mth.sin(-interpPitch * degToRad);
        HitResult blockHit = level.clip(new ClipContext(entity.position(), entity.position().add(Mth.sin((float) (-interpYaw * degToRad - Math.PI)) * offsetPitchCos * range, offsetPitchSin * range, Mth.cos((float) (-interpYaw * degToRad - Math.PI)) * offsetPitchCos * range), ClipContext.Block.OUTLINE, targetNonSolid ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, entity));
        return entityHit == null || blockHit.getLocation().distanceTo(entity.position()) < entityHit.getLocation().distanceTo(entity.position()) ? blockHit : entityHit;
    }

    @Override
    public float getModifiedStat(float baseValue, ISpellPartStat stat, List<ISpellModifier> modifiers, ISpell spell, LivingEntity caster, @Nullable HitResult target) {
        float modified = baseValue;
        for (ISpellModifier iSpellModifier : modifiers) {
            if (iSpellModifier.getStatsModified().contains(stat)) {
                ISpellPartStatModifier modifier = iSpellModifier.getStatModifier(stat);
                modified = modifier.modify(baseValue, modified, spell, caster, target);
            }
        }
        return modified;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        List<Pair<? extends ISpellPart, List<ISpellModifier>>> pwm = spell.partsWithModifiers();
        Pair<? extends ISpellPart, List<ISpellModifier>> part = pwm.get(index);
        switch (part.getFirst().getType()) {
            case COMPONENT -> {
                SpellCastResult result = SpellCastResult.EFFECT_FAILED;
                ISpellComponent component = (ISpellComponent) part.getFirst();
                if (target instanceof EntityHitResult entityHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), entityHitResult, index + 1, castingTicks);
                }
                if (target instanceof BlockHitResult blockHitResult) {
                    result = component.invoke(spell, caster, level, part.getSecond(), blockHitResult, index + 1, castingTicks);
                }
                return result.isFail() || index + 1 == pwm.size() ? result : invoke(spell, caster, level, target, castingTicks, index + 1, awardXp);
            }
            case SHAPE -> {
                ISpellShape shape = (ISpellShape) part.getFirst();
                return shape.invoke(spell, caster, level, part.getSecond(), target, castingTicks, index + 1, awardXp);
            }
            default -> {
                return SpellCastResult.EFFECT_FAILED;
            }
        }
    }

    @Override
    public void nextShapeGroup(ItemStack stack) {
        ISpell spell = SpellItem.getSpell(stack);
        spell.currentShapeGroupIndex((byte) ((spell.currentShapeGroupIndex() + 1) % spell.shapeGroups().size()));
        SpellItem.saveSpell(stack, spell);
    }
}
