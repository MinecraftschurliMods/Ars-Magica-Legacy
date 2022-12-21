package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ContingencyType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.ISkillPoint;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Supplier;

@NonExtendable
public interface AMRegistries {
    DeferredRegister<Block>                   BLOCKS              = DeferredRegister.create(ForgeRegistries.BLOCKS,                ArsMagicaAPI.MOD_ID);
    DeferredRegister<Fluid>                   FLUIDS              = DeferredRegister.create(ForgeRegistries.FLUIDS,                ArsMagicaAPI.MOD_ID);
    DeferredRegister<Item>                    ITEMS               = DeferredRegister.create(ForgeRegistries.ITEMS,                 ArsMagicaAPI.MOD_ID);
    DeferredRegister<MobEffect>               MOB_EFFECTS         = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,           ArsMagicaAPI.MOD_ID);
    DeferredRegister<Attribute>               ATTRIBUTES          = DeferredRegister.create(ForgeRegistries.ATTRIBUTES,            ArsMagicaAPI.MOD_ID);
    DeferredRegister<SoundEvent>              SOUND_EVENTS        = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,          ArsMagicaAPI.MOD_ID);
    DeferredRegister<Potion>                  POTIONS             = DeferredRegister.create(ForgeRegistries.POTIONS,               ArsMagicaAPI.MOD_ID);
    DeferredRegister<Enchantment>             ENCHANTMENTS        = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,          ArsMagicaAPI.MOD_ID);
    DeferredRegister<EntityType<?>>           ENTITIES            = DeferredRegister.create(ForgeRegistries.ENTITIES,              ArsMagicaAPI.MOD_ID);
    DeferredRegister<BlockEntityType<?>>      BLOCK_ENTITIES      = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,        ArsMagicaAPI.MOD_ID);
    DeferredRegister<ParticleType<?>>         PARTICLE_TYPES      = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,        ArsMagicaAPI.MOD_ID);
    DeferredRegister<MenuType<?>>             MENU_TYPES          = DeferredRegister.create(ForgeRegistries.CONTAINERS,            ArsMagicaAPI.MOD_ID);
    DeferredRegister<RecipeSerializer<?>>     RECIPE_SERIALIZERS  = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS,    ArsMagicaAPI.MOD_ID);
    DeferredRegister<StatType<?>>             STAT_TYPES          = DeferredRegister.create(ForgeRegistries.STAT_TYPES,            ArsMagicaAPI.MOD_ID);
    DeferredRegister<DataSerializerEntry>     DATA_SERIALIZERS    = DeferredRegister.create(ForgeRegistries.Keys.DATA_SERIALIZERS, ArsMagicaAPI.MOD_ID);
    DeferredRegister<Feature<?>>              FEATURES            = DeferredRegister.create(ForgeRegistries.FEATURES,              ArsMagicaAPI.MOD_ID);
    DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY,  ArsMagicaAPI.MOD_ID);
    DeferredRegister<PlacedFeature>           PLACED_FEATURES     = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY,      ArsMagicaAPI.MOD_ID);
    DeferredRegister<ISkillPoint>             SKILL_POINTS        = DeferredRegister.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "skill_point"),      ArsMagicaAPI.MOD_ID);
    DeferredRegister<IAffinity>               AFFINITIES          = DeferredRegister.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"),         ArsMagicaAPI.MOD_ID);
    DeferredRegister<ISpellPart>              SPELL_PARTS         = DeferredRegister.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_part"),       ArsMagicaAPI.MOD_ID);
    DeferredRegister<IAbility>                ABILITIES           = DeferredRegister.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ability"),          ArsMagicaAPI.MOD_ID);
    DeferredRegister<ContingencyType>         CONTINGENCY_TYPE    = DeferredRegister.create(new ResourceLocation(ArsMagicaAPI.MOD_ID, "contingency_type"), ArsMagicaAPI.MOD_ID);

    Supplier<IForgeRegistry<ISkillPoint>>     SKILL_POINT_REGISTRY      = SKILL_POINTS.makeRegistry(ISkillPoint.class,         () -> new RegistryBuilder<ISkillPoint>().setDefaultKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "none")));
    Supplier<IForgeRegistry<IAffinity>>       AFFINITY_REGISTRY         = AFFINITIES.makeRegistry(IAffinity.class,             () -> new RegistryBuilder<IAffinity>().setDefaultKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "none")));
    Supplier<IForgeRegistry<ISpellPart>>      SPELL_PART_REGISTRY       = SPELL_PARTS.makeRegistry(ISpellPart.class,           RegistryBuilder::new);
    Supplier<IForgeRegistry<IAbility>>        ABILITY_REGISTRY          = ABILITIES.makeRegistry(IAbility.class,               RegistryBuilder::new);
    Supplier<IForgeRegistry<ContingencyType>> CONTINGENCY_TYPE_REGISTRY = CONTINGENCY_TYPE.makeRegistry(ContingencyType.class, () -> new RegistryBuilder<ContingencyType>().setDefaultKey(ContingencyType.NONE));

    /**
     * Registers the registries to the given event bus.
     *
     * @param bus The event bus to register to.
     */
    @Internal
    static void init(IEventBus bus) {
        AMBlocks.register();
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
        AMSkillPoints.register();
        AMAffinities.register();
        AMSpellParts.register();
        AMAbilities.register();
        AMContingencyTypes.register();
        AMFeatures.register();
        BLOCKS.register(bus);
        FLUIDS.register(bus);
        ITEMS.register(bus);
        MOB_EFFECTS.register(bus);
        SOUND_EVENTS.register(bus);
        POTIONS.register(bus);
        ENCHANTMENTS.register(bus);
        ENTITIES.register(bus);
        BLOCK_ENTITIES.register(bus);
        PARTICLE_TYPES.register(bus);
        MENU_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        STAT_TYPES.register(bus);
        ATTRIBUTES.register(bus);
        DATA_SERIALIZERS.register(bus);
        SKILL_POINTS.register(bus);
        AFFINITIES.register(bus);
        SPELL_PARTS.register(bus);
        ABILITIES.register(bus);
        CONTINGENCY_TYPE.register(bus);
        FEATURES.register(bus);
        CONFIGURED_FEATURES.register(bus);
        PLACED_FEATURES.register(bus);
    }
}
