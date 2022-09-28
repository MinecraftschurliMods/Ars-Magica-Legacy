package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowArmGoal;
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
import software.bernie.geckolib3.core.manager.AnimationData;

public class IceGuardian extends AbstractBoss {
    private int arms = 2;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 300).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 3000).add(AMAttributes.MAX_BURNOUT.get(), 3000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return null;
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new SmashGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowArmGoal(this));
    }

    @Override
    public void aiStep() {
        if (this.tickCount % 100 == 0) {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, -3, 0), e -> !(e instanceof AbstractBoss))) {
                e.hurt(DamageSource.FREEZE, 4);
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
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createBaseAnimationController("ice_guardian"));
        data.addAnimationController(createActionAnimationController("ice_guardian", "idle", Action.IDLE));
        data.addAnimationController(createActionAnimationController("ice_guardian", "smash", Action.SMASH));
        data.addAnimationController(createActionAnimationController("ice_guardian", "strike", Action.STRIKE));
        data.addAnimationController(createActionAnimationController("ice_guardian", "throw", Action.THROW));
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId <= -8 && pId >= -10) {
            arms = pId + 8;
        }
        super.handleEntityEvent(pId);
    }

    public void launchArm() {
        arms--;
        level.broadcastEntityEvent(this, (byte) (arms - 8));
    }

    public void returnArm() {
        arms++;
        level.broadcastEntityEvent(this, (byte) (arms - 8));
    }

    public boolean canLaunchArm() {
        return arms > 0;
    }

    public int getArmCount() {
        return arms;
    }
}
