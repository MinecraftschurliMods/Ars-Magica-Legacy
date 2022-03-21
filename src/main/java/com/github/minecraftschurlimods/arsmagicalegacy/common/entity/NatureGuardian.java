package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.NatureSpinAttackGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.NatureStrikeAttackGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.NatureThrowSickleGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ShieldBashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class NatureGuardian extends AbstractBoss {
    private float tendrilRotation;
    private boolean hasSickle = true;
    private float spinRotation = 0;
    private NatureGuardianAction action;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20);
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
    protected SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (level.isClientSide()) {
            updateMovementAngles();
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
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
        goalSelector.addGoal(5, new ShieldBashGoal(this));
        goalSelector.addGoal(3, new NatureSpinAttackGoal(this));
        goalSelector.addGoal(2, new NatureStrikeAttackGoal(this));
        goalSelector.addGoal(3, new NatureThrowSickleGoal(this));
    }

    private void updateMovementAngles() {
        tendrilRotation += 0.2f;
        tendrilRotation %= 360;
        if (getAction() == NatureGuardianAction.SPINNING) {
            spinRotation = (spinRotation - 40) % 360;
        }
    }

    public NatureGuardianAction getAction() {
        return action;
    }

    public void setAction(final NatureGuardianAction action) {
        spinRotation = 0;
        this.action = action;
        ticksInAction = 0;
    }

    public boolean isNatureGuardianActionValid(NatureGuardianAction action) {
        if (action == NatureGuardianAction.STRIKE || action == NatureGuardianAction.SPINNING || action == NatureGuardianAction.THROWING_SICKLE) {
            return hasSickle;
        }
        return true;
    }

    @Override
    public boolean canCastSpell() {
        return action == NatureGuardianAction.IDLE;
    }

    @Override
    public boolean isCastingSpell() {
        return action == NatureGuardianAction.CASTING;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            action = NatureGuardianAction.CASTING;
        } else if (action == NatureGuardianAction.CASTING) {
            action = NatureGuardianAction.IDLE;
        }
    }

    public enum NatureGuardianAction {
        IDLE(-1),
        CASTING(-1),
        SPINNING(160),
        STRIKE(15),
        THROWING_SICKLE(15),
        SHIELD_BASH(15);

        private final int maxActionTime;

        NatureGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
