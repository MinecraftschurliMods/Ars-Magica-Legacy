package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Damage;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Dig;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.component.Effect;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.modifier.AbstractModifier;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Self;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.shape.Touch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.SPELL_PARTS;

public interface AMSpellParts {
    RegistryObject<Self>           SELF             = SPELL_PARTS.register("self",             Self::new);
    RegistryObject<Touch>          TOUCH            = SPELL_PARTS.register("touch",            Touch::new);

    RegistryObject<Damage>         FIRE_DAMAGE      = SPELL_PARTS.register("fire_damage",      () -> new Damage(caster -> DamageSource.IN_FIRE, 6));
    RegistryObject<Damage>         FROST_DAMAGE     = SPELL_PARTS.register("frost_damage",     () -> new Damage(caster -> DamageSource.FREEZE, 6));
    RegistryObject<Damage>         LIGHTNING_DAMAGE = SPELL_PARTS.register("lightning_damage", () -> new Damage(caster -> DamageSource.LIGHTNING_BOLT, 6));
    RegistryObject<Damage>         MAGIC_DAMAGE     = SPELL_PARTS.register("magic_damage",     () -> new Damage(caster -> DamageSource.indirectMagic(caster, null), 6));
    RegistryObject<Damage>         PHYSICAL_DAMAGE  = SPELL_PARTS.register("physical_damage",  () -> new Damage(caster -> caster instanceof Player player ? DamageSource.playerAttack(player) : DamageSource.mobAttack(caster), 6));

    RegistryObject<Effect>         ABSORPTION       = SPELL_PARTS.register("absorption",       () -> new Effect(new MobEffectInstance(MobEffects.ABSORPTION, 600)));
    RegistryObject<Effect>         BLINDNESS        = SPELL_PARTS.register("blindness",        () -> new Effect(new MobEffectInstance(MobEffects.BLINDNESS, 600)));
    RegistryObject<Effect>         HASTE            = SPELL_PARTS.register("haste",            () -> new Effect(new MobEffectInstance(MobEffects.DIG_SPEED, 600)));
    RegistryObject<Effect>         INVISIBILITY     = SPELL_PARTS.register("invisibility",     () -> new Effect(new MobEffectInstance(MobEffects.INVISIBILITY, 600)));
    RegistryObject<Effect>         JUMP_BOOST       = SPELL_PARTS.register("jump_boost",       () -> new Effect(new MobEffectInstance(MobEffects.JUMP, 600)));
    RegistryObject<Effect>         LEVITATION       = SPELL_PARTS.register("levitation",       () -> new Effect(new MobEffectInstance(MobEffects.LEVITATION, 600)));
    RegistryObject<Effect>         NAUSEA           = SPELL_PARTS.register("nausea",           () -> new Effect(new MobEffectInstance(MobEffects.CONFUSION, 600)));
    RegistryObject<Effect>         REGENERATION     = SPELL_PARTS.register("regeneration",     () -> new Effect(new MobEffectInstance(MobEffects.REGENERATION, 600)));
    RegistryObject<Effect>         SLOWNESS         = SPELL_PARTS.register("slowness",         () -> new Effect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600)));
    RegistryObject<Effect>         SLOW_FALLING     = SPELL_PARTS.register("slowfall",         () -> new Effect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600)));
    RegistryObject<Effect>         WATER_BREATHING  = SPELL_PARTS.register("water_breathing",  () -> new Effect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600)));
    // TODO astral_distortion, entangle, flight, fury, gravity_well, scramble_synapses, shrink, silence, swift_swim, true_sight, watery_grave

    RegistryObject<Dig>            DIG              = SPELL_PARTS.register("dig",              Dig::new);

    RegistryObject<ISpellModifier> MINING_POWER     = SPELL_PARTS.register("mining_power",     () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> FORTUNE          = SPELL_PARTS.register("fortune",          () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> SILK_TOUCH       = SPELL_PARTS.register("silk_touch",       () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> TARGET_NON_SOLID = SPELL_PARTS.register("target_non_solid", () -> new AbstractModifier() {});
    RegistryObject<ISpellModifier> RANGE            = SPELL_PARTS.register("range",            () -> new AbstractModifier() {});

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
