package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.registries.DataSerializerEntry;
import org.jetbrains.annotations.NotNull;

public class WaterGuardian extends AbstractBoss {
    private WaterGuardian master;
    private WaterGuardian clone1;
    private WaterGuardian clone2;
    private static final EntityDataAccessor<Boolean> IS_CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    //private float spinRotation = 0;
    //private unerSpinAvailable = false;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        //currentAction = BossAction.IDLE;
        this.master = null;
        this.clone1 = null;
        this.clone2 = null;
        //EntityExtension.For(this).setMagicLevelWithMana(10);

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 75D).add(Attributes.ARMOR, 10);
    }

    @Override
    public int getMaxFallDistance() {
        return this.getTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    public void setClones(WaterGuardian clone1, WaterGuardian clone2){
        this.clone1 = clone1;
        this.clone2 = clone2;
    }

    private boolean hasClones(){
        return this.clone1 != null || this.clone2 != null;
    }

    public void clearClones(){
        if (this.clone1 != null){
            this.clone1.setRemoved(RemovalReason.DISCARDED);
            this.clone1 = null;
        }
        if (this.clone2 != null){
            this.clone2.setRemoved(RemovalReason.DISCARDED);
            this.clone2 = null;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CLONE, false);
    }

    public boolean isClone(){
        return this.entityData.get(IS_CLONE);
    }

    public void setMaster(WaterGuardian master){
        this.entityData.set(IS_CLONE, true);
        this.master = master;
    }

    public void clearMaster(){
        this.master = null;
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
        this.entityData.set(IS_CLONE, pCompound.getBoolean("isClone"));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return AMSounds.WATER_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.WATER_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (!(pSource.getEntity() instanceof WaterGuardian)) {
            return false;
        }

        if (isClone() && master != null) {
            //master.enableUberAttack();
            this.master.clearClones();
        } else if (this.hasClones()) {
            this.clearClones();
        }

        if (!isClone() && pSource != DamageSource.OUT_OF_WORLD && random.nextInt(10) < 6) {
            this.level.playSound(null, this, getHurtSound(pSource), SoundSource.HOSTILE, 1.0f, 0.4f + random.nextFloat() * 0.6f);
            return false;
        }

        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
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
        } else {
            super.travel(pTravelVector);
        }
    }
}
