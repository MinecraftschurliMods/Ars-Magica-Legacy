package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.util.BlockUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ZoneEntity extends Entity implements ItemSupplier {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(ZoneEntity.class, EntityDataSerializers.ITEM_STACK);

    public ZoneEntity(EntityType<? extends ZoneEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setBoundingBox(new AABB(getX() - 0.1, getY() - 0.1, getZ() - 0.1, getX() + 0.1, getY() + 0.1, getZ() + 0.1));
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
        entityData.define(GRAVITY, 0f);
        entityData.define(RADIUS, 1.4f);
        entityData.define(STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(TARGET_NON_SOLID, tag.getBoolean("TargetNonSolid"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(INDEX, tag.getInt("Index"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(GRAVITY, tag.getFloat("Gravity"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
        entityData.set(STACK, ItemStack.of(tag.getCompound("Stack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putBoolean("TargetNonSolid", entityData.get(TARGET_NON_SOLID));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Index", entityData.get(INDEX));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Gravity", entityData.get(GRAVITY));
        tag.putFloat("Radius", entityData.get(RADIUS));
        CompoundTag stack = new CompoundTag();
        entityData.get(STACK).save(stack);
        tag.put("Stack", stack);
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        Entity entity = getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? 0 : entity.getId());
    }

    @Override
    public void tick() {
        if (tickCount > getDuration() || getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        setPos(getX(), getY() + getGravity(), getZ());
        if (firstTick || tickCount % 20 == 0) {
            HitResult result = BlockUtil.getHitResult(position(), position().add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
            for (Entity e : level.getEntities(this, new AABB(getX() - getRadius(), getY() - 3, getZ() - getRadius(), getX() + getRadius(), getY() + 3, getZ() + getRadius()))) {
                if (e == this || e == getOwner()) continue;
                if (e instanceof EnderDragonPart) {
                    e = ((EnderDragonPart) e).parentMob;
                }
                if (e instanceof LivingEntity) {
                    ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, result, tickCount, getIndex(), true);
                }
            }
            for (float i = -getRadius(); i <= getRadius(); i += 0.1f) {
                for (int j = -1; j <= 1; j++) {
                    Vec3 a = new Vec3(getX() + i, getY() + j, getZ() - getRadius());
                    Vec3 b = new Vec3(getX() + i, getY() + j, getZ() + getRadius());
                    double stepX = a.x < b.x ? 0.2f : -0.2f;
                    double stepZ = a.z < b.z ? 0.2f : -0.2f;
                    ArrayList<Vec3> vecs = new ArrayList<>();
                    Vec3 curPos = a.add(Vec3.ZERO);
                    for (int k = 0; k < getBbHeight(); k++) {
                        vecs.add(new Vec3(curPos.x, curPos.y + k, curPos.z));
                    }
                    while (stepX != 0 || stepZ != 0) {
                        if ((stepX < 0 && curPos.x <= b.x) || (stepX > 0 && curPos.x >= b.x)) {
                            stepX = 0;
                        }
                        if ((stepZ < 0 && curPos.z <= b.z) || (stepZ > 0 && curPos.z >= b.z)) {
                            stepZ = 0;
                        }
                        curPos = new Vec3(curPos.x + stepX, curPos.y, curPos.z + stepZ);
                        Vec3 tempPos = curPos.add(Vec3.ZERO);
                        if (!vecs.contains(tempPos)) {
                            for (int k = 0; k < getBbHeight(); k++) {
                                vecs.add(new Vec3(tempPos.x, tempPos.y + k, tempPos.z));
                            }
                        }
                    }
                    for (Vec3 vec : vecs) {
                        result = BlockUtil.getHitResult(vec, vec.add(getDeltaMovement()), this, getTargetNonSolid() ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER, getTargetNonSolid() ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE);
                        ArsMagicaAPI.get().getSpellHelper().invoke(SpellItem.getSpell(getStack()), getOwner(), level, result, 0, getIndex(), true);
                    }
                }
            }
        }
    }

    public boolean getTargetNonSolid() {
        return entityData.get(TARGET_NON_SOLID);
    }

    public void setTargetNonSolid() {
        entityData.set(TARGET_NON_SOLID, true);
    }

    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    public int getIndex() {
        return entityData.get(INDEX);
    }

    public void setIndex(int index) {
        entityData.set(INDEX, index);
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(Entity owner) {
        if (owner instanceof LivingEntity) {
            entityData.set(OWNER, owner.getId());
        }
    }

    public float getGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
    }

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(AMItems.BLANK_RUNE.get());
    }

    @Override
    public boolean shouldRender(double p_20296_, double p_20297_, double p_20298_) {
        return false;
    }
}
