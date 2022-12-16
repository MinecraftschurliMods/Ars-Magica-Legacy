package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.ObeliskFuel;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualTrigger;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.SkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.PrefabSpell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellIngredientType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.StatType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

@NonExtendable
public interface AMRegistries {
    // Deferred Registers for vanilla registries
    DeferredRegister<Block>               BLOCKS             = DeferredRegister.create(ForgeRegistries.BLOCKS,             ArsMagicaAPI.MOD_ID);
    DeferredRegister<Fluid>               FLUIDS             = DeferredRegister.create(ForgeRegistries.FLUIDS,             ArsMagicaAPI.MOD_ID);
    DeferredRegister<Item>                ITEMS              = DeferredRegister.create(ForgeRegistries.ITEMS,              ArsMagicaAPI.MOD_ID);
    DeferredRegister<MobEffect>           MOB_EFFECTS        = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,        ArsMagicaAPI.MOD_ID);
    DeferredRegister<Attribute>           ATTRIBUTES         = DeferredRegister.create(ForgeRegistries.ATTRIBUTES,         ArsMagicaAPI.MOD_ID);
    DeferredRegister<SoundEvent>          SOUND_EVENTS       = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,       ArsMagicaAPI.MOD_ID);
    DeferredRegister<Potion>              POTIONS            = DeferredRegister.create(ForgeRegistries.POTIONS,            ArsMagicaAPI.MOD_ID);
    DeferredRegister<Enchantment>         ENCHANTMENTS       = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,       ArsMagicaAPI.MOD_ID);
    DeferredRegister<EntityType<?>>       ENTITY_TYPES       = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,       ArsMagicaAPI.MOD_ID);
    DeferredRegister<BlockEntityType<?>>  BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ArsMagicaAPI.MOD_ID);
    DeferredRegister<ParticleType<?>>     PARTICLE_TYPES     = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,     ArsMagicaAPI.MOD_ID);
    DeferredRegister<MenuType<?>>         MENU_TYPES         = DeferredRegister.create(ForgeRegistries.MENU_TYPES,         ArsMagicaAPI.MOD_ID);
    DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<StatType<?>>         STAT_TYPES         = DeferredRegister.create(ForgeRegistries.STAT_TYPES,         ArsMagicaAPI.MOD_ID);
    DeferredRegister<ResourceLocation>    CUSTOM_STATS       = DeferredRegister.create(Registries.CUSTOM_STAT,             ArsMagicaAPI.MOD_ID);
    DeferredRegister<Feature<?>>          FEATURES           = DeferredRegister.create(ForgeRegistries.FEATURES,           ArsMagicaAPI.MOD_ID);

    // Deferred Registers for forge registries
    DeferredRegister<FluidType>                            FLUID_TYPES             = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES,                      ArsMagicaAPI.MOD_ID);
    DeferredRegister<EntityDataSerializer<?>>              ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS,          ArsMagicaAPI.MOD_ID);
    DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS   = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ArsMagicaAPI.MOD_ID);

    // Deferred Registers for custom registries
    DeferredRegister<SkillPoint>                         SKILL_POINTS             = DeferredRegister.create(SkillPoint.REGISTRY_KEY,          ArsMagicaAPI.MOD_ID);
    DeferredRegister<Affinity>                           AFFINITIES               = DeferredRegister.create(Affinity.REGISTRY_KEY,            ArsMagicaAPI.MOD_ID);
    DeferredRegister<ISpellPart>                         SPELL_PARTS              = DeferredRegister.create(ISpellPart.REGISTRY_KEY,          ArsMagicaAPI.MOD_ID);
    DeferredRegister<ContingencyType>                    CONTINGENCY_TYPE         = DeferredRegister.create(ContingencyType.REGISTRY_KEY,     ArsMagicaAPI.MOD_ID);
    DeferredRegister<Codec<? extends RitualTrigger>>     RITUAL_TRIGGER_TYPES     = DeferredRegister.create(RitualTrigger.REGISTRY_KEY,       ArsMagicaAPI.MOD_ID);
    DeferredRegister<Codec<? extends RitualRequirement>> RITUAL_REQUIREMENT_TYPES = DeferredRegister.create(RitualRequirement.REGISTRY_KEY,   ArsMagicaAPI.MOD_ID);
    DeferredRegister<Codec<? extends RitualEffect>>      RITUAL_EFFECT_TYPES      = DeferredRegister.create(RitualEffect.REGISTRY_KEY,        ArsMagicaAPI.MOD_ID);
    DeferredRegister<SpellIngredientType<?>>             SPELL_INGREDIENT_TYPES   = DeferredRegister.create(SpellIngredientType.REGISTRY_KEY, ArsMagicaAPI.MOD_ID);

    // region Custom registries get via ArsMagicaAPI.get().getXYZRegistry()
    Supplier<IForgeRegistry<SkillPoint>>                         SKILL_POINT_REGISTRY             = SKILL_POINTS.makeRegistry(() -> new RegistryBuilder<SkillPoint>().setDefaultKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "none")));
    Supplier<IForgeRegistry<Affinity>>                           AFFINITY_REGISTRY                = AFFINITIES.makeRegistry(() -> new RegistryBuilder<Affinity>().setDefaultKey(Affinity.NONE));
    Supplier<IForgeRegistry<ISpellPart>>                         SPELL_PART_REGISTRY              = SPELL_PARTS.makeRegistry(RegistryBuilder::new);
    Supplier<IForgeRegistry<ContingencyType>>                    CONTINGENCY_TYPE_REGISTRY        = CONTINGENCY_TYPE.makeRegistry(() -> new RegistryBuilder<ContingencyType>().setDefaultKey(ContingencyType.NONE));
    Supplier<IForgeRegistry<Codec<? extends RitualTrigger>>>     RITUAL_TRIGGER_TYPE_REGISTRY     = RITUAL_TRIGGER_TYPES.makeRegistry(RegistryBuilder::new);
    Supplier<IForgeRegistry<Codec<? extends RitualRequirement>>> RITUAL_REQUIREMENT_TYPE_REGISTRY = RITUAL_REQUIREMENT_TYPES.makeRegistry(RegistryBuilder::new);
    Supplier<IForgeRegistry<Codec<? extends RitualEffect>>>      RITUAL_EFFECT_TYPE_REGISTRY      = RITUAL_EFFECT_TYPES.makeRegistry(RegistryBuilder::new);
    Supplier<IForgeRegistry<SpellIngredientType<?>>>             SPELL_INGREDIENT_TYPE_REGISTRY   = SPELL_INGREDIENT_TYPES.makeRegistry(RegistryBuilder::new);
    // endregion

    /**
     * Registers the registries to the given event bus.
     *
     * @param bus The event bus to register to.
     */
    @Internal
    static void init(IEventBus bus) {
        AMBlocks.register();
        AMFluids.register();
        AMItems.register();
        AMMobEffects.register();
        AMAttributes.register();
        AMSounds.register();
        AMEntities.register();
        AMBlockEntities.register();
        AMMenuTypes.register();
        AMStats.register();
        bus.addListener(AMStats::onRegister);
        AMDataSerializers.register();
        AMLootModifiers.register();
        AMSkillPoints.register();
        AMAffinities.register();
        AMSpellParts.register();
        AMContingencyTypes.register();
        AMFeatures.register();
        AMRituals.register();
        AMSpellIngredientTypes.register();

        BLOCKS.register(bus);
        FLUIDS.register(bus);
        ITEMS.register(bus);
        MOB_EFFECTS.register(bus);
        ATTRIBUTES.register(bus);
        SOUND_EVENTS.register(bus);
        POTIONS.register(bus);
        ENCHANTMENTS.register(bus);
        ENTITY_TYPES.register(bus);
        BLOCK_ENTITY_TYPES.register(bus);
        PARTICLE_TYPES.register(bus);
        MENU_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        STAT_TYPES.register(bus);
        CUSTOM_STATS.register(bus);
        FLUID_TYPES.register(bus);
        ENTITY_DATA_SERIALIZERS.register(bus);
        GLOBAL_LOOT_MODIFIERS.register(bus);
        SKILL_POINTS.register(bus);
        AFFINITIES.register(bus);
        SPELL_PARTS.register(bus);
        CONTINGENCY_TYPE.register(bus);
        FEATURES.register(bus);
        RITUAL_TRIGGER_TYPES.register(bus);
        RITUAL_REQUIREMENT_TYPES.register(bus);
        RITUAL_EFFECT_TYPES.register(bus);
        SPELL_INGREDIENT_TYPES.register(bus);

        bus.addListener(AMRegistries::registerDatapackRegistries);
    }

    private static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(Ability.REGISTRY_KEY, Ability.DIRECT_CODEC, Ability.DIRECT_CODEC);
        event.dataPackRegistry(Skill.REGISTRY_KEY, Skill.DIRECT_CODEC, Skill.DIRECT_CODEC);
        event.dataPackRegistry(Ritual.REGISTRY_KEY, Ritual.DIRECT_CODEC);
        event.dataPackRegistry(OcculusTab.REGISTRY_KEY, OcculusTab.CODEC, OcculusTab.CODEC);
        event.dataPackRegistry(SpellTransformation.REGISTRY_KEY, SpellTransformation.DIRECT_CODEC);
        event.dataPackRegistry(AltarStructureMaterial.REGISTRY_KEY, AltarStructureMaterial.CODEC, AltarStructureMaterial.CODEC);
        event.dataPackRegistry(AltarCapMaterial.REGISTRY_KEY, AltarCapMaterial.CODEC, AltarCapMaterial.CODEC);
        event.dataPackRegistry(PrefabSpell.REGISTRY_KEY, PrefabSpell.DIRECT_CODEC, PrefabSpell.DIRECT_CODEC);
        event.dataPackRegistry(ObeliskFuel.REGISTRY_KEY, ObeliskFuel.CODEC, ObeliskFuel.NETWORK_CODEC);
    }
}
