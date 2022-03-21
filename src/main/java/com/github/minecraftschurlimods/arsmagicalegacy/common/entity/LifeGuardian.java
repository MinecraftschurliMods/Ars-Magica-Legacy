package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.ArrayList;

public class LifeGuardian extends AbstractBoss {
    private static final EntityDataAccessor<Integer> MINION_COUNT = SynchedEntityData.defineId(LifeGuardian.class, EntityDataSerializers.INT);
    private final ArrayList<LivingEntity> minions = new ArrayList<>();
    private final ArrayList<LivingEntity> queuedMinions = new ArrayList<>();
    private LifeGuardianAction lifeGuardianAction;

    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
        this.entityData.define(MINION_COUNT, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 200).add(Attributes.ARMOR, Attributes.ARMOR.getDefaultValue());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIFE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.LIFE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIFE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_ATTACK.get();
    }

    @Override
    public void aiStep() {
        if (!level.isClientSide()) {
            minions.addAll(queuedMinions);
            queuedMinions.clear();
            minions.removeIf(minion -> minion == null || minion.isDeadOrDying());
        }
        entityData.set(MINION_COUNT, minions.size());
        if (tickCount % 100 == 0) {
            for (LivingEntity e : minions) {
                // Particles
            }
        }
        if (tickCount % 40 == 0) {
            heal(2f);
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && pSource.getEntity() instanceof LivingEntity) {
            for (LivingEntity e : minions) {
                e.setLastHurtByMob((LivingEntity) pSource.getEntity());
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "dispel")).spell(), 16, 40));
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal_self")).spell(), 16, 80));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "nausea")).spell(), 16, 4));
        //goalSelector.addGoal(3, new SummonAllies(this, EarthElemental.class, FireElemental.class, ManaElemental.class, Darkling.class));
    }

    @Override
    public float getEyeHeight(Pose pPose) {
        return 1.5F;
    }

    public int getMinionCount() {
        return entityData.get(MINION_COUNT);
    }

    public void addQueuedMinions(LivingEntity minion) {
        queuedMinions.add(minion);
    }

    public LifeGuardianAction getLifeGuardianAction() {
        return lifeGuardianAction;
    }

    public void setLifeGuardianAction(final LifeGuardianAction action) {
        lifeGuardianAction = action;
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
            lifeGuardianAction = LifeGuardianAction.CASTING;
        } else if (lifeGuardianAction == LifeGuardianAction.CASTING) {
            lifeGuardianAction = LifeGuardianAction.IDLE;
        }
    }

    public enum LifeGuardianAction {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        LifeGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
