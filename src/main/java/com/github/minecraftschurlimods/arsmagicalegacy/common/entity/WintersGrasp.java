package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

// TODO
public class WintersGrasp extends Entity {
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(WintersGrasp.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(WintersGrasp.class, EntityDataSerializers.ITEM_STACK);

    public WintersGrasp(EntityType<? extends WintersGrasp> type, Level level) {
        super(type, level);
    }

    /**
     * Creates a new instance of this class in the given level. This is necessary, as otherwise the entity registration yells at us with some weird overloading error.
     *
     * @param level the level to create the new instance in
     * @return a new instance of this class in the given level
     */
    public static WintersGrasp create(Level level) {
        return new WintersGrasp(AMEntities.WINTERS_GRASP.get(), level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER, 0);
        entityData.define(STACK, ItemStack.EMPTY);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(STACK, ItemStack.of(tag.getCompound("Stack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Owner", entityData.get(OWNER));
        CompoundTag icon = new CompoundTag();
        entityData.get(STACK).save(icon);
        tag.put("Stack", icon);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        setPos(position().add(getDeltaMovement()));
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public ItemStack getStack() {
        return entityData.get(STACK);
    }

    public void setStack(ItemStack stack) {
        entityData.set(STACK, stack);
    }
}
