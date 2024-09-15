package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HealGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.SummonAlliesGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animation.AnimatableManager;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class LifeGuardian extends AbstractBoss {
    public final Set<LivingEntity> minions = new HashSet<>();

    public LifeGuardian(EntityType<? extends LifeGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
                .add(Attributes.MAX_HEALTH, 400)
                .add(Attributes.ARMOR, 10)
                .add(AMAttributes.MAX_MANA, 2500)
                .add(AMAttributes.MAX_BURNOUT, 2500);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.LIFE_GUARDIAN_AMBIENT.value();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.LIFE_GUARDIAN_HURT.value();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.LIFE_GUARDIAN_DEATH.value();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_ATTACK.value();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void registerGoals() {
        super.registerGoals();
//        goalSelector.addGoal(1, new SummonAlliesGoal(this, AMEntities.EARTH_ELEMENTAL.get(), AMEntities.FIRE_ELEMENTAL.get(), AMEntities.MANA_ELEMENTAL.get(), AMEntities.DARKLING.get()));
        goalSelector.addGoal(1, new SummonAlliesGoal(this, List.of(EntityType.PILLAGER, EntityType.VINDICATOR, EntityType.WITCH)));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, level().registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY).get(ArsMagicaAPI.resource("nausea")).spell(), 30));
        goalSelector.addGoal(1, new HealGoal<>(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!level().isClientSide()) {
            minions.removeIf(Objects::isNull);
            Set<LivingEntity> toRemove = new HashSet<>();
            for (LivingEntity e : minions) {
                if (e.tickCount > 1200 || e.isRemoved()) {
                    e.kill();
                    toRemove.add(e);
                }
                if (e.isDeadOrDying() && !toRemove.contains(e)) {
                    hurt(damageSources().fellOutOfWorld(), e.getMaxHealth());
                    toRemove.add(e);
                }
            }
            minions.removeAll(toRemove);
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() != null && pSource.getEntity() instanceof LivingEntity) {
            for (LivingEntity e : minions) {
                e.setLastHurtByMob((LivingEntity) pSource.getEntity());
            }
        }
        return pSource.is(DamageTypes.FELL_OUT_OF_WORLD) && super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(createBaseAnimationController("life_guardian"));
    }
}
