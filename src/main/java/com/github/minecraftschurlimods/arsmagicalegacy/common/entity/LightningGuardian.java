package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class LightningGuardian extends AbstractBoss {
    private LightningGuardianAction lightningGuardianAction;

    public LightningGuardian(EntityType<? extends LightningGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 250D).add(Attributes.ARMOR, 18);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.LIGHTNING_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIGHTNING_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (getTarget() != null) {
            if (!level.isClientSide() && distanceToSqr(getTarget()) > 64D && getLightningGuardianAction() == LightningGuardianAction.IDLE) {
                getNavigation().moveTo(getTarget(), 0.5f);
            }
        }
        if (level.isClientSide()) {
            int halfDist = 8;
            int dist = 16;
            if (getLightningGuardianAction() == LightningGuardianAction.CHARGE) {
                if (ticksInAction > 50) {
                    // Particles
                }
                if (ticksInAction > 66) {
                    // Particles
                }
            } else if (getLightningGuardianAction() == LightningGuardianAction.LONG_CASTING) {
                if (ticksInAction > 25 && ticksInAction < 150) {
                    // Particles
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic() || pSource == DamageSource.MAGIC) {
            pAmount *= 2;
        } else if (pSource == DamageSource.DROWN) {
            pAmount *= 4;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount *= -1;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_bolt")).spell(), 20, 180));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_rune")).spell(), 20, 180));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "area_lightning")).spell(), 20, 180));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "scramble_synapses")).spell(), 40, 240));
        // LightningBolt
        // LightningRod
        // Static
    }

    @Override
    public float getEyeHeight(Pose pPose) {
        return 2;
    }

    public LightningGuardianAction getLightningGuardianAction() {
        return lightningGuardianAction;
    }

    public void setLightningGuardianAction(final LightningGuardianAction action) {
        lightningGuardianAction = action;
        ticksInAction = 0;
        setNoGravity(action == LightningGuardianAction.LONG_CASTING);
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
            lightningGuardianAction = LightningGuardianAction.CASTING;
        } else if (lightningGuardianAction == LightningGuardianAction.CASTING) {
            lightningGuardianAction = LightningGuardianAction.IDLE;
        }
    }

    public enum LightningGuardianAction {
        IDLE(-1),
        CASTING(-1),
        CHARGE(-1),
        LONG_CASTING(-1),
        SMASH(20);

        private final int maxActionTime;

        LightningGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
