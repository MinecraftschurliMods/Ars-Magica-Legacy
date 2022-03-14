package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeAttackGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.phys.Vec3;

public class EarthGuardian extends AbstractBoss {
    public boolean leftArm = false;
    private float rodRotation = 0;
    private EarthGuardianAction earthGuardianAction;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 140).add(Attributes.ARMOR, 23);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (ticksInAction > 40 && !level.isClientSide()) {
            setEarthGuardianAction(EarthGuardianAction.IDLE);
        } else if (level.isClientSide()) {
            updateRotations();
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount /= 4f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(2, new SmashGoal(this, 0.5f, AMSpellParts.PHYSICAL_DAMAGE.get()));
        goalSelector.addGoal(2, new StrikeAttackGoal(this, 0.5f, 4.0f, AMSpellParts.PHYSICAL_DAMAGE.get()));
        // ThrowRock
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

    public boolean shouldRenderRock() {
        return getEarthGuardianAction() == EarthGuardianAction.THROWING_ROCK && ticksInAction > 5 && ticksInAction < 27;
    }

    public float getRodRotations() {
        return rodRotation;
    }

    private void updateRotations() {
        rodRotation += 0.02f;
        rodRotation %= 360;
    }

    public EarthGuardianAction getEarthGuardianAction() {
        return earthGuardianAction;
    }

    public void setEarthGuardianAction(final EarthGuardianAction action) {
        earthGuardianAction = action;
        ticksInAction = 0;
        if (getEarthGuardianAction() != action && action == EarthGuardianAction.STRIKE && level.isClientSide()) {
            leftArm = !leftArm;
        }
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
            earthGuardianAction = EarthGuardianAction.CASTING;
        } else if (earthGuardianAction == EarthGuardianAction.CASTING) {
            earthGuardianAction = EarthGuardianAction.IDLE;
        }
    }

    public enum EarthGuardianAction {
        IDLE(-1),
        CASTING(-1),
        STRIKE(15),
        THROWING_ROCK(30),
        SMASH(20); // maybe not needed

        private final int maxActionTime;

        EarthGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
