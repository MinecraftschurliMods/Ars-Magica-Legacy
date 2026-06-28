package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class Zone extends SpellShapeEntity {
    private static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Zone.class, EntityDataSerializers.FLOAT);
    private static final String GRAVITY_KEY = "gravity";
    private static final String RANGE_KEY = "range";

    public Zone(EntityType<? extends Zone> type, Level level) {
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
        setPos(position().add(0, -getGravity(), 0));
        if (cancelTick(AMServerConfig.ZONE_TICK_INTERVAL.get())) return;
        float range = getRange();
        castArea(new AABB(position().add(-range, 0, -range), position().add(range, AMServerConfig.ZONE_HEIGHT.get(), range)), _ -> true, _ -> true, false);
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

    public void setRange(float range) {
        entityData.set(RANGE, range);
    }
}
