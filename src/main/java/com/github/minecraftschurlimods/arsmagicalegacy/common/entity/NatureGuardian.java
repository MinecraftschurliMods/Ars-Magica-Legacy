package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
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
        if (pSource.isFire() || pSource == DamageSource.FREEZE) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.DROWN) {
            heal(pAmount);
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new StrikeGoal<>(this, NatureGuardianAction.STRIKE, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new SpinGoal<>(this, NatureGuardianAction.SPINNING, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new ThrowScytheGoal(this));
    }

    @Override
    public Action[] getActions() {
        return NatureGuardianAction.values();
    }

    public enum NatureGuardianAction implements Action {
        IDLE(-1, IDLE_ID),
        CASTING(-1, CASTING_ID),
        STRIKE(20, ACTION_1_ID),
        SPINNING(40, ACTION_2_ID),
        THROWING(20, ACTION_3_ID);

        private final int maxActionTime;
        private final byte animationId;

        NatureGuardianAction(int maxActionTime, byte animationId) {
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
