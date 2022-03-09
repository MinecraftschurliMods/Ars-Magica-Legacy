package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.List;

// TODO aiStep() maybe spawnParticles()
// TODO registerGoal()
// TODO spawnParticles() missing
// TODO setIceGuardianAction() Network Handler?

public class IceGuardian extends AbstractBoss {
    private boolean hasRightArm = true;
    private boolean hasLeftArm = true;
    private float orbitRotation;
    private IceGuardianAction iceGuardianAction;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 290D).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide()) {
            this.updateRotations();
            //this.spawnParticles();
        } else {
            if (this.tickCount % 100 == 0) {
                List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5 , 2.5).expandTowards(0, -3, 0));
                for (LivingEntity e : entities) {
                    if (e != this) {
                        e.hurt(DamageSource.ON_FIRE, 5);
                    }
                }
            }
        }

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

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // ExecuteSpellGoal (dispel)
        // Smash
        // StrikeAttack
        // WinterGuardianLaunchArm
    }

    public void returnOneArm() {
        if (!this.hasLeftArm) {
            this.hasLeftArm = true;
        } else if (!this.hasRightArm) {
            this.hasRightArm = true;
        }
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

    private void updateRotations(){
        this.orbitRotation += 2f;
        this.orbitRotation %= 360;
    }

    public float getOrbitRotation(){
        return this.orbitRotation;
    }

    public IceGuardianAction getIceGuardianAction() {
        return this.iceGuardianAction;
    }

    public void setIceGuardianAction(final IceGuardianAction action) {
        if (!this.level.isClientSide()) {
            //AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
        }
        this.iceGuardianAction = action;
        this.ticksInAction = 0;
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
            this.iceGuardianAction = IceGuardianAction.CASTING;
        } else {
            this.iceGuardianAction = IceGuardianAction.IDLE;
        }
    }

    public enum IceGuardianAction {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        private IceGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
