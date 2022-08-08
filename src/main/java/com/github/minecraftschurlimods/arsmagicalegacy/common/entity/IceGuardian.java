package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.LaunchArmGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class IceGuardian extends AbstractBoss {
    private boolean hasRightArm = true;
    private boolean hasLeftArm = true;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 300).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 3000).add(AMAttributes.MAX_BURNOUT.get(), 3000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public Action getIdleAction() {
        return IceGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return IceGuardianAction.CASTING;
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void aiStep() {
        if (this.tickCount % 100 == 0) {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, -3, 0), e -> !(e instanceof AbstractBoss))) {
                e.hurt(DamageSource.FREEZE, 5);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isFire()) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new StrikeGoal<>(this, IceGuardianAction.STRIKE, DamageSource.FREEZE));
        goalSelector.addGoal(2, new SmashGoal<>(this, IceGuardianAction.SMASH, DamageSource.FREEZE));
        goalSelector.addGoal(2, new LaunchArmGoal(this));
    }

    public void returnArm() {
        if (!hasLeftArm) {
            hasLeftArm = true;
        } else if (!hasRightArm) {
            hasRightArm = true;
        }
    }

    public void launchArm() {
        if (hasLeftArm) {
            hasLeftArm = false;
        } else if (hasRightArm) {
            hasRightArm = false;
        }
    }

    public boolean hasLeftArm() {
        return hasLeftArm;
    }

    public boolean hasRightArm() {
        return hasRightArm;
    }

    @Override
    public Action[] getActions() {
        return IceGuardianAction.values();
    }

    public enum IceGuardianAction implements Action {
        IDLE(-1, IDLE_ID),
        CASTING(-1, CASTING_ID),
        STRIKE(20, ACTION_1_ID),
        SMASH(20, ACTION_2_ID),
        LAUNCHING(30, ACTION_3_ID);

        private final int maxActionTime;
        private final byte animationId;

        IceGuardianAction(int maxActionTime, byte animationId) {
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
