package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EarthSmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EarthStrikeAttackGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.IceSmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.IceStrikeAttackGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.LaunchArmGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.List;

public class IceGuardian extends AbstractBoss {
    private boolean hasRightArm = true;
    private boolean hasLeftArm = true;
    private float orbitRotation;
    private IceGuardianAction iceGuardianAction;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 290D).add(Attributes.ARMOR, 23);
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
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    public void aiStep() {
        if (this.level.isClientSide()) {
            this.updateRotations();
        } else {
            if (this.tickCount % 100 == 0) {
                List<LivingEntity> entities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, -3, 0));
                for (LivingEntity e : entities) {
                    if (e != this) {
                        e.hurt(DamageSource.ON_FIRE, 5);
                    }
                }
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount = 0;
        } else if (pSource.isFire() || pSource == DamageSource.ON_FIRE || pSource == DamageSource.IN_FIRE) {
            pAmount *= 2f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(4, new LaunchArmGoal(this, 0.5f));
        goalSelector.addGoal(2, new IceSmashGoal(this, 0.5f));
        goalSelector.addGoal(3, new IceStrikeAttackGoal(this, 0.5f, 6f));
    }

    public void returnOneArm() {
        if (!hasLeftArm) {
            hasLeftArm = true;
        } else if (!hasRightArm) {
            hasRightArm = true;
        }
    }

    public void launchOneArm() {
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

    private void updateRotations() {
        orbitRotation += 2f;
        orbitRotation %= 360;
    }

    public float getOrbitRotation() {
        return orbitRotation;
    }

    public IceGuardianAction getIceGuardianAction() {
        return iceGuardianAction;
    }

    public void setIceGuardianAction(final IceGuardianAction action) {
        iceGuardianAction = action;
        ticksInAction = 0;
    }

    @Override
    public boolean canCastSpell() {
        return true;
    }

    @Override
    public boolean isCastingSpell() {
        return false;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            iceGuardianAction = IceGuardianAction.CASTING;
        } else if (iceGuardianAction == IceGuardianAction.CASTING) {
            iceGuardianAction = IceGuardianAction.IDLE;
        }
    }

    public enum IceGuardianAction {
        IDLE(-1),
        STRIKE(15),
        SMASH(20),
        CASTING(-1),
        LAUNCHING(20);

        private final int maxActionTime;

        IceGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
