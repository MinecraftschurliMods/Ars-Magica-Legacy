package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ISpellCasterEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

//TODO WaterGuadian registerGoals()

public class WaterGuardian extends AbstractBoss {
    private WaterGuardian master = null;
    private WaterGuardian clone1 = null;
    private WaterGuardian clone2 = null;
    private static final EntityDataAccessor<Boolean> IS_CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private float orbitRotation;
    private float spinRotation = 0;
    private boolean uberSpinAvailable = false;
    private WaterGuardianAction waterGuardianAction;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
        this.waterGuardianAction = WaterGuardianAction.IDLE;
        this.entityData.define(IS_CLONE, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 75D).add(Attributes.ARMOR, 10);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
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
        if (this.getWaterGuardianAction() == WaterGuardianAction.CASTING) {
            this.uberSpinAvailable = false;
        } else if (!this.level.isClientSide() && this.uberSpinAvailable && this.getWaterGuardianAction() != WaterGuardianAction.CASTING && this.getWaterGuardianAction() != WaterGuardianAction.IDLE) {
            this.setWaterGuardianAction(WaterGuardianAction.IDLE);
        } else if (!this.level.isClientSide() && this.isClone() && (this.master == null || this.tickCount > 400)) {
            this.remove(RemovalReason.KILLED);
        } else if (this.level.isClientSide()) {
            this.updateRotation();
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (!(pSource.getEntity() instanceof WaterGuardian)) {
            return false;
        } else if (this.isClone() && this.master != null) {
            this.master.enableUberAttack();
            this.master.clearClones();
        } else if (this.hasClones()) {
            this.clearClones();
        } else if (!this.isClone() && random.nextInt(10) < 6) {
            this.level.playSound(null, this, this.getHurtSound(pSource), SoundSource.HOSTILE, 1.0f, 0.4f + random.nextFloat() * 0.6f);
            return false;
        }

        if(pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2.0f;
        } else if(pSource.getEntity() != null && pSource.getEntity() instanceof WaterGuardian) {
            pAmount = 0;
        } else if(pSource == DamageSource.FREEZE) {
            pAmount = 0;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // ChaosWaterBolt
        // CloneSelf
        // SpinAttack
        //goalSelector.addGoal(4, new ExecuteSpellGoal<WaterGuardian>(this, new ISpell(), 12, 23));
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
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CLONE, false);
    }

    @Override
    public int getMaxFallDistance() {
        return getTarget() == null ? 3 : 3 + (int)(getHealth() - 1.0F);
    }

    @Override
    public float getWalkTargetValue(@NotNull BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10.0F + pLevel.getBrightness(pPos) - 0.5F : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(@NotNull Vec3 pTravelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(0.1F, pTravelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
    }

    private void enableUberAttack(){
        uberSpinAvailable = true;
    }

    private void updateRotation() {
        if (!this.isClone()) {
            this.orbitRotation += 2f;
        } else {
            this.orbitRotation -= 2f;
        }

        if (this.getWaterGuardianAction() == WaterGuardianAction.SPINNING || this.getWaterGuardianAction() == WaterGuardianAction.CASTING) {
            this.spinRotation = (this.spinRotation - 30) % 360;
        }
    }

    public float getOrbitRotation() {
        return orbitRotation;
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
        } else if (this.clone2 != null){
            this.clone2.setRemoved(RemovalReason.DISCARDED);
            this.clone2 = null;
        }
    }

    public boolean isClone(){
        return this.entityData.get(IS_CLONE);
    }

    public void setMaster(WaterGuardian master){
        this.entityData.set(IS_CLONE, true);
        this.master = master;
    }

    public void clearMaster(){
        master = null;
    }

    public WaterGuardianAction getWaterGuardianAction() {
        return this.waterGuardianAction;
    }

    public void setWaterGuardianAction(final WaterGuardianAction action) {
        this.waterGuardianAction = action;
        this.spinRotation = 0;
    }

    public boolean isWaterGuardianActionValid(WaterGuardianAction action) {
        if (this.uberSpinAvailable && action != WaterGuardianAction.CASTING) {
            return false;
        } else if (action == WaterGuardianAction.CASTING) {
            return this.uberSpinAvailable;
        } else if (action == WaterGuardianAction.CLONE) {
            return !this.isClone();
        }
        return true;
    }

    @Override
    public boolean canCastSpell() {
        return false;
    }

    @Override
    public boolean isCastingSpell() {
        return false;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if(isCastingSpell) {
            this.waterGuardianAction = WaterGuardianAction.CASTING;
        } else {
            this.waterGuardianAction = WaterGuardianAction.IDLE;
        }
    }

    public enum WaterGuardianAction {
        IDLE(-1),
        SPINNING(160),
        CASTING(-1),
        CLONE(30);

        private final int maxActionTime;

        private WaterGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
