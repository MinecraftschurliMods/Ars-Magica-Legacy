package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

public class NatureScythe extends Entity {
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(NatureScythe.class, EntityDataSerializers.ITEM_STACK);
    private boolean hasHit = false;
    private int hitTicks = -1;

    public NatureScythe(EntityType<? extends NatureScythe> type, Level level) {
        super(type, level);
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
        CompoundTag stack = new CompoundTag();
        entityData.get(STACK).save(stack);
        tag.put("Stack", stack);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        if (getOwner() == null) {
            remove(RemovalReason.KILLED);
            return;
        }
        if (hitTicks != -1 && tickCount / 2 > hitTicks) {
            returnToOwner();
        } else if (tickCount > 50) {
            setHasHit();
        }
        HitResult result = AMUtil.getHitResult(position(), position().add(getDeltaMovement()), this, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE);
        if (result.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof PartEntity) {
                entity = ((PartEntity<?>) entity).getParent();
            }
            if (entity instanceof LivingEntity living && entity != getOwner()) {
                living.hurt(AMDamageSources.natureScythe(this, getOwner()), 12);
                setHasHit();
            }
            if (hasHit && distanceTo(getOwner()) < 4) {
                returnToOwner();
            }
        } else if (result.getType() == HitResult.Type.BLOCK) {
            setHasHit();
        }
        setPos(position().add(getDeltaMovement()));
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(entityData.get(OWNER));
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

    private void setHasHit() {
        if (!hasHit) {
            setDeltaMovement(getDeltaMovement().multiply(-1, -1, -1));
            hasHit = true;
            hitTicks = tickCount;
        }
    }

    private void returnToOwner() {
        LivingEntity owner = getOwner();
        if (owner instanceof NatureGuardian guardian) {
            guardian.setHasScythe(true);
        } else if (owner instanceof Player player && !player.addItem(getStack())) {
            ItemEntity item = new ItemEntity(level(), player.getX(), player.getY(), player.getZ(), getStack());
            level().addFreshEntity(item);
        }
        remove(RemovalReason.KILLED);
    }
}
