package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FireRainGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FlamethrowerGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class FireGuardian extends AbstractBoss {
    public FireGuardian(EntityType<? extends FireGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 250).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 4500).add(AMAttributes.MAX_BURNOUT.get(), 4500);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.FIRE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.FIRE_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.FIRE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_ATTACK.get();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new FireRainGoal(this));
        goalSelector.addGoal(1, new FlamethrowerGoal(this));
        Registry<PrefabSpell> prefabSpells = level.registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY);
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_bolt")).spell(), 20));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_fire_bolt")).spell(), 20));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "melt_armor")).spell(), 20));
    }

    @Override
    public void aiStep() {
        if (tickCount % 30 == 0) {
            level.playSound(null, this, AMSounds.FIRE_GUARDIAN_NOVA.get(), SoundSource.HOSTILE, 1f, 0.5f + level.getRandom().nextFloat() * 0.5f);
            if (level.isClientSide()) {
                // Particles
            } else {
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, 3, 0), e -> !(e instanceof AbstractBoss))) {
                    e.hurt(damageSources().onFire(), 5);
                }
            }
        }
        if (getTicksInAction() > 10 && getAction() == Action.LONG_CAST) {
            if (getTarget() != null) {
                lookAt(getTarget(), 10, 10);
            }
            level.playSound(null, this, AMSounds.FIRE_GUARDIAN_FLAMETHROWER.get(), SoundSource.HOSTILE, 1f, 0.5f + level.getRandom().nextFloat() * 0.5f);
            flamethrower();
        }
        for (Player p : level.players()) {
            if (distanceToSqr(p) < 9) {
                p.hurt(damageSources().onFire(), 8);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.IS_DROWNING)) {
            pAmount *= 2f;
        } else if (pSource.is(DamageTypeTags.IS_FREEZING)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                createActionAnimationController("fire_guardian", "idle", Action.IDLE),
                createActionAnimationController("fire_guardian", "cast", Action.CAST),
                createActionAnimationController("fire_guardian", "cast", Action.LONG_CAST)
        );
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public void flamethrower() {
        Vec3 look = getLookAngle();
        if (level.isClientSide()) {
            for (int i = 0; i < 20; i++) {
                level.addParticle(ParticleTypes.FLAME, getRandomX(1), getRandomY() + 1.5, getRandomZ(1), look.x, look.y, look.z);
            }
        } else {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(look.x * 3, 0, look.z * 3), e -> !(e instanceof AbstractBoss))) {
                e.hurt(damageSources().onFire(), 8);
            }
        }
    }
}
