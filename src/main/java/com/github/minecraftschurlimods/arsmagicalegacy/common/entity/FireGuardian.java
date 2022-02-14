package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FireGuardian extends AbstractBoss {
    private boolean isUnderground = false;
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
        return AMSounds.FIRE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return AMSounds.FIRE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.FIRE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_ATTACK.get();
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

    @Override
    public boolean fireImmune() {
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

    }
}
