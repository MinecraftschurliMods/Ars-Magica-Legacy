package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderRushGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderTorrentGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ExecuteBossSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.OtherworldlyRoarGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ShadowstepGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class EnderGuardian extends AbstractBoss {
    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PURPLE);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 5000).add(AMAttributes.MAX_BURNOUT.get(), 5000);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return AMSounds.ENDER_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ENDER_GUARDIAN_HURT.get();
    }

    @Override
    public SoundEvent getDeathSound() {
        return AMSounds.ENDER_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ATTACK.get();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new EnderRushGoal(this));
        goalSelector.addGoal(1, new ShadowstepGoal(this));
        Registry<PrefabSpell> prefabSpells = level().registryAccess().registryOrThrow(PrefabSpell.REGISTRY_KEY);
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_bolt")).spell(), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, prefabSpells.get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_wave")).spell(), 10));
        goalSelector.addGoal(1, new EnderTorrentGoal(this));
        goalSelector.addGoal(1, new OtherworldlyRoarGoal(this));
    }

    @Override
    public void aiStep() {
        if (tickCount % 10 == 0) {
            level().playSound(null, this, AMSounds.ENDER_GUARDIAN_FLAP.get(), SoundSource.HOSTILE, 1f, 1f);
        }
        if (getAction() == Action.LONG_CAST) {
            if (getTicksInAction() == 20) {
                level().playSound(null, this, AMSounds.ENDER_GUARDIAN_ROAR.get(), SoundSource.HOSTILE, 1f, 1f);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof EnderMan) {
            pSource.getEntity().hurt(damageSources().fellOutOfWorld(), 5000);
            heal(10);
            return false;
        }
        if (pSource.is(DamageTypes.MAGIC) || pSource.is(DamageTypeTags.IS_DROWNING)) {
            pAmount *= 2f;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                createBaseAnimationController("ender_guardian"),
                createActionAnimationController("ender_guardian", "idle", Action.IDLE),
                createActionAnimationController("ender_guardian", "cast", Action.CAST),
                createActionAnimationController("ender_guardian", "cast", Action.LONG_CAST)
        );
    }
}
