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

// TODO aiStep() maybe spawnParticles()
// TODO spawnParticles() missing
// TODO registerGoal()

public class NatureGuardian extends AbstractBoss {
    private float tendrilRotation;
    private boolean hasSickle = true;
//    private float last_rotation_x_main = 0;
//    private float last_rotation_x_shield = 0;
//    private float last_rotation_y_main = 0;
//    private float last_rotation_y_shield = 0;
//    private float last_rotation_z_main = 0;
//    private float last_rotation_z_shield = 0;
    private float spinRotation = 0;
    private NatureGuardianAction natureGuardianAction;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 500D).add(Attributes.ARMOR, 20);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.NATURE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.NATURE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.NATURE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide()) {
            this.updateMovementAngles();
            //this.spawnParticles();
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            pAmount *= 1.5f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // ExecuteSpellGoal (dispel)
        // PlantGuardianThrowSickle
        // SpinAttack
        // StrikeAttack
        // ShieldBash
    }

    private void updateMovementAngles(){
        this.tendrilRotation += 0.2f;
        this.tendrilRotation %= 360;

        switch (this.getNatureGuardianAction()){
            case IDLE:
                break;
            case SPINNING:
                this.spinRotation = (this.spinRotation - 40) % 360;
                break;
            case STRIKE:
                break;
            case THROWING_SICKLE:
                break;
            default:
                break;
        }
    }

    public NatureGuardianAction getNatureGuardianAction() {
        return this.natureGuardianAction;
    }

    public void setNatureGuardianAction(final NatureGuardianAction action) {
        this.spinRotation = 0;

        if (!level.isClientSide()) {
            //AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
        }
        this.natureGuardianAction = action;
        this.ticksInAction = 0;
    }

    public boolean isNatureGuardianActionValid(NatureGuardianAction action) {
        if (action == NatureGuardianAction.STRIKE || action == NatureGuardianAction.SPINNING || action == NatureGuardianAction.THROWING_SICKLE) {
            return this.hasSickle;
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
            this.natureGuardianAction = NatureGuardianAction.CASTING;
        } else {
            this.natureGuardianAction = NatureGuardianAction.IDLE;
        }
    }

    public enum NatureGuardianAction {
        IDLE(-1),
        STRIKE(15),
        THROWING_SICKLE(15),
        SPINNING(160),
        CASTING(-1);

        private final int maxActionTime;

        private NatureGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
