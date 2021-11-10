package com.github.minecraftschurli.arsmagicalegacy.common.entity;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Entity;

public class FireGuardian extends AbstractBoss {
    private boolean isUnderground;
    private int hitCount = 0;

    public FireGuardian(EntityType<? extends FireGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
        this.isUnderground = false;
        this.hitCount = 0;
        this.fireImmune();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 250D).add(Attributes.ARMOR, 17);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
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
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.DROWN) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            pAmount /= 3f;
//        } else if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
//            pAmount = 0;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public boolean getIsUnderground() {
        return this.isUnderground;
    }

    public boolean isBurning() {
        return !this.isUnderground;
    }

    public int getHitCount() {
        return this.hitCount;
    }
}
