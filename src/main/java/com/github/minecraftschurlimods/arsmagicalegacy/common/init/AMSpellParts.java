package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.DefaultSpellPartStatModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.SpellPartStats;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component.*;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.GenericSpellModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.Lunar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.modifier.Solar;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.AoE;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Beam;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Chain;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Channel;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Projectile;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Rune;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Self;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Touch;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Wall;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Wave;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.shape.Zone;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.SPELL_PARTS;

@NonExtendable
public interface AMSpellParts {
    RegistryObject<AoE> AOE = SPELL_PARTS.register("aoe", AoE::new);
    RegistryObject<Beam> BEAM = SPELL_PARTS.register("beam", Beam::new);
    RegistryObject<Chain> CHAIN = SPELL_PARTS.register("chain", Chain::new);
    RegistryObject<Channel> CHANNEL = SPELL_PARTS.register("channel", Channel::new);
    RegistryObject<Projectile> PROJECTILE = SPELL_PARTS.register("projectile", Projectile::new);
    RegistryObject<Rune> RUNE = SPELL_PARTS.register("rune", Rune::new);
    RegistryObject<Self> SELF = SPELL_PARTS.register("self", Self::new);
    RegistryObject<Touch> TOUCH = SPELL_PARTS.register("touch", Touch::new);
    RegistryObject<Wall> WALL = SPELL_PARTS.register("wall", Wall::new);
    RegistryObject<Wave> WAVE = SPELL_PARTS.register("wave", Wave::new);
    RegistryObject<Zone> ZONE = SPELL_PARTS.register("zone", Zone::new);
    // TODO contingencies: damage, death, fall, fire, health

