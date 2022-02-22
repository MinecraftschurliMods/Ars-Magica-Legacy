package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.phys.Vec3;

// TODO aiStep() condition with ticksInCurrentAction
// TODO registerGoal()
// TODO shouldRenderRock()
// TODO setEarthGuardianAction() Network Handler?
// TODO setIsCastingSpell() STRIKE or THROWING_ROCK

public class EarthGuardian extends AbstractBoss {
    private float rodRotation = 0;
    public boolean leftArm = false;
    private EarthGuardianAction earthGuardianAction;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 140).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        // if (ticksInCurrentAction > 40 && !level.isClientSide()) {
        if (!level.isClientSide()) { // missing condition
            this.setEarthGuardianAction(EarthGuardianAction.IDLE);
        } else if (level.isClientSide()) {
            this.updateRotations();
        }

        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount *= 2.0f;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount /= 4.0f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Dispel
        // ThrowRock
        // Smash
        // StrikeAttack
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

    public boolean shouldRenderRock(){
        return true;

        // frag david
        //return this.getEarthGuardianAction() == EarthGuardianAction.THROWING_ROCK && ticksInCurrentAction > 5 && tick < 27;
    }

    public float getRodRotations(){
        return this.rodRotation;
    }

    private void updateRotations(){
        this.rodRotation += 0.02f;
        this.rodRotation %= 360;
    }

    public EarthGuardianAction getEarthGuardianAction() {
        return this.earthGuardianAction;
    }

    public void setEarthGuardianAction(final EarthGuardianAction action) {
        if (this.getEarthGuardianAction() != action && action == EarthGuardianAction.STRIKE && level.isClientSide()) {
            this.leftArm = !this.leftArm;
        }
        //        } else if (!level.isClientSide()) {
        //            AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
        //        }
        this.earthGuardianAction = action;
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
            // STRIKE oder THROWING_ROCK
            this.earthGuardianAction = EarthGuardianAction.STRIKE;
        } else {
            this.earthGuardianAction = EarthGuardianAction.IDLE;
        }
    }

    public enum EarthGuardianAction {
        IDLE(-1),
        STRIKE(15),
        THROWING_ROCK(30);

        private final int maxActionTime;

        private EarthGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }
        public int getMaxActionTime(){
            return maxActionTime;
        }

    }
}
