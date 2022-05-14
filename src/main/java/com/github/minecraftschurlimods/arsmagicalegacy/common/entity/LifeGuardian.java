package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class LifeGuardian extends AbstractBoss {
    private final List<LivingEntity> minions = new ArrayList<>();
    private final List<LivingEntity> queuedMinions = new ArrayList<>();

    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 200).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 2500).add(AMAttributes.MAX_BURNOUT.get(), 2500);
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
    public SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_ATTACK.get();
    }

    @Override
    public Action getIdleAction() {
        return LifeGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return LifeGuardianAction.CASTING;
    }

    @Override
    public void aiStep() {
        if (!level.isClientSide()) {
            minions.addAll(queuedMinions);
            queuedMinions.clear();
            minions.removeIf(minion -> minion == null || minion.isDeadOrDying());
        }
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
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal_self")).spell(), 16, 80));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "nausea")).spell(), 16, 4));
//        goalSelector.addGoal(3, new SummonAlliesGoal(this, List.of(AMEntities.EARTH_ELEMENTAL.get(), AMEntities.FIRE_ELEMENTAL.get(), AMEntities.MANA_ELEMENTAL.get(), AMEntities.DARKLING.get())));
    }

    public void addQueuedMinion(LivingEntity minion) {
        queuedMinions.add(minion);
    }

    public enum LifeGuardianAction implements Action {
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
