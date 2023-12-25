package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.network.SpawnAMParticlesPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Blizzard extends AbstractSpellEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);

    public Blizzard(EntityType<? extends Blizzard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(COLOR, -1);
        entityData.define(DURATION, 200);
        entityData.define(OWNER, 0);
        entityData.define(DAMAGE, 0f);
        entityData.define(RADIUS, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(COLOR, tag.getInt("Color"));
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(DAMAGE, tag.getFloat("Damage"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Color", entityData.get(COLOR));
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Damage", entityData.get(DAMAGE));
        tag.putFloat("Radius", entityData.get(RADIUS));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide() || tickCount % 5 != 0) return;
        float damage = getDamage();
        forAllInRange(getRadius(), true,  e -> {
            e.hurt(damageSources().freeze(), damage);
            e.addEffect(new MobEffectInstance(AMMobEffects.FROST.get(), 50));
        });
        if (tickCount > 0) {
            ArsMagicaLegacy.NETWORK_HANDLER.sendToAllAround(new SpawnAMParticlesPacket(this), level(), blockPosition(), 128);
        }
    }

    @Override
    public int getDuration() {
        return entityData.get(DURATION);
    }

    public void setDuration(int duration) {
        entityData.set(DURATION, duration);
    }

    @Override
    @Nullable
    public LivingEntity getOwner() {
        Entity entity = level().getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity living ? living : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    public float getDamage() {
        return entityData.get(DAMAGE);
    }

    public void setDamage(float damage) {
        entityData.set(DAMAGE, damage);
    }

    public float getRadius() {
        return entityData.get(RADIUS);
    }

    public void setRadius(float radius) {
        entityData.set(RADIUS, radius);
    }

    @Override
    public int getColor() {
        return entityData.get(COLOR);
    }

    public void setColor(int color) {
        entityData.set(COLOR, color);
    }
}
