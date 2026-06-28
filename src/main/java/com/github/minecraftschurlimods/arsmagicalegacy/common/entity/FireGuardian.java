package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FireRainGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.FlamethrowerGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class FireGuardian extends AbstractBoss {
    public FireGuardian(EntityType<? extends FireGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.RED, AMTags.DamageTypes.FIRE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.FIRE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.FIRE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 250)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 4500)
            .add(AMAttributes.MAX_BURNOUT, 4500);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.FIRE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.FIRE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.FIRE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.FIRE_GUARDIAN_HURT.get();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        Registry<Spell> registry = registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
        goalSelector.addGoal(1, new FireRainGoal(this));
        goalSelector.addGoal(1, new FlamethrowerGoal(this));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("fire_bolt")), 20));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("strong_fire_bolt")), 20));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("melt_armor")), 20));
    }

    @Override
    public void aiStep() {
        Level level = Objects.requireNonNull(level());
        if (tickCount % 30 == 0) {
            if (level.getRandom().nextInt(10) == 0) {
                level.playSound(null, this, AMSounds.FIRE_GUARDIAN_NOVA.value(), SoundSource.HOSTILE, 0.1f, 0.5f + level.getRandom().nextFloat() * 0.5f);
            }
            if (level instanceof ServerLevel serverLevel) {
                for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(0, 3, 0), e -> !(e instanceof AbstractBoss))) {
                    e.hurtServer(serverLevel, damageSources().onFire(), 5);
                }
            } else {
                // TODO particles
            }
        }
        if (getTicksInAction() > 10 && getAction() == Action.LONG_CAST) {
            if (getTarget() != null) {
                lookAt(getTarget(), 10, 10);
            }
            level.playSound(null, this, AMSounds.FIRE_GUARDIAN_FLAMETHROWER.value(), SoundSource.HOSTILE, 1f, 0.5f + level.getRandom().nextFloat() * 0.5f);
            flamethrower();
        }
        if (level instanceof ServerLevel serverLevel) {
            for (Player p : level.players()) {
                if (distanceToSqr(p) < 9) {
                    p.hurtServer(serverLevel, damageSources().onFire(), 8);
                }
            }
        }
        super.aiStep();
    }

    public void flamethrower() {
        Vec3 look = getLookAngle();
        Level level = level();
        if (level instanceof ServerLevel serverLevel) {
            for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(2.5, 2.5, 2.5).expandTowards(look.x * 3, 0, look.z * 3), e -> !(e instanceof AbstractBoss))) {
                e.hurtServer(serverLevel, damageSources().onFire(), 8);
            }
        } else {
            for (int i = 0; i < 20; i++) {
                level.addParticle(ParticleTypes.FLAME, getRandomX(1), getRandomY() + 1.5, getRandomZ(1), look.x, look.y, look.z);
            }
        }
    }
}
