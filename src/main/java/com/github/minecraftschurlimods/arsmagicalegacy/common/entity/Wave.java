package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class Wave extends SpellShapeEntity {
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Wave.class, EntityDataSerializers.FLOAT);
    private static final String GRAVITY_KEY = "gravity";
    private static final String RANGE_KEY = "range";

    public Wave(EntityType<? extends Wave> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(GRAVITY, 0f)
            .define(RANGE, 1f);
    }

    @Override
    protected void readData(ValueInput tag) {
        super.readData(tag);
        entityData.set(GRAVITY, tag.getFloatOr(GRAVITY_KEY, 0));
        entityData.set(RANGE, tag.getFloatOr(RANGE_KEY, 1));
    }

    @Override
    protected void writeData(ValueOutput tag) {
        super.writeData(tag);
        tag.putFloat(GRAVITY_KEY, entityData.get(GRAVITY));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
    }

    @Override
    public void tick() {
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - getGravity(), getDeltaMovement().z);
        setPos(position().add(getDeltaMovement()));
        float range = getRange();
        AABB aabb = new AABB(position().add(-range, -range, -range), position().add(range, range, range));
        if (cancelTick(AMServerConfig.WAVE_TICK_INTERVAL.get())) {
            BlockPos.betweenClosedStream(aabb).map(BlockPos::getBottomCenter).forEach(this::spawnParticles);
        } else {
            LivingEntity owner = getOwner();
            castArea(aabb, _ -> true, entity -> entity != owner, true);
        }
    }

    @Override
    public double getDefaultGravity() {
        return entityData.get(GRAVITY);
    }

    public void setGravity(float gravity) {
        entityData.set(GRAVITY, gravity);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float radius) {
        entityData.set(RANGE, radius);
    }
}
