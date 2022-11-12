package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ThrownRock extends Entity {
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(ThrownRock.class, EntityDataSerializers.INT);

    public ThrownRock(EntityType<? extends ThrownRock> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER, 0);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(OWNER, tag.getInt("Owner"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Owner", entityData.get(OWNER));
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
        tickCount++;
        if (getOwner() == null || getOwner().isDeadOrDying() || tickCount >= 100) {
            setRemoved(RemovalReason.KILLED);
        }
        Vec3 oldPos = position();
        Vec3 newPos = position().add(getDeltaMovement());
        HitResult hit = AMUtil.getHitResult(oldPos, newPos, this, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE);
        if (hit.getType() != HitResult.Type.MISS) {
            newPos = hit.getLocation();
        }
        if (hit.getType() == HitResult.Type.ENTITY && !level.isClientSide()) {
            Entity entity = ((EntityHitResult) hit).getEntity();
            if (entity instanceof LivingEntity living && entity != getOwner()) {
                if (!living.isBlocking()) {
                    entity.hurt(AMDamageSources.thrownRock(this, getOwner()), 6);
                } else if (living instanceof Player player) {
                    player.stopUsingItem();
                    if (random.nextFloat() < 0.25f) {
                        player.getCooldowns().addCooldown(Items.SHIELD, 100);
                        level.broadcastEntityEvent(player, (byte) 30);
                    }
                }
                setRemoved(RemovalReason.KILLED);
            }
        }
        setPos(newPos);
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }
}
