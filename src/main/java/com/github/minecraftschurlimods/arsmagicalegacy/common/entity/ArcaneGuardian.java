package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteRandomSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.HealGoal;
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

import java.util.List;

public class ArcaneGuardian extends AbstractBoss {
    public ArcaneGuardian(EntityType<? extends ArcaneGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PINK);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 120).add(Attributes.ARMOR, 10).add(AMAttributes.MAX_MANA.get(), 2000).add(AMAttributes.MAX_BURNOUT.get(), 2000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.ARCANE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ARCANE_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.ARCANE_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ARCANE_GUARDIAN_ATTACK.get();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new HealGoal<>(this));
        goalSelector.addGoal(1, new ExecuteRandomSpellGoal<>(this, List.of(
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "water_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane_bolt")).spell()
        ), 30));
        goalSelector.addGoal(1, new ExecuteRandomSpellGoal<>(this, List.of(
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_water_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_fire_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_earth_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_lightning_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_ice_bolt")).spell(),
                PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "strong_arcane_bolt")).spell()
        ), 30));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "blink")).spell(), 30));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "debuff")).spell(), 30));
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(createBaseAnimationController("arcane_guardian"));
        data.addAnimationController(createActionAnimationController("arcane_guardian", "idle", Action.IDLE));
        data.addAnimationController(createActionAnimationController("arcane_guardian", "cast", Action.CAST));
    }
}
