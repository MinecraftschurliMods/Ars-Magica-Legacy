package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.CloneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpinGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
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
import software.bernie.geckolib3.core.manager.AnimationData;

public class WaterGuardian extends AbstractBoss {
    private static final EntityDataAccessor<Boolean> IS_CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private WaterGuardian master = null;
    private WaterGuardian clone1 = null;
    private WaterGuardian clone2 = null;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
        setPathfindingMalus(BlockPathTypes.WATER, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 80).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 500).add(AMAttributes.MAX_BURNOUT.get(), 500);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return null;
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

    @Override
    public void aiStep() {
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

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new CloneGoal(this));
        goalSelector.addGoal(1, new SpinGoal<>(this));
        Registry<PrefabSpell> prefabSpells = level.registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY);
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_bolt")).spell(), 40));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_water_bolt")).spell(), 40));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "chaos_water_bolt")).spell(), 40));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createBaseAnimationController("water_guardian"));
        data.addAnimationController(createActionAnimationController("water_guardian", "idle", Action.IDLE));
        data.addAnimationController(createActionAnimationController("water_guardian", "cast", Action.CAST));
        data.addAnimationController(createActionAnimationController("water_guardian", "cast", Action.LONG_CAST));
        data.addAnimationController(createActionAnimationController("water_guardian", "spin", Action.SPIN));
    }

    @Override
    public boolean canCastSpell() {
        return super.canCastSpell() && !isClone();
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
}
