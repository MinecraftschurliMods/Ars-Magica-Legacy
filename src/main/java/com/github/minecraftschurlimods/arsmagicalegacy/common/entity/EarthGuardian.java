package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowRockGoal;
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

public class EarthGuardian extends AbstractBoss {
    public boolean shouldRenderRock;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.value(), 1000).add(AMAttributes.MAX_BURNOUT.value(), 1000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.value();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.value();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.value();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.value();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new SmashGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowRockGoal(this));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_FREEZING) || pSource.is(DamageTypeTags.IS_DROWNING)) {
            pAmount *= 2f;
        } else if (pSource.is(DamageTypeTags.IS_FIRE) || pSource.is(DamageTypeTags.IS_LIGHTNING)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                createBaseAnimationController("earth_guardian"),
                createActionAnimationController("earth_guardian", "idle", Action.IDLE),
                createActionAnimationController("earth_guardian", "smash", Action.SMASH),
                createActionAnimationController("earth_guardian", "strike", Action.STRIKE),
                createActionAnimationController("earth_guardian", "throw", Action.THROW)
        );
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == -8) {
            shouldRenderRock = true;
        } else if (pId == -9) {
            shouldRenderRock = false;
        }
        super.handleEntityEvent(pId);
    }
}
