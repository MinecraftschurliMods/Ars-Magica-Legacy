package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.affinity.AffinityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.ibm.icu.impl.locale.Extension;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

// TODO aiStep() (EntityExtension & Particle)
// TODO maybe hurt()
// TODO registerGoal()
// TODO onDeath()

public class LightningGuardian extends AbstractBoss {
    LightningGuardianAction lightningGuardianAction;

    public LightningGuardian(EntityType<? extends LightningGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 250D).add(Attributes.ARMOR, 18);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.LIGHTNING_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIGHTNING_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.getTarget() != null) {
            if (this.getLightningGuardianAction() != LightningGuardianAction.LONG_CASTING) {
                // EntityExtension.For(getAttackTarget()).setDisableGravity(false);
            }

            if (!this.level.isClientSide() && this.distanceToSqr(this.getTarget()) > 64D && this.getLightningGuardianAction() == LightningGuardianAction.IDLE) {
                this.getNavigation().moveTo(this.getTarget(), 0.5f);
            }
        }

        if (this.level.isClientSide()) {
            int halfDist = 8;
            int dist = 16;
            if (this.getLightningGuardianAction() == LightningGuardianAction.CHARGE) {
                if (this.ticksInAction > 50) {
                    // Particle
                }
                if (this.ticksInAction > 66) {
                    // Particle
                }
            } else if (this.getLightningGuardianAction() == LightningGuardianAction.LONG_CASTING) {
                if (this.ticksInAction > 25 && this.ticksInAction < 150) {
                    // Particle
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic() || pSource == DamageSource.MAGIC) {
            pAmount *= 2;
        } else if (pSource == DamageSource.DROWN) {
            pAmount *= 4;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) { // maybe custom Lightning
            pAmount *= -1;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // ExecuteSpellGoal (dispel)
        // LightningRod
        // Static
        // ExecuteSpellGoal (lightningRune)
        // ExecuteSpellGoal (scrambleSynapses)
        // LightningBolt
    }

//    @Override
//    public void onDeath(DamageSource pSource){
//        if (this.getTarget() != null)
//            EntityExtension.For(this.getTarget()).setDisableGravity(false);
//        super.onDeath(pSource);
//    }


    @Override
    public float getEyeHeight(Pose pPose) {
        return 2;
    }

//        public void setAnimID(int id) {
//            this.setLightningGuardianAction(LightningGuardianAction.values()[id]);
//            this.ticksInAction = 0;
//        }
//
//        public void setAnimTick(int tick) {
//            this.ticksInAction = tick;
//        }
//
//        public int getAnimID() {
//            return this.getLightningGuardianAction().ordinal();
//        }
//
//        public int getAnimTick() {
//            return this.ticksInAction;
//        }

    public LightningGuardianAction getLightningGuardianAction() {
        return this.lightningGuardianAction;
    }

    public void setLightningGuardianAction(final LightningGuardianAction action) {
        this.lightningGuardianAction = action;
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
            this.lightningGuardianAction = LightningGuardianAction.CASTING;
        } else {
            this.lightningGuardianAction = LightningGuardianAction.IDLE;
        }
    }

    public enum LightningGuardianAction {
        IDLE(-1),
        SMASH(20),
        CASTING(-1),
        LONG_CASTING(-1),
        CHARGE(-1);

        private final int maxActionTime;

        private LightningGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
