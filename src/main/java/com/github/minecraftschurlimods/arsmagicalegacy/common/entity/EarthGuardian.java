package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StompGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StrikeGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ThrowRockGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EarthGuardian extends AbstractBoss {
    private static final byte HAS_ROCK_TRUE = (byte) -8;
    private static final byte HAS_ROCK_FALSE = (byte) -9;
    private static final String HAS_ROCK_KEY = "has_rock";
    private boolean hasRock = false;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN, AMTags.DamageTypes.EARTH_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 120)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 1000)
            .add(AMAttributes.MAX_BURNOUT, 1000);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> hasRock = child.getBooleanOr(HAS_ROCK_KEY, true));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.child(ArsMagicaApi.MOD_ID).putBoolean(HAS_ROCK_KEY, hasRock);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new StompGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowRockGoal(this));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == HAS_ROCK_TRUE) {
            hasRock = true;
        } else if (id == HAS_ROCK_FALSE) {
            hasRock = false;
        }
        super.handleEntityEvent(id);
    }

    public boolean hasRock() {
        return hasRock;
    }

    public void setHasRock(boolean hasRock) {
        this.hasRock = hasRock;
        level().broadcastEntityEvent(this, hasRock ? HAS_ROCK_TRUE : HAS_ROCK_FALSE);
    }
}
