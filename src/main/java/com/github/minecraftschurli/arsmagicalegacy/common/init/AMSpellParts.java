package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Damage;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Dig;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Effect;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.modifier.AbstractModifier;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Rune;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Self;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Touch;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SPELL_PARTS;

public interface AMSpellParts {
    // TODO aoe, beam, chain, channel, projectile, rune, wall, wave, zone
    RegistryObject<Self>           SELF              = SPELL_PARTS.register("self",              Self::new);
    RegistryObject<Touch>          TOUCH             = SPELL_PARTS.register("touch",             Touch::new);
    RegistryObject<Rune>           RUNE              = SPELL_PARTS.register("rune",              Rune::new);
    // TODO contingencies: damage, death, fall, fire, health

    RegistryObject<Damage>         DROWNING_DAMAGE   = SPELL_PARTS.register("drowning_damage",   () -> new Damage(caster -> DamageSource.DROWN, 6));
    RegistryObject<Damage>         FIRE_DAMAGE       = SPELL_PARTS.register("fire_damage",       () -> new Damage(caster -> DamageSource.IN_FIRE, 6));
    RegistryObject<Damage>         FROST_DAMAGE      = SPELL_PARTS.register("frost_damage",      () -> new Damage(caster -> DamageSource.FREEZE, 6));
    RegistryObject<Damage>         LIGHTNING_DAMAGE  = SPELL_PARTS.register("lightning_damage",  () -> new Damage(caster -> DamageSource.LIGHTNING_BOLT, 6));
    RegistryObject<Damage>         MAGIC_DAMAGE      = SPELL_PARTS.register("magic_damage",      () -> new Damage(caster -> DamageSource.indirectMagic(caster, null), 6));
    RegistryObject<Damage>         PHYSICAL_DAMAGE   = SPELL_PARTS.register("physical_damage",   () -> new Damage(caster -> caster instanceof Player player ? DamageSource.playerAttack(player) : DamageSource.mobAttack(caster), 6));
    RegistryObject<Effect>         ABSORPTION        = SPELL_PARTS.register("absorption",        () -> new Effect(MobEffects.ABSORPTION, 600));
    RegistryObject<Effect>         BLINDNESS         = SPELL_PARTS.register("blindness",         () -> new Effect(MobEffects.BLINDNESS, 600));
    RegistryObject<Effect>         HASTE             = SPELL_PARTS.register("haste",             () -> new Effect(MobEffects.DIG_SPEED, 600));
    RegistryObject<Effect>         INVISIBILITY      = SPELL_PARTS.register("invisibility",      () -> new Effect(MobEffects.INVISIBILITY, 600));
    RegistryObject<Effect>         JUMP_BOOST        = SPELL_PARTS.register("jump_boost",        () -> new Effect(MobEffects.JUMP, 600));
    RegistryObject<Effect>         LEVITATION        = SPELL_PARTS.register("levitation",        () -> new Effect(MobEffects.LEVITATION, 600));
    RegistryObject<Effect>         NAUSEA            = SPELL_PARTS.register("nausea",            () -> new Effect(MobEffects.CONFUSION, 600));
    RegistryObject<Effect>         NIGHT_VISION      = SPELL_PARTS.register("night_vision",      () -> new Effect(MobEffects.NIGHT_VISION, 600));
    RegistryObject<Effect>         REGENERATION      = SPELL_PARTS.register("regeneration",      () -> new Effect(MobEffects.REGENERATION, 600));
    RegistryObject<Effect>         SLOWNESS          = SPELL_PARTS.register("slowness",          () -> new Effect(MobEffects.MOVEMENT_SLOWDOWN, 600));
    RegistryObject<Effect>         SLOW_FALLING      = SPELL_PARTS.register("slowfall",          () -> new Effect(MobEffects.SLOW_FALLING, 600));
    RegistryObject<Effect>         WATER_BREATHING   = SPELL_PARTS.register("water_breathing",   () -> new Effect(MobEffects.WATER_BREATHING, 600));
    RegistryObject<Effect>         ASTRAL_DISTORTION = SPELL_PARTS.register("astral_distortion", () -> new Effect(AMMobEffects.ASTRAL_DISTORTION, 600));
    RegistryObject<Effect>         ENTANGLE          = SPELL_PARTS.register("entangle",          () -> new Effect(AMMobEffects.ENTANGLE, 600));
    RegistryObject<Effect>         FLIGHT            = SPELL_PARTS.register("flight",            () -> new Effect(AMMobEffects.FLIGHT, 600));
    RegistryObject<Effect>         FURY              = SPELL_PARTS.register("fury",              () -> new Effect(AMMobEffects.FURY, 600));
    RegistryObject<Effect>         GRAVITY_WELL      = SPELL_PARTS.register("gravity_well",      () -> new Effect(AMMobEffects.GRAVITY_WELL, 600));
    RegistryObject<Effect>         SCRAMBLE_SYNAPSES = SPELL_PARTS.register("scramble_synapses", () -> new Effect(AMMobEffects.SCRAMBLE_SYNAPSES, 600));
    RegistryObject<Effect>         SHIELD            = SPELL_PARTS.register("shield",            () -> new Effect(AMMobEffects.SHIELD, 600));
    RegistryObject<Effect>         SHRINK            = SPELL_PARTS.register("shrink",            () -> new Effect(AMMobEffects.SHRINK, 600));
    RegistryObject<Effect>         SILENCE           = SPELL_PARTS.register("silence",           () -> new Effect(AMMobEffects.SILENCE, 600));
    RegistryObject<Effect>         SWIFT_SWIM        = SPELL_PARTS.register("swift_swim",        () -> new Effect(AMMobEffects.SWIFT_SWIM, 600));
    RegistryObject<Effect>         TEMPORAL_ANCHOR   = SPELL_PARTS.register("temporal_anchor",   () -> new Effect(AMMobEffects.TEMPORAL_ANCHOR, 600));
    RegistryObject<Effect>         TRUE_SIGHT        = SPELL_PARTS.register("true_sight",        () -> new Effect(AMMobEffects.TRUE_SIGHT, 600));
    RegistryObject<Effect>         WATERY_GRAVE      = SPELL_PARTS.register("watery_grave",      () -> new Effect(AMMobEffects.WATERY_GRAVE, 600));
    RegistryObject<Dig>            DIG               = SPELL_PARTS.register("dig",               Dig::new);
    // TODO attract, banish_rain, blink, blizzard, charm, create_water, daylight, disarm, dispel, divine_intervention, drought, ender_intervention, falling_star, fire_rain, fling, forge, grow, harvest_plants, heal, igniton, knockback, life_drain, life_tap, light, mana_blast, mana_drain, mana_link, mana_shield, mark, moonrise, place_block, plant, plow, random_teleport, recall, repel, rift, storm, summon, telekinesis, transplace, wizards_autumn

    // TODO bounce, damage, dismembering, duration, effect_power, gravity, healing, lunar, piercing, radius, rune_procs, solar, target_non_solid, velocity
    RegistryObject<ISpellModifier> MINING_POWER      = SPELL_PARTS.register("mining_power",      () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> PROSPERITY        = SPELL_PARTS.register("prosperity",        () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> RANGE             = SPELL_PARTS.register("range",             () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> SILK_TOUCH        = SPELL_PARTS.register("silk_touch",        () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> TARGET_NON_SOLID  = SPELL_PARTS.register("target_non_solid",  () -> new AbstractModifier() {});

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
