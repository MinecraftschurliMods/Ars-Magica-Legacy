package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSounds;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WaterGuardian extends AbstractBoss {
    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 75D).add(Attributes.ARMOR, 10);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
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
