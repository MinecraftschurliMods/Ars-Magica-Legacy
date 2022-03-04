package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Iterator;

// TODO aiStep() Particle
// TODO hurt() Random minion line
// registerGoal()
public class LifeGuardian extends AbstractBoss {
    private ArrayList<LivingEntity> minions = new ArrayList<LivingEntity>();
    private ArrayList<LivingEntity> queuedMinions = new ArrayList<LivingEntity>();
    private LifeGuardianAction lifeGuardianAction;
    private static final EntityDataAccessor<Integer> DATA_MINION_COUNT = SynchedEntityData.defineId(LifeGuardian.class, EntityDataSerializers.INT);

    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
        this.entityData.define(DATA_MINION_COUNT, 0);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, Attributes.FOLLOW_RANGE.getDefaultValue()).add(Attributes.MAX_HEALTH, 200D).add(Attributes.ARMOR, Attributes.ARMOR.getDefaultValue());
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
        if(!this.level.isClientSide()) {
            this.minions.addAll(this.queuedMinions);
            this.queuedMinions.clear();
            this.minions.removeIf(minion -> minion == null || minion.isDeadOrDying());
        }

        this.entityData.set(DATA_MINION_COUNT, this.minions.size());

//        if (this.tickCount % 100 == 0) {
//            for (LivingEntity e : this.minions) {
//                // Particle
//            }
//        }
        if (this.tickCount % 40 == 0) {
            this.heal(2f);
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.minions.size() > 0) {
            pAmount = 0;
            LivingEntity minion = this.minions.get(random.nextInt(this.minions.size()));  // probabily wrong
        }
        if (pSource.getEntity() != null && pSource.getEntity() instanceof LivingEntity) {
            for (LivingEntity minion : this.minions.toArray((new LivingEntity[minions.size()]))) {
                ((LivingEntity)minion).doHurtTarget((LivingEntity)pSource.getEntity());
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // Dispel
        // ExecuteSpellGoal (healSelf)
        // ExecuteSpellGoal (nauseate)
        // SummonAllies
    }

    @Override
    public float getEyeHeight(Pose pPose) {
        return 1.5F;
    }

    public int getNumMinions() {
        return this.entityData.get(DATA_MINION_COUNT);
    }

    public LifeGuardianAction getLifeGuardianAction() {
        return this.lifeGuardianAction;
    }

    public void setLifeGuardianAction(final LifeGuardianAction action) {
        this.lifeGuardianAction = action;
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
        if(isCastingSpell) {
            this.lifeGuardianAction = LifeGuardianAction.CASTING;
        } else {
            this.lifeGuardianAction = LifeGuardianAction.IDLE;
        }
    }

    public enum LifeGuardianAction {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        private LifeGuardianAction(int maxTime){
            maxActionTime = maxTime;
        }

        public int getMaxActionTime(){
            return maxActionTime;
        }
    }
}
