package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttachments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.entity.PartEntity;

public class Blizzard extends SpellEntity {
    private static final EntityDataAccessor<Integer> FROST_DURATION = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RANGE = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);
    private static final String FROST_DURATION_KEY = "frost_duration";
    private static final String DAMAGE_KEY = "damage";
    private static final String RANGE_KEY = "range";

    public Blizzard(EntityType<? extends Blizzard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(FROST_DURATION, 200)
            .define(DAMAGE, 0f)
            .define(RANGE, 1f);
    }

    @Override
    protected void readData(ValueInput tag) {
        entityData.set(FROST_DURATION, tag.getIntOr(FROST_DURATION_KEY, 200));
        entityData.set(DAMAGE, tag.getFloatOr(DAMAGE_KEY, 0f));
        entityData.set(RANGE, tag.getFloatOr(RANGE_KEY, 1f));
    }

    @Override
    protected void writeData(ValueOutput tag) {
        tag.putInt(FROST_DURATION_KEY, entityData.get(FROST_DURATION));
        tag.putFloat(DAMAGE_KEY, entityData.get(DAMAGE));
        tag.putFloat(RANGE_KEY, entityData.get(RANGE));
    }

    @Override
    public void tick() {
        LivingEntity owner = getOwner();
        float range = getRange();
        double verticalRange = range * AMServerConfig.BLIZZARD_HEIGHT.get();
        if (level().isClientSide()) {
            AMClientUtil.spawnSpellEntityParticles(this, range, verticalRange, getColor(), owner);
        }
        if (cancelTick(AMServerConfig.BLIZZARD_TICK_INTERVAL.get())) return;
        int frostDuration = getFrostDuration();
        float damage = getDamage();
        for (Entity entity : level().getEntities(this, new AABB(position().add(-range, 0, -range), position().add(range, verticalRange, range)))) {
            while (entity instanceof PartEntity<?> part) {
                entity = part.getParent();
            }
            if (entity == owner || !entity.canFreeze() || entity instanceof Player player && player.isCreative()) continue;
            if (level() instanceof ServerLevel serverLevel) {
                entity.hurtServer(serverLevel, damageSources().freeze(), damage);
            }
            entity.setData(AMAttachments.FROST, frostDuration);
        }
    }

    public int getFrostDuration() {
        return entityData.get(FROST_DURATION);
    }

    public void setFrostDuration(int frostDuration) {
        entityData.set(FROST_DURATION, frostDuration);
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRange() {
        return entityData.get(RANGE);
    }

    public void setRange(float radius) {
        entityData.set(RANGE, radius);
    }
}
