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
    private FireGuardianAction fireGuardianAction;

    public FireGuardian(EntityType<? extends FireGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
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
//        if (ticksInCurrentAction == 30 && this.getFireGuardianAction() == FireGuardianAction.SPINNING) {
//            nova();
//        }
//
//        if (ticksInCurrentAction > 13 && this.getFireGuardianAction() == FireGuardianAction.LONG_CASTING) {
//            if (this.getTarget() != null) {
//                this.lookAt(this.getTarget(), 10, 10);
//            }
//            flamethrower();
//        }
//
//        doFlameShield();
        super.aiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (this.isUnderground && this.getFireGuardianAction() != FireGuardianAction.SPINNING) {
            return false;
        } else {
            if (this.getFireGuardianAction() == FireGuardianAction.SPINNING) {
                ++this.hitCount;
            }
        }

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
        // FireRain
        // Dispel
        // Dive
        // ExecuteSpellGoal (meltAmor)
        // Flamethrower
        // ExecuteSpellGoal (fireBolt)
    }

//    public void fall(float par1, float par2) {
//        if (this.getFireGuardianAction() == FireGuardianAction.SPINNING) {
//            this.isUnderground = true;
//            return;
//        }
//        super.fall(par1, par2);
//    }

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

    public FireGuardianAction getFireGuardianAction() {
        return this.fireGuardianAction;
    }

    public void setFireGuardianAction(final FireGuardianAction action) {
        if (action == FireGuardianAction.SPINNING) {
            // Particle
            // this.addVelocity(0, 1.5, 0);
        } else {
            this.hitCount = 0;
            this.isUnderground = false;
        }

        if (!level.isClientSide()) {
            //AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
        }
        this.fireGuardianAction = fireGuardianAction;
    }

    public boolean isFireGuardianActionValid(FireGuardianAction action) {
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
            this.fireGuardianAction = FireGuardianAction.CASTING;
        } else {
            // SPINNING statt IDLE?
            this.fireGuardianAction = FireGuardianAction.SPINNING;
        }
    }

    public enum FireGuardianAction {
        SPINNING(160),
        CASTING(-1),
        LONG_CASTING(-1);

        private final int maxActionTime;

        private FireGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
