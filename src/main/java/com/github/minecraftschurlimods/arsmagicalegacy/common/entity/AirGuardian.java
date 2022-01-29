package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AirGuardian extends AbstractBoss {
    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 220D).add(Attributes.ARMOR, 14);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
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
        return super.hurt(pSource, pAmount);
    }
}
