package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

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
import software.bernie.geckolib3.core.manager.AnimationData;

public class EarthGuardian extends AbstractBoss {
    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 1000).add(AMAttributes.MAX_BURNOUT.get(), 1000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new SmashGoal<>(this));
        goalSelector.addGoal(2, new StrikeGoal<>(this));
        goalSelector.addGoal(2, new ThrowRockGoal(this));
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createIdleAnimationController(this, "earth_guardian"));
    }
}
