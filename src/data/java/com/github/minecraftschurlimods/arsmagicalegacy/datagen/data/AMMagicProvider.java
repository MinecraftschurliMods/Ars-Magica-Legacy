package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.AttributeAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.BurnoutCostModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.DamageModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EffectResistanceAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.EndermanPumpkinAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.ExtraDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FirePunchAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FrostPunchAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.FrostWalkerAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.JumpBoostAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.KillEffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.LightHealthModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.NetherDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.SpellCastEffectAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.ThornsAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.WaterDamageAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ability.WaterHealthModifierAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellGrammar;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellPartData;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEtheriumTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMagic;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMParticles;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.LearnSkillRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.SetBlockRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect.SpawnEntityRitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.BiomeTagRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.DimensionRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.EnvironmentAttributeRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.HeightRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.IngredientRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement.StructureRitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.DroppedItemRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.GameEventRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.KillEntityRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SetBlockStateRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.trigger.SpellCastRitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.EtheriumSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.ItemSpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.LinearAttributeModifier;
import net.minecraft.advancements.criterion.EntityFlagsPredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.ReplaceDisk;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.MoonPhase;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class AMMagicProvider {
    public static final Map<ResourceKey<Ability>, PatchouliAbilityData> PATCHOULI_ABILITY_DATA = new HashMap<>();

    public static void addAffinities(BootstrapContext<Affinity> bootstrap) {
        HolderOwner<Affinity> owner = new HolderOwner<>() {
            @Override
            public boolean canSerializeIn(HolderOwner<Affinity> owner) {
                return true;
            }
        };
        bootstrap.register(Affinity.NONE, new Affinity(Holder.Reference.createStandAlone(owner, Affinity.NONE), List.of(), List.of(), List.of(), 0, -1, Optional.of(AMSounds.CAST_NONE), Optional.empty(), AMParticles.NONE_HAND.get()));
        // @formatter:off
        Holder.Reference<Affinity> water     = Holder.Reference.createStandAlone(owner, AMMagic.WATER);
        Holder.Reference<Affinity> fire      = Holder.Reference.createStandAlone(owner, AMMagic.FIRE);
        Holder.Reference<Affinity> earth     = Holder.Reference.createStandAlone(owner, AMMagic.EARTH);
        Holder.Reference<Affinity> air       = Holder.Reference.createStandAlone(owner, AMMagic.AIR);
        Holder.Reference<Affinity> ice       = Holder.Reference.createStandAlone(owner, AMMagic.ICE);
        Holder.Reference<Affinity> lightning = Holder.Reference.createStandAlone(owner, AMMagic.LIGHTNING);
        Holder.Reference<Affinity> nature    = Holder.Reference.createStandAlone(owner, AMMagic.NATURE);
        Holder.Reference<Affinity> life      = Holder.Reference.createStandAlone(owner, AMMagic.LIFE);
        Holder.Reference<Affinity> arcane    = Holder.Reference.createStandAlone(owner, AMMagic.ARCANE);
        Holder.Reference<Affinity> ender     = Holder.Reference.createStandAlone(owner, AMMagic.ENDER);
        bootstrap.register(AMMagic.WATER,     new Affinity(fire,      List.of(lightning, ender), List.of(air, arcane),      List.of(ice, nature),      0x0b5cef,  8, AMSounds.CAST_WATER,     AMSounds.LOOP_WATER,     AMParticles.WATER_HAND.get()));
        bootstrap.register(AMMagic.FIRE,      new Affinity(water,     List.of(ice, nature),      List.of(earth, life),      List.of(lightning, ender), 0xef260b,  3, AMSounds.CAST_FIRE,      AMSounds.LOOP_FIRE,      AMParticles.FIRE_HAND.get()));
        bootstrap.register(AMMagic.EARTH,     new Affinity(air,       List.of(lightning, life),  List.of(fire, nature),     List.of(ice, arcane),      0x61330b, 10, AMSounds.CAST_EARTH,     AMSounds.LOOP_EARTH,     AMParticles.EARTH_HAND.get()));
        bootstrap.register(AMMagic.AIR,       new Affinity(earth,     List.of(ice, arcane),      List.of(water, ender),     List.of(lightning, life),  0x777777,  5, AMSounds.CAST_AIR,       AMSounds.LOOP_AIR,       AMParticles.AIR_HAND.get()));
        bootstrap.register(AMMagic.ICE,       new Affinity(lightning, List.of(fire, air),        List.of(life, ender),      List.of(water, earth),     0xd3e8fc,  9, AMSounds.CAST_ICE,       AMSounds.LOOP_ICE,       AMParticles.ICE_HAND.get()));
        bootstrap.register(AMMagic.LIGHTNING, new Affinity(ice,       List.of(water, earth),     List.of(nature, arcane),   List.of(fire, air),        0xdece19,  4, AMSounds.CAST_LIGHTNING, AMSounds.LOOP_LIGHTNING, AMParticles.LIGHTNING_HAND.get()));
        bootstrap.register(AMMagic.NATURE,    new Affinity(ender,     List.of(fire, arcane),     List.of(earth, lightning), List.of(water, life),      0x228718,  7, AMSounds.CAST_NATURE,    AMSounds.LOOP_NATURE,    AMParticles.NATURE_HAND.get()));
        bootstrap.register(AMMagic.LIFE,      new Affinity(arcane,    List.of(earth, ender),     List.of(fire, ice),        List.of(air, nature),      0x34e122,  6, AMSounds.CAST_LIFE,      AMSounds.LOOP_LIFE,      AMParticles.LIFE_HAND.get()));
        bootstrap.register(AMMagic.ARCANE,    new Affinity(life,      List.of(air, nature),      List.of(water, lightning), List.of(earth, ender),     0xb935cd,  1, AMSounds.CAST_ARCANE,    AMSounds.LOOP_ARCANE,    AMParticles.ARCANE_HAND.get()));
        bootstrap.register(AMMagic.ENDER,     new Affinity(nature,    List.of(water, life),      List.of(air, ice),         List.of(fire, arcane),     0x3f043d,  2, AMSounds.CAST_ENDER,     AMSounds.LOOP_ENDER,     AMParticles.ENDER_HAND.get()));
        // @formatter:on
    }

    public static void addAbilities(BootstrapContext<Ability> bootstrap) {
        HolderGetter<DamageType> damageTypes = bootstrap.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<EntityType<?>> entityTypes = bootstrap.lookup(Registries.ENTITY_TYPE);
        // @formatter:off
        ability(bootstrap, AMAbilities.SWIM_SPEED,             AMMagic.WATER,     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(NeoForgeMod.SWIM_SPEED, new LinearAttributeModifier(AMAbilities.SWIM_SPEED.identifier(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        ability(bootstrap, AMAbilities.ENDER_THORNS,           AMMagic.WATER,     MinMaxBounds.Doubles.atLeast(1), new ThornsAbilityEffect(1, 1, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY))));
        ability(bootstrap, AMAbilities.NETHER_DAMAGE_WATER,    AMMagic.WATER,     MinMaxBounds.Doubles.between(0.5, 1), true, new NetherDamageAbilityEffect(0, 0.25));
        ability(bootstrap, AMAbilities.FIRE_RESISTANCE,        AMMagic.FIRE,      MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY), 1, 0.5));
        ability(bootstrap, AMAbilities.FIRE_PUNCH,             AMMagic.FIRE,      MinMaxBounds.Doubles.atLeast(1), new FirePunchAbilityEffect(100, 100));
        ability(bootstrap, AMAbilities.WATER_DAMAGE_FIRE,      AMMagic.FIRE,      MinMaxBounds.Doubles.between(0.5, 1), true, new WaterDamageAbilityEffect(0, 0.25));
        ability(bootstrap, AMAbilities.RESISTANCE,             AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY), 1, 0.5));
        ability(bootstrap, AMAbilities.HASTE,                  AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.ATTACK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.identifier(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), Attributes.BLOCK_BREAK_SPEED, new LinearAttributeModifier(AMAbilities.HASTE.identifier(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        ability(bootstrap, AMAbilities.FALL_DAMAGE,            AMMagic.EARTH,     MinMaxBounds.Doubles.between(0.5, 1), true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY), 1, 1.5));
        ability(bootstrap, AMAbilities.JUMP_BOOST,             AMMagic.AIR,       MinMaxBounds.Doubles.between(0.01, 1), new JumpBoostAbilityEffect(0, 0.5));
        ability(bootstrap, AMAbilities.FEATHER_FALLING,        AMMagic.AIR,       MinMaxBounds.Doubles.between(0.01, 1), new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY), 1, 0.5));
        ability(bootstrap, AMAbilities.GRAVITY,                AMMagic.AIR,       MinMaxBounds.Doubles.between(0.5, 1), true, new AttributeAbilityEffect(Map.of(Attributes.GRAVITY, new LinearAttributeModifier(AMAbilities.GRAVITY.identifier(), 0, 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))));
        ability(bootstrap, AMAbilities.FROST_PUNCH,            AMMagic.ICE,       MinMaxBounds.Doubles.between(0.01, 1), new FrostPunchAbilityEffect(0, 100));
        ability(bootstrap, AMAbilities.FROST_WALKER,           AMMagic.ICE,       MinMaxBounds.Doubles.atLeast(1), new FrostWalkerAbilityEffect(1, 1, new ReplaceDisk(
            new LevelBasedValue.Clamped(LevelBasedValue.perLevel(3, 1), 0, 16),
            LevelBasedValue.constant(1),
            new Vec3i(0, -1, 0),
            Optional.of(BlockPredicate.allOf(BlockPredicate.matchesTag(new Vec3i(0, 1, 0), BlockTags.AIR), BlockPredicate.matchesBlocks(Blocks.WATER), BlockPredicate.matchesFluids(Fluids.WATER), BlockPredicate.unobstructed())),
            BlockStateProvider.simple(Blocks.FROSTED_ICE),
            Optional.of(GameEvent.BLOCK_PLACE))));
        ability(bootstrap, AMAbilities.SLOWNESS,               AMMagic.ICE,       MinMaxBounds.Doubles.between(0.5, 1), true, new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SLOWNESS.identifier(), 0, -0.05, AttributeModifier.Operation.ADD_VALUE))));
        ability(bootstrap, AMAbilities.SPEED,                  AMMagic.LIGHTNING, MinMaxBounds.Doubles.between(0.01, 1), new AttributeAbilityEffect(Map.of(Attributes.MOVEMENT_SPEED, new LinearAttributeModifier(AMAbilities.SPEED.identifier(), 0, 0.05, AttributeModifier.Operation.ADD_VALUE))));
        ability(bootstrap, AMAbilities.STEP_ASSIST,            AMMagic.LIGHTNING, MinMaxBounds.Doubles.atLeast(1), new AttributeAbilityEffect(Map.of(Attributes.STEP_HEIGHT, new LinearAttributeModifier(AMAbilities.STEP_ASSIST.identifier(), 0, 0.4, AttributeModifier.Operation.ADD_VALUE))));
        ability(bootstrap, AMAbilities.WATER_DAMAGE_LIGHTNING, AMMagic.LIGHTNING, MinMaxBounds.Doubles.between(0.5, 1), true, new WaterDamageAbilityEffect(0, 0.25));
        ability(bootstrap, AMAbilities.THORNS,                 AMMagic.NATURE,    MinMaxBounds.Doubles.between(0.01, 1), new ThornsAbilityEffect(0, 0.5, Optional.empty()));
        ability(bootstrap, AMAbilities.SATURATION,             AMMagic.NATURE,    MinMaxBounds.Doubles.atLeast(1), new EffectAbilityEffect(MobEffects.SATURATION, 10, 0, false));
        ability(bootstrap, AMAbilities.NETHER_DAMAGE_NATURE,   AMMagic.NATURE,    MinMaxBounds.Doubles.between(0.5, 1), true, new NetherDamageAbilityEffect(0, 0.25));
        ability(bootstrap, AMAbilities.SMITE,                  AMMagic.LIFE,      MinMaxBounds.Doubles.between(0.01, 1), new ExtraDamageAbilityEffect(0, 4, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY))));
        ability(bootstrap, AMAbilities.REGENERATION,           AMMagic.LIFE,      MinMaxBounds.Doubles.atLeast(1), new EffectAbilityEffect(MobEffects.REGENERATION, 10, 0, false));
        ability(bootstrap, AMAbilities.NAUSEA,                 AMMagic.LIFE,      MinMaxBounds.Doubles.between(0.5, 1), true, new KillEffectAbilityEffect(MobEffects.NAUSEA, 0, 600, 0, false, Optional.of(entityTypes.getOrThrow(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY))));
        ability(bootstrap, AMAbilities.BURNOUT_REDUCTION,      AMMagic.ARCANE,    MinMaxBounds.Doubles.between(0.01, 1), new BurnoutCostModifierAbilityEffect(1, 0.5));
        ability(bootstrap, AMAbilities.CLARITY,                AMMagic.ARCANE,    MinMaxBounds.Doubles.atLeast(1), new SpellCastEffectAbilityEffect(AMMobEffects.CLARITY, 1200, 0, true, 0.5));
        ability(bootstrap, AMAbilities.MAGIC_DAMAGE,           AMMagic.ARCANE,    MinMaxBounds.Doubles.between(0.5, 1), true, new DamageModifierAbilityEffect(damageTypes.getOrThrow(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY), 1, 1.5));
        ability(bootstrap, AMAbilities.POISON_RESISTANCE,      AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 1),  new EffectResistanceAbilityEffect(List.of(MobEffects.POISON)));
        ability(bootstrap, AMAbilities.NIGHT_VISION,           AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 1),  new EffectAbilityEffect(MobEffects.NIGHT_VISION, 210, 0, false));
        ability(bootstrap, AMAbilities.ENDERMAN_PUMPKIN,       AMMagic.ENDER,     MinMaxBounds.Doubles.atLeast(1), EndermanPumpkinAbilityEffect.INSTANCE);
        ability(bootstrap, AMAbilities.LIGHT_HEALTH_REDUCTION, AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 0.99), true, new LightHealthModifierAbilityEffect(0, -0.2, 10));
        ability(bootstrap, AMAbilities.WATER_HEALTH_REDUCTION, AMMagic.ENDER,     MinMaxBounds.Doubles.between(0.5, 0.99), true, new WaterHealthModifierAbilityEffect(0, -0.2));
        // @formatter:on
    }

    public static void addOcculusTabs(BootstrapContext<OcculusTab> bootstrap) {
        bootstrap.register(AMMagic.OFFENSE, new OcculusTab(368, 320, 85, 0, 0, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.DEFENSE, new OcculusTab(320, 368, 38, 0, 1, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.UTILITY, new OcculusTab(320, 368, 38, 0, 2, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.TALENT, new OcculusTab(224, 196, 14, 0, 3, ArsMagicaApi.id("skill_tree")));
        bootstrap.register(AMMagic.AFFINITY, new OcculusTab(196, 196, 0, 0, 4, ArsMagicaApi.id("affinity")));
    }

    public static void addSkillPoints(BootstrapContext<SkillPoint> bootstrap) {
        bootstrap.register(AMMagic.BLUE_POINT, new SkillPoint(0x0000ff, 0, 1));
        bootstrap.register(AMMagic.GREEN_POINT, new SkillPoint(0x00ff00, 10, 2));
        bootstrap.register(AMMagic.RED_POINT, new SkillPoint(0xff0000, 20, 3));
    }

    @SuppressWarnings("unused")
    public static void addSkills(BootstrapContext<Skill> bootstrap) {
        // @formatter:off
        Holder<Skill> projectile       = skill(bootstrap, AMSpells.PROJECTILE,        AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168,  24);
        Holder<Skill> bounce           = skill(bootstrap, AMSpells.BOUNCE,            AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216,  24, projectile);
        Holder<Skill> gravity          = skill(bootstrap, AMSpells.GRAVITY,           AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120,  24, projectile);
        Holder<Skill> physicalDamage   = skill(bootstrap, AMSpells.PHYSICAL_DAMAGE,   AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168,  72, projectile);
        Holder<Skill> fireDamage       = skill(bootstrap, AMSpells.FIRE_DAMAGE,       AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120,  72, physicalDamage);
        Holder<Skill> frostDamage      = skill(bootstrap, AMSpells.FROST_DAMAGE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216,  72, physicalDamage);
        Holder<Skill> lightningDamage  = skill(bootstrap, AMSpells.LIGHTNING_DAMAGE,  AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120, 120, physicalDamage);
        Holder<Skill> magicDamage      = skill(bootstrap, AMSpells.MAGIC_DAMAGE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 216, 120, physicalDamage);
        Holder<Skill> areaOfEffect     = skill(bootstrap, AMSpells.AREA_OF_EFFECT,    AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 168, 120, fireDamage, frostDamage, lightningDamage, magicDamage);
        Holder<Skill> beam             = skill(bootstrap, AMSpells.BEAM,              AMMagic.GREEN_POINT, AMMagic.OFFENSE, 168, 168, areaOfEffect);
        Holder<Skill> chain            = skill(bootstrap, AMSpells.CHAIN,             AMMagic.GREEN_POINT, AMMagic.OFFENSE, 168, 216, beam);
        Holder<Skill> damage           = skill(bootstrap, AMSpells.DAMAGE,            AMMagic.RED_POINT,   AMMagic.OFFENSE, 168, 264, chain);
        Holder<Skill> fury             = skill(bootstrap, AMSpells.FURY,              AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 264, damage);
        Holder<Skill> explosion        = skill(bootstrap, AMSpells.EXPLOSION,         AMMagic.RED_POINT,   AMMagic.OFFENSE,  72, 264, fury);
        Holder<Skill> ignition         = skill(bootstrap, AMSpells.IGNITION,          AMMagic.BLUE_POINT,  AMMagic.OFFENSE,  72,  72, fireDamage);
        Holder<Skill> forge            = skill(bootstrap, AMSpells.FORGE,             AMMagic.BLUE_POINT,  AMMagic.OFFENSE,  24,  72, ignition);
        Holder<Skill> contingencyFire  = skill(bootstrap, AMSpells.CONTINGENCY_FIRE,  AMMagic.RED_POINT,   AMMagic.OFFENSE,  24, 120, forge);
        Holder<Skill> storm            = skill(bootstrap, AMSpells.STORM,             AMMagic.RED_POINT,   AMMagic.OFFENSE,  72, 120, lightningDamage);
        Holder<Skill> blindness        = skill(bootstrap, AMSpells.BLINDNESS,         AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 120, 168, lightningDamage);
        Holder<Skill> solar            = skill(bootstrap, AMSpells.SOLAR,             AMMagic.RED_POINT,   AMMagic.OFFENSE, 120, 216, blindness);
        Holder<Skill> frost            = skill(bootstrap, AMSpells.FROST,             AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 264,  72, frostDamage);
        Holder<Skill> piercing         = skill(bootstrap, AMSpells.PIERCING,          AMMagic.GREEN_POINT, AMMagic.OFFENSE, 312,  72, frost);
        Holder<Skill> drowningDamage   = skill(bootstrap, AMSpells.DROWNING_DAMAGE,   AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 264, 120, magicDamage);
        Holder<Skill> wateryGrave      = skill(bootstrap, AMSpells.WATERY_GRAVE,      AMMagic.BLUE_POINT,  AMMagic.OFFENSE, 312, 120, drowningDamage);
        Holder<Skill> astralDistortion = skill(bootstrap, AMSpells.ASTRAL_DISTORTION, AMMagic.GREEN_POINT, AMMagic.OFFENSE, 216, 168, magicDamage);
        Holder<Skill> silence          = skill(bootstrap, AMSpells.SILENCE,           AMMagic.GREEN_POINT, AMMagic.OFFENSE, 216, 216, astralDistortion);
        Holder<Skill> knockback        = skill(bootstrap, AMSpells.KNOCKBACK,         AMMagic.GREEN_POINT, AMMagic.OFFENSE, 264, 168, magicDamage);
        Holder<Skill> fling            = skill(bootstrap, AMSpells.FLING,             AMMagic.GREEN_POINT, AMMagic.OFFENSE, 264, 216, knockback);
        Holder<Skill> velocity         = skill(bootstrap, AMSpells.VELOCITY,          AMMagic.RED_POINT,   AMMagic.OFFENSE, 264, 264, fling);
        Holder<Skill> wave             = skill(bootstrap, AMSpells.WAVE,              AMMagic.RED_POINT,   AMMagic.OFFENSE, 216, 264, damage, velocity);

        Holder<Skill> self              = skill(bootstrap, AMSpells.SELF,               AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120,  24);
        Holder<Skill> jumpBoost         = skill(bootstrap, AMSpells.JUMP_BOOST,         AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72,  24, self);
        Holder<Skill> slowFalling       = skill(bootstrap, AMSpells.SLOW_FALLING,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72,  72, jumpBoost);
        Holder<Skill> contingencyFall   = skill(bootstrap, AMSpells.CONTINGENCY_FALL,   AMMagic.RED_POINT,   AMMagic.DEFENSE,  24,  72, slowFalling);
        Holder<Skill> slowness          = skill(bootstrap, AMSpells.SLOWNESS,           AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 120, slowFalling);
        Holder<Skill> gravityWell       = skill(bootstrap, AMSpells.GRAVITY_WELL,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  72, 120, slowFalling);
        Holder<Skill> swiftness         = skill(bootstrap, AMSpells.SWIFTNESS,          AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 120, slowFalling);
        Holder<Skill> repel             = skill(bootstrap, AMSpells.REPEL,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 168, slowness);
        Holder<Skill> levitation        = skill(bootstrap, AMSpells.LEVITATION,         AMMagic.GREEN_POINT, AMMagic.DEFENSE,  72, 168, gravityWell);
        Holder<Skill> swiftSwim         = skill(bootstrap, AMSpells.SWIFT_SWIM,         AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 168, swiftness);
        Holder<Skill> entangle          = skill(bootstrap, AMSpells.ENTANGLE,           AMMagic.BLUE_POINT,  AMMagic.DEFENSE,  24, 216, repel);
        Holder<Skill> flight            = skill(bootstrap, AMSpells.FLIGHT,             AMMagic.RED_POINT,   AMMagic.DEFENSE,  72, 216, levitation);
        Holder<Skill> haste             = skill(bootstrap, AMSpells.HASTE,              AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 120, 216, swiftSwim);
        Holder<Skill> wall              = skill(bootstrap, AMSpells.WALL,               AMMagic.GREEN_POINT, AMMagic.DEFENSE,  24, 264, entangle);
        Holder<Skill> rune              = skill(bootstrap, AMSpells.RUNE,               AMMagic.GREEN_POINT, AMMagic.DEFENSE,  72, 264, haste, entangle);
        Holder<Skill> runePower         = skill(bootstrap, AMSpells.RUNE_POWER,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120, 264, rune);
        Holder<Skill> regeneration      = skill(bootstrap, AMSpells.REGENERATION,       AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 168,  24, self);
        Holder<Skill> shrink            = skill(bootstrap, AMSpells.SHRINK,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216,  24, regeneration);
        Holder<Skill> heal              = skill(bootstrap, AMSpells.HEAL,               AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216,  72, regeneration);
        Holder<Skill> healing           = skill(bootstrap, AMSpells.HEALING,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 264,  72, heal);
        Holder<Skill> lifeTap           = skill(bootstrap, AMSpells.LIFE_TAP,           AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168,  72, heal);
        Holder<Skill> lifeDrain         = skill(bootstrap, AMSpells.LIFE_DRAIN,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 120, lifeTap);
        Holder<Skill> manaDrain         = skill(bootstrap, AMSpells.MANA_DRAIN,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 168, lifeDrain);
        Holder<Skill> summon            = skill(bootstrap, AMSpells.SUMMON,             AMMagic.GREEN_POINT, AMMagic.DEFENSE, 120,  72, lifeTap);
        Holder<Skill> dispel            = skill(bootstrap, AMSpells.DISPEL,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 216, 120, heal);
        Holder<Skill> disarm            = skill(bootstrap, AMSpells.DISARM,             AMMagic.BLUE_POINT,  AMMagic.DEFENSE, 264, 120, dispel);
        Holder<Skill> zone              = skill(bootstrap, AMSpells.ZONE,               AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 168, dispel);
        Holder<Skill> resistance        = skill(bootstrap, AMSpells.RESISTANCE,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 216, zone);
        Holder<Skill> contingencyHealth = skill(bootstrap, AMSpells.CONTINGENCY_HEALTH, AMMagic.RED_POINT,   AMMagic.DEFENSE, 264, 216, resistance);
        Holder<Skill> absorption        = skill(bootstrap, AMSpells.ABSORPTION,         AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 216, resistance);
        Holder<Skill> reflect           = skill(bootstrap, AMSpells.REFLECT,            AMMagic.GREEN_POINT, AMMagic.DEFENSE, 216, 264, resistance);
        Holder<Skill> contingencyDamage = skill(bootstrap, AMSpells.CONTINGENCY_DAMAGE, AMMagic.RED_POINT,   AMMagic.DEFENSE, 216, 312, reflect);
        Holder<Skill> temporalAnchor    = skill(bootstrap, AMSpells.TEMPORAL_ANCHOR,    AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 264, reflect);
        Holder<Skill> duration          = skill(bootstrap, AMSpells.DURATION,           AMMagic.GREEN_POINT, AMMagic.DEFENSE, 168, 312, temporalAnchor);

        Holder<Skill> touch              = skill(bootstrap, AMSpells.TOUCH,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  24);
        Holder<Skill> targetNonSolid     = skill(bootstrap, AMSpells.TARGET_NON_SOLID,    AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72,  24, touch);
        Holder<Skill> dig                = skill(bootstrap, AMSpells.DIG,                 AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120,  72, touch);
        Holder<Skill> placeBlock         = skill(bootstrap, AMSpells.PLACE_BLOCK,         AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168,  24, dig);
        Holder<Skill> wizardsAutumn      = skill(bootstrap, AMSpells.WIZARDS_AUTUMN,      AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168,  72, dig);
        Holder<Skill> silkTouch          = skill(bootstrap, AMSpells.SILK_TOUCH,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72,  72, dig);
        Holder<Skill> miningPower        = skill(bootstrap, AMSpells.MINING_POWER,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  24,  72, silkTouch);
        Holder<Skill> light              = skill(bootstrap, AMSpells.LIGHT,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 120, 120, dig);
        Holder<Skill> rift               = skill(bootstrap, AMSpells.RIFT,                AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 168, light);
        Holder<Skill> channel            = skill(bootstrap, AMSpells.CHANNEL,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 120, 216, rift);
        Holder<Skill> nightVision        = skill(bootstrap, AMSpells.NIGHT_VISION,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72, 120, light);
        Holder<Skill> lunar              = skill(bootstrap, AMSpells.LUNAR,               AMMagic.RED_POINT,   AMMagic.UTILITY,  24, 120, nightVision);
        Holder<Skill> trueSight          = skill(bootstrap, AMSpells.TRUE_SIGHT,          AMMagic.BLUE_POINT,  AMMagic.UTILITY,  72, 168, nightVision);
        Holder<Skill> invisibility       = skill(bootstrap, AMSpells.INVISIBILITY,        AMMagic.BLUE_POINT,  AMMagic.UTILITY,  24, 168, trueSight);
        Holder<Skill> randomTeleport     = skill(bootstrap, AMSpells.RANDOM_TELEPORT,     AMMagic.GREEN_POINT, AMMagic.UTILITY,  24, 216, invisibility);
        Holder<Skill> range              = skill(bootstrap, AMSpells.RANGE,               AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 216, randomTeleport);
        Holder<Skill> blink              = skill(bootstrap, AMSpells.BLINK,               AMMagic.GREEN_POINT, AMMagic.UTILITY,  24, 264, randomTeleport);
        Holder<Skill> transplace         = skill(bootstrap, AMSpells.TRANSPLACE,          AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 264, blink);
        Holder<Skill> recall             = skill(bootstrap, AMSpells.RECALL,              AMMagic.GREEN_POINT, AMMagic.UTILITY,  72, 312, transplace);
        Holder<Skill> divineIntervention = skill(bootstrap, AMSpells.DIVINE_INTERVENTION, AMMagic.RED_POINT,   AMMagic.UTILITY,  24, 312, recall);
        Holder<Skill> enderIntervention  = skill(bootstrap, AMSpells.ENDER_INTERVENTION,  AMMagic.RED_POINT,   AMMagic.UTILITY, 120, 312, recall);
        Holder<Skill> contingencyDeath   = skill(bootstrap, AMSpells.CONTINGENCY_DEATH,   AMMagic.RED_POINT,   AMMagic.UTILITY, 168, 312, enderIntervention);
        Holder<Skill> charm              = skill(bootstrap, AMSpells.CHARM,               AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 120, light);
        Holder<Skill> attract            = skill(bootstrap, AMSpells.ATTRACT,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 120, charm);
        Holder<Skill> plow               = skill(bootstrap, AMSpells.PLOW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 168, light);
        Holder<Skill> grow               = skill(bootstrap, AMSpells.GROW,                AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 168, plow);
        Holder<Skill> harvest            = skill(bootstrap, AMSpells.HARVEST,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 168, grow);
        Holder<Skill> replant            = skill(bootstrap, AMSpells.REPLANT,             AMMagic.BLUE_POINT,  AMMagic.UTILITY, 264, 216, harvest);
        Holder<Skill> createWater        = skill(bootstrap, AMSpells.CREATE_WATER,        AMMagic.BLUE_POINT,  AMMagic.UTILITY, 168, 216, plow);
        Holder<Skill> waterBreathing     = skill(bootstrap, AMSpells.WATER_BREATHING,     AMMagic.BLUE_POINT,  AMMagic.UTILITY, 216, 216, createWater);
        Holder<Skill> drought            = skill(bootstrap, AMSpells.DROUGHT,             AMMagic.GREEN_POINT, AMMagic.UTILITY, 168, 264, createWater);
        Holder<Skill> banishRain         = skill(bootstrap, AMSpells.BANISH_RAIN,         AMMagic.GREEN_POINT, AMMagic.UTILITY, 216, 264, drought);

        Holder<Skill> color                  = skill(bootstrap, AMSpells.COLOR,                    AMMagic.BLUE_POINT,  AMMagic.TALENT,  24,  24);
        Holder<Skill> manaRegenerationBoost1 = skill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_1, AMMagic.BLUE_POINT,  AMMagic.TALENT, 120,  24);
        Holder<Skill> manaRegenerationBoost2 = skill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_2, AMMagic.GREEN_POINT, AMMagic.TALENT, 120,  72, manaRegenerationBoost1);
        Holder<Skill> manaRegenerationBoost3 = skill(bootstrap, AMMagic.MANA_REGENERATION_BOOST_3, AMMagic.RED_POINT,   AMMagic.TALENT, 120, 120, manaRegenerationBoost2);
        Holder<Skill> affinityGainsBoost     = skill(bootstrap, AMMagic.AFFINITY_GAINS_BOOST,      AMMagic.BLUE_POINT,  AMMagic.TALENT, 168,  24, manaRegenerationBoost1);
        //Holder<Skill> mageBand1              = addSkill(bootstrap, AMMagic.MAGE_BAND_1,               AMMagic.GREEN_POINT, AMMagic.TALENT, 168,  72, manaRegenerationBoost2);
        //Holder<Skill> mageBand2              = addSkill(bootstrap, AMMagic.MAGE_BAND_2,               AMMagic.RED_POINT,   AMMagic.TALENT, 168, 120, mageBand1);
        Holder<Skill> spellMotion            = skill(bootstrap, AMMagic.SPELL_MOTION,              AMMagic.BLUE_POINT,  AMMagic.TALENT,  72,  24, manaRegenerationBoost1);
        Holder<Skill> augmentedCasting       = skill(bootstrap, AMMagic.AUGMENTED_CASTING,         AMMagic.GREEN_POINT, AMMagic.TALENT,  72,  72, spellMotion);
        Holder<Skill> extraSummons           = skill(bootstrap, AMMagic.EXTRA_SUMMONS,             AMMagic.RED_POINT,   AMMagic.TALENT,  72, 120, augmentedCasting);

        hiddenSkill(bootstrap, AMSpells.BLIZZARD,       AMMagic.OFFENSE,  24, 168);
        hiddenSkill(bootstrap, AMSpells.DAYLIGHT,       AMMagic.UTILITY, 216,  24);
        hiddenSkill(bootstrap, AMSpells.FALLING_STAR,   AMMagic.OFFENSE,  72, 168);
        hiddenSkill(bootstrap, AMSpells.FIRE_RAIN,      AMMagic.OFFENSE,  24, 216);
        hiddenSkill(bootstrap, AMSpells.HEALTH_BOOST,   AMMagic.DEFENSE,  24,  24);
        hiddenSkill(bootstrap, AMSpells.MANA_BLAST,     AMMagic.OFFENSE,  72, 216);
        hiddenSkill(bootstrap, AMSpells.MOONRISE,       AMMagic.UTILITY, 264,  24);
        hiddenSkill(bootstrap, AMSpells.DISMEMBERING,   AMMagic.OFFENSE,  24, 264);
        hiddenSkill(bootstrap, AMSpells.EFFECT_POWER,   AMMagic.DEFENSE, 264,  24);
        hiddenSkill(bootstrap, AMSpells.PROSPERITY,     AMMagic.UTILITY,  24,  24);
        hiddenSkill(bootstrap, AMMagic.SHIELD_OVERLOAD, AMMagic.TALENT,   24,  72);
        // @formatter:on
    }

    public static void addSpellPartData(BootstrapContext<SpellPartData> bootstrap) {
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistries.Keys.AFFINITY);
        HolderGetter<EtheriumType> etheriumTypes = bootstrap.lookup(AMRegistries.Keys.ETHERIUM_TYPE);
        HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
        spellPartData(bootstrap, AMSpells.AREA_OF_EFFECT, 2f,
            new ItemSpellIngredient(Ingredient.of(Items.TNT), 1),
            new EtheriumSpellIngredient(1));
        spellPartData(bootstrap, AMSpells.BEAM, 1f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1),
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 2500));
        spellPartData(bootstrap, AMSpells.CHAIN, 1f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STRINGS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.LEAD), 1),
            new ItemSpellIngredient(Ingredient.of(Items.TRIPWIRE_HOOK), 1),
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 2500));
        spellPartData(bootstrap, AMSpells.CHANNEL, 0.5f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        spellPartData(bootstrap, AMSpells.CONTINGENCY_DAMAGE, 10f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(5000));
        spellPartData(bootstrap, AMSpells.CONTINGENCY_DEATH, 10f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.DARK), 5000));
        spellPartData(bootstrap, AMSpells.CONTINGENCY_FALL, 10f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(5000));
        spellPartData(bootstrap, AMSpells.CONTINGENCY_FIRE, 10f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(5000));
        spellPartData(bootstrap, AMSpells.CONTINGENCY_HEALTH, 10f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.LIGHT), 5000));
        spellPartData(bootstrap, AMSpells.PROJECTILE, 1f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1),
            new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        spellPartData(bootstrap, AMSpells.RUNE, 2f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1));
        spellPartData(bootstrap, AMSpells.SELF, 0.5f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        spellPartData(bootstrap, AMSpells.TOUCH, 1f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLAY_BALL), 1));
        spellPartData(bootstrap, AMSpells.WALL, 2.5f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FENCES_WOODEN)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.WALLS)), 1),
            new EtheriumSpellIngredient(2500));
        spellPartData(bootstrap, AMSpells.WAVE, 2.5f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGIC_WALL.get()), 1),
            new EtheriumSpellIngredient(2500));
        spellPartData(bootstrap, AMSpells.ZONE, 2.5f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1),
            new EtheriumSpellIngredient(2500));
        spellPartData(bootstrap, AMSpells.DROWNING_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.WATER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.FIRE_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.FIRE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FLINT_AND_STEEL), 1));
        spellPartData(bootstrap, AMSpells.FROST_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.ICE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ICE), 1));
        spellPartData(bootstrap, AMSpells.LIGHTNING_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        spellPartData(bootstrap, AMSpells.MAGIC_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.BOOK), 1));
        spellPartData(bootstrap, AMSpells.PHYSICAL_DAMAGE, 25f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_IRON)), 1));
        spellPartData(bootstrap, AMSpells.ABSORPTION, 50f,
            affinities.getOrThrow(AMMagic.LIFE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_APPLE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHIELD), 1));
        spellPartData(bootstrap, AMSpells.BLINDNESS, 40f,
            affinities.getOrThrow(AMMagic.ENDER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.NIGHT_VISION), Items.POTION), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WEAKNESS), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.HASTE, 30f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1));
        spellPartData(bootstrap, AMSpells.HEALTH_BOOST, 50f,
            affinities.getOrThrow(AMMagic.LIFE), 0.001f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE), 1));
        spellPartData(bootstrap, AMSpells.INVISIBILITY, 40f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.INVISIBILITY), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.JUMP_BOOST, 30f,
            affinities.getOrThrow(AMMagic.AIR), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.TARMA_ROOT.get()), 1));
        spellPartData(bootstrap, AMSpells.LEVITATION, 40f,
            affinities.getOrThrow(AMMagic.AIR), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.POPPED_CHORUS_FRUIT), 1));
        spellPartData(bootstrap, AMSpells.NIGHT_VISION, 30f,
            affinities.getOrThrow(AMMagic.ENDER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.GOLDEN_CARROT), 1));
        spellPartData(bootstrap, AMSpells.NAUSEA, 200f,
            affinities.getOrThrow(AMMagic.LIFE), 0.0001f);
        spellPartData(bootstrap, AMSpells.REGENERATION, 30f,
            affinities.getOrThrow(AMMagic.LIFE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        spellPartData(bootstrap, AMSpells.RESISTANCE, 50f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHIELD), 1));
        spellPartData(bootstrap, AMSpells.SLOWNESS, 30f,
            affinities.getOrThrow(AMMagic.ICE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        spellPartData(bootstrap, AMSpells.SLOW_FALLING, 30f,
            affinities.getOrThrow(AMMagic.AIR), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.PHANTOM_MEMBRANE), 1));
        spellPartData(bootstrap, AMSpells.SWIFTNESS, 40f,
            affinities.getOrThrow(AMMagic.AIR), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1));
        spellPartData(bootstrap, AMSpells.WATER_BREATHING, 40f,
            affinities.getOrThrow(AMMagic.WATER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.WAKEBLOOM.get()), 1),
            new ItemSpellIngredient(Ingredient.of(Items.PUFFERFISH), 1));
        spellPartData(bootstrap, AMSpells.ASTRAL_DISTORTION, 40f,
            affinities.getOrThrow(AMMagic.ENDER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        spellPartData(bootstrap, AMSpells.ENTANGLE, 40f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.VINE), 1));
        spellPartData(bootstrap, AMSpells.FLIGHT, 50f,
            affinities.getOrThrow(AMMagic.AIR), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHER_STARS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.GHAST_TEAR), 1));
        spellPartData(bootstrap, AMSpells.FROST, 40f,
            affinities.getOrThrow(AMMagic.ICE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.POWDER_SNOW_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.FURY, 50f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_BLAZE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.TROPICAL_FISH), 1));
        spellPartData(bootstrap, AMSpells.GRAVITY_WELL, 40f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STONES)), 1));
        spellPartData(bootstrap, AMSpells.REFLECT, 50f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_BLAZE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GLASS_BLOCKS)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.WITCHWOOD_LOGS)), 1));
        spellPartData(bootstrap, AMSpells.SCRAMBLE_SYNAPSES, 3000f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.0001f);
        spellPartData(bootstrap, AMSpells.SHRINK, 30f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.BONES)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.BROWN_MUSHROOM), 1),
            new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.STONE_BUTTON), 1));
        spellPartData(bootstrap, AMSpells.SILENCE, 50f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.WOOL)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.JUKEBOX), 1));
        spellPartData(bootstrap, AMSpells.SWIFT_SWIM, 40f,
            affinities.getOrThrow(AMMagic.WATER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.FISHES)), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FISHING_ROD), 1));
        spellPartData(bootstrap, AMSpells.TEMPORAL_ANCHOR, 50f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHER_STARS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        spellPartData(bootstrap, AMSpells.TRUE_SIGHT, 30f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GLASS_BLOCKS)), 1));
        spellPartData(bootstrap, AMSpells.WATERY_GRAVE, 40f,
            affinities.getOrThrow(AMMagic.WATER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.STONES)), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.LEATHER_BOOTS), 1));
        spellPartData(bootstrap, AMSpells.ATTRACT, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_IRON)), 1));
        spellPartData(bootstrap, AMSpells.BANISH_RAIN, 200f,
            affinities.getOrThrow(AMMagic.WATER), 0.005f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1));
        spellPartData(bootstrap, AMSpells.BLINK, 80f,
            affinities.getOrThrow(AMMagic.ENDER), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        spellPartData(bootstrap, AMSpells.BLIZZARD, 1000f,
            affinities.getOrThrow(AMMagic.ICE), 0.01f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ICE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.PACKED_ICE), 1));
        spellPartData(bootstrap, AMSpells.CHARM, 60f,
            affinities.getOrThrow(AMMagic.LIFE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.RED_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.WHEAT), 1));
        spellPartData(bootstrap, AMSpells.CREATE_WATER, 5f,
            affinities.getOrThrow(AMMagic.WATER), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLUE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.WATER_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.DAYLIGHT, 2000f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        spellPartData(bootstrap, AMSpells.DIG, 5f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BROWN_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_AXE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_PICKAXE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SHOVEL), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_HOE), 1));
        spellPartData(bootstrap, AMSpells.DISARM, 60f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SPEAR), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_SWORD), 1));
        spellPartData(bootstrap, AMSpells.DISPEL, 60f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.MILK_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.DIVINE_INTERVENTION, 200f,
            affinities.getOrThrow(AMMagic.ENDER), 0.005f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.DRAGON_BREATH), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.BEDS)), 1));
        spellPartData(bootstrap, AMSpells.DROUGHT, 5f,
            affinities.getOrThrow(AMMagic.FIRE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.DEAD_BUSH), 1));
        spellPartData(bootstrap, AMSpells.ENDER_INTERVENTION, 200f,
            affinities.getOrThrow(AMMagic.ENDER), 0.005f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.DRAGON_BREATH), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        spellPartData(bootstrap, AMSpells.EXPLOSION, 100f,
            affinities.getOrThrow(AMMagic.FIRE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.FIRE_CHARGE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.TNT), 1));
        spellPartData(bootstrap, AMSpells.FALLING_STAR, 1000f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.01f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_ARCANE_ASH)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.END_STONES)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.FIRE_RAIN, 1000f,
            affinities.getOrThrow(AMMagic.FIRE), 0.01f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_ARCANE_ASH)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.NETHERRACKS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.LAVA_BUCKET), 1));
        spellPartData(bootstrap, AMSpells.FLING, 80f,
            affinities.getOrThrow(AMMagic.AIR), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        spellPartData(bootstrap, AMSpells.FORGE, 80f,
            affinities.getOrThrow(AMMagic.FIRE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.FURNACE), 1));
        spellPartData(bootstrap, AMSpells.GROW, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.BONE_MEAL), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.WITCHWOOD_LOGS)), 1));
        spellPartData(bootstrap, AMSpells.HARVEST, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.SHEARS), 1));
        spellPartData(bootstrap, AMSpells.HEAL, 60f,
            affinities.getOrThrow(AMMagic.LIFE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1));
        spellPartData(bootstrap, AMSpells.IGNITION, 80f,
            affinities.getOrThrow(AMMagic.FIRE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.ORANGE_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.FLINT_AND_STEEL), 1));
        spellPartData(bootstrap, AMSpells.KNOCKBACK, 80f,
            affinities.getOrThrow(AMMagic.AIR), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.PISTON), 1));
        spellPartData(bootstrap, AMSpells.LIFE_DRAIN, 5f,
            affinities.getOrThrow(AMMagic.LIFE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        spellPartData(bootstrap, AMSpells.LIFE_TAP, 5f,
            affinities.getOrThrow(AMMagic.LIFE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.AUM.get()), 1));
        spellPartData(bootstrap, AMSpells.LIGHT, 60f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.VINTEUM_TORCH.get()), 1));
        spellPartData(bootstrap, AMSpells.MANA_BLAST, 0f,
            affinities.getOrThrow(AMMagic.ENDER), 0.001f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ENDER), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1));
        spellPartData(bootstrap, AMSpells.MANA_DRAIN, 5f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.CYAN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1));
        spellPartData(bootstrap, AMSpells.MELT_ARMOR, 200f,
            affinities.getOrThrow(AMMagic.FIRE), 0.0001f);
        spellPartData(bootstrap, AMSpells.MOONRISE, 2000f,
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        spellPartData(bootstrap, AMSpells.PLACE_BLOCK, 5f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIGHT_GRAY_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.CHESTS_WOODEN)), 1));
        spellPartData(bootstrap, AMSpells.REPLANT, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.WHEAT_SEEDS), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        spellPartData(bootstrap, AMSpells.PLOW, 5f,
            affinities.getOrThrow(AMMagic.EARTH), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.DAMAGE, 0, Items.IRON_HOE), 1));
        spellPartData(bootstrap, AMSpells.RANDOM_TELEPORT, 80f,
            affinities.getOrThrow(AMMagic.ENDER), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.ENDER_PEARLS)), 1));
        spellPartData(bootstrap, AMSpells.RECALL, 80f,
            affinities.getOrThrow(AMMagic.ARCANE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.LIME_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.MAP), 1));
        spellPartData(bootstrap, AMSpells.REPEL, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_PURIFIED_VINTEUM)), 1));
        spellPartData(bootstrap, AMSpells.RIFT, 80f,
            affinities.getOrThrow(AMMagic.ENDER), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.PURPLE_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_CHEST), 1));
        spellPartData(bootstrap, AMSpells.STORM, 200f,
            affinities.getOrThrow(AMMagic.LIGHTNING), 0.005f,
            new ItemSpellIngredient(Ingredient.of(AMItems.YELLOW_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.LIGHTNING_ROD), 1));
        spellPartData(bootstrap, AMSpells.SUMMON, 80f,
            affinities.getOrThrow(AMMagic.LIFE), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GRAY_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.DUSTS_PURIFIED_VINTEUM)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1),
            new EtheriumSpellIngredient(etheriumTypes.getOrThrow(AMEtheriumTypes.DARK), 2500));
        spellPartData(bootstrap, AMSpells.TRANSPLACE, 80f,
            affinities.getOrThrow(AMMagic.ENDER), 0.002f,
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ENDER_EYE), 1));
        spellPartData(bootstrap, AMSpells.WIZARDS_AUTUMN, 5f,
            affinities.getOrThrow(AMMagic.NATURE), 0.001f,
            new ItemSpellIngredient(Ingredient.of(AMItems.GREEN_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.RODS_WOODEN)), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.WITCHWOOD_SAPLING.get()), 1));
        spellPartData(bootstrap, AMSpells.BOUNCE, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        spellPartData(bootstrap, AMSpells.DAMAGE, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.FIRE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.COPPER_INGOT), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HARMING), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.DISMEMBERING, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.BONES)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.WITHER_SKELETON_SKULL), 1));
        spellPartData(bootstrap, AMSpells.DURATION, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.SLIME_BALLS)), 1));
        spellPartData(bootstrap, AMSpells.EFFECT_POWER, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.CROPS_NETHER_WART)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_GLOWSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GUNPOWDERS)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.GLASS_BOTTLE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.SPIDER_EYE), 1));
        spellPartData(bootstrap, AMSpells.GRAVITY, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.COMPASS), 1));
        spellPartData(bootstrap, AMSpells.HEALING, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIFE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.EGG), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.HEALING), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.LUNAR, 1f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_MOONSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        spellPartData(bootstrap, AMSpells.MINING_POWER, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_DIAMOND)), 1));
        spellPartData(bootstrap, AMSpells.PIERCING, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1),
            new ItemSpellIngredient(Ingredient.of(Items.SNOWBALL), 1));
        spellPartData(bootstrap, AMSpells.PROSPERITY, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ICE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.INGOTS_GOLD)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_EMERALD)), 1));
        spellPartData(bootstrap, AMSpells.RANGE, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.AIR), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.DUSTS_REDSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.ARROW), 1));
        spellPartData(bootstrap, AMSpells.RUNE_POWER, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.ARCANE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.BLACK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.MAGENTA_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.PINK_RUNE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.WHITE_RUNE), 1));
        spellPartData(bootstrap, AMSpells.SILK_TOUCH, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.EARTH), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1));
        spellPartData(bootstrap, AMSpells.SOLAR, 1f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.NATURE), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_SUNSTONE)), 1),
            new ItemSpellIngredient(Ingredient.of(Items.CLOCK), 1));
        spellPartData(bootstrap, AMSpells.TARGET_NON_SOLID, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(AMItems.CERUBLOSSOM.get()), 1),
            new ItemSpellIngredient(Ingredient.of(Items.POPPY), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.VELOCITY, 1.25f,
            new ItemSpellIngredient(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.LIGHTNING), AMItems.AFFINITY_ESSENCE), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(Tags.Items.FEATHERS)), 1),
            new ItemSpellIngredient(Ingredient.of(items.getOrThrow(ItemTags.BOATS)), 1),
            new ItemSpellIngredient(DataComponentIngredient.of(false, DataComponents.POTION_CONTENTS, new PotionContents(Potions.SWIFTNESS), Items.POTION), 1));
        spellPartData(bootstrap, AMSpells.COLOR, 1.0f,
            new ItemSpellIngredient(Ingredient.of(AMItems.CHIMERITE.get()), 1));
    }

    public static void addSpellPrefabs(BootstrapContext<Spell> bootstrap) {
        spellPrefab(bootstrap, "water_bolt", "beam_blue_3",
            List.of(AMSpells.DROWNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "fire_bolt", "beam_orange_3",
            List.of(AMSpells.FIRE_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "earth_bolt", "beam_acid_3",
            List.of(AMSpells.PHYSICAL_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "ice_bolt", "beam_sky_3",
            List.of(AMSpells.FROST_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "lightning_bolt", "beam_eerie_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "arcane_bolt", "beam_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_water_bolt", "lightning_blue_3",
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_fire_bolt", "lightning_orange_3",
            List.of(AMSpells.FIRE_DAMAGE.get(), AMSpells.IGNITION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_earth_bolt", "lightning_acid_3",
            List.of(AMSpells.PHYSICAL_DAMAGE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_ice_bolt", "lightning_sky_3",
            List.of(AMSpells.FROST_DAMAGE.get(), AMSpells.FROST.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_lightning_bolt", "lightning_eerie_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.BLINDNESS.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "strong_arcane_bolt", "lightning_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.LEVITATION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "area_lightning", "rip_water_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get()));
        spellPrefab(bootstrap, "blink", "whirlwind_magenta_3",
            List.of(AMSpells.BLINK.get()),
            List.of(AMSpells.SELF.get()));
        spellPrefab(bootstrap, "chaos_water_bolt", "beam_red_3",
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "debuff", "explosion_sky_3",
            List.of(AMSpells.NAUSEA.get(), AMSpells.SLOWNESS.get(), AMSpells.ASTRAL_DISTORTION.get(), AMSpells.ENTANGLE.get(), AMSpells.GRAVITY_WELL.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "dispel", "shield_royal_3",
            List.of(AMSpells.DISPEL.get()),
            List.of(AMSpells.SELF.get()));
        spellPrefab(bootstrap, "ender_bolt", "beam_jade_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.RANDOM_TELEPORT.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "ender_torrent", "light_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get(), AMSpells.AREA_OF_EFFECT.get()));
        spellPrefab(bootstrap, "ender_wave", "wind_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.WAVE.get(), AMSpells.RANGE.get()));
        spellPrefab(bootstrap, "heal_self", "heart_royal_3",
            List.of(AMSpells.HEAL.get()),
            List.of(AMSpells.SELF.get()));
        spellPrefab(bootstrap, "lightning_rune", "rune_orange_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.RUNE.get()));
        spellPrefab(bootstrap, "melt_armor", "spawner_fire_3",
            List.of(AMSpells.MELT_ARMOR.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "nausea", "sword_eerie_3",
            List.of(AMSpells.NAUSEA.get()),
            List.of(AMSpells.PROJECTILE.get()));
        spellPrefab(bootstrap, "otherworldly_roar", "gravity_magenta_3",
            List.of(AMSpells.BLINDNESS.get(), AMSpells.SLOWNESS.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get()));
        spellPrefab(bootstrap, "scramble_synapses", "slice_orange_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.SCRAMBLE_SYNAPSES.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get()));
    }

    public static void addRituals(BootstrapContext<Ritual<?>> bootstrap) {
        HolderGetter<EntityType<?>> entityTypes = bootstrap.lookup(Registries.ENTITY_TYPE);
        HolderGetter<Item> items = bootstrap.lookup(Registries.ITEM);
        HolderGetter<Affinity> affinities = bootstrap.lookup(AMRegistries.Keys.AFFINITY);
        HolderGetter<Skill> skills = bootstrap.lookup(AMRegistries.Keys.SKILL);
        ritual(bootstrap, "purification",
            new SpellCastRitualTrigger(List.of(AMSpells.SELF.get(), AMSpells.LIGHT.get())),
            List.of(
                new IngredientRitualRequirement(Ingredient.of(AMItems.MOONSTONE), 4),
                new StructureRitualRequirement(AMMultiblocks.PURIFICATION, BlockPos.ZERO.below(3))),
            List.of(
                new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER), BlockPos.ZERO.below(3)),
                new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER), BlockPos.ZERO.below(2)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1))));
        ritual(bootstrap, "corruption",
            new SpellCastRitualTrigger(List.of(AMSpells.FIRE_DAMAGE.get())),
            List.of(
                new IngredientRitualRequirement(Ingredient.of(AMItems.SUNSTONE), 4),
                new StructureRitualRequirement(AMMultiblocks.CORRUPTION, BlockPos.ZERO.below(3))),
            List.of(
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(3)),
                new SetBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.below(2)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1))));
        unlockRitual(bootstrap, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
        unlockRitual(bootstrap, AMSpells.DAYLIGHT, AMSpells.DIVINE_INTERVENTION, AMSpells.TRUE_SIGHT, AMSpells.SOLAR);
        unlockRitual(bootstrap, AMSpells.DISMEMBERING, AMSpells.PHYSICAL_DAMAGE, AMSpells.DAMAGE, AMSpells.HEALING, AMSpells.PIERCING);
        unlockRitual(bootstrap, AMSpells.EFFECT_POWER, AMSpells.FLIGHT, AMSpells.FURY, AMSpells.REFLECT, AMSpells.SHRINK, AMSpells.SWIFT_SWIM, AMSpells.TEMPORAL_ANCHOR);
        unlockRitual(bootstrap, AMSpells.FALLING_STAR, AMSpells.ASTRAL_DISTORTION, AMSpells.MAGIC_DAMAGE, AMSpells.GRAVITY);
        unlockRitual(bootstrap, AMSpells.FIRE_RAIN, AMSpells.FIRE_DAMAGE, AMSpells.IGNITION, AMSpells.STORM);
        unlockRitual(bootstrap, AMSpells.HEALTH_BOOST, AMSpells.LIFE_TAP, AMSpells.RESISTANCE);
        unlockRitual(bootstrap, AMSpells.MANA_BLAST, AMSpells.EXPLOSION, AMSpells.MANA_DRAIN);
        unlockRitual(bootstrap, AMSpells.MOONRISE, AMSpells.ENDER_INTERVENTION, AMSpells.NIGHT_VISION, AMSpells.LUNAR);
        unlockRitual(bootstrap, AMSpells.PROSPERITY, AMSpells.DIG, AMSpells.PHYSICAL_DAMAGE, AMSpells.MINING_POWER, AMSpells.SILK_TOUCH);
        ritual(bootstrap, "unlock_shield_overload",
            new SpellCastRitualTrigger(List.of(AMSpells.RESISTANCE.get(), AMSpells.MANA_DRAIN.get())),
            List.of(),
            List.of(new LearnSkillRitualEffect(skills.getOrThrow(AMMagic.SHIELD_OVERLOAD))));
        spawnRitual(bootstrap, AMEntities.WATER_GUARDIAN, AMMultiblocks.WATER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(ItemTags.BOATS)), Ingredient.of(Items.WATER_BUCKET)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new BiomeTagRitualRequirement(AMTags.Biomes.CAN_SUMMON_WATER_GUARDIAN)));
        spawnRitual(bootstrap, AMEntities.FIRE_GUARDIAN, AMMultiblocks.FIRE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(false, AMDataComponents.AFFINITY, affinities.getOrThrow(AMMagic.WATER), AMItems.AFFINITY_ESSENCE)),
            List.of(new EnvironmentAttributeRitualRequirement<>(EnvironmentAttributes.WATER_EVAPORATES, true)));
        spawnRitual(bootstrap, AMEntities.EARTH_GUARDIAN, AMMultiblocks.EARTH_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(items.getOrThrow(Tags.Items.GEMS_EMERALD)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_CHIMERITE)), Ingredient.of(items.getOrThrow(AMTags.Items.GEMS_TOPAZ))),
            List.of(new DimensionRitualRequirement(Level.OVERWORLD)));
        spawnRitual(bootstrap, AMEntities.AIR_GUARDIAN, AMMultiblocks.AIR_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(AMItems.TARMA_ROOT)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new HeightRitualRequirement(MinMaxBounds.Doubles.atLeast(128))));
        spawnRitual(bootstrap, AMEntities.ICE_GUARDIAN, AMMultiblocks.ICE_GUARDIAN_SPAWN_RITUAL,
            new SetBlockStateRitualTrigger(new BlockMatchTest(Blocks.CARVED_PUMPKIN), new BlockPos(0, 2, 0)),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new BiomeTagRitualRequirement(Tags.Biomes.IS_COLD)),
            List.of(
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 1, 0)),
                new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), new BlockPos(0, 2, 0))));
        spawnRitual(bootstrap, AMEntities.LIGHTNING_GUARDIAN, AMMultiblocks.LIGHTNING_GUARDIAN_SPAWN_RITUAL, new BlockPos(0, -3, 0),
            new GameEventRitualTrigger(GameEvent.LIGHTNING_STRIKE), List.of(), List.of());
        spawnRitual(bootstrap, AMEntities.LIFE_GUARDIAN, AMMultiblocks.LIFE_GUARDIAN_SPAWN_RITUAL,
            new KillEntityRitualTrigger(EntityPredicate.Builder.entity().of(entityTypes, EntityType.VILLAGER).flags(EntityFlagsPredicate.Builder.flags().setIsBaby(true)).build()),
            List.of(
                new DimensionRitualRequirement(Level.OVERWORLD),
                new EnvironmentAttributeRitualRequirement<>(EnvironmentAttributes.MOON_PHASE, MoonPhase.NEW_MOON)));
        spawnRitual(bootstrap, AMEntities.ARCANE_GUARDIAN, AMMultiblocks.ARCANE_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(DataComponentIngredient.of(true, ArsMagicaApi.book())), List.of());
        spawnRitual(bootstrap, AMEntities.ENDER_GUARDIAN, AMMultiblocks.ENDER_GUARDIAN_SPAWN_RITUAL,
            new DroppedItemRitualTrigger(Ingredient.of(Items.ENDER_EYE)),
            List.of(new DimensionRitualRequirement(Level.END)));
    }

    public static void ability(BootstrapContext<Ability> bootstrap, ResourceKey<Ability> ability, ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds, AbilityEffect effect) {
        ability(bootstrap, ability, affinity, bounds, false, effect);
    }

    public static void ability(BootstrapContext<Ability> bootstrap, ResourceKey<Ability> ability, ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, AbilityEffect effect) {
        bootstrap.register(ability, new Ability(bootstrap.lookup(AMRegistries.Keys.AFFINITY).getOrThrow(affinity), bounds, negative, effect));
        PATCHOULI_ABILITY_DATA.put(ability, new PatchouliAbilityData(affinity, bounds));
    }

    @SafeVarargs
    private static Holder<Skill> skill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return skill(bootstrap, skillFromPart(part), point, tab, x, y, parents);
    }

    @SafeVarargs
    private static Holder<Skill> skill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, Holder<Skill>... parents) {
        return bootstrap.register(key, new Skill(
            Arrays.asList(parents),
            Optional.of(bootstrap.lookup(AMRegistries.Keys.SKILL_POINT).getOrThrow(point)),
            bootstrap.lookup(AMRegistries.Keys.OCCULUS_TAB).getOrThrow(tab),
            x,
            y,
            false));
    }

    private static void hiddenSkill(BootstrapContext<Skill> bootstrap, DeferredHolder<SpellPart, ?> part, ResourceKey<OcculusTab> tab, int x, int y) {
        hiddenSkill(bootstrap, skillFromPart(part), tab, x, y);
    }

    private static void hiddenSkill(BootstrapContext<Skill> bootstrap, ResourceKey<Skill> key, ResourceKey<OcculusTab> tab, int x, int y) {
        bootstrap.register(key, new Skill(List.of(), Optional.empty(), bootstrap.lookup(AMRegistries.Keys.OCCULUS_TAB).getOrThrow(tab), x, y, true));
    }

    private static void spellPartData(BootstrapContext<SpellPartData> bootstrap, DeferredHolder<SpellPart, ?> part, double mana, SpellIngredient... ingredients) {
        bootstrap.register(dataFromPart(part), new SpellPartData(mana, Optional.empty(), Map.of(), Arrays.asList(ingredients)));
    }

    private static void spellPartData(BootstrapContext<SpellPartData> bootstrap, DeferredHolder<SpellPart, ?> part, double mana, Holder<Affinity> affinity, double affinityShift, SpellIngredient... ingredients) {
        bootstrap.register(dataFromPart(part), new SpellPartData(mana, Optional.empty(), Map.of(affinity, affinityShift), Arrays.asList(ingredients)));
    }

    private static ResourceKey<Skill> skillFromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistries.Keys.SKILL, Objects.requireNonNull(AMRegistries.SPELL_PARTS.getKey(part.get())));
    }

    private static ResourceKey<SpellPartData> dataFromPart(DeferredHolder<SpellPart, ?> part) {
        return ResourceKey.create(AMRegistries.Keys.SPELL_PART_DATA, Objects.requireNonNull(AMRegistries.SPELL_PARTS.getKey(part.get())));
    }

    private static void spellPrefab(BootstrapContext<Spell> bootstrap, String name, String icon, List<SpellPart> grammar, List<SpellPart> shapeGroup) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id(name)), new Spell(
            Optional.of(Component.translatable("spell_prefab." + ArsMagicaApi.MOD_ID + "." + name)),
            Optional.of(ArsMagicaApi.id(icon)),
            List.of(SpellShapeGroup.of(shapeGroup)),
            0,
            SpellGrammar.of(grammar),
            SpellDataComponentMap.EMPTY
        ));
    }

    private static void spawnRitual(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, RitualTrigger<?> trigger, List<RitualRequirement> requirements) {
        spawnRitual(bootstrap, boss, structure, trigger, requirements, List.of());
    }

    private static void spawnRitual(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        spawnRitual(bootstrap, boss, structure, BlockPos.ZERO, trigger, requirements, effects);
    }

    private static void spawnRitual(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<EntityType<?>, ?> boss, Identifier structure, BlockPos offset, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        List<RitualRequirement> newRequirements = new ArrayList<>(requirements);
        newRequirements.addFirst(new StructureRitualRequirement(structure, offset));
        List<RitualEffect> newEffects = new ArrayList<>(effects);
        newEffects.addFirst(new SpawnEntityRitualEffect(boss.get()));
        ritual(bootstrap, "spawn_" + boss.getId().getPath(), trigger, newRequirements, newEffects);
    }

    @SafeVarargs
    private static void unlockRitual(BootstrapContext<Ritual<?>> bootstrap, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        Identifier id = part.getId();
        ritual(bootstrap, "unlock_" + id.getPath(), new SpellCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()), List.of(), List.of(new LearnSkillRitualEffect(bootstrap.lookup(AMRegistries.Keys.SKILL).get(ResourceKey.create(AMRegistries.Keys.SKILL, id)).orElseThrow())));
    }

    private static void ritual(BootstrapContext<Ritual<?>> bootstrap, String name, RitualTrigger<?> trigger, List<RitualRequirement> requirements, List<RitualEffect> effects) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.RITUAL, ArsMagicaApi.id(name)), new Ritual<>(requirements, trigger, effects));
    }

    public record PatchouliAbilityData(ResourceKey<Affinity> affinity, MinMaxBounds.Doubles bounds) {
    }
}
