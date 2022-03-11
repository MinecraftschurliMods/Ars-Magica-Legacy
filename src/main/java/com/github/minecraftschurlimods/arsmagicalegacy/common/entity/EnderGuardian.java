package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.mojang.math.Vector3f;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EnderGuardian extends AbstractBoss {
    private int wingFlapTime = 0;
    private int ticksSinceLastAttack = 0;
    private int hitCount = 0;
    private Vector3f spawn;
    private EnderGuardianAction enderGuardianAction;

    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 490D).add(Attributes.ARMOR, 16);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ENDER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ENDER_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ENDER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (spawn == null) {
            spawn = new Vector3f(position());
        }
        wingFlapTime++;
        ticksSinceLastAttack++;
        if (getDeltaMovement().y() < 0) {
            setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() * 0.7999999f, getDeltaMovement().z());
        }
        switch (getEnderGuardianAction()) {
            case LONG_CASTING:
                if (ticksInAction == 32) {
                    level.playSound(null, this, AMSounds.ENDER_GUARDIAN_ROAR.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
                }
                break;
            case CHARGE:
                if (getNoActionTime() == 0) {
                    setDeltaMovement(getDeltaMovement().x(), getDeltaMovement().y() + 1.5f, getDeltaMovement().z());
                }
                break;
        }
        if (shouldFlapWings() && wingFlapTime % (50 * getWingFlapSpeed()) == 0) {
            level.playSound(null, this, AMSounds.ENDER_GUARDIAN_FLAP.get(), SoundSource.HOSTILE, 1.0f, 1.0f);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic()) {
            pAmount *= 2f;
        }
        if (pSource.getEntity() instanceof EnderMan) {
            pSource.getEntity().hurt(DamageSource.OUT_OF_WORLD, 5000);
            heal(10);
            return false;
        }
        if (pSource == DamageSource.OUT_OF_WORLD) {
            if (spawn != null) {
                moveTo(spawn.x(), spawn.y(), spawn.z());
                setEnderGuardianAction(EnderGuardianAction.IDLE);
            } else {
                removeAfterChangingDimensions();
            }
            return false;
        }
        ticksSinceLastAttack = 0;
        if (!level.isClientSide() && pSource.getEntity() != null && pSource.getEntity() instanceof Player) {
            if (pSource == getLastDamageSource()) {
                hitCount++;
                if (hitCount > 5) {
                    heal(pAmount / 4);
                }
                return false;
            } else {
                hitCount = 1;
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "boss_dispel")).spell(), 20, 50));
        // EnderBolt
        // EnderRush
        // EnderTorrent
        // EnderWave
        // OtherworldlyRoar
        // Protect
        // Shadowstep
    }

    @Override
    public float getEyeHeight(Pose pPose) {
        return 2.5F;
    }

    public int getTicksSinceLastAttack() {
        return ticksSinceLastAttack;
    }

    public int getWingFlapTime() {
        return wingFlapTime;
    }

    public float getWingFlapSpeed() {
        return switch (getEnderGuardianAction()) {
            case CASTING -> 0.5f;
            case CHARGE -> getTicksInAction() < 15 ? 0.25f : 0.75f;
            case STRIKE -> 0.4f;
            default -> 0.25f;
        };
    }

    public boolean shouldFlapWings() {
        return getEnderGuardianAction() != EnderGuardianAction.LONG_CASTING && getEnderGuardianAction() != EnderGuardianAction.SHIELD_BASH;
    }

    @Override
    public boolean hasEffect(final MobEffect pPotion) {
        if ((pPotion == AMMobEffects.REFLECT.get() || pPotion == AMMobEffects.MAGIC_SHIELD.get()) && (getEnderGuardianAction() == EnderGuardianAction.SHIELD_BASH || getEnderGuardianAction() == EnderGuardianAction.LONG_CASTING))
            return true;
        return super.hasEffect(pPotion);
    }

    public EnderGuardianAction getEnderGuardianAction() {
        return enderGuardianAction;
    }

    public void setEnderGuardianAction(final EnderGuardianAction action) {
        enderGuardianAction = action;
        if (action == EnderGuardianAction.LONG_CASTING) {
            wingFlapTime = 0;
        }
        ticksInAction = 0;
    }

    @Override
    public boolean canCastSpell() {
        return false;
    }

    @Override
    public boolean isCastingSpell() {
        return false;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            enderGuardianAction = EnderGuardianAction.CASTING;
        } else if (enderGuardianAction == EnderGuardianAction.CASTING) {
            enderGuardianAction = EnderGuardianAction.IDLE;
        }
    }

    public enum EnderGuardianAction {
        IDLE(-1),
        CASTING(-1),
        CHARGE(-1),
        LONG_CASTING(-1),
        SHIELD_BASH(15),
        STRIKE(15);

        private final int maxActionTime;

        EnderGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
