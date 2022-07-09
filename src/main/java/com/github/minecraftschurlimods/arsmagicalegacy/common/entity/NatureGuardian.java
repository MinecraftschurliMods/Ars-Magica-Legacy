package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ShieldBashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpinGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowScytheGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class NatureGuardian extends AbstractBoss {
    public boolean hasScythe = true;
    private float spinRotation = 0;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 3500).add(AMAttributes.MAX_BURNOUT.get(), 3500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.NATURE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.NATURE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.NATURE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.get();
    }

    @Override
    public Action getIdleAction() {
        return NatureGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return NatureGuardianAction.CASTING;
    }

    public float getSpinRotation() {
        return spinRotation;
    }

    @Override
    public void aiStep() {
        if (level.isClientSide() && getAction() == NatureGuardianAction.SPINNING) {
            spinRotation = (spinRotation + 30) % 360;
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isFire()) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            pAmount *= 1.5f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(3, new SpinGoal<>(this, NatureGuardianAction.SPINNING, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new StrikeGoal<>(this, NatureGuardianAction.STRIKE, DamageSource.mobAttack(this)));
        goalSelector.addGoal(3, new ThrowScytheGoal(this));
        goalSelector.addGoal(5, new ShieldBashGoal(this));
    }

    public enum NatureGuardianAction implements Action {
        IDLE(-1),
        CASTING(-1),
        SPINNING(160),
        STRIKE(20),
        SHIELD_BASH(15),
        THROWING(15);

        private final int maxActionTime;

        NatureGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
