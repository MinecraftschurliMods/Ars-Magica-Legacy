package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HurricanGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpawnWhirlwindGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AirGuardian extends AbstractBoss {
    private boolean useLeftArm = false;
    private float orbitRotation;
    private float spinRotation = 0;
    private AirGuardianAction airGuardianAction;

    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 220D).add(Attributes.ARMOR, 14);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public void aiStep() {
        orbitRotation += 2f;
        orbitRotation %= 360;
        if (airGuardianAction == AirGuardianAction.SPINNING) {
            spinRotation = (spinRotation - 40) % 360;
            if (level.isClientSide()) {
                // Particles
            }
        }
        if (getDeltaMovement().y() < 0) {
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() * 0.7999999f, getDeltaMovement().z());
        }
        if (getY() < 150) {
            if (level.isClientSide()) {
                // Particles
            } else if (getY() < 145) {
                removeAfterChangingDimensions();
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic()) {
            pAmount /= 2;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(2, new HurricanGoal(this, 0.5f));
        goalSelector.addGoal(1, new SpawnWhirlwindGoal(this, 0.5f));
    }

    @Override
    public boolean isPushable() {
        return airGuardianAction != AirGuardianAction.SPINNING;
    }

    public boolean useLeftArm() {
        return useLeftArm;
    }

    public float getOrbitRotation() {
        return orbitRotation;
    }

    public AirGuardianAction getAirGuardianAction() {
        return airGuardianAction;
    }

    public void setAirGuardianAction(final AirGuardianAction action) {
        airGuardianAction = action;
        spinRotation = 0;
        ticksInAction = 0;
        if (action == AirGuardianAction.CASTING) {
            useLeftArm = !useLeftArm;
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
            airGuardianAction = AirGuardianAction.CASTING;
        } else if (airGuardianAction == AirGuardianAction.CASTING) {
            airGuardianAction = AirGuardianAction.IDLE;
        }
    }

    public enum AirGuardianAction {
        IDLE(-1),
        CASTING(-1),
        SPINNING(160);

        private final int maxActionTime;

        AirGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
