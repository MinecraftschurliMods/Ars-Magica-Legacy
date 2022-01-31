package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class WaterGuardian extends AbstractBoss {
    private static final EntityDataAccessor<Boolean> IS_CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private WaterGuardian master = null;
    private WaterGuardian clone1 = null;
    private WaterGuardian clone2 = null;
    //private float spinRotation = 0;
    //private unerSpinAvailable = false;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        //currentAction = BossAction.IDLE;
        //EntityExtension.For(this).setMagicLevelWithMana(10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 75D).add(Attributes.ARMOR, 10);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!(pSource.getEntity() instanceof WaterGuardian)) {
            return false;
        }
        if (isClone() && master != null) {
            //master.enableUberAttack();
            master.clearClones();
        } else if (hasClones()) {
            clearClones();
        }
        if (!isClone() && random.nextInt(10) < 6) {
            level.playSound(null, this, getHurtSound(pSource), SoundSource.HOSTILE, 1.0f, 0.4f + random.nextFloat() * 0.6f);
            return false;
        }
        if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2.0f;
        }
        if (pSource.getEntity() != null && pSource.getEntity() instanceof WaterGuardian) {
            pAmount = 0;
        }
        if (pSource == DamageSource.FREEZE) {
            pAmount = 0;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isClone", isClone());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(IS_CLONE, pCompound.getBoolean("isClone"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_CLONE, false);
    }

    @Override
    public int getMaxFallDistance() {
        return getTarget() == null ? 3 : 3 + (int) (getHealth() - 1.0F);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10.0F + pLevel.getBrightness(pPos) - 0.5F : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(0.1F, pTravelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
    }

    public void setClones(WaterGuardian clone1, WaterGuardian clone2) {
        this.clone1 = clone1;
        this.clone2 = clone2;
    }

    private boolean hasClones() {
        return clone1 != null || clone2 != null;
    }

    public void clearClones() {
        if (clone1 != null) {
            clone1.setRemoved(RemovalReason.DISCARDED);
            clone1 = null;
        }
        if (clone2 != null) {
            clone2.setRemoved(RemovalReason.DISCARDED);
            clone2 = null;
        }
    }

    public boolean isClone() {
        return entityData.get(IS_CLONE);
    }

    public void setMaster(WaterGuardian master) {
        entityData.set(IS_CLONE, true);
        this.master = master;
    }

    public void clearMaster() {
        master = null;
    }
}
