package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Blizzard extends AbstractSpellEntity {
    private static final EntityDataAccessor<Integer> DURATION = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(Blizzard.class, EntityDataSerializers.FLOAT);

    public Blizzard(EntityType<? extends Blizzard> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(DURATION, 200);
        entityData.define(OWNER, 0);
        entityData.define(DAMAGE, 0f);
        entityData.define(RADIUS, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(DURATION, tag.getInt("Duration"));
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(DAMAGE, tag.getFloat("Damage"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Duration", entityData.get(DURATION));
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Damage", entityData.get(DAMAGE));
        tag.putFloat("Radius", entityData.get(RADIUS));
    }

    @Override
    public void tick() {
        super.tick();
        for (int i = 0; i < 20 * getRadius(); ++i) {
            level.addParticle(ParticleTypes.SNOWFLAKE, getRandomX(getRadius() * 2), getY() + (2d * random.nextDouble() - 1d) * getRadius() / 2, getRandomZ(getRadius() * 2), 0, 0, 0);
        }
        if (level.isClientSide() || tickCount % 5 != 0) return;
        float damage = getDamage();
        forAllInRange(getRadius(), true,  e -> {
            e.hurt(DamageSource.FREEZE, damage);
            e.addEffect(new MobEffectInstance(AMMobEffects.FROST.get(), 50));
        });
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
    public Entity getOwner() {
        return level.getEntity(entityData.get(OWNER));
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
}
