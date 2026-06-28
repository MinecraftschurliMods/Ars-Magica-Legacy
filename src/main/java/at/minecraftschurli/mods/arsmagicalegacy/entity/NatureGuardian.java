package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.SpinGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StrikeGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ThrowScytheGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class NatureGuardian extends AbstractBoss {
    private static final byte HAS_SCYTHE_TRUE = (byte) -8;
    private static final byte HAS_SCYTHE_FALSE = (byte) -9;
    private static final String HAS_SCYTHE_KEY = "has_scythe";
    private boolean hasScythe = true;

    public NatureGuardian(EntityType<? extends NatureGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.GREEN, AMTags.DamageTypes.NATURE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.NATURE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.NATURE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 500)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 3500)
            .add(AMAttributes.MAX_BURNOUT, 3500);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> hasScythe = child.getBooleanOr(HAS_SCYTHE_KEY, true));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.child(ArsMagicaApi.MOD_ID).putBoolean(HAS_SCYTHE_KEY, hasScythe);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.NATURE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.NATURE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.NATURE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.NATURE_GUARDIAN_HURT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new SpinGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowScytheGoal(this));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == HAS_SCYTHE_TRUE) {
            hasScythe = true;
        } else if (id == HAS_SCYTHE_FALSE) {
            hasScythe = false;
        }
        super.handleEntityEvent(id);
    }

    public boolean hasScythe() {
        return hasScythe;
    }

    public void setHasScythe(boolean hasScythe) {
        this.hasScythe = hasScythe;
        level().broadcastEntityEvent(this, hasScythe ? HAS_SCYTHE_TRUE : HAS_SCYTHE_FALSE);
    }
}
