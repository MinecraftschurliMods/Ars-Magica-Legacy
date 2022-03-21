package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DiveGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FireRainGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FlamethrowerGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FireGuardian extends AbstractBoss {
    private boolean isUnderground = false;
    private int hitCount = 0;
    private FireGuardianAction fireGuardianAction;

    public FireGuardian(EntityType<? extends FireGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 250).add(Attributes.ARMOR, 20);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.FIRE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.FIRE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.FIRE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (ticksInAction == 30 && getFireGuardianAction() == FireGuardianAction.SPINNING) {
            nova();
        }
        if (ticksInAction > 13 && getFireGuardianAction() == FireGuardianAction.LONG_CASTING) {
            if (getTarget() != null) {
                lookAt(getTarget(), 10, 10);
            }
            flamethrower();
        }
        doFlameShield();
        super.aiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (isUnderground && getFireGuardianAction() != FireGuardianAction.SPINNING) return false;
        if (getFireGuardianAction() == FireGuardianAction.SPINNING) {
            hitCount++;
        }
        if (pSource == DamageSource.DROWN) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.FREEZE) {
            pAmount /= 3f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "melt_armor")).spell(), 12, 18));
        goalSelector.addGoal(4, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_bolt")).spell(), 12, 18));
        goalSelector.addGoal(2, new DiveGoal(this));
        goalSelector.addGoal(1, new FireRainGoal(this));
        goalSelector.addGoal(3, new FlamethrowerGoal(this));
    }

    @Override
    protected int calculateFallDamage(final float pDistance, final float pDamageMultiplier) {
        if (getFireGuardianAction() == FireGuardianAction.SPINNING) {
            isUnderground = true;
        }
        return super.calculateFallDamage(pDistance, pDamageMultiplier);
    }

    public void nova() {
        if (level.isClientSide()) {
            // Particles
        } else {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, -3, 0))) {
                if (e != this) {
                    e.hurt(DamageSource.ON_FIRE, 5);
                }
            }
        }
    }

    public void flamethrower() {
        Vec3 look = getLookAngle();
        if (level.isClientSide()) {
            // Particles
        } else {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(look.x * 3, 0, look.z * 3))) {
                if (e != this) {
                    e.hurt(DamageSource.ON_FIRE, 5);
                }
            }
        }
    }

    public void doFlameShield() {
        if (!level.isClientSide()) {
            for (Player p : level.players()) {
                if (distanceToSqr(p) < 9) {
                    p.hurt(DamageSource.ON_FIRE, 5);
                }
            }
        }
    }

    public boolean getIsUnderground() {
        return isUnderground;
    }

    public boolean isBurning() {
        return !isUnderground;
    }

    public int getHitCount() {
        return hitCount;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public FireGuardianAction getFireGuardianAction() {
        return fireGuardianAction;
    }

    public void setFireGuardianAction(final FireGuardianAction action) {
        if (action == FireGuardianAction.SPINNING) {
            // Particles
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() + 1.5, getDeltaMovement().z());
        } else {
            hitCount = 0;
            isUnderground = false;
        }
        fireGuardianAction = action;
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
            fireGuardianAction = FireGuardianAction.CASTING;
        } else if (fireGuardianAction == FireGuardianAction.CASTING) {
            fireGuardianAction = FireGuardianAction.IDLE;
        }
    }

    public enum FireGuardianAction {
        IDLE(-1),
        CASTING(-1),
        LONG_CASTING(-1),
        SPINNING(160);

        private final int maxActionTime;

        FireGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