    RegistryObject<Damage> DROWNING_DAMAGE = SPELL_PARTS.register("drowning_damage", () -> new Damage(e -> DamageSource.DROWN, Config.SERVER.DAMAGE.get(), e -> e instanceof LivingEntity l && l.canBreatheUnderwater()));
    RegistryObject<Damage> FIRE_DAMAGE = SPELL_PARTS.register("fire_damage", () -> new Damage(e -> DamageSource.IN_FIRE, Config.SERVER.DAMAGE.get(), Entity::fireImmune));
    RegistryObject<Damage> FROST_DAMAGE = SPELL_PARTS.register("frost_damage", () -> new Damage(e -> DamageSource.FREEZE, Config.SERVER.DAMAGE.get(), e -> !e.canFreeze()));
    RegistryObject<Damage> LIGHTNING_DAMAGE = SPELL_PARTS.register("lightning_damage", () -> new Damage(e -> DamageSource.LIGHTNING_BOLT, Config.SERVER.DAMAGE.get()));
    RegistryObject<Damage> MAGIC_DAMAGE = SPELL_PARTS.register("magic_damage", () -> new Damage(e -> DamageSource.indirectMagic(e, null), e -> (float) (e.isInvertedHealAndHarm() ? -Config.SERVER.DAMAGE.get() : Config.SERVER.DAMAGE.get())));
    RegistryObject<Damage> PHYSICAL_DAMAGE = SPELL_PARTS.register("physical_damage", () -> new Damage(e -> e instanceof Player p ? DamageSource.playerAttack(p) : DamageSource.mobAttack(e), Config.SERVER.DAMAGE.get()));
    RegistryObject<Effect> ABSORPTION = SPELL_PARTS.register("absorption", () -> new Effect(MobEffects.ABSORPTION));
    RegistryObject<Effect> BLINDNESS = SPELL_PARTS.register("blindness", () -> new Effect(MobEffects.BLINDNESS));
    RegistryObject<Effect> HASTE = SPELL_PARTS.register("haste", () -> new Effect(MobEffects.DIG_SPEED));
    RegistryObject<Effect> INVISIBILITY = SPELL_PARTS.register("invisibility", () -> new Effect(MobEffects.INVISIBILITY));
    RegistryObject<Effect> JUMP_BOOST = SPELL_PARTS.register("jump_boost", () -> new Effect(MobEffects.JUMP));
    RegistryObject<Effect> LEVITATION = SPELL_PARTS.register("levitation", () -> new Effect(MobEffects.LEVITATION));
    RegistryObject<Effect> NAUSEA = SPELL_PARTS.register("nausea", () -> new Effect(MobEffects.CONFUSION));
    RegistryObject<Effect> NIGHT_VISION = SPELL_PARTS.register("night_vision", () -> new Effect(MobEffects.NIGHT_VISION));
    RegistryObject<Effect> REGENERATION = SPELL_PARTS.register("regeneration", () -> new Effect(MobEffects.REGENERATION));
    RegistryObject<Effect> SLOWNESS = SPELL_PARTS.register("slowness", () -> new Effect(MobEffects.MOVEMENT_SLOWDOWN));
    RegistryObject<Effect> SLOW_FALLING = SPELL_PARTS.register("slow_falling", () -> new Effect(MobEffects.SLOW_FALLING));
    RegistryObject<Effect> WATER_BREATHING = SPELL_PARTS.register("water_breathing", () -> new Effect(MobEffects.WATER_BREATHING));
    RegistryObject<Effect> AGILITY = SPELL_PARTS.register("agility", () -> new Effect(AMMobEffects.AGILITY));
    RegistryObject<Effect> ASTRAL_DISTORTION = SPELL_PARTS.register("astral_distortion", () -> new Effect(AMMobEffects.ASTRAL_DISTORTION));
    RegistryObject<Effect> ENTANGLE = SPELL_PARTS.register("entangle", () -> new Effect(AMMobEffects.ENTANGLE));
    RegistryObject<Effect> FLIGHT = SPELL_PARTS.register("flight", () -> new Effect(AMMobEffects.FLIGHT));
    RegistryObject<Effect> FROST = SPELL_PARTS.register("frost", () -> new Effect(AMMobEffects.FROST));
    RegistryObject<Effect> FURY = SPELL_PARTS.register("fury", () -> new Effect(AMMobEffects.FURY));
    RegistryObject<Effect> GRAVITY_WELL = SPELL_PARTS.register("gravity_well", () -> new Effect(AMMobEffects.GRAVITY_WELL));
    RegistryObject<Effect> REFLECT = SPELL_PARTS.register("reflect", () -> new Effect(AMMobEffects.REFLECT));
    RegistryObject<Effect> SCRAMBLE_SYNAPSES = SPELL_PARTS.register("scramble_synapses", () -> new Effect(AMMobEffects.SCRAMBLE_SYNAPSES));
    RegistryObject<Effect> SHIELD = SPELL_PARTS.register("shield", () -> new Effect(AMMobEffects.SHIELD));
    RegistryObject<Effect> SHRINK = SPELL_PARTS.register("shrink", () -> new Effect(AMMobEffects.SHRINK));
    RegistryObject<Effect> SILENCE = SPELL_PARTS.register("silence", () -> new Effect(AMMobEffects.SILENCE));
    RegistryObject<Effect> SWIFT_SWIM = SPELL_PARTS.register("swift_swim", () -> new Effect(AMMobEffects.SWIFT_SWIM));
    RegistryObject<Effect> TEMPORAL_ANCHOR = SPELL_PARTS.register("temporal_anchor", () -> new Effect(AMMobEffects.TEMPORAL_ANCHOR));
    RegistryObject<Effect> TRUE_SIGHT = SPELL_PARTS.register("true_sight", () -> new Effect(AMMobEffects.TRUE_SIGHT));
    RegistryObject<Effect> WATERY_GRAVE = SPELL_PARTS.register("watery_grave", () -> new Effect(AMMobEffects.WATERY_GRAVE));
    RegistryObject<Attract> ATTRACT = SPELL_PARTS.register("attract", Attract::new);
    RegistryObject<BanishRain> BANISH_RAIN = SPELL_PARTS.register("banish_rain", BanishRain::new);
    RegistryObject<Blink> BLINK = SPELL_PARTS.register("blink", Blink::new);
    RegistryObject<Blizzard> BLIZZARD = SPELL_PARTS.register("blizzard", Blizzard::new);
    RegistryObject<Charm> CHARM = SPELL_PARTS.register("charm", Charm::new);
    RegistryObject<CreateWater> CREATE_WATER = SPELL_PARTS.register("create_water", CreateWater::new);
    RegistryObject<Daylight> DAYLIGHT = SPELL_PARTS.register("daylight", Daylight::new);
    RegistryObject<Dig> DIG = SPELL_PARTS.register("dig", Dig::new);
    RegistryObject<Disarm> DISARM = SPELL_PARTS.register("disarm", Disarm::new);
    RegistryObject<Dispel> DISPEL = SPELL_PARTS.register("dispel", Dispel::new);
    RegistryObject<DivineIntervention> DIVINE_INTERVENTION = SPELL_PARTS.register("divine_intervention", DivineIntervention::new);
    RegistryObject<Drought> DROUGHT = SPELL_PARTS.register("drought", Drought::new);
    RegistryObject<EnderIntervention> ENDER_INTERVENTION = SPELL_PARTS.register("ender_intervention", EnderIntervention::new);
    RegistryObject<FallingStar> FALLING_STAR = SPELL_PARTS.register("falling_star", FallingStar::new);
    RegistryObject<FireRain> FIRE_RAIN = SPELL_PARTS.register("fire_rain", FireRain::new);
    RegistryObject<Fling> FLING = SPELL_PARTS.register("fling", Fling::new);
    RegistryObject<Forge> FORGE = SPELL_PARTS.register("forge", Forge::new);
    RegistryObject<Grow> GROW = SPELL_PARTS.register("grow", Grow::new);
    RegistryObject<Harvest> HARVEST = SPELL_PARTS.register("harvest", Harvest::new);
    RegistryObject<Heal> HEAL = SPELL_PARTS.register("heal", Heal::new);
    RegistryObject<Ignition> IGNITION = SPELL_PARTS.register("ignition", Ignition::new);
    RegistryObject<Knockback> KNOCKBACK = SPELL_PARTS.register("knockback", Knockback::new);
    RegistryObject<LifeDrain> LIFE_DRAIN = SPELL_PARTS.register("life_drain", LifeDrain::new);
    RegistryObject<LifeTap> LIFE_TAP = SPELL_PARTS.register("life_tap", LifeTap::new);
    RegistryObject<Light> LIGHT = SPELL_PARTS.register("light", Light::new);
    RegistryObject<ManaBlast> MANA_BLAST = SPELL_PARTS.register("mana_blast", ManaBlast::new);
    RegistryObject<ManaDrain> MANA_DRAIN = SPELL_PARTS.register("mana_drain", ManaDrain::new);
    RegistryObject<ManaShield> MANA_SHIELD = SPELL_PARTS.register("mana_shield", ManaShield::new);
    RegistryObject<MeltArmor> MELT_ARMOR = SPELL_PARTS.register("melt_armor", MeltArmor::new);
    RegistryObject<Moonrise> MOONRISE = SPELL_PARTS.register("moonrise", Moonrise::new);
    RegistryObject<PlaceBlock> PLACE_BLOCK = SPELL_PARTS.register("place_block", PlaceBlock::new);
    RegistryObject<Plant> PLANT = SPELL_PARTS.register("plant", Plant::new);
    RegistryObject<Plow> PLOW = SPELL_PARTS.register("plow", Plow::new);
    RegistryObject<RandomTeleport> RANDOM_TELEPORT = SPELL_PARTS.register("random_teleport", RandomTeleport::new);
    RegistryObject<Recall> RECALL = SPELL_PARTS.register("recall", Recall::new);
    RegistryObject<Repel> REPEL = SPELL_PARTS.register("repel", Repel::new);
    RegistryObject<Rift> RIFT = SPELL_PARTS.register("rift", Rift::new);
    RegistryObject<Storm> STORM = SPELL_PARTS.register("storm", Storm::new);
    RegistryObject<Summon> SUMMON = SPELL_PARTS.register("summon", Summon::new);
    RegistryObject<Telekinesis> TELEKINESIS = SPELL_PARTS.register("telekinesis", Telekinesis::new);
    RegistryObject<Transplace> TRANSPLACE = SPELL_PARTS.register("transplace", Transplace::new);
    RegistryObject<WizardsAutumn> WIZARDS_AUTUMN = SPELL_PARTS.register("wizards_autumn", WizardsAutumn::new);

