package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StompGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StrikeGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ThrowArmGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class IceGuardian extends AbstractBoss {
    private static final String ARMS_KEY = "arms";
    private int arms = 2;

    public IceGuardian(EntityType<? extends IceGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.BLUE, AMTags.DamageTypes.ICE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.ICE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.ICE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 300)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 3000)
            .add(AMAttributes.MAX_BURNOUT, 3000);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> arms = child.getIntOr(ARMS_KEY, 2));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.child(ArsMagicaApi.MOD_ID).putInt(ARMS_KEY, arms);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ICE_GUARDIAN_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ICE_GUARDIAN_DEATH.get();
    }

    @Override
    public boolean canFreeze() {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new StompGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowArmGoal(this));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id <= -8 && id >= -10) {
            arms = id + 8;
        }
        super.handleEntityEvent(id);
    }

    public void launchArm() {
        arms--;
        level().broadcastEntityEvent(this, (byte) (arms - 8));
    }

    public void returnArm() {
        arms++;
        level().broadcastEntityEvent(this, (byte) (arms - 8));
    }

    public boolean canLaunchArm() {
        return arms > 0;
    }

    public int getArmCount() {
        return arms;
    }
}
