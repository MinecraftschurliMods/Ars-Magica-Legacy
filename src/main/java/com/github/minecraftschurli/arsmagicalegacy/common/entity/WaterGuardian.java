package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.entity.ai.ExecuteSpellGoal;
import net.minecraft.core.BlockPos;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WaterGuardian extends Monster {
//    protected static final int ATTACK_TIME = 80;
//    private static final EntityDataAccessor<Boolean> DATA_ID_MOVING = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
//    private static final EntityDataAccessor<Integer> DATA_ID_ATTACK_TARGET = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.INT);
//    protected RandomStrollGoal randomStrollGoal;

    public WaterGuardian(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 10;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(1, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        //this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, new ExecuteSpellGoal(this)));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.1D).add(Attributes.FOLLOW_RANGE, 16.0D).add(Attributes.MAX_HEALTH, 75.0D);
    }

//    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(DATA_ID_MOVING, false);
//        this.entityData.define(DATA_ID_ATTACK_TARGET, 0);
//    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    @NotNull
    public MobType getMobType() { return MobType.WATER; }

//    public boolean isMoving() {
//        return this.entityData.get(DATA_ID_MOVING);
//    }
//
//    void setMoving(boolean pMoving) {
//        this.entityData.set(DATA_ID_MOVING, pMoving);
//    }
//
//    public int getAttackDuration() {
//        return 80;
//    }
//
//    void setActiveAttackTarget(int pEntityId) {
//        this.entityData.set(DATA_ID_ATTACK_TARGET, pEntityId);
//    }
//
//    public boolean hasActiveAttackTarget() {
//        return this.entityData.get(DATA_ID_ATTACK_TARGET) != 0;
//    }

//    @Override
//    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
//        super.onSyncedDataUpdated(pKey);
//        if (DATA_ID_ATTACK_TARGET.equals(pKey)) {
//            this.clientSideAttackTime = 0;
//            this.clientSideCachedAttackTarget = null;
//        }
//
//    }


    @Override
    public int getAmbientSoundInterval() {
        return 160;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_AMBIENT : SoundEvents.GUARDIAN_AMBIENT_LAND;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_HURT : SoundEvents.GUARDIAN_HURT_LAND;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.isInWaterOrBubble() ? SoundEvents.GUARDIAN_DEATH : SoundEvents.GUARDIAN_DEATH_LAND;
    }

    @Override
    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose pPose, EntityDimensions pSize) {
        return pSize.height * 0.5F;
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10.0F + pLevel.getBrightness(pPos) - 0.5F : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            this.moveRelative(0.1F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
//            if (!this.isMoving() && this.getTarget() == null) {
//                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
//            }
        } else {
            super.travel(pTravelVector);
        }

    }

}
