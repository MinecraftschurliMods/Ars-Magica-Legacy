package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.CloneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpinGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class WaterGuardian extends AbstractBoss {
    private static final EntityDataAccessor<Boolean> CLONE = SynchedEntityData.defineId(WaterGuardian.class, EntityDataSerializers.BOOLEAN);
    private static final String CLONE_KEY = "clone";
    private static final String MASTER_KEY = "master";
    private static final String CLONE_1_KEY = "clone_1";
    private static final String CLONE_2_KEY = "clone_2";
    @Nullable
    private WaterGuardian master = null;
    @Nullable
    private WaterGuardian clone1 = null;
    @Nullable
    private WaterGuardian clone2 = null;

    public WaterGuardian(EntityType<? extends WaterGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE, AMTags.DamageTypes.WATER_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.WATER_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.WATER_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 80)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 500)
            .add(AMAttributes.MAX_BURNOUT, 500);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(CLONE, false);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> {
            entityData.set(CLONE, child.getBooleanOr(CLONE_KEY, false));
            master = readWaterGuardian(child, MASTER_KEY);
            clone1 = readWaterGuardian(child, CLONE_1_KEY);
            clone2 = readWaterGuardian(child, CLONE_2_KEY);
        });
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        ValueOutput child = output.child(ArsMagicaApi.MOD_ID);
        child.putBoolean(CLONE_KEY, entityData.get(CLONE));
        if (master != null) {
            child.putString(MASTER_KEY, master.getStringUUID());
        }
        if (clone1 != null) {
            child.putString(CLONE_1_KEY, clone1.getStringUUID());
        }
        if (clone2 != null) {
            child.putString(CLONE_2_KEY, clone2.getStringUUID());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.WATER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.WATER_GUARDIAN_DEATH.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (isClone() && master != null) {
            master.clearClones();
            return false;
        } else if (hasClones()) {
            clearClones();
            return false;
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        Registry<Spell> registry = registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
        goalSelector.addGoal(1, new CloneGoal(this));
        goalSelector.addGoal(1, new SpinGoal<>(this));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("water_bolt")), 40));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("strong_water_bolt")), 40));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("chaos_water_bolt")), 40));
    }

    @Override
    public void aiStep() {
        if (!level().isClientSide() && isClone() && (master == null || tickCount > 400)) {
            remove(RemovalReason.KILLED);
        }
        super.aiStep();
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
            clone1.remove(RemovalReason.KILLED);
            clone1 = null;
        }
        if (clone2 != null) {
            clone2.remove(RemovalReason.KILLED);
            clone2 = null;
        }
    }

    public boolean isClone() {
        return entityData.get(CLONE);
    }

    public void setMaster(WaterGuardian master) {
        entityData.set(CLONE, true);
        this.master = master;
    }

    @Nullable
    private WaterGuardian readWaterGuardian(ValueInput input, String key) {
        return input.getString(key)
            .map(UUID::fromString)
            .map(level()::getEntity)
            .filter(WaterGuardian.class::isInstance)
            .map(WaterGuardian.class::cast)
            .orElse(null);
    }
}
