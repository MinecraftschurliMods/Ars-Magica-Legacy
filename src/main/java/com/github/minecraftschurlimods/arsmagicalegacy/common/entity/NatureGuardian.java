package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SpinGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowScytheGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class NatureGuardian extends AbstractBoss {
    private boolean hasScythe = true;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.value(), 3500).add(AMAttributes.MAX_BURNOUT.value(), 3500);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.NATURE_GUARDIAN_AMBIENT.value();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.NATURE_GUARDIAN_HURT.value();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.NATURE_GUARDIAN_DEATH.value();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.value();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new SpinGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowScytheGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FIRE) || pSource.is(DamageTypeTags.IS_FREEZING)) {
            pAmount *= 2f;
        } else if (pSource.is(DamageTypeTags.IS_DROWNING)) {
            heal(pAmount);
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                createBaseAnimationController("nature_guardian"),
                createActionAnimationController("nature_guardian", "idle", Action.IDLE),
                createActionAnimationController("nature_guardian", "spin", Action.SPIN),
                createActionAnimationController("nature_guardian", "strike", Action.STRIKE),
                createActionAnimationController("nature_guardian", "throw", Action.THROW)
        );
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == -8) {
            hasScythe = false;
        } else if (pId == -9) {
            hasScythe = true;
        }
        super.handleEntityEvent(pId);
    }

    public boolean hasScythe() {
        return hasScythe;
    }

    public void setHasScythe(boolean hasScythe) {
        this.hasScythe = hasScythe;
        level().broadcastEntityEvent(this, (byte) (hasScythe ? -9 : -8));
    }
}
