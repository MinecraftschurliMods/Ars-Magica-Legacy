package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class WinterGuardian extends AbstractBoss {
    private boolean hasRightArm;
    private boolean hasLeftArm;
    private float orbitRotation;

    public WinterGuardian(EntityType<? extends WinterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
        this.hasRightArm = true;
        this.hasLeftArm = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 290D).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WINTER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.WINTER_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WINTER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.WINTER_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount = 0;
        } else if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
            pAmount *= 2.0f;
        }
        return super.hurt(pSource, pAmount);
    }

    public void returnOneArm() {
        if (!this.hasLeftArm) {
            this.hasLeftArm = true;
        } else if (!this.hasRightArm) {
            this.hasRightArm = true;
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public void launchOneArm() {
        if (this.hasLeftArm) {
            this.hasLeftArm = false;
        } else if (this.hasRightArm) {
            this.hasRightArm = false;
        }
    }

    public boolean hasLeftArm() {
        return this.hasLeftArm;
    }

    public boolean hasRightArm() {
        return this.hasRightArm;
    }
}