    RegistryObject<ISpellModifier> BOUNCE = SPELL_PARTS.register("bounce", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.BOUNCE, DefaultSpellPartStatModifier.add(2)));
    RegistryObject<ISpellModifier> DAMAGE = SPELL_PARTS.register("damage", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.DAMAGE, DefaultSpellPartStatModifier.add(2f)));
    RegistryObject<ISpellModifier> DISMEMBERING = SPELL_PARTS.register("dismembering", GenericSpellModifier::new);
    RegistryObject<ISpellModifier> DURATION = SPELL_PARTS.register("duration", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.DURATION, DefaultSpellPartStatModifier.multiply(2f)));
    RegistryObject<ISpellModifier> EFFECT_POWER = SPELL_PARTS.register("effect_power", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.POWER, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> GRAVITY = SPELL_PARTS.register("gravity", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.GRAVITY, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> HEALING = SPELL_PARTS.register("healing", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.HEALING, DefaultSpellPartStatModifier.multiply(2)));
    RegistryObject<ISpellModifier> LUNAR = SPELL_PARTS.register("lunar", Lunar::new);
    RegistryObject<ISpellModifier> MINING_POWER = SPELL_PARTS.register("mining_power", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.MINING_TIER, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> PIERCING = SPELL_PARTS.register("piercing", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.PIERCING, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> PROSPERITY = SPELL_PARTS.register("prosperity", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.FORTUNE, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> RANGE = SPELL_PARTS.register("range", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.RANGE, DefaultSpellPartStatModifier.multiply(4)));
    RegistryObject<ISpellModifier> RUNE_PROCS = SPELL_PARTS.register("rune_procs", GenericSpellModifier::new);
    RegistryObject<ISpellModifier> SILK_TOUCH = SPELL_PARTS.register("silk_touch", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.SILKTOUCH, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> SOLAR = SPELL_PARTS.register("solar", Solar::new);
    RegistryObject<ISpellModifier> TARGET_NON_SOLID = SPELL_PARTS.register("target_non_solid", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.TARGET_NON_SOLID, DefaultSpellPartStatModifier.COUNTING));
    RegistryObject<ISpellModifier> VELOCITY = SPELL_PARTS.register("velocity", () -> new GenericSpellModifier().addStatModifier(SpellPartStats.SPEED, DefaultSpellPartStatModifier.addMultipliedBase(0.5f)));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
