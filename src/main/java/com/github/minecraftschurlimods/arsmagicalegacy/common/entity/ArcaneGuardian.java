package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class ArcaneGuardian extends AbstractBoss {
    public ArcaneGuardian(EntityType<? extends ArcaneGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PINK);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 2000).add(AMAttributes.MAX_BURNOUT.get(), 2000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ARCANE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ARCANE_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ARCANE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ARCANE_GUARDIAN_ATTACK.get();
    }

    @Override
    public Action getIdleAction() {
        return ArcaneGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return ArcaneGuardianAction.CASTING;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new DispelGoal<>(this));
        goalSelector.addGoal(1, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal_self")).spell(), 16, 40));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "blink")).spell(), 16, 4));
        goalSelector.addGoal(3, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_bolt")).spell(), 12, 18));
    }

    public enum ArcaneGuardianAction implements Action {
        IDLE(-1),
        CASTING(-1);

        private final int maxActionTime;

        ArcaneGuardianAction(int maxTime) {
            maxActionTime = maxTime;
        }

        public int getMaxActionTime() {
            return maxActionTime;
        }
    }
}
