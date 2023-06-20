package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class ManaVortex extends Entity {
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(ManaVortex.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> MANA = SynchedEntityData.defineId(ManaVortex.class, EntityDataSerializers.FLOAT);

    public ManaVortex(EntityType<? extends ManaVortex> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DURATION, 50 + level.getRandom().nextInt(250));
        entityData.define(MANA, 0f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(MANA, tag.getFloat("Mana"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putFloat("Mana", entityData.get(MANA));
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
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        tickCount++;
        if (entityData.get(DURATION) - this.tickCount > 30) {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4, 4, 4))) {
                if (e.getAttribute(AMAttributes.MAX_MANA.get()) == null) continue;
                if (!level.isClientSide()) {
                    var helper = ArsMagicaAPI.get().getManaHelper();
                    float stolen = Math.min(helper.getMana(e), helper.getMaxMana(e) / 100f);
                    entityData.set(MANA, entityData.get(MANA) + stolen);
                    helper.setMana(e, helper.getMana(e) - stolen);
                    Vec3 movement = e.position().subtract(position()).normalize();
                    setDeltaMovement(movement.x * 0.075f, movement.y * 0.075f, movement.z * 0.075f);
                } else {
                    level.addParticle(new DustParticleOptions(new Vector3f(0.24f, 0.24f, 0.8f), 1), position().x(), position().y(), position().z(), 0, 0, 0);
                }
            }
            moveTo(position().add(getDeltaMovement()));
        }
        if (entityData.get(DURATION) - tickCount <= 20) {
            this.setBoundingBox(getBoundingBox().inflate(-0.05f));
        }
        if (entityData.get(DURATION) - tickCount <= 5) {
            if (!level.isClientSide()) {
                float damage = Math.min(100, entityData.get(MANA) / 100f);
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(4, 4, 4))) {
                    e.hurt(damageSources().magic(), damage);
                }
            } else {
                for (int i = 0; i < 72; i++) {
                    level.addParticle(new DustParticleOptions(new Vector3f(0.24f, 0.24f, 0.8f), 1), position().x(), position().y(), position().z(), 0, 0, 0);
                }
            }
            setRemoved(RemovalReason.KILLED);
        }
    }
}
