package com.github.minecraftschurlimods.arsmagicalegacy.common.entity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.AbstractBoss;
import com.github.minecraftschurlimods.arsmagicalegacy.api.entity.ExecuteSpellGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.DispelGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderRushGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.EnderTorrentGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.OtherworldlyRoarGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ProtectGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.entity.ai.ShadowstepGoal;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EnderGuardian extends AbstractBoss {
    private int hitCount = 0;
    private int ticksSinceLastAttack = 0;
    private float wingFlapTime = 0;
    private Vec3 spawn;

    public EnderGuardian(EntityType<? extends EnderGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PURPLE);
        action = EnderGuardianAction.IDLE;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMonsterAttributes().add(Attributes.MAX_HEALTH, 500).add(Attributes.ARMOR, 20).add(AMAttributes.MAX_MANA.get(), 5000).add(AMAttributes.MAX_BURNOUT.get(), 5000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ENDER_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return AMSounds.ENDER_GUARDIAN_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ENDER_GUARDIAN_DEATH.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ATTACK.get();
    }

    @Override
    public Action getIdleAction() {
        return EnderGuardianAction.IDLE;
    }

    @Override
    public Action getCastingAction() {
        return EnderGuardianAction.CASTING;
    }

    @Override
    public void aiStep() {
        if (spawn == null) {
            spawn = position();
        }
        ticksSinceLastAttack++;
        if (getAction() == EnderGuardianAction.LONG_CASTING) {
            if (getTicksInAction() == 32) {
                level.playSound(null, this, AMSounds.ENDER_GUARDIAN_ROAR.get(), SoundSource.HOSTILE, 1f, 1f);
            }
        }
        if (shouldFlapWings()) {
            wingFlapTime += getWingFlapSpeed() * 20f;
            if (wingFlapTime % (50 * getWingFlapSpeed()) == 0) {
                level.playSound(null, this, AMSounds.ENDER_GUARDIAN_FLAP.get(), SoundSource.HOSTILE, 1f, 1f);
            }
        }
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isMagic() || pSource == DamageSource.DROWN) {
            pAmount *= 2f;
        }
        if (pSource.getEntity() instanceof EnderMan) {
            pSource.getEntity().hurt(DamageSource.OUT_OF_WORLD, 5000);
            heal(10);
            return false;
        }
        if (pSource == DamageSource.OUT_OF_WORLD) {
            if (spawn != null) {
                moveTo(spawn.x(), spawn.y(), spawn.z());
                setAction(EnderGuardianAction.IDLE);
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new ProtectGoal(this));
        goalSelector.addGoal(2, new EnderRushGoal(this));
        goalSelector.addGoal(2, new ShadowstepGoal(this));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_bolt")).spell(), 20));
        goalSelector.addGoal(2, new ExecuteSpellGoal<>(this, PrefabSpellManager.instance().get(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender_wave")).spell(), 20));
        goalSelector.addGoal(2, new EnderTorrentGoal(this));
        goalSelector.addGoal(2, new OtherworldlyRoarGoal(this));
    }

    public int getTicksSinceLastAttack() {
        return ticksSinceLastAttack;
    }

    public float getWingFlapTime() {
        return wingFlapTime;
    }

    public float getWingFlapSpeed() {
        Action action = getAction();
        return action == EnderGuardianAction.CASTING ? 0.5f : 0.25f;
    }

    public boolean shouldFlapWings() {
        return getAction() != EnderGuardianAction.LONG_CASTING;
    }

    @Override
    public void setAction(Action action) {
        super.setAction(action);
        if (action == EnderGuardianAction.LONG_CASTING) {
            wingFlapTime = 0;
        }
    }

    @Override
    public Action[] getActions() {
        return EnderGuardianAction.values();
    }

    public enum EnderGuardianAction implements Action {
        IDLE(-1, IDLE_ID),
        CASTING(-1, CASTING_ID),
        LONG_CASTING(-1, ACTION_1_ID);

        private final int maxActionTime;
        private final byte animationId;

        EnderGuardianAction(int maxActionTime, byte animationId) {
            this.maxActionTime = maxActionTime;
            this.animationId = animationId;
        }

        @Override
        public int getMaxActionTime() {
            return maxActionTime;
        }

        @Override
        public byte getAnimationId() {
            return animationId;
        }
    }
}
