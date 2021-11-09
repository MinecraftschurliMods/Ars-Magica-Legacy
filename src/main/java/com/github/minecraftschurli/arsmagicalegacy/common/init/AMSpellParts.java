package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Damage;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Potion;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Self;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SPELL_PARTS;

public interface AMSpellParts {
    RegistryObject<Self> SELF               = SPELL_PARTS.register("self", Self::new);
    RegistryObject<Damage> FIRE_DAMAGE      = SPELL_PARTS.register("fire_damage",      () -> new Damage(caster -> DamageSource.IN_FIRE, 6));
    RegistryObject<Damage> FROST_DAMAGE     = SPELL_PARTS.register("frost_damage",     () -> new Damage(caster -> DamageSource.FREEZE, 6));
    RegistryObject<Damage> LIGHTNING_DAMAGE = SPELL_PARTS.register("lightning_damage", () -> new Damage(caster -> DamageSource.LIGHTNING_BOLT, 6));
    RegistryObject<Damage> MAGIC_DAMAGE     = SPELL_PARTS.register("magic_damage",     () -> new Damage(caster -> DamageSource.MAGIC, 6));
    RegistryObject<Damage> PHYSICAL_DAMAGE  = SPELL_PARTS.register("physical_damage",  () -> new Damage(caster -> caster instanceof Player ? DamageSource.playerAttack((Player) caster) : DamageSource.mobAttack(caster), 6));
    // TODO astral_distortion, entangle, flight, fury, gravity_well, scramble_synapses, shrink, silence, swift_swim, true_sight, watery_grave
    RegistryObject<Potion> ABSORPTION       = SPELL_PARTS.register("absorption",       () -> new Potion(new MobEffectInstance(MobEffects.ABSORPTION, 600)));
    RegistryObject<Potion> BLINDNESS        = SPELL_PARTS.register("blindness",        () -> new Potion(new MobEffectInstance(MobEffects.BLINDNESS, 600)));
    RegistryObject<Potion> HASTE            = SPELL_PARTS.register("haste",            () -> new Potion(new MobEffectInstance(MobEffects.DIG_SPEED, 600)));
    RegistryObject<Potion> INVISIBILITY     = SPELL_PARTS.register("invisibility",     () -> new Potion(new MobEffectInstance(MobEffects.INVISIBILITY, 600)));
    RegistryObject<Potion> JUMP_BOOST       = SPELL_PARTS.register("jump_boost",       () -> new Potion(new MobEffectInstance(MobEffects.JUMP, 600)));
    RegistryObject<Potion> LEVITATION       = SPELL_PARTS.register("levitation",       () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 600)));
    RegistryObject<Potion> NAUSEA           = SPELL_PARTS.register("nausea",           () -> new Potion(new MobEffectInstance(MobEffects.CONFUSION, 600)));
    RegistryObject<Potion> REGENERATION     = SPELL_PARTS.register("regeneration",     () -> new Potion(new MobEffectInstance(MobEffects.REGENERATION, 600)));
    RegistryObject<Potion> SLOWNESS         = SPELL_PARTS.register("slowness",         () -> new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600)));
    RegistryObject<Potion> SLOW_FALLING     = SPELL_PARTS.register("slowfall",         () -> new Potion(new MobEffectInstance(MobEffects.SLOW_FALLING, 600)));
    RegistryObject<Potion> WATER_BREATHING  = SPELL_PARTS.register("water_breathing",  () -> new Potion(new MobEffectInstance(MobEffects.WATER_BREATHING, 600)));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
