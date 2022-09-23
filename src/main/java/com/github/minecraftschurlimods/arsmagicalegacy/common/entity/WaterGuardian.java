package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.CloneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpinGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class WaterGuardian extends AbstractBoss implements IAnimatable {
    private static final EntityDataAccessor<Boolean> IS_CLONE     = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private final        AnimationFactory            factory      = new AnimationFactory(this);
    private              float                       spinRotation = 0;
    private              WaterGuardian               master       = null;
    private              WaterGuardian               clone1       = null;
    private              WaterGuardian               clone2       = null;
    private AnimationController<WaterGuardian> idleController;
    private AnimationController<WaterGuardian> spinController;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
        noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 80).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 500).add(AMAttributes.MAX_BURNOUT.get(), 500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public Action getIdleAction() {
        return WaterGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return WaterGuardianAction.CASTING;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("IsClone", isClone());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(IS_CLONE, pCompound.getBoolean("IsClone"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_CLONE, false);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10 : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(getSpeed(), pTravelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
    }

    public float getSpinRotation() {
        return spinRotation;
    }

    @Override
    public void aiStep() {
        if (action == WaterGuardianAction.SPINNING) {
            spinRotation += 30;
        }
        if (!level.isClientSide() && isClone() && (master == null || tickCount > 400)) {
            remove(RemovalReason.KILLED);
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (isClone() && master != null) {
            master.clearClones();
            return false;
        } else if (hasClones()) {
            clearClones();
            return false;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.DROWN) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        //        goalSelector.addGoal(2, new CloneGoal(this));
        goalSelector.addGoal(2, new SpinGoal<>(this, WaterGuardianAction.SPINNING, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_bolt")).spell(), 10));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "chaos_water_bolt")).spell(), 12));
    }

    public void setClones(WaterGuardian clone1, WaterGuardian clone2) {
        this.clone1 = clone1;
        this.clone2 = clone2;
    }

    public boolean hasClones() {
        return clone1 != null || clone2 != null;
    }

    public void clearClones() {
        if (clone1 != null) {
            clone1.kill();
            clone1 = null;
        }
        if (clone2 != null) {
            clone2.kill();
            clone2 = null;
        }
    }

    public boolean isClone() {
        return entityData.get(IS_CLONE);
    }

    public void setMaster(WaterGuardian master) {
        entityData.set(IS_CLONE, true);
        this.master = master;
    }

    @Override
    public Action[] getActions() {
        return WaterGuardianAction.values();
    }

    @Override
    public void registerControllers(AnimationData data) {
        idleController = new AnimationController<>(this, "idleController", 5, e -> {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("animation.water_guardian.idle"));
            return PlayState.CONTINUE;
        });
        spinController = new AnimationController<>(this, "spinController", 5, e -> {
            if (action == WaterGuardianAction.CASTING || action == WaterGuardianAction.SPINNING) {
                e.getController().setAnimation(new AnimationBuilder().addAnimation("animation.water_guardian.spin"));
                return PlayState.CONTINUE;
            } else {
                e.getController().clearAnimationCache();
                return PlayState.STOP;
            }
        });
        data.addAnimationController(idleController);
        data.addAnimationController(spinController);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public enum WaterGuardianAction implements Action {
        IDLE(-1, IDLE_ID), CASTING(-1, CASTING_ID), CLONE(30, ACTION_1_ID), SPINNING(40, ACTION_2_ID);

        private final int  maxActionTime;
        private final byte animationId;

        WaterGuardianAction(int maxActionTime, byte animationId) {
            this.maxActionTime = maxActionTime;
            this.animationId = animationId;
        }

        @Override
        public int getMaxActionTime() {
            return maxActionTime;
        }

        @Override
        public byte getAnimationId() {
            return animationId;
        }
    }
}
