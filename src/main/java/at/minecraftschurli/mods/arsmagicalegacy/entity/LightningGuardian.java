package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ExecuteBossSpellGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.LightningRodGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StaticGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class LightningGuardian extends AbstractBoss {
    public LightningGuardian(EntityType<? extends LightningGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.YELLOW, AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 250)
            .add(Attributes.ARMOR, 20)
            .add(AMAttributes.MAX_MANA, 4000)
            .add(AMAttributes.MAX_BURNOUT, 4000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.LIGHTNING_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.LIGHTNING_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.LIGHTNING_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.LIGHTNING_GUARDIAN_HURT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        Registry<Spell> registry = registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
        goalSelector.addGoal(1, new LightningRodGoal(this));
        goalSelector.addGoal(1, new StaticGoal(this));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("lightning_bolt")), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("strong_lightning_bolt")), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("area_lightning")), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("lightning_rune")), 10));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("scramble_synapses")), 10));
    }

    @Override
    public void setAction(Action action) {
        super.setAction(action);
        setNoGravity(action == Action.LONG_CAST);
    }

    @Override
    public void aiStep() {
        if (level().isClientSide()) {
            // TODO particles
        }
        super.aiStep();
    }
}
