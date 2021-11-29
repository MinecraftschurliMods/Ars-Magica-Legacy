package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WallEntity extends Entity {
    private static final EntityDataAccessor<Boolean> TARGET_NON_SOLID = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> INDEX = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(ProjectileEntity.class, EntityDataSerializers.ITEM_STACK);

    public WallEntity(EntityType<? extends WallEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static WallEntity create(Level level) {
        return new WallEntity(AMEntities.WALL.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TARGET_NON_SOLID, false);
        entityData.define(DURATION, 200);
        entityData.define(INDEX, 0);
        entityData.define(OWNER, 0);
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
}
