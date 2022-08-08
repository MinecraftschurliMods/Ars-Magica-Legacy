package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowRockGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EarthGuardian extends AbstractBoss {
    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 1000).add(AMAttributes.MAX_BURNOUT.get(), 1000);
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
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    public Action getIdleAction() {
        return EarthGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return EarthGuardianAction.CASTING;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new StrikeGoal<>(this, EarthGuardianAction.STRIKE, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new SmashGoal<>(this, EarthGuardianAction.SMASH, DamageSource.mobAttack(this)));
        goalSelector.addGoal(2, new ThrowRockGoal(this));
    }

    @Override
    public Action[] getActions() {
        return EarthGuardianAction.values();
    }

    public enum EarthGuardianAction implements Action {
        IDLE(-1, IDLE_ID),
        CASTING(-1, CASTING_ID),
        STRIKE(20, ACTION_1_ID),
        SMASH(20, ACTION_2_ID),
        THROWING(20, ACTION_3_ID);

        private final int maxActionTime;
        private final byte animationId;

        EarthGuardianAction(int maxActionTime, byte animationId) {
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
