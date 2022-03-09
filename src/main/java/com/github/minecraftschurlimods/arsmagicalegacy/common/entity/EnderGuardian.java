package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.mojang.math.Vector3f;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import org.jetbrains.annotations.Nullable;

// TODO hurt() addDeferredTargetSet()
// TODO registerGoal()

public class EnderGuardian extends AbstractBoss {
    private int wingFlapTime = 0;
    private int ticksSinceLastAttack = 0;
    private int hitCount = 0;
    private Vector3f spawn;
    private EnderGuardianAction enderGuardianAction;

    private static final EntityDataAccessor<Integer> ATTACK_TARGET = SynchedEntityData.defineId(EnderGuardian.class, EntityDataSerializers.INT);

    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
        this.entityData.define(ATTACK_TARGET, -1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 490D).add(Attributes.ARMOR, 16);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ENDER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ENDER_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ENDER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.spawn == null) {
            this.spawn = new Vector3f(this.position());
        }
        this.wingFlapTime++;
        this.ticksSinceLastAttack++;

        if (this.getDeltaMovement().y() < 0) {
            this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() * 0.7999999f, this.getDeltaMovement().z());
        }

        switch (this.getEnderGuardianAction()) {
            case LONG_CASTING:
                if (this.ticksInAction == 32) {
                    this.level.playSound(null, this, AMSounds.ENDER_GUARDIAN_ROAR.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
                }
                break;
            case CHARGE:
                if (this.getNoActionTime() == 0) {
                    this.setDeltaMovement(this.getDeltaMovement().x(), this.getDeltaMovement().y() + 1.5f, this.getDeltaMovement().z());
                }
                break;
            default:
                break;
        }

        if (this.shouldFlapWings() && this.wingFlapTime % (50 * this.getWingFlapSpeed()) == 0) {
            this.level.playSound(null, this, AMSounds.ENDER_GUARDIAN_FLAP.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic()) {
            pAmount *= 2f;
        }

        if (pSource.getEntity() instanceof EnderMan) {
            pSource.getEntity().hurt(DamageSource.OUT_OF_WORLD, 5000);
            this.heal(10);
            return false;
        }

        if (pSource == DamageSource.OUT_OF_WORLD) {
            if (this.spawn != null) {
                this.setPos(this.spawn.x(), this.spawn.y(), this.spawn.z());
                this.setEnderGuardianAction(EnderGuardianAction.IDLE);
                if (!this.level.isClientSide()) {
                    //ArsMagica2.proxy.addDeferredTargetSet(this, null);
                }
            } else {
                this.removeAfterChangingDimensions();
            }
            return false;
        }

        this.ticksSinceLastAttack = 0;

        if (!level.isClientSide() && pSource.getEntity() != null && pSource.getEntity() instanceof Player) {
            if (pSource == this.getLastDamageSource()) {
                this.hitCount++;
                if (this.hitCount > 5) {
                    this.heal(pAmount / 4);
                }
                return false;
            } else {
                this.hitCount = 1;
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Shadowstep
        // Enderwave
        // OtherworldyRoar
        // Protect
        // EnderRush
        // Endertorrent
        // Enderbolt
    }

    @Override
    public LivingEntity getTarget() {
        if (!this.level.isClientSide()) {
            return super.getTarget();
        } else {
            return (LivingEntity) this.level.getEntity(this.entityData.get(ATTACK_TARGET));
        }
    }

    @Override
    public void setTarget(LivingEntity pLivingEntity) {
        super.setTarget(pLivingEntity);
        if (!this.level.isClientSide()) {
            if (pLivingEntity != null) {
                this.entityData.set(ATTACK_TARGET, pLivingEntity.getId());
            } else {
                this.entityData.set(ATTACK_TARGET, -1);
            }
        }
    }

    @Override
    public float getEyeHeight(Pose pPose) {
        return 2.5F;
    }

    public int getTicksSinceLastAttack() {
        return this.ticksSinceLastAttack;
    }

    public int getWingFlapTime() {
        return this. wingFlapTime;
    }

    public float getWingFlapSpeed() {
        switch (this.getEnderGuardianAction()) {
            case CASTING:
                return 0.5f;
            case STRIKE:
                return 0.4f;
            case CHARGE:
                if (this.getTicksInAction() < 15) {
                    return 0.25f;
                }
                return 0.75f;
            default:
                return 0.25f;
        }
    }

    public boolean shouldFlapWings() {
        return this.getEnderGuardianAction() != EnderGuardianAction.LONG_CASTING && this.getEnderGuardianAction() != EnderGuardianAction.SHIELD_BASH;
    }

    @Override
    public boolean hasEffect(final MobEffect pPotion) {
        if (pPotion == AMMobEffects.REFLECT.get() && (this.getEnderGuardianAction() == EnderGuardianAction.SHIELD_BASH || this.getEnderGuardianAction() == EnderGuardianAction.LONG_CASTING)) {
            return true;
        }
        if (pPotion == AMMobEffects.MAGIC_SHIELD.get() && (this.getEnderGuardianAction() == EnderGuardianAction.SHIELD_BASH || this.getEnderGuardianAction() == EnderGuardianAction.LONG_CASTING)) {
            return true;
        }
        return super.hasEffect(pPotion);
    }

//    public void setAnimID(int id) {
//        this.setEnderGuardianAction(EnderGuardianAction.values()[id]);
//        this.noActionTime = 0;  // is ticksInCurrentAction --> noActionTime
//    }
//
//    public void setAnimTick(int tick) {
//        this.noActionTime = tick;  // is ticksInCurrentAction --> noActionTime
//    }
//
//    public int getAnimID() {
//        return this.getEnderGuardianAction().ordinal();
//    }
//
//    public int getAnimTick() {
//        return this.noActionTime;  // is ticksInCurrentAction --> noActionTime
//    }

    public EnderGuardianAction getEnderGuardianAction() {
        return this.enderGuardianAction;
    }

    public void setEnderGuardianAction(final EnderGuardianAction action) {
        this.enderGuardianAction = action;
        if (action == EnderGuardianAction.LONG_CASTING) {
            this.wingFlapTime = 0;
        }
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
            this.enderGuardianAction = EnderGuardianAction.CASTING;
        } else {
            this.enderGuardianAction = EnderGuardianAction.IDLE;
        }
    }

    public enum EnderGuardianAction {
        IDLE(-1),
        STRIKE(15),
        SHIELD_BASH(15),
        CASTING(-1),
        LONG_CASTING(-1),
        CHARGE(-1);

        private final int maxActionTime;

        private EnderGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
