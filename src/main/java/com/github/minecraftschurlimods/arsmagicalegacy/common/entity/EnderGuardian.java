package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderRushGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderTorrentGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.OtherworldlyRoarGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ShadowstepGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class EnderGuardian extends AbstractBoss {
    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PURPLE, AMTags.DamageTypes.ENDER_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.ENDER_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.ENDER_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 5000)
            .add(AMAttributes.MAX_BURNOUT, 5000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ENDER_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ENDER_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.ENDER_GUARDIAN_HURT.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        Entity entity = source.getEntity();
        if (entity != null && entity.is(AMTags.EntityTypes.ENDER_GUARDIAN_SACRIFICES)) {
            entity.hurtServer(level, damageSources().fellOutOfWorld(), 5000);
            heal(10);
            return false;
        }
        return super.hurtServer(level, source, damage);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        Registry<Spell> registry = registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
        goalSelector.addGoal(1, new EnderRushGoal(this));
        goalSelector.addGoal(1, new ShadowstepGoal(this));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("ender_bolt")), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("ender_wave")), 10));
        goalSelector.addGoal(1, new EnderTorrentGoal(this));
        goalSelector.addGoal(1, new OtherworldlyRoarGoal(this));
    }

    @Override
    public void aiStep() {
        if (tickCount % 10 == 0) {
            level().playSound(null, this, AMSounds.ENDER_GUARDIAN_FLAP.get(), SoundSource.HOSTILE, 1f, 1f);
        }
        if (getAction() == Action.LONG_CAST && getTicksInAction() == 20) {
            level().playSound(null, this, AMSounds.ENDER_GUARDIAN_ROAR.get(), SoundSource.HOSTILE, 1f, 1f);
        }
        super.aiStep();
    }
}
