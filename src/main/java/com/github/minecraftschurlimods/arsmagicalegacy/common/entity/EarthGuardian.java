package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EarthSmashGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EarthStrikeGoal;
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

public class EarthGuardian extends AbstractBoss {
    private EarthGuardianAction action;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 1000).add(AMAttributes.MAX_BURNOUT.get(), 1000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.FREEZE) {
            pAmount *= 2f;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            pAmount /= 4f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(1, new ThrowRockGoal(this));
        goalSelector.addGoal(2, new EarthSmashGoal(this));
        goalSelector.addGoal(2, new EarthStrikeGoal(this));
    }

    @Override
    public int getMaxFallDistance() {
        return getTarget() == null ? 3 : 3 + (int) (getHealth() - 1F);
    }

    public boolean shouldRenderRock() {
        return getAction() == EarthGuardianAction.THROWING_ROCK && ticksInAction > 3 && ticksInAction < 27;
    }

    public EarthGuardianAction getAction() {
        return action;
    }

    public void setAction(final EarthGuardianAction action) {
        this.action = action;
        ticksInAction = 0;
    }

    @Override
    public boolean canCastSpell() {
        return action == EarthGuardianAction.IDLE;
    }

    @Override
    public boolean isCastingSpell() {
        return action == EarthGuardianAction.CASTING;
    }

    @Override
    public void setIsCastingSpell(boolean isCastingSpell) {
        if (isCastingSpell) {
            action = EarthGuardianAction.CASTING;
        } else if (action == EarthGuardianAction.CASTING) {
            action = EarthGuardianAction.IDLE;
        }
    }

    public enum EarthGuardianAction {
        IDLE(-1),
        CASTING(-1),
        STRIKE(15),
        THROWING_ROCK(30),
        SMASH(20);

        private final int maxActionTime;

        EarthGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
