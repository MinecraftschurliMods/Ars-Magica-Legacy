package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HurricaneGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.WhirlwindGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class AirGuardian extends AbstractBoss {
    public AirGuardian(EntityType<? extends AirGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.YELLOW);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 200D).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 1500).add(AMAttributes.MAX_BURNOUT.get(), 1500);
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
    public SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public Action getIdleAction() {
        return AirGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return AirGuardianAction.CASTING;
    }

    @Override
    public void aiStep() {
        if (level.isClientSide()) {
            // Particles
        }
        if (getY() > 150) {
            setDeltaMovement(getDeltaMovement().x(), Math.min(getDeltaMovement().y(), 0), getDeltaMovement().z());
        } else if (getY() < 128) {
            remove(RemovalReason.KILLED);
        } else if (getY() < 136) {
            setDeltaMovement(getDeltaMovement().x(), Math.max(getDeltaMovement().y(), 0.1), getDeltaMovement().z());
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic()) {
            pAmount /= 2;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= 2;
        } else if (pSource.isProjectile()) {
            pAmount = 0;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new HurricaneGoal(this));
        goalSelector.addGoal(2, new WhirlwindGoal(this));
    }

    public enum AirGuardianAction implements Action {
        IDLE(-1),
        CASTING(-1),
        SPINNING(40);

        private final int maxActionTime;

        AirGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
