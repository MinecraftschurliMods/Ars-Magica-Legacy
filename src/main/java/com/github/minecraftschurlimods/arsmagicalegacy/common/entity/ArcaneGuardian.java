package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ArcaneGuardian extends AbstractBoss {
    private float runeRotationY = 0;
    private float runeRotationZ = 0;
    private ArcaneGuardianAction arcaneGuardianAction;

    public ArcaneGuardian(EntityType<? extends ArcaneGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 115D).add(Attributes.ARMOR, 9);
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
        if (getDeltaMovement().y() < 0) {
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() * 0.7999999f, getDeltaMovement().z());
        }
        updateRotation();
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FALL) return false;
        if (pSource.getEntity() == null || checkRuneRetaliation(pSource)) return super.hurt(pSource, pAmount);
        return super.hurt(pSource, pAmount * 0.8F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal_self")).spell(), 16, 40));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "blink")).spell(), 16, 4));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_bolt")).spell(), 12, 18));
    }

    private void updateRotation() {
        runeRotationZ = 0;
        float targetRuneRotationY = 0;
        float runeRotationSpeed = 0.3f;
        if (getTarget() != null) {
            double deltaX = getTarget().getX();
            double deltaZ = getTarget().getZ();
            double angle = Math.atan2(deltaZ, deltaX);
            angle -= Math.toRadians(Mth.wrapDegrees(getYRot() + 90) + 180);
            targetRuneRotationY = (float) angle;
            runeRotationSpeed = 0.085f;
        }
        if (targetRuneRotationY > runeRotationY) {
            runeRotationY += runeRotationSpeed;
        } else if (targetRuneRotationY < runeRotationY) {
            runeRotationY -= runeRotationSpeed;
        }
        if (isWithin(runeRotationY, targetRuneRotationY, 0.25f)) {
            runeRotationY = targetRuneRotationY;
        }
    }

    private boolean checkRuneRetaliation(DamageSource pSource) {
        if (pSource.getEntity() instanceof ArcaneGuardian) return true;
        double deltaX = pSource.getEntity().getX() - getX();
        double deltaZ = pSource.getEntity().getZ() - getZ();
        double angle = Math.atan2(deltaZ, deltaX);
        angle -= Math.toRadians(Mth.wrapDegrees(getYRot() + 90) + 180);
        if (distanceToSqr(pSource.getEntity()) < 9) {
            double speed = 2.5;
            double vSpeed = 0.325;
            deltaZ = pSource.getEntity().getZ() - getZ();
            deltaX = pSource.getEntity().getX() - getX();
            angle = Math.atan2(deltaZ, deltaX);
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

    public float getRuneRotationY() {
        return runeRotationY;
    }

    public float getRuneRotationZ() {
        return runeRotationZ;
    }

    public ArcaneGuardianAction getArcaneGuardianAction() {
        return arcaneGuardianAction;
    }

    public void setArcaneGuardianAction(final ArcaneGuardianAction action) {
        arcaneGuardianAction = action;
        ticksInAction = 0;
    }

    @Override
    public boolean canCastSpell() {
        return true;
    }

    @Override
    public boolean isCastingSpell() {
        return false;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            arcaneGuardianAction = ArcaneGuardianAction.CASTING;
        } else if (arcaneGuardianAction == ArcaneGuardianAction.CASTING) {
            arcaneGuardianAction = ArcaneGuardianAction.IDLE;
        }
    }

    public enum ArcaneGuardianAction {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        ArcaneGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
