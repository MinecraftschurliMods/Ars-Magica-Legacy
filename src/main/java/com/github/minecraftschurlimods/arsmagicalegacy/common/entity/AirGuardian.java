package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

// TODO aiStep() (Goal & Particles)
// TODO registerGoal()
// TODO setAirGuardianAction() Network Handler

public class AirGuardian extends AbstractBoss {
    private boolean useLeftArm = false;
    private float spinRotation = 0;
    private float orbitRotation;
    private int hitCount = 0;
    private boolean firstTick = true;
    private AirGuardianAction airGuardianAction;

    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 220D).add(Attributes.ARMOR, 14);
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
        if (firstTick) {
            //this.goalSelector.addGoal(0, new SpawnLocationGoal(this, 0.5F, 5, 16, new Vec3(this)));
            this.firstTick = false;
        }

        this.orbitRotation += 2f;
        this.orbitRotation %= 360;

        switch(this.airGuardianAction) {
            case IDLE:
                break;
            case SPINNING:
                this.spinRotation = (this.spinRotation - 40) % 360;
                if (this.level.isClientSide()) {
                    // Particle
                }
                break;
            default:
                break;
        }

        if (this.getDeltaMovement().y() < 0) {
            this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() * 0.7999999f, this.getDeltaMovement().z());
        }

        if (this.getY() < 150) {
            if (this.level.isClientSide()) {
                // Particle
            } else {
                if (this.getY() < 145) {
                    this.removeAfterChangingDimensions();
                }
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        this.hitCount++;
        if (pSource.isMagic()) {
            pAmount /= 2;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Wander
        // ExecuteSpellGoal (dispel)
        // SpawnWhirlwind
        // Hurrican
    }

    public boolean useLeftArm() {
        return this.useLeftArm;
    }

    public float getOrbitRotation() {
        return this.orbitRotation;
    }

    @Override
    public boolean isPushable() {
        return this.airGuardianAction != AirGuardianAction.SPINNING;
    }

    public AirGuardianAction getAirGuardianAction() {
        return this.airGuardianAction;
    }

    public void setAirGuardianAction(final AirGuardianAction action) {
        this.spinRotation = 0;
        this.hitCount = 0;

        if (action == AirGuardianAction.CASTING) {
            this.useLeftArm = !this.useLeftArm;
        }
//        if (!this.level.isClientSide()){
//            AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
//        }
        this.airGuardianAction = action;
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
            this.airGuardianAction = AirGuardianAction.CASTING;
        } else {
            this.airGuardianAction = AirGuardianAction.IDLE;
        }
    }

    public enum AirGuardianAction {
        IDLE(-1),
        SPINNING(160),
        CASTING(-1);

        private final int maxActionTime;

        private AirGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
