package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

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
import software.bernie.geckolib3.core.manager.AnimationData;

public class NatureGuardian extends AbstractBoss {
    public boolean hasScythe = true;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 3500).add(AMAttributes.MAX_BURNOUT.get(), 3500);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.NATURE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.NATURE_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.NATURE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new SpinGoal<>(this));
        goalSelector.addGoal(2, new StrikeGoal<>(this));
        goalSelector.addGoal(2, new ThrowScytheGoal(this));
    }

    @Override
    public void aiStep() {
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createIdleAnimationController(this, "nature_guardian"));
    }
}
