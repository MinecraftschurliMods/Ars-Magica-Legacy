package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class ArcaneGuardian extends AbstractBoss {
    private float runeRotationZ = 0;
    private float runeRotationY = 0;
    private ArcaneGuardianAction arcaneGuardianAction;

    private static final EntityDataAccessor<Integer> DW_TARGET_ID = SynchedEntityData.defineId(ArcaneGuardian.class, EntityDataSerializers.INT);

    public ArcaneGuardian(EntityType<? extends ArcaneGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 115.0D).add(Attributes.ARMOR, 9);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ARCANE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ARCANE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ARCANE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ARCANE_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (this.getDeltaMovement().y() < 0) {
            this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() * 0.7999999f, this.getDeltaMovement().z());
        }

        this.updateRotation();
        if (!this.level.isClientSide()) {
            int eid = this.entityData.get(DW_TARGET_ID);
            int tid = -1;
            if (eid != tid) {
                this.entityData.set(DW_TARGET_ID, tid);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FALL) {
            return false;
        }
        if (pSource.getEntity() == null || this.checkRuneRetaliation(pSource)) {
            return super.hurt(pSource, pAmount);
        }
        return super.hurt(pSource, pAmount * 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // ExecuteSpellGoal (dispel)
        // ExecuteSpellGoal (healSelf)
        // ExecuteSpellGoal (blink)
        // ExecuteSpellGoal (arcaneBolt)
    }

    private void updateRotation() {
        this.runeRotationZ = 0;
        float targetRuneRotationY = 0;
        float runeRotationSpeed = 0.3f;
        if (this.getTarget() != null) {
            double deltaX = this.getTarget().getX();
            double deltaZ = this.getTarget().getZ();
            double angle = Math.atan2(deltaZ, deltaX);
            angle -= Math.toRadians(Mth.wrapDegrees(this.getYRot() + 90) + 180);
            targetRuneRotationY = (float) angle;
            runeRotationSpeed = 0.085f;
        }

        if (targetRuneRotationY > this.runeRotationY) {
            this.runeRotationY += runeRotationSpeed;
        } else if (targetRuneRotationY < this.runeRotationY) {
            this.runeRotationY -= runeRotationSpeed;
        }

        float tolerance = 0.25f;
        if(this.isWithin(this.runeRotationY, targetRuneRotationY, 0.25f)) {
            this.runeRotationY = targetRuneRotationY;
        }
    }

    private boolean checkRuneRetaliation(DamageSource pSource) {
        if (pSource.getEntity() instanceof  ArcaneGuardian) {
            return true;
        }

        double deltaX = pSource.getEntity().getX() - this.getX();
        double deltaZ = pSource.getEntity().getZ() - this.getZ();
        double angle = Math.atan2(deltaZ, deltaX);
        angle -= Math.toRadians(Mth.wrapDegrees(this.getYRot() + 90) + 180);
        float targetRuneRotationY = (float) angle;

        if (this.distanceToSqr(pSource.getEntity()) < 9) {
            double speed = 2.5;
            double vSpeed = 0.325;

            deltaZ = pSource.getEntity().getZ() - this.getZ();
            deltaX = pSource.getEntity().getX() - this.getX();
            angle = Math.atan2(deltaZ, deltaX);
            double radians = angle;

            if (pSource.getEntity() instanceof Player) {
                //AMNetHandler.INSTANCE.sendVelocityAddPacket(source.worldObj, (EntityLivingBase)source, speed * Math.cos(radians), vertSpeed, speed * Math.sin(radians));
            }
            pSource.getEntity().setDeltaMovement(speed * Math.cos(angle), vSpeed, speed * Math.sin(angle));
            pSource.getEntity().hurt(DamageSource.mobAttack(this), 2);
            return false;
        }
        return true;
    }

    private boolean isWithin(float source, float target, float tolerance) {
        return source + tolerance > target && source - tolerance < target;
    }

    @Override
    public LivingEntity getTarget() {
        int id = this.entityData.get(DW_TARGET_ID);
        if (id == -1) {
            return null;
        }
        return (LivingEntity) this.level.getEntity(id);
    }

    public float getRuneRotationY() {
        return this.runeRotationY;
    }

    public float getRuneRotationZ() {
        return this.runeRotationZ;
    }

    public ArcaneGuardianAction getArcaneGuardianAction() {
        return this.arcaneGuardianAction;
    }

    public void setArcaneGuardianAction(final ArcaneGuardianAction action) {
//        if (!this.level.isClientSide()){
//            AMNetHandler.INSTANCE.sendActionUpdateToAllAround(this);
//        }
        this.arcaneGuardianAction = action;
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
            this.arcaneGuardianAction = ArcaneGuardianAction.CASTING;
        } else {
            this.arcaneGuardianAction = ArcaneGuardianAction.IDLE;
        }
    }

    public enum ArcaneGuardianAction {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        private ArcaneGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
