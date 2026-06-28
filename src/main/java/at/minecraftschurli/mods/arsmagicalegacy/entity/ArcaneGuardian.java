package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ExecuteBossSpellGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ExecuteRandomSpellGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.HealGoal;
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

import java.util.List;

public class ArcaneGuardian extends AbstractBoss {
    public ArcaneGuardian(EntityType<? extends ArcaneGuardian> type, Level level) {
        super(type, level, BossEvent.BossBarColor.PINK, AMTags.DamageTypes.ARCANE_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.ARCANE_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.ARCANE_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 120)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 2000)
            .add(AMAttributes.MAX_BURNOUT, 2000);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.ARCANE_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.ARCANE_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.ARCANE_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.ARCANE_GUARDIAN_HURT.get();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void registerGoals() {
        super.registerGoals();
        Registry<Spell> registry = registryAccess().lookupOrThrow(AMRegistries.Keys.SPELL_PREFAB);
        goalSelector.addGoal(1, new HealGoal<>(this));
        goalSelector.addGoal(1, new ExecuteRandomSpellGoal<>(this, List.of(
            registry.getValue(ArsMagicaApi.id("water_bolt")),
            registry.getValue(ArsMagicaApi.id("fire_bolt")),
            registry.getValue(ArsMagicaApi.id("earth_bolt")),
            registry.getValue(ArsMagicaApi.id("lightning_bolt")),
            registry.getValue(ArsMagicaApi.id("ice_bolt")),
            registry.getValue(ArsMagicaApi.id("arcane_bolt"))
        ), 30));
        goalSelector.addGoal(1, new ExecuteRandomSpellGoal<>(this, List.of(
            registry.getValue(ArsMagicaApi.id("strong_water_bolt")),
            registry.getValue(ArsMagicaApi.id("strong_fire_bolt")),
            registry.getValue(ArsMagicaApi.id("strong_earth_bolt")),
            registry.getValue(ArsMagicaApi.id("strong_lightning_bolt")),
            registry.getValue(ArsMagicaApi.id("strong_ice_bolt")),
            registry.getValue(ArsMagicaApi.id("strong_arcane_bolt"))
        ), 30));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("blink")), 30));
        goalSelector.addGoal(1, new ExecuteBossSpellGoal<>(this, registry.getValue(ArsMagicaApi.id("debuff")), 30));
    }
}
