package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageSources;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class FallingStar extends AbstractSpellEntity {
    private static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> RADIUS = SynchedEntityData.defineId(FallingStar.class, EntityDataSerializers.FLOAT);
    private static final ParticleOptions PARTICLE = new DustParticleOptions(new Vector3f(0.24f, 0.24f, 0.8f), 1);
    private final DamageSource damageSource = AMDamageSources.fallingStar(this);
    private final Set<LivingEntity> damaged = new HashSet<>();
    private int timeSinceImpact = -1;

    public FallingStar(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER, 0);
        entityData.define(DAMAGE, 10f);
        entityData.define(RADIUS, 6f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        entityData.set(OWNER, tag.getInt("Owner"));
        entityData.set(DAMAGE, tag.getFloat("Damage"));
        entityData.set(RADIUS, tag.getFloat("Radius"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        CompoundTag tag = pCompound.getCompound(ArsMagicaAPI.MOD_ID);
        tag.putInt("Owner", entityData.get(OWNER));
        tag.putFloat("Damage", entityData.get(DAMAGE));
        tag.putFloat("Radius", entityData.get(RADIUS));
    }

    @Override
    public void tick() {
        super.tick();
        if (timeSinceImpact == -1) {
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() > -1f ? -1f : getDeltaMovement().y() - 0.1f, getDeltaMovement().z());
            moveTo(position().add(getDeltaMovement()));
            for (int i = 0; i < 8; i++) {
                level.addParticle(PARTICLE, position().x() + random.nextDouble() / 10, position().y() + (i - 4) * Math.abs(getDeltaMovement().y()) / 8f, position().z() + random.nextDouble() / 10, 0, -0.1, 0);
            }
            HitResult result = ArsMagicaAPI.get().getSpellHelper().trace(this, level, 0.01, true, false);
            if (result.getType() == HitResult.Type.MISS) return;
            Vec3 vec = result.getLocation();
            if (result.getType() == HitResult.Type.BLOCK) {
                while (level.getBlockState(new BlockPos(vec)).getMaterial().isSolid()) {
                    vec = vec.add(0, 1, 0);
                }
                moveTo(vec);
            }
            timeSinceImpact = 0;
        }
        timeSinceImpact++;
        double scaledTimeSinceImpact = timeSinceImpact / 10d;
        for (int i = 0; i < getDamage() * 24; i++) {
            Vec3 offset = Vec3.directionFromRotation(0, i / 24f / getDamage() * 360f).multiply(scaledTimeSinceImpact, scaledTimeSinceImpact, scaledTimeSinceImpact);
            level.addParticle(PARTICLE, position().x() + random.nextDouble() / 10 + offset.x(), position().y() + 1.5 - random.nextDouble() * 2, position().z() + random.nextDouble() / 10 + offset.z(), 0, 0, 0);
        }
        for (Entity e : level.getEntities(this, getBoundingBox().inflate(scaledTimeSinceImpact, 1, scaledTimeSinceImpact), e -> e instanceof LivingEntity living && !damaged.contains(living))) {
            if (e instanceof Player player && player.isCreative()) continue;
            e.hurt(damageSource, getDamage());
            damaged.add((LivingEntity) e);
        }
        if (timeSinceImpact > getRadius() * 10) {
            kill();
        }
    }

    @Override
    @Nullable
    public Entity getOwner() {
        Entity entity = level.getEntity(entityData.get(OWNER));
        return entity instanceof LivingEntity ? (LivingEntity) entity : null;
    }

    @Override
    public void setOwner(LivingEntity owner) {
        entityData.set(OWNER, owner.getId());
    }

    @Override
    public int getDuration() {
        return Integer.MAX_VALUE;
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
