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
    public HitResult trace(Entity caster, Level world, double range, boolean includeEntities, boolean targetWater) {
        HitResult entityPos = null;
        if (includeEntities) {
            Entity pointedEntity = getPointedEntity(world, caster, range, 1, false, targetWater);
            if (pointedEntity != null) entityPos = new EntityHitResult(pointedEntity);
        }
        float factor = 1;
        float interpPitch = caster.xRotO + (caster.getXRot() - caster.xRotO) * factor;
        float interpYaw = caster.yRotO + (caster.getYRot() - caster.yRotO) * factor;
        double interpPosX = caster.xo + (caster.getX() - caster.xo) * factor;
        double interpPosY = caster.yo + (caster.getY() - caster.yo) * factor + caster.getEyeHeight();
        double interpPosZ = caster.zo + (caster.getZ() - caster.zo) * factor;
        Vec3 vec3 = new Vec3(interpPosX, interpPosY, interpPosZ);
        float magic = 0.017453292F;
        float offsetYawCos = Mth.cos(-interpYaw * magic - (float) Math.PI);
        float offsetYawSin = Mth.sin(-interpYaw * magic - (float) Math.PI);
        float offsetPitchCos = -Mth.cos(-interpPitch * magic);
        float offsetPitchSin = Mth.sin(-interpPitch * magic);
        float finalXOffset = offsetYawSin * offsetPitchCos;
        float finalZOffset = offsetYawCos * offsetPitchCos;
        Vec3 targetVector = vec3.add(finalXOffset * range, offsetPitchSin * range, finalZOffset * range);
        HitResult mop = world.clip(new ClipContext(vec3, targetVector, ClipContext.Block.OUTLINE, targetWater ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE, caster));
        return entityPos == null || mop.getLocation().distanceTo(caster.position()) < entityPos.getLocation().distanceTo(caster.position()) ? mop : entityPos;
    }

    @Nullable
    public static Entity getPointedEntity(Level world, Entity player, double range, double collideRadius, boolean nonCollide, boolean targetWater) {
        Entity pointedEntity = null;
        Vec3 vec = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
        Vec3 lookVec = player.getLookAngle();
        List<Entity> list = world.getEntities(player, player.getBoundingBox().inflate(lookVec.x * range, lookVec.y * range, lookVec.z * range).inflate(collideRadius, collideRadius, collideRadius));
        double d = 0;
        for (Entity entity : list) {
            HitResult hit = world.clip(new ClipContext(new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ()), new Vec3(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ()), ClipContext.Block.COLLIDER, targetWater ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE, player));
            if ((entity.canBeCollidedWith() || nonCollide) && hit.getType() == HitResult.Type.MISS) {
                float f2 = Math.max(0.8F, entity.getBbWidth());
                AABB aabb = entity.getBoundingBox().inflate(f2, f2, f2);
                Optional<Vec3> optional = aabb.clip(vec, lookVec);
                if (aabb.contains(vec)) {
                    pointedEntity = entity;
                    d = 0;
                } else if (optional.isPresent()) {
                    double d3 = vec.distanceTo(optional.get());
                    if ((d3 < d) || (d == 0)) {
                        pointedEntity = entity;
                        d = d3;
                    }
                }
            }
        }
        return pointedEntity;
    }

    @Override
    public SpellCastResult invoke(ISpell spell, LivingEntity caster, Level level, @Nullable HitResult target, int castingTicks, int index, boolean awardXp) {
        Pair<? extends ISpellPart, List<ISpellModifier>> part = spell.partsWithModifiers().get(index);
        switch (part.getFirst().getType()) {
            case COMPONENT -> {
                SpellCastResult result = SpellCastResult.EFFECT_FAILED;
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
                return SpellCastResult.EFFECT_FAILED;
            }
        }
    }

    @Override
    public void nextShapeGroup(ItemStack stack) {
        Spell spell = SpellItem.getSpell(stack);
        spell.currentShapeGroupIndex((byte) ((spell.currentShapeGroupIndex() + 1) % spell.shapeGroups().size()));
        SpellItem.saveSpell(stack, spell);
    }
}
