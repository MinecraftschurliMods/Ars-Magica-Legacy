package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.CloneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.WaterSpinAttackGoal;
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
import net.minecraft.sounds.SoundSource;
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
import org.jetbrains.annotations.NotNull;

public class WaterGuardian extends AbstractBoss {
    private static final EntityDataAccessor<Boolean> IS_CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private WaterGuardian master = null;
    private WaterGuardian clone1 = null;
    private WaterGuardian clone2 = null;
    private float spinRotation = 0;
    private WaterGuardianAction action;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        setPathfindingMalus(BlockPathTypes.WATER, 0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 80).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 500).add(AMAttributes.MAX_BURNOUT.get(), 500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
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
    public void aiStep() {
        if (!level.isClientSide() && isClone() && (master == null || tickCount > 400)) {
            remove(RemovalReason.KILLED);
        } else if (level.isClientSide() && (getAction() == WaterGuardianAction.SPINNING || getAction() == WaterGuardianAction.CASTING)) {
            spinRotation = (spinRotation + 30) % 360;
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof WaterGuardian) return false;
        if (isClone() && master != null) {
            master.clearClones();
        } else if (hasClones()) {
            clearClones();
        } else if (!isClone() && random.nextInt(10) < 5) return false;
        if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            pAmount = 0;
        }
        SoundEvent sound = getHurtSound(pSource);
        if (sound != null) {
            level.playSound(null, this, sound, SoundSource.HOSTILE, 1f, 0.4f + random.nextFloat() * 0.6f);
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(4, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_bolt")).spell(), 12, 18));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "chaos_water_bolt")).spell(), 10, 10));
        goalSelector.addGoal(3, new CloneGoal(this));
        goalSelector.addGoal(3, new WaterSpinAttackGoal(this));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("isClone", isClone());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        entityData.set(IS_CLONE, pCompound.getBoolean("isClone"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(IS_CLONE, false);
    }

    @Override
    public int getMaxFallDistance() {
        return getTarget() == null ? 3 : 3 + (int) (getHealth() - 1F);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getFluidState(pPos).is(FluidTags.WATER) ? 10F + pLevel.getBrightness(pPos) - 0.5F : super.getWalkTargetValue(pPos, pLevel);
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (isEffectiveAi() && isInWater()) {
            moveRelative(0.1F, pTravelVector);
            move(MoverType.SELF, getDeltaMovement());
            setDeltaMovement(getDeltaMovement().scale(0.9D));
        } else {
            super.travel(pTravelVector);
        }
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
            clone1.remove(RemovalReason.KILLED);
            clone1 = null;
        }
        if (clone2 != null) {
            clone2.remove(RemovalReason.KILLED);
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

    public float getSpinRotation() {
        return spinRotation;
    }

    public WaterGuardianAction getAction() {
        return action;
    }

    public void setAction(final WaterGuardianAction action) {
        this.action = action;
        ticksInAction = 0;
    }

    @Override
    public boolean canCastSpell() {
        return action == WaterGuardianAction.IDLE;
    }

    @Override
    public boolean isCastingSpell() {
        return action == WaterGuardianAction.CASTING;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            action = WaterGuardianAction.CASTING;
        } else if (action == WaterGuardianAction.CASTING) {
            action = WaterGuardianAction.IDLE;
        }
    }

    public enum WaterGuardianAction {
        IDLE(-1),
        CASTING(-1),
        CLONE(30),
        SPINNING(160);

        private final int maxActionTime;

        WaterGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
