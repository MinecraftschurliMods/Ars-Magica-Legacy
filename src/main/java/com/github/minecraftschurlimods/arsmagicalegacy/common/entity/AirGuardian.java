package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HurricaneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.WhirlwindGoal;
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
    private AirGuardianAction action;

    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.YELLOW);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 200D).add(Attributes.ARMOR, 10);
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
        if (action == AirGuardianAction.SPINNING) {
            spinRotation = (spinRotation - 40) % 360;
            if (level.isClientSide()) {
                // Particles
            }
        }
        if (getDeltaMovement().y() < 0) {
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() * 0.7999999f, getDeltaMovement().z());
        }
        if (getY() < 136) {
            if (level.isClientSide()) {
                // Particles
            } else if (getY() < 128) {
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
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new HurricaneGoal(this));
        goalSelector.addGoal(1, new WhirlwindGoal(this));
    }

    @Override
    public boolean isPushable() {
        return action != AirGuardianAction.SPINNING;
    }

    public boolean useLeftArm() {
        return useLeftArm;
    }

    public float getOrbitRotation() {
        return orbitRotation;
    }

    public AirGuardianAction getAction() {
        return action;
    }

    public void setAction(final AirGuardianAction action) {
        this.action = action;
        spinRotation = 0;
        ticksInAction = 0;
        if (action == AirGuardianAction.CASTING) {
            useLeftArm = !useLeftArm;
        }
    }

    @Override
    public boolean canCastSpell() {
        return action == AirGuardianAction.IDLE;
    }

    @Override
    public boolean isCastingSpell() {
        return action == AirGuardianAction.CASTING;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            action = AirGuardianAction.CASTING;
        } else if (action == AirGuardianAction.CASTING) {
            action = AirGuardianAction.IDLE;
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
