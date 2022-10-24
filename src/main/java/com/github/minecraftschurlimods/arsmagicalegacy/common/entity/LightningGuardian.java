package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.LightningRodGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.StaticGoal;
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
import software.bernie.geckolib3.core.manager.AnimationData;

public class LightningGuardian extends AbstractBoss {
    public LightningGuardian(EntityType<? extends LightningGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.YELLOW);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 250).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 4000).add(AMAttributes.MAX_BURNOUT.get(), 4000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.LIGHTNING_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.LIGHTNING_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_ATTACK.get();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new LightningRodGoal(this));
        goalSelector.addGoal(1, new StaticGoal(this));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_bolt")).spell(), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_lightning_bolt")).spell(), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "area_lightning")).spell(), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_rune")).spell(), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "scramble_synapses")).spell(), 10));
    }

    @Override
    public void aiStep() {
        if (level.isClientSide()) {
            // Particles
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource == DamageSource.DROWN) {
            pAmount *= 2;
        } else if (pSource == DamageSource.LIGHTNING_BOLT) {
            heal(pAmount);
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createActionAnimationController("lightning_guardian", "idle", Action.IDLE));
        data.addAnimationController(createActionAnimationController("lightning_guardian", "cast", Action.CAST));
        data.addAnimationController(createActionAnimationController("lightning_guardian", "cast", Action.LONG_CAST));
        data.addAnimationController(createActionAnimationController("lightning_guardian", "spin", Action.SPIN));
    }

    @Override
    public void setAction(Action action) {
        super.setAction(action);
        setNoGravity(action == Action.LONG_CAST);
    }
}
