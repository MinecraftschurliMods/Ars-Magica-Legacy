package com.github.minecraftschurlimods.arsmagicalegacy.datagen.assets;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTranslations;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAbilities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEtheriumTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMagic;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMobEffects;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSounds;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpells;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.Map;

public final class AMLanguageProvider extends LanguageProvider {
    private final Map<String, String> cached;

    public AMLanguageProvider(PackOutput output, Map<String, String> cached) {
        super(output, ArsMagicaApi.MOD_ID, "en_us");
        this.cached = cached;
    }

    @Override
    protected void addTranslations() {
        cached.forEach(this::add);
        itemIdTranslation(AMItems.SPELL);
        itemIdTranslation(AMItems.SPELL_RECIPE);
        itemWithVariantTranslation(AMItems.ETHERIUM_PLACEHOLDER, AMEtheriumTypes.LIGHT.identifier(), "Light Etherium");
        itemWithVariantTranslation(AMItems.ETHERIUM_PLACEHOLDER, AMEtheriumTypes.NEUTRAL.identifier(), "Neutral Etherium");
        itemWithVariantTranslation(AMItems.ETHERIUM_PLACEHOLDER, AMEtheriumTypes.DARK.identifier(), "Dark Etherium");
        itemIdTranslation(AMItems.ETHERIUM_PLACEHOLDER);
        blockIdTranslation(AMBlocks.SPELL_LIGHT);
        blockIdTranslation(AMBlocks.SPELL_RUNE);
        blockIdTranslation(AMBlocks.LIQUID_ETHERIUM);
        blockIdTranslation(AMBlocks.LIQUID_ETHERIUM_CAULDRON);
        addItem(AMItems.LIQUID_ETHERIUM_BUCKET, "Liquid Etherium Bucket");
        blockIdTranslation(AMBlocks.OCCULUS);
        blockIdTranslation(AMBlocks.INSCRIPTION_TABLE);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
        itemIdTranslation(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
        blockIdTranslation(AMBlocks.ALTAR_CORE);
        blockIdTranslation(AMBlocks.MAGIC_WALL);
        blockIdTranslation(AMBlocks.OBELISK);
        blockIdTranslation(AMBlocks.CELESTIAL_PRISM);
        blockIdTranslation(AMBlocks.BLACK_AUREM);
        itemIdTranslation(AMItems.CRYSTAL_WRENCH);
        addBlock(AMBlocks.WIZARDS_CHALK, "Wizard's Chalk");
        addItem(AMItems.WIZARDS_CHALK, "Wizard's Chalk");
        blockIdTranslation(AMBlocks.REDSTONE_INLAY);
        blockIdTranslation(AMBlocks.IRON_INLAY);
        blockIdTranslation(AMBlocks.GOLD_INLAY);
        blockIdTranslation(AMBlocks.VINTEUM_TORCH);
        itemIdTranslation(AMItems.VINTEUM_TORCH);
        itemIdTranslation(AMItems.SPELL_PARCHMENT);
        itemIdTranslation(AMItems.SPELL_BOOK);
        itemIdTranslation(AMItems.MAGITECH_GOGGLES);
        itemIdTranslation(AMItems.MAGE_HELMET);
        itemIdTranslation(AMItems.MAGE_CHESTPLATE);
        itemIdTranslation(AMItems.MAGE_LEGGINGS);
        itemIdTranslation(AMItems.MAGE_BOOTS);
        itemIdTranslation(AMItems.BATTLEMAGE_HELMET);
        itemIdTranslation(AMItems.BATTLEMAGE_CHESTPLATE);
        itemIdTranslation(AMItems.BATTLEMAGE_LEGGINGS);
        itemIdTranslation(AMItems.BATTLEMAGE_BOOTS);
        itemIdTranslation(AMItems.MANA_CAKE);
        itemIdTranslation(AMItems.MANA_MARTINI);
        itemWithVariantTranslation(AMItems.INFINITY_ORB, AMMagic.BLUE_POINT.identifier(), "Blue Infinity Orb");
        itemWithVariantTranslation(AMItems.INFINITY_ORB, AMMagic.GREEN_POINT.identifier(), "Green Infinity Orb");
        itemWithVariantTranslation(AMItems.INFINITY_ORB, AMMagic.RED_POINT.identifier(), "Red Infinity Orb");
        itemIdTranslation(AMItems.INFINITY_ORB);
        addItem(AMItems.WINTERS_GRASP, "Winter's Grasp");
        itemIdTranslation(AMItems.NATURE_SCYTHE);
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, Affinity.NONE.identifier(), "Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.WATER.identifier(), "Water Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.FIRE.identifier(), "Fire Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.EARTH.identifier(), "Earth Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.AIR.identifier(), "Air Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.ICE.identifier(), "Ice Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.LIGHTNING.identifier(), "Lightning Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.NATURE.identifier(), "Nature Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.LIFE.identifier(), "Life Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.ARCANE.identifier(), "Arcane Affinity Essence");
        itemWithVariantTranslation(AMItems.AFFINITY_ESSENCE, AMMagic.ENDER.identifier(), "Ender Affinity Essence");
        itemIdTranslation(AMItems.AFFINITY_ESSENCE);
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, Affinity.NONE.identifier(), "None Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.WATER.identifier(), "Water Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.FIRE.identifier(), "Fire Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.EARTH.identifier(), "Earth Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.AIR.identifier(), "Air Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.ICE.identifier(), "Ice Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.LIGHTNING.identifier(), "Lightning Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.NATURE.identifier(), "Nature Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.LIFE.identifier(), "Life Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.ARCANE.identifier(), "Arcane Affinity Tome");
        itemWithVariantTranslation(AMItems.AFFINITY_TOME, AMMagic.ENDER.identifier(), "Ender Affinity Tome");
        itemIdTranslation(AMItems.AFFINITY_TOME);
        itemIdTranslation(AMItems.BLANK_RUNE);
        itemIdTranslation(AMItems.WHITE_RUNE);
        itemIdTranslation(AMItems.ORANGE_RUNE);
        itemIdTranslation(AMItems.MAGENTA_RUNE);
        itemIdTranslation(AMItems.LIGHT_BLUE_RUNE);
        itemIdTranslation(AMItems.YELLOW_RUNE);
        itemIdTranslation(AMItems.LIME_RUNE);
        itemIdTranslation(AMItems.PINK_RUNE);
        itemIdTranslation(AMItems.GRAY_RUNE);
        itemIdTranslation(AMItems.LIGHT_GRAY_RUNE);
        itemIdTranslation(AMItems.CYAN_RUNE);
        itemIdTranslation(AMItems.PURPLE_RUNE);
        itemIdTranslation(AMItems.BLUE_RUNE);
        itemIdTranslation(AMItems.BROWN_RUNE);
        itemIdTranslation(AMItems.GREEN_RUNE);
        itemIdTranslation(AMItems.RED_RUNE);
        itemIdTranslation(AMItems.BLACK_RUNE);
        itemIdTranslation(AMItems.RUNE_BAG);
        blockIdTranslation(AMBlocks.CHIMERITE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemIdTranslation(AMItems.CHIMERITE);
        addBlock(AMBlocks.CHIMERITE_BLOCK, "Block of Chimerite");
        blockIdTranslation(AMBlocks.TOPAZ_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemIdTranslation(AMItems.TOPAZ);
        addBlock(AMBlocks.TOPAZ_BLOCK, "Block of Topaz");
        blockIdTranslation(AMBlocks.VINTEUM_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemIdTranslation(AMItems.VINTEUM_DUST);
        addBlock(AMBlocks.VINTEUM_BLOCK, "Block of Vinteum");
        blockIdTranslation(AMBlocks.MOONSTONE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemIdTranslation(AMItems.MOONSTONE);
        addBlock(AMBlocks.MOONSTONE_BLOCK, "Block of Moonstone");
        blockIdTranslation(AMBlocks.SUNSTONE_ORE);
        itemIdTranslation(AMItems.SUNSTONE);
        addBlock(AMBlocks.SUNSTONE_BLOCK, "Block of Sunstone");
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        blockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.WITCHWOOD_WOOD);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_WOOD);
        blockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        blockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.POTTED_WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        blockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        blockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        itemIdTranslation(AMItems.WITCHWOOD_DOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        blockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_SIGN);
        blockIdTranslation(AMBlocks.WITCHWOOD_HANGING_SIGN);
        itemIdTranslation(AMItems.WITCHWOOD_SIGN);
        itemIdTranslation(AMItems.WITCHWOOD_HANGING_SIGN);
        itemIdTranslation(AMItems.WITCHWOOD_BOAT);
        itemIdTranslation(AMItems.WITCHWOOD_CHEST_BOAT);
        blockIdTranslation(AMBlocks.AUM);
        blockIdTranslation(AMBlocks.POTTED_AUM);
        blockIdTranslation(AMBlocks.CERUBLOSSOM);
        blockIdTranslation(AMBlocks.POTTED_CERUBLOSSOM);
        blockIdTranslation(AMBlocks.DESERT_NOVA);
        blockIdTranslation(AMBlocks.POTTED_DESERT_NOVA);
        blockIdTranslation(AMBlocks.TARMA_ROOT);
        blockIdTranslation(AMBlocks.POTTED_TARMA_ROOT);
        blockIdTranslation(AMBlocks.WAKEBLOOM);
        blockIdTranslation(AMBlocks.POTTED_WAKEBLOOM);
        itemIdTranslation(AMItems.WAKEBLOOM);
        itemIdTranslation(AMItems.DRYAD_SPAWN_EGG);
        itemIdTranslation(AMItems.MANA_CREEPER_SPAWN_EGG);
        itemIdTranslation(AMItems.WATER_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.FIRE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.EARTH_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.AIR_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ICE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.NATURE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.LIFE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ARCANE_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.ENDER_GUARDIAN_SPAWN_EGG);
        itemIdTranslation(AMItems.CRYSTAL_PHYLACTERY);
        addEntityType(AMEntities.WITCHWOOD_BOAT, "Boat");
        addEntityType(AMEntities.WITCHWOOD_CHEST_BOAT, "Chest Boat");
        entityIdTranslation(AMEntities.BLIZZARD);
        entityIdTranslation(AMEntities.FALLING_STAR);
        entityIdTranslation(AMEntities.FIRE_RAIN);
        entityIdTranslation(AMEntities.PROJECTILE);
        entityIdTranslation(AMEntities.WALL);
        entityIdTranslation(AMEntities.WAVE);
        entityIdTranslation(AMEntities.ZONE);
        entityIdTranslation(AMEntities.DRYAD);
        entityIdTranslation(AMEntities.MANA_CREEPER);
        entityIdTranslation(AMEntities.MANA_VORTEX);
        entityIdTranslation(AMEntities.WATER_GUARDIAN);
        entityIdTranslation(AMEntities.FIRE_GUARDIAN);
        entityIdTranslation(AMEntities.EARTH_GUARDIAN);
        entityIdTranslation(AMEntities.AIR_GUARDIAN);
        entityIdTranslation(AMEntities.ICE_GUARDIAN);
        entityIdTranslation(AMEntities.LIGHTNING_GUARDIAN);
        entityIdTranslation(AMEntities.NATURE_GUARDIAN);
        entityIdTranslation(AMEntities.LIFE_GUARDIAN);
        entityIdTranslation(AMEntities.ARCANE_GUARDIAN);
        entityIdTranslation(AMEntities.ENDER_GUARDIAN);
        addEntityType(AMEntities.WINTERS_GRASP, "Winter's Grasp");
        entityIdTranslation(AMEntities.NATURE_SCYTHE);
        entityIdTranslation(AMEntities.SHOCKWAVE);
        entityIdTranslation(AMEntities.THROWN_ROCK);
        entityIdTranslation(AMEntities.WHIRLWIND);
        effectIdTranslation(AMMobEffects.ASTRAL_DISTORTION);
        effectIdTranslation(AMMobEffects.BURNOUT_REDUCTION);
        effectIdTranslation(AMMobEffects.CLARITY);
        effectIdTranslation(AMMobEffects.ENTANGLE);
        effectIdTranslation(AMMobEffects.FLIGHT);
        effectIdTranslation(AMMobEffects.FURY);
        effectIdTranslation(AMMobEffects.GRAVITY_WELL);
        effectIdTranslation(AMMobEffects.ILLUMINATION);
        effectIdTranslation(AMMobEffects.INSTANT_MANA);
        effectIdTranslation(AMMobEffects.MANA_BOOST);
        effectIdTranslation(AMMobEffects.MANA_REGENERATION);
        effectIdTranslation(AMMobEffects.REFLECT);
        effectIdTranslation(AMMobEffects.SCRAMBLE_SYNAPSES);
        effectIdTranslation(AMMobEffects.SHRINK);
        effectIdTranslation(AMMobEffects.SILENCE);
        effectIdTranslation(AMMobEffects.SWIFT_SWIM);
        effectIdTranslation(AMMobEffects.TEMPORAL_ANCHOR);
        effectIdTranslation(AMMobEffects.TRUE_SIGHT);
        effectIdTranslation(AMMobEffects.WATERY_GRAVE);
        potionIdTranslation(AMMobEffects.LESSER_MANA);
        potionIdTranslation(AMMobEffects.STANDARD_MANA);
        potionIdTranslation(AMMobEffects.GREATER_MANA);
        potionIdTranslation(AMMobEffects.EPIC_MANA);
        potionIdTranslation(AMMobEffects.LEGENDARY_MANA);
        potionIdTranslation(AMMobEffects.INFUSED_MANA);
        attributeIdTranslation(AMAttributes.MANA_REGENERATION);
        attributeIdTranslation(AMAttributes.BURNOUT_REGENERATION);
        attributeIdTranslation(AMAttributes.MAX_MANA);
        attributeIdTranslation(AMAttributes.MAX_BURNOUT);
        add(AMTags.Blocks.ORES_CHIMERITE, "Chimerite Ores");
        add(AMTags.Blocks.ORES_TOPAZ, "Topaz Ores");
        add(AMTags.Blocks.ORES_VINTEUM, "Vinteum Ores");
        add(AMTags.Blocks.ORES_MOONSTONE, "Moonstone Ores");
        add(AMTags.Blocks.ORES_SUNSTONE, "Sunstone Ores");
        add(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, "Chimerite Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, "Topaz Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, "Vinteum Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, "Moonstone Storage Blocks");
        add(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE, "Sunstone Storage Blocks");
        add(AMTags.Blocks.WITCHWOOD_LOGS, "Witchwood Logs");
        add(AMTags.Blocks.AUM_PLANTABLE_ON, "Soil for Aum");
        add(AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON, "Soil for Cerublossom");
        add(AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON, "Soil for Desert Nova");
        add(AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON, "Soil for Tarma Root");
        add(AMTags.Blocks.DRYADS_SPAWNABLE_ON, "Ground for Dryads");
        add(AMTags.Blocks.WIZARDS_AUTUMN_LEAVES, "Wizard's Autumn Leaves");
        add(AMTags.Blocks.ETHERIUM_PROVIDERS, "Etherium Providers");
        add(AMTags.Blocks.ETHERIUM_CONSUMERS, "Etherium Consumers");
        add(AMTags.Items.ORES_CHIMERITE, "Chimerite Ores");
        add(AMTags.Items.ORES_TOPAZ, "Topaz Ores");
        add(AMTags.Items.ORES_VINTEUM, "Vinteum Ores");
        add(AMTags.Items.ORES_MOONSTONE, "Moonstone Ores");
        add(AMTags.Items.ORES_SUNSTONE, "Sunstone Ores");
        add(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, "Chimerite Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_TOPAZ, "Topaz Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_VINTEUM, "Vinteum Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_MOONSTONE, "Moonstone Storage Blocks");
        add(AMTags.Items.STORAGE_BLOCKS_SUNSTONE, "Sunstone Storage Blocks");
        add(AMTags.Items.GEMS_CHIMERITE, "Chimerite Gems");
        add(AMTags.Items.GEMS_TOPAZ, "Topaz Gems");
        add(AMTags.Items.DUSTS_VINTEUM, "Vinteum Dusts");
        add(AMTags.Items.GEMS_MOONSTONE, "Moonstone Gems");
        add(AMTags.Items.GEMS_SUNSTONE, "Sunstone Gems");
        add(AMTags.Items.DUSTS_ARCANE_COMPOUND, "Arcane Compound Dusts");
        add(AMTags.Items.DUSTS_ARCANE_ASH, "Arcane Ash Dusts");
        add(AMTags.Items.DUSTS_PURIFIED_VINTEUM, "Purified Vinteum Dusts");
        add(AMTags.Items.WITCHWOOD_LOGS, "Witchwood Logs");
        add(AMTags.Items.MAGITECH_GOGGLES_REPAIR_ITEMS, "Magitech Goggles Repair Items");
        add(AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, "Mage Armor Repair Items");
        add(AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, "Battlemage Armor Repair Items");
        add(AMTags.Items.ARCANE_COMPENDIUM_BOOKS, "Arcane Compendium Books");
        add(AMTags.Items.INSCRIPTION_TABLE_BOOKS, "Inscription Table Books");
        add(AMTags.Items.OCCULUS_FORGET_ALL, "Occulus Forgetting Items");
        add(AMTags.Items.RUNES, "Runes");
        add(AMTags.Items.SHOWS_BARS_LAYER, "Shows Bars Layer");
        add(AMTags.Items.SHOWS_SPELL_VISUALS, "Shows Spell Visuals");
        add(AMTags.Items.SPELLCRAFTING_START, "Spellcrafting Start Items");
        add(AMTags.Items.SPELLCRAFTING_END, "Spellcrafting End Items");
        add(AMTags.EntityTypes.BLACK_AUREM_IMMUNE, "Immune to Black Aurem");
        add(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY, "Affected by the Ender Thorns Ability");
        add(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY, "Affected by the Smite Ability");
        add(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY, "Affected by the Nausea Ability");
        add(AMTags.EntityTypes.ENDER_GUARDIAN_SACRIFICES, "Ender Guardian Sacrifices");
        add(AMTags.EntityTypes.SUMMONING_NOT_SUPPORTED, "Summoning not supported");
        add(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY, "Affected by the Fire Resistance Ability");
        add(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY, "Affected by the Resistance Ability");
        add(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY, "Affected by the Fall Damage Ability");
        add(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY, "Affected by the Feather Falling Ability");
        add(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY, "Affected by the Magic Damage Ability");
        add(AMTags.DamageTypes.BYPASSES_SHIELD_OVERLOAD, "Bypasses Shield Overload");
        add(AMTags.DamageTypes.IS_SPELL, "Spell Damage");
        add(AMTags.DamageTypes.WATER_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Water Guardian");
        add(AMTags.DamageTypes.WATER_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Water Guardian");
        add(AMTags.DamageTypes.WATER_GUARDIAN_IS_HEAL_TO, "Heals the Water Guardian");
        add(AMTags.DamageTypes.FIRE_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Fire Guardian");
        add(AMTags.DamageTypes.FIRE_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Fire Guardian");
        add(AMTags.DamageTypes.FIRE_GUARDIAN_IS_HEAL_TO, "Heals the Fire Guardian");
        add(AMTags.DamageTypes.EARTH_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Earth Guardian");
        add(AMTags.DamageTypes.EARTH_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Earth Guardian");
        add(AMTags.DamageTypes.EARTH_GUARDIAN_IS_HEAL_TO, "Heals the Earth Guardian");
        add(AMTags.DamageTypes.AIR_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Air Guardian");
        add(AMTags.DamageTypes.AIR_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Air Guardian");
        add(AMTags.DamageTypes.AIR_GUARDIAN_IS_HEAL_TO, "Heals the Air Guardian");
        add(AMTags.DamageTypes.ICE_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Ice Guardian");
        add(AMTags.DamageTypes.ICE_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Ice Guardian");
        add(AMTags.DamageTypes.ICE_GUARDIAN_IS_HEAL_TO, "Heals the Ice Guardian");
        add(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Lightning Guardian");
        add(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Lightning Guardian");
        add(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_HEAL_TO, "Heals the Lightning Guardian");
        add(AMTags.DamageTypes.NATURE_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Nature Guardian");
        add(AMTags.DamageTypes.NATURE_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Nature Guardian");
        add(AMTags.DamageTypes.NATURE_GUARDIAN_IS_HEAL_TO, "Heals the Nature Guardian");
        add(AMTags.DamageTypes.LIFE_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Life Guardian");
        add(AMTags.DamageTypes.LIFE_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Life Guardian");
        add(AMTags.DamageTypes.LIFE_GUARDIAN_IS_HEAL_TO, "Heals the Life Guardian");
        add(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Arcane Guardian");
        add(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Arcane Guardian");
        add(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_HEAL_TO, "Heals the Arcane Guardian");
        add(AMTags.DamageTypes.ENDER_GUARDIAN_IS_VULNERABLE_TO, "Is vulnerability of the Ender Guardian");
        add(AMTags.DamageTypes.ENDER_GUARDIAN_IS_IMMUNE_TO, "Is immunity of the Ender Guardian");
        add(AMTags.DamageTypes.ENDER_GUARDIAN_IS_HEAL_TO, "Heals the Ender Guardian");
        add(AMTags.Biomes.CAN_SUMMON_WATER_GUARDIAN, "Can Summon Water Guardian");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_AMBIENT, "Arcane Guardian hisses");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_ATTACK, "Arcane Guardian attacks");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_DEATH, "Arcane Guardian dies");
        subtitleTranslation(AMSounds.ARCANE_GUARDIAN_HURT, "Arcane Guardian hurts");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_AMBIENT, "Earth Guardian rumbles");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_ATTACK, "Earth Guardian attacks");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_DEATH, "Earth Guardian dies");
        subtitleTranslation(AMSounds.EARTH_GUARDIAN_HURT, "Earth Guardian hurts");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_AMBIENT, "Ender Guardian hisses");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_ATTACK, "Ender Guardian attacks");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_DEATH, "Ender Guardian dies");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_HURT, "Ender Guardian hurts");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_AMBIENT, "Fire Guardian cackles");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_ATTACK, "Fire Guardian attacks");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_DEATH, "Fire Guardian dies");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_HURT, "Fire Guardian hurts");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_AMBIENT, "Ice Guardian cracks");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_DEATH, "Ice Guardian dies");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_AMBIENT, "Life Guardian hums");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_ATTACK, "Life Guardian attacks");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_DEATH, "Life Guardian dies");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_HURT, "Life Guardian hurts");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_AMBIENT, "Lightning Guardian zaps");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_ATTACK, "Lightning Guardian attacks");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_DEATH, "Lightning Guardian dies");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_HURT, "Lightning Guardian hurts");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_AMBIENT, "Nature Guardian hisses");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_ATTACK, "Nature Guardian attacks");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_DEATH, "Nature Guardian dies");
        subtitleTranslation(AMSounds.NATURE_GUARDIAN_HURT, "Nature Guardian hurts");
        subtitleTranslation(AMSounds.WATER_GUARDIAN_AMBIENT, "Water Guardian bubbles");
        subtitleTranslation(AMSounds.WATER_GUARDIAN_DEATH, "Water Guardian dies");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_FLAP, "Ender Guardian flaps");
        subtitleTranslation(AMSounds.ENDER_GUARDIAN_ROAR, "Ender Guardian roars");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_FLAMETHROWER, "Fire Guardian burns");
        subtitleTranslation(AMSounds.FIRE_GUARDIAN_NOVA, "Fire Guardian shoots");
        subtitleTranslation(AMSounds.ICE_GUARDIAN_LAUNCH_ARM, "Ice Guardian launches arm");
        subtitleTranslation(AMSounds.LIFE_GUARDIAN_HEAL, "Life Guardian heals");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD, "Lightning Guardian summons lightning");
        subtitleTranslation(AMSounds.LIGHTNING_GUARDIAN_STATIC, "Lightning Guardian thunders");
        subtitleTranslation(AMSounds.CAST_AIR, "Air spell is cast");
        subtitleTranslation(AMSounds.CAST_ARCANE, "Arcane spell is cast");
        subtitleTranslation(AMSounds.CAST_EARTH, "Earth spell is cast");
        subtitleTranslation(AMSounds.CAST_ENDER, "Ender spell is cast");
        subtitleTranslation(AMSounds.CAST_FIRE, "Fire spell is cast");
        subtitleTranslation(AMSounds.CAST_ICE, "Ice spell is cast");
        subtitleTranslation(AMSounds.CAST_LIFE, "Life spell is cast");
        subtitleTranslation(AMSounds.CAST_LIGHTNING, "Lightning spell is cast");
        subtitleTranslation(AMSounds.CAST_NATURE, "Nature spell is cast");
        subtitleTranslation(AMSounds.CAST_NONE, "Spell is cast");
        subtitleTranslation(AMSounds.CAST_WATER, "Water spell is cast");
        subtitleTranslation(AMSounds.LOOP_AIR, "Air spell is looped");
        subtitleTranslation(AMSounds.LOOP_ARCANE, "Arcane spell is looped");
        subtitleTranslation(AMSounds.LOOP_EARTH, "Earth spell is looped");
        subtitleTranslation(AMSounds.LOOP_ENDER, "Ender spell is looped");
        subtitleTranslation(AMSounds.LOOP_FIRE, "Fire spell is looped");
        subtitleTranslation(AMSounds.LOOP_ICE, "Ice spell is looped");
        subtitleTranslation(AMSounds.LOOP_LIFE, "Life spell is looped");
        subtitleTranslation(AMSounds.LOOP_LIGHTNING, "Lightning spell is looped");
        subtitleTranslation(AMSounds.LOOP_NATURE, "Nature spell is looped");
        subtitleTranslation(AMSounds.LOOP_WATER, "Water spell is looped");
        subtitleTranslation(AMSounds.CONTINGENCY, "Contingency sparkles");
        subtitleTranslation(AMSounds.FALLING_STAR, "Star falls down");
        subtitleTranslation(AMSounds.INFINITY_ORB, "Infinity Orb vanishes");
        subtitleTranslation(AMSounds.LEVEL_UP, "Magic jingle");
        subtitleTranslation(AMSounds.MANA_SHIELD, "Mana Shield is raised");
        subtitleTranslation(AMSounds.RUNE, "Rune activates");
        subtitleTranslation(AMSounds.SPELLCRAFTING_ADD_INGREDIENT, "Spellcrafting Altar blings");
        subtitleTranslation(AMSounds.SPELLCRAFTING_FINISH, "Spellcrafting Altar jingles");
        subtitleTranslation(AMSounds.TAKE_BOOK, "Book is taken");
        advancementTranslation("root", "Ars Magica: Legacy", "A renewed look into Minecraft with a splash of magic...");
        advancementTranslation("skill", "It's A Kind Of Magic", "Learn a skill");
        advancementTranslation("all_skills", "A Full Compendium", "Learn all skills");
        advancementTranslation("hidden_skill", "Forgotten Magic", "Learn a silver skill");
        advancementTranslation("all_hidden_skills", "Sorcerer Supreme", "Learn all silver skills");
        advancementTranslation("spell", "You're A Wizard, Harry!", "Craft your first spell");
        advancementTranslation("affinity_one_percent", "Supernatural", "Shift into an affinity");
        advancementTranslation("affinity_fifty_percent", "Side Effects", "Shift deep enough into an affinity to experience negative effects");
        advancementTranslation("affinity_full", "Locked In", "Lock into an affinity");
        advancementTranslation("affinity_tome", "Breaking The Curse", "Break out of an affinity lock");
        advancementTranslation("level_10", "Getting Stronger", "Reach magic level 10");
        advancementTranslation("level_100", "Archmage", "Reach magic level 100");
        skillTranslation(AMSpells.ABSORPTION.getId(), "Absorption", "Like a slightly flimsier shield.", "components", "You gain absorption hearts, like you would when eating a golden apple. This does not stack with golden apples.");
        skillTranslation(AMSpells.AREA_OF_EFFECT.getId(), "Area of Effect", "All around me!", "shapes", "After charging your spell, you can shape it into a blast that radiates outwards from the spell's origin. An AoE spell will not affect the caster.");
        skillTranslation(AMSpells.ASTRAL_DISTORTION.getId(), "Astral Distortion", "Going nowhere.", "components", "This spell entirely prevents teleportation of the target for some time. Also works on endermen and shulkers!");
        skillTranslation(AMSpells.ATTRACT.getId(), "Attract", "You go there.", "components", "You create an area of negative pressure, pulling everything but you towards the target position.");
        skillTranslation(AMSpells.BANISH_RAIN.getId(), "Banish Rain", "Come back later. Or don't. It would be kind.", "components", "Rain rain, go away. Come again another day!$(br2)Who would have thought that those were the words to the spell?");
        skillTranslation(AMSpells.BEAM.getId(), "Beam", "Beam me up, Scotty!", "shapes", "You can fire a concentrated beam of magic at your target. The maximum range of the beam is 64 blocks, which is more than enough for most use cases. Be warned that this requires A LOT of mana.");
        skillTranslation(AMSpells.BLINDNESS.getId(), "Blindness", "Just as the name says.", "components", "Having a fireball to throw at dark mages is good. But making it so they also can't see to retaliate is better.");
        skillTranslation(AMSpells.BLINK.getId(), "Blink", "Well, I'm out.", "components", "You can teleport a short distance directly forward the way you are facing.$(br2)Blink can take you through solid walls, but will make every effort to ensure you don't get stuck in one.");
        skillTranslation(AMSpells.BLIZZARD.getId(), "Blizzard", "Snow. Lots of snow.", "components", "You have learned to summon a fearsome blizzard, which will slow and damage any entities in its radius.$(br2)Blizzard has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Though blizzard will harm all entities in its radius, it will never harm its caster.");
        skillTranslation(AMSpells.BOUNCE.getId(), "Bounce", "We do a little trolling.", "modifiers", "Causes $(l:shapes/projectile)spell projectiles$() to bounce off surfaces.");
        skillTranslation(AMSpells.CHAIN.getId(), "Chain", "Looks like you brought friends. Well, I don't mind, you're all gonna die.", "shapes", "You can modify your Beam spell to jump from target to target, hitting up to five enemies, at the cost of range - it is now limited to 16 blocks instead of 64. The jump range between targets is 4 blocks, but can be extended using $(l:modifiers/range)Range$() modifiers.$(br2)The spell will never harm its caster. When the spell is used on a block, the chaining behavior does not occur, and it will act like a regular beam.");
        skillTranslation(AMSpells.CHANNEL.getId(), "Channel", "You might want to concentrate.", "shapes", "Through intense concentration, you can maintain a flow of magic on yourself.$(br2)This is useful for things like $(l:components/heal)Heal$(), $(l:components/attract)Attract$() and $(l:components/repel)Repel$().");
        skillTranslation(AMSpells.CHARM.getId(), "Charm", "One plus one is three!", "components", "You can cause breedable creatures to breed.");
        skillTranslation(AMSpells.COLOR.getId(), "Color", "Rainbow!", "modifiers", "This modifier allows changing the particles of a spell component. Want green fireballs? Have at it!$(br2)The color is determined during spell creation. When adding the modifier, choose the color you want. If you want to change the color, you can do so by right-clicking the modifier again. The color cannot be changed after the spell is crafted!");
        skillTranslation(AMSpells.CONTINGENCY_DAMAGE.getId(), "Contingency: Damage", "Hurting me? That would be bad.", "shapes", "You have managed to create a spell that triggers only when you get hurt.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpells.CONTINGENCY_DEATH.getId(), "Contingency: Death", "You're coming with me.", "shapes", "Enemies may get you, but in those few seconds it takes, you will have all the time you need to ensure they come along for the ride.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpells.CONTINGENCY_FALL.getId(), "Contingency: Fall", "The higher you climb, the harder you fall.", "shapes", "You have figured out a spell shape that triggers when falling the instant before you hit the ground.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpells.CONTINGENCY_FIRE.getId(), "Contingency: Fire", "You shall (not) burn!", "shapes", "You've decided you really don't like being on fire anymore. To that end, you made a spell that will light up when the flames do.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpells.CONTINGENCY_HEALTH.getId(), "Contingency: Health", "I'm not going down. Not right now.", "shapes", "No matter your power, a knife between the shoulder blades will seriously cramp your style. You have found a way to make your enemies regret trying that, though. Or need two knives. This contingency triggers when your health is less than or equal to 25%% of maximum.$(br2)You can only have one contingency active at a time.");
        skillTranslation(AMSpells.CREATE_WATER.getId(), "Create Water", "Please help me, I'm under the water.", "components", "You can coalesce moisture from the air around into one location, creating water where there was none. It can also be used to fill a cauldron.$(br2)This will not work in the nether.");
        skillTranslation(AMSpells.DAMAGE.getId(), "Damage", "Now it hurts.", "modifiers", "Amplifies the damage dealt by spells, or the healing done by damage spells to the undead.");
        skillTranslation(AMSpells.DAYLIGHT.getId(), "Daylight", "Does that mean I can control time?", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dawn.");
        skillTranslation(AMSpells.DIG.getId(), "Dig", "Diggy Diggy Hole!", "components", "The ground shatters with a snap of your fingers. Harder blocks take more mana to break.$(br2)Dig starts out equivalent to an Iron pickaxe, but can be upgraded with the use of the $(l:modifiers/mining_power)Mining Power$() modifier.");
        skillTranslation(AMSpells.DISARM.getId(), "Disarm", "Woops, you dropped something?", "components", "Now that you have learned to summon tools to your hand, it was a small step to be able to make others drop what they are holding.");
        skillTranslation(AMSpells.DISMEMBERING.getId(), "Dismembering", "Wasn't me. I swear he had no head when I came in.", "modifiers", "You like souvenirs so much that you have discovered how to make your damaging spells leave some pieces intact.$(br2)Each modifier adds a 5%% chance to drop a head when defeating an enemy.");
        skillTranslation(AMSpells.DISPEL.getId(), "Dispel", "Witches have no say here.", "components", "Creating a localized field of deficit, you can remove up to six levels of potion effects on your target.");
        skillTranslation(AMSpells.DIVINE_INTERVENTION.getId(), "Divine Intervention", "Dimension-hopping! Yay!", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the overworld from anywhere, except the nether.");
        skillTranslation(AMSpells.DROUGHT.getId(), "Drought", "Heat. Lots of heat.", "components", "You have taken your knowledge of creating water and have reversed the process.$(br2)This spell will draw water out of whatever it hits, removing water blocks, turning dirt-ish blocks to sand, withering plants it hits, and cracking stone to cobblestone.");
        skillTranslation(AMSpells.DROWNING_DAMAGE.getId(), "Drowning Damage", "How can you drown? There isn't any water.", "components", "You can create water directly inside the target's lungs, causing them to take drowning damage.");
        skillTranslation(AMSpells.DURATION.getId(), "Duration", "Time manipulation tricks.", "modifiers", "Enhances the duration of all effect spells, and increases the lifetime of $(l:shapes/projectile)projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones.");
        skillTranslation(AMSpells.EFFECT_POWER.getId(), "Effect Power", "Harder, better, faster and my mana pool is empty.", "modifiers", "You can put more power into your effects. Each modifier added increases the level of the effect applied by one.");
        skillTranslation(AMSpells.ENDER_INTERVENTION.getId(), "Ender Intervention", "But in the End, it doesn't even matter!", "components", "You have mastered teleportation magic, to the level at which you can transcend interdimensional barriers, and can enter the end from anywhere, except the nether.");
        skillTranslation(AMSpells.ENTANGLE.getId(), "Entangle", "Stop right there.", "components", "At your command, vines can burst from the ground and ensnare your target, holding them completely immobile.");
        skillTranslation(AMSpells.EXPLOSION.getId(), "Explosion", "Creeper? Aww Man!", "components", "You can cause an explosion, destroying and dropping blocks around its center.");
        skillTranslation(AMSpells.FALLING_STAR.getId(), "Falling Star", "Shiny! Wait, is it falling towards me?", "components", "You can call down a star from the skies and cause it to strike all entities within the blast radius. It will harm friendly targets but not the caster, and pierce through walls.$(br2)There is a short delay between casting the spell and the impact. This spell will not work underground (the star will fall onto the surface).");
        skillTranslation(AMSpells.FIRE_DAMAGE.getId(), "Fire Damage", "You shall burn!", "components", "With a word, you can release your will, and fire will erupt from out in front of you, searing everything in its path. Fire damage is hard hitting, but many nether mobs are immune to its effects.");
        skillTranslation(AMSpells.FIRE_RAIN.getId(), "Fire Rain", "Through the fire and the flames!", "components", "You have learned to summon a terrible firestorm, which will do large amounts of damage to all entities in its radius. Firestorm has a built-in $(l:shapes/aoe)AoE$() to it.$(br2)Firestorm does not ignite the ground and will never harm its caster.");
        skillTranslation(AMSpells.FLIGHT.getId(), "Flight", "Does this count as cheating?", "components", "With a word, you can rise into the air.");
        skillTranslation(AMSpells.FLING.getId(), "Fling", "Ready for an air fight?", "components", "This spell makes wind whirl around under your target, and suddenly all at once blow them straight up, sending them skyward.");
        skillTranslation(AMSpells.FORGE.getId(), "Forge", "Portable furnace.", "components", "You have gained fine control over fire and can use it to magically smelt blocks where they stand, without charring them to ash.");
        skillTranslation(AMSpells.FROST.getId(), "Frost", "Freeze!", "components", "You breathe deeply and open your eyes. Water will become ice. Enemies move at a crawl. Perfect.");
        skillTranslation(AMSpells.FROST_DAMAGE.getId(), "Frost Damage", "Let it snow!", "components", "Many underestimate the power that frost can wield. The creeping chill can bypass many armors.");
        skillTranslation(AMSpells.FURY.getId(), "Fury", "Berserker rage!", "components", "You can send yourself into an absolute rage, dealing increased damage, moving extremely fast, passively regenerating, and mining at inhuman speeds.$(br2)When the effect ends, you are left exhausted for a few moments and must recover.");
        skillTranslation(AMSpells.GRAVITY.getId(), "Gravity", "Created by Isaac Newton.", "modifiers", "$(l:shapes/zone)Zones$() and $(l:shapes/projectile)projectiles$() will be affected by gravity.");
        skillTranslation(AMSpells.GRAVITY_WELL.getId(), "Gravity Well", "Not like a chicken. The opposite.", "components", "You have learned to create a localized gravity well under your target, greatly increasing the speed at which they fall.");
        skillTranslation(AMSpells.GROW.getId(), "Grow", "I won't sit all day.", "components", "Pouring energy into plants, equal to months of talking to them, will cause them to grow more rapidly.");
        skillTranslation(AMSpells.HARVEST.getId(), "Harvest", "Add a hammer and start a revolution.", "components", "You can use magic to harvest fully-grown plants. Note: For this to work properly with most plants, add a $(l:modifiers/target_non_solid)Target Non Solid$() modifier to your spell.");
        skillTranslation(AMSpells.HASTE.getId(), "Haste", "Mining away!", "components", "Wrapping the target's hands in arcane energy, you can greatly increase mining speed.");
        skillTranslation(AMSpells.HEAL.getId(), "Heal", "Instant healing.", "components", "By greatly increasing the amount of power put into regenerative effects, you can knit almost any injury back together. The effect is taxing, however.");
        skillTranslation(AMSpells.HEALING.getId(), "Healing", "Efficiency over number.", "modifiers", "Amplifies the healing done by spells, or the damage dealt by healing spells to the undead.");
        skillTranslation(AMSpells.HEALTH_BOOST.getId(), "Health Boost", "1 UP!", "components", "Your target receives a temporary boost in health, allowing them to live longer. The extra health is not automatically healed, it must be regenerated using conventional methods first.");
        skillTranslation(AMSpells.IGNITION.getId(), "Ignition", "Burn harder!", "components", "You see fire as a damaging, destructive force, and that can be true.$(br2)But how often do you hear fire and imagine lighting a campfire or candle?");
        skillTranslation(AMSpells.INVISIBILITY.getId(), "Invisibility", "Wanna play Hide & Seek?", "components", "You can bend light around yourself to become effectively invisible.");
        skillTranslation(AMSpells.JUMP_BOOST.getId(), "Jump Boost", "Not a frog? Who cares?", "components", "Gathering wind around you, you can propel yourself into the air.");
        skillTranslation(AMSpells.KNOCKBACK.getId(), "Knockback", "Punch from a distance!", "components", "As a mage, you most likely don't want to be in melee range. This component allows that situation to be corrected.");
        skillTranslation(AMSpells.LEVITATION.getId(), "Levitation", "Use the force.", "components", "Through practicing air magic, you can now hold yourself suspended in midair.$(br2)With small wind currents, you can move slowly about while floating.");
        skillTranslation(AMSpells.LIFE_DRAIN.getId(), "Life Drain", "I'm taking all of it. Including you.", "components", "By creating a sinister link with the target's life force, you can siphon it off into your own, bolstering your own health.");
        skillTranslation(AMSpells.LIFE_TAP.getId(), "Life Tap", "I'm borrowing this.", "components", "If you are desparate and mana is scarce, you can fuel your spells using your own life force.");
        skillTranslation(AMSpells.LIGHT.getId(), "Light", "The end of a tunnel.", "components", "You can light up an area with magic.$(br2)If you apply this component onto a living being, it will light up the darkness by itself.");
        skillTranslation(AMSpells.LIGHTNING_DAMAGE.getId(), "Lightning Damage", "Zap!", "components", "Lightning does an exceptional amount of damage, but carries a hefty mana cost.");
        skillTranslation(AMSpells.LUNAR.getId(), "Lunar", "I'm gonna be a werewolf!", "modifiers", "Powers up your spell during the night. The closer to midnight it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching full moon).$(br2)Lunar is more powerful than $(l:modifiers/solar)Solar$() due to nights not lasting as long as day.");
        skillTranslation(AMSpells.MAGIC_DAMAGE.getId(), "Magic Damage", "Hit from the void!", "components", "Magical damage differs from physical damage in that it bypasses many kinds of armors and attacks the target's aura directly - which can be just as devastating, if not more.");
        skillTranslation(AMSpells.MANA_BLAST.getId(), "Mana Blast", "I love mana, especially when it blows up in someone's face.", "components", "Your entire mana is used up to damage the target. The more mana you had, the more damage it does!");
        skillTranslation(AMSpells.MANA_DRAIN.getId(), "Mana Drain", "So many pools at my disposal!", "components", "You can create a parasitic bond with the target's aura, draining their mana and boosting your own.");
        skillTranslation(AMSpells.MINING_POWER.getId(), "Mining Power", "Who needs diamonds?", "modifiers", "You have learned to put more power into your digging spells. This causes them to be able to dig more dense blocks that would require a better tool.$(br2)Each modifier bumps the spell up by one tool level.$(br2)The base $(l:components/dig)dig$() component operates at iron mining level.");
        skillTranslation(AMSpells.MOONRISE.getId(), "Moonrise", "Full moon.", "components", "You have gained the ability to control time.$(br2)This spell will cause the time to move to dusk.");
        skillTranslation(AMSpells.NIGHT_VISION.getId(), "Night Vision", "Oh? There was a tunnel?", "components", "Your knowledge of light has allowed you to devise a spell that will let you amplify light levels, effectively letting you see in the dark.");
        skillTranslation(AMSpells.PHYSICAL_DAMAGE.getId(), "Physical Damage", "Magical swords. Why not?", "components", "Often, you will begin your training with simple physical force. Force is a physical damage type, and does not pierce armor.");
        skillTranslation(AMSpells.PIERCING.getId(), "Piercing", "Armor, here I come!", "modifiers", "Allows $(l:shapes/projectile)projectiles$() to pierce through entities and blocks.");
        skillTranslation(AMSpells.PLACE_BLOCK.getId(), "Place Block", "Don't mind me, I'm just sending an anvil.", "components", "You can use this spell part to place blocks! In order to place a block, you need to set the spell to place it (shift-use on the block), and you need to have at least one of said block in your inventory.");
        skillTranslation(AMSpells.PLOW.getId(), "Plow", "Hoes are useless. Everyone knows that.", "components", "You can cause the earth to churn at your command, creating deep furrows ideal for planting.");
        skillTranslation(AMSpells.PROJECTILE.getId(), "Projectile", "Snowball!", "shapes", "You are able to focus your will into a concentrated ball, which is then propelled forwards away from you.$(br2)The projectile will last for five seconds of flight, or until it strikes something.$(br2)It will by default pass through water and non-collidable blocks unless you modify it with $(l:modifiers/target_non_solid)Target Non Solid$().");
        skillTranslation(AMSpells.PROSPERITY.getId(), "Prosperity", "Bling!", "modifiers", "Fortune strikes! You can make your digging spells more likely to drop additional ores, and your damaging spells more likely to cause enemies to drop better loot.$(br2)Each modifier added is equivalent to one level of fortune/looting on the spell.");
        skillTranslation(AMSpells.RANDOM_TELEPORT.getId(), "Random Teleport", "I wanna go there! No, the other there!", "components", "You can randomly teleport your target a short distance away.");
        skillTranslation(AMSpells.RANGE.getId(), "Range", "Think you're far enough? No, you're not.", "modifiers", "Increases the range/size of many spells.");
        skillTranslation(AMSpells.RECALL.getId(), "Recall", "I don't recall leaving my house.", "components", "You can tune your teleportation magic to home in on a mark you have left by shift-using the spell, transporting the target back to that location.");
        skillTranslation(AMSpells.REFLECT.getId(), "Reflect", "Bounces back to you.", "components", "You create a magic shield that $(l:shapes/projectile)spell projectiles$(), $(l:shapes/wall)walls$(), $(l:shapes/wave)waves$() and $(l:shapes/zone)zones$() will bounce off.");
        skillTranslation(AMSpells.REGENERATION.getId(), "Regeneration", "A little bit of health.", "components", "I wrapped my arm in a healing light, and watched as every injury, down to the last bruise, slowly vanished before my eyes.");
        skillTranslation(AMSpells.REPEL.getId(), "Repel", "Go away from me!", "components", "You can create a singularity in space, which, as long as you maintain it, will radiate waves of force, pushing anything but you away from the target position.");
        skillTranslation(AMSpells.REPLANT.getId(), "Replant", "Why bother using hand when magic can do the same?", "components", "In addition to $(l:components/harvest)harvesting$(), you have also learned to replant your crops while you are at it.");
        skillTranslation(AMSpells.RESISTANCE.getId(), "Resistance", "Like a shield.", "components", "You can summon arcane energy to shield yourself, reducing physical damage.");
        skillTranslation(AMSpells.RIFT.getId(), "Rift", "One day I'll walk through it, for now, it'll just store items.", "components", "You can tear open a rift in space, granting access to a small inventory to store items in. More $(l:modifiers/effect_power)effect power$() modifiers give greater storage access.$(br2)You can, if your friends are foolish enough, also open their personal rift instead.");
        skillTranslation(AMSpells.RUNE.getId(), "Rune", "Placeable magic.", "shapes", "You can create a magically infused rune on the ground that, when someone steps on them, can apply powerful buffs - or trigger deadly traps.");
        skillTranslation(AMSpells.RUNE_POWER.getId(), "Rune Power", "I want more!", "modifiers", "Increases the number of times a $(l:shapes/rune)rune$() can apply its effect before being destroyed.");
        skillTranslation(AMSpells.SELF.getId(), "Self", "It's all about me.", "shapes", "One of the simplest forms of magic application is applying the magic to yourself. The distance is low, and the target is willing. You only hope you don't accidentally light yourself on fire.");
        skillTranslation(AMSpells.SHRINK.getId(), "Shrink", "Looks like I'm smaller now!", "components", "You can make yourself tiny! When this effect is active, you are physically smaller, so you can fit through 1x1 gaps.$(br2)Due to your light weight, you fall slowly enough that landing doesn't hurt either. However, all damage you do is halved.");
        skillTranslation(AMSpells.SILENCE.getId(), "Silence", "No talking! (Or casting in this case!)", "components", "You can silence another entity, preventing all spell casting for a duration.");
        skillTranslation(AMSpells.SILK_TOUCH.getId(), "Silk Touch", "Feels soft.", "modifiers", "With great power comes broken valuables.$(br2)You've learned to be more careful when casting your digging spells and break things less often.$(br2)Each modifier is equivalent to one level of Silk Touch on the spell.");
        skillTranslation(AMSpells.SLOWNESS.getId(), "Slowness", "No more running!", "components", "By applying the equivalent of a magical ball and chain, you can greatly slow the movements of your target.");
        skillTranslation(AMSpells.SLOW_FALLING.getId(), "Slow Falling", "Like a chicken!", "components", "Become light as a feather, and fall without fear.");
        skillTranslation(AMSpells.SOLAR.getId(), "Solar", "Sun power!", "modifiers", "Powers up your spell during the day. The closer to noon it is, the more $(l:modifiers/damage)damage$() and $(l:modifiers/healing)healing$() the spell does. $(l:modifiers/duration)Duration$() and $(l:modifiers/range)range$() are increased where applicable based on the phase of the moon (more power approaching new moon).");
        skillTranslation(AMSpells.STORM.getId(), "Storm", "It's raining men! Hallelujah!", "components", "The cloud darken, and rain begins to fall. The wind howls, and a flash of lightning strikes across the sky, leaving bright flashes in your vision.$(br2)This component changes the weather to a thunderstorm.");
        skillTranslation(AMSpells.SUMMON.getId(), "Summon", "Rise, creation!", "components", "You have learned to harvest the souls of creatures you defeat in combat. These souls can be used in creating a spell to summon that creature to protect you.$(br2)To summon a creature, first craft a $(l:items/crystal_phylactery)Crystal Phylactery$(). During spell creation, you can throw in any filled phylactery when prompted by the lectern. This step is what determines what your spell will summon.");
        skillTranslation(AMSpells.SWIFTNESS.getId(), "Swiftness", "Seems like you won't be catching me anytime soon.", "components", "You gain movement speed, like from a potion.");
        skillTranslation(AMSpells.SWIFT_SWIM.getId(), "Swift Swim", "No more swimming for hours.", "components", "By manipulating water currents, you can propel yourself along much more quickly underwater.");
        skillTranslation(AMSpells.TARGET_NON_SOLID.getId(), "Target Non Solid", "And I decided that I would fight the water.", "modifiers", "Allows the spell to target non-solid blocks (grass, water, lava) rather than passing through it.");
        skillTranslation(AMSpells.TEMPORAL_ANCHOR.getId(), "Temporal Anchor", "Let's look at the time. Oh dear! It went backward!", "components", "You can anchor yourself in time.$(br2)When the timer runs out, you are transported back to the place you cast this spell, with your health, mana, burnout, and hunger returning to what they were when the spell was first cast.");
        skillTranslation(AMSpells.TOUCH.getId(), "Touch", "Someone in there?", "shapes", "Simply wrap your hand in magic, and reach out.$(br2)Touch is a very short range shape, it does not follow the block highlighting. You need to be almost bumping into your target for touch to apply its effect.");
        skillTranslation(AMSpells.TRANSPLACE.getId(), "Transplace", "From point A to point B.", "components", "Your knowledge of teleportation magic has grown. You can now switch places with your target.");
        skillTranslation(AMSpells.TRUE_SIGHT.getId(), "True Sight", "Reveal what is hidden.", "components", "Your magical sight allows you to see things as they really are. Who knows what beauties and horrors you will discover?");
        skillTranslation(AMSpells.VELOCITY.getId(), "Velocity", "Faster! FASTER!", "modifiers", "Enhances speed altering effects of spells, most notably the speed of $(l:shapes/projectile)projectiles$().");
        skillTranslation(AMSpells.WALL.getId(), "Wall", "You shall not pass.", "shapes", "You can manifest a wall in front of you.$(br2)Walls function similarly to $(l:shapes/zone)zones$(), but with a different form.");
        skillTranslation(AMSpells.WATERY_GRAVE.getId(), "Watery Grave", "Bottom of the ocean.", "components", "You can make water come alive, wrapping tendrils around the target and dragging it down into the black, crushing depths.");
        skillTranslation(AMSpells.WATER_BREATHING.getId(), "Water Breathing", "Creating air directly inside my lungs? Cool!", "components", "You can use your magic to pull oxygen from the water, allowing you to get enough to not drown.");
        skillTranslation(AMSpells.WAVE.getId(), "Wave", "You might not want to surf on this one.", "shapes", "You can project a wave of magic in front of you that rolls forward, applying its effect to everything in its path.");
        skillTranslation(AMSpells.WIZARDS_AUTUMN.getId(), "Wizard's Autumn", "Leaves must leave.", "components", "You have learned to focus your digging magic into a small radius that directly affects leaves.$(br2)This component has a built-in $(l:shapes/aoe)AoE$() that can be modified with $(l:modifiers/range)Range$() modifiers.");
        skillTranslation(AMSpells.ZONE.getId(), "Zone", "No one can beat me in my sanctuary!", "shapes", "You have learned to focus your will into an area effect that will persist for a time.");
        skillTranslation(AMMagic.AFFINITY_GAINS_BOOST.identifier(), "Affinity Gains Boost", "Let's skip to the part where I have superpowers.", "talents", "You gain a 5%% boost in affinity gains.");
        skillTranslation(AMMagic.AUGMENTED_CASTING.identifier(), "Augmented Casting", "Upgrades, people, upgrades.", "talents", "All your spells gain a little boost. A little more damage, a little more duration, a little more speed, a little bit of everything.");
        skillTranslation(AMMagic.EXTRA_SUMMONS.identifier(), "Extra Summons", "Why should I do the fighting?", "talents", "When $(l:components/summon)summoning$() creatures, you can have just a little bit more of them.");
        //skillTranslation(AMMagic.MAGE_BAND_1.identifier(), "Mage Band I", "Starting a cult.", "talents", "You have built enough trust with light mages for them to follow you if requested.");
        //skillTranslation(AMMagic.MAGE_BAND_2.identifier(), "Mage Band II", "Group effort!", "talents", "Building even more trust, light mages will now automatically link their mana pools with yours if you are close.");
        skillTranslation(AMMagic.MANA_REGENERATION_BOOST_1.identifier(), "Mana Regeneration I", "And I would gain 500 mana...", "talents", "Your mana regeneration is boosted by 5%%.");
        skillTranslation(AMMagic.MANA_REGENERATION_BOOST_2.identifier(), "Mana Regeneration II", "...and I would gain 500 more...", "talents", "Your mana regeneration is boosted by 10%%. This replaces the boost of $(l:talents/mana_regen_1)Mana Regen I$().");
        skillTranslation(AMMagic.MANA_REGENERATION_BOOST_3.identifier(), "Mana Regeneration III", "...just to get back every single mana point I have consumed before.", "talents", "Your mana regeneration is boosted by 15%%. This replaces the boosts of $(l:talents/mana_regen_1)Mana Regen I$() and $(l:talents/mana_regen_2)Mana Regen II$().");
        skillTranslation(AMMagic.SHIELD_OVERLOAD.identifier(), "Shield Overload", "No more wasting excess mana.", "talents", "When your mana bar is full, excess mana regenerated turns into a shield that protects you from 5%% of all incoming damage.");
        skillTranslation(AMMagic.SPELL_MOTION.identifier(), "Spell Motion", "I like to move it, move it.", "talents", "Manipulating the winds around you, you have found a way to move at normal speed while using spells.");
        abilityTranslation(AMAbilities.SWIM_SPEED, "Swim Speed", "After using water spells for some time, you develop better swimming skills. As you delve deeper, you notice your speed in water getting faster and faster.$(br2)Affinity: Water$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.ENDER_THORNS, "Ender Thorns", "Becoming part water, ender creatures that attack you now take damage themselves.$(br2)Affinity: Water$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.NETHER_DAMAGE_WATER, "Damage in the Nether", "Having fun with water magic has made you less suitable for fire. You will take damage in the nether, though not enough to kill you on its own.$(br2)Affinity: Water$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.FIRE_RESISTANCE, "Fire Resistance", "After using fire spells for some time, you develop some resistance to fire. As you delve deeper, you notice the resistance getting stronger and stronger.$(br2)Affinity: Fire$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.FIRE_PUNCH, "Fire Punch", "Becoming part fire, enemies you hit now get set on fire.$(br2)Affinity: Fire$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.WATER_DAMAGE_FIRE, "Damage in Water", "Having fun with fire magic has made you less suitable for water. You will take damage in water, though not enough to kill you on its own.$(br2)Affinity: Fire$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.RESISTANCE, "Resistance", "After using earth spells for some time, you develop some physical resistance. As you delve deeper, you notice the resistance getting stronger and stronger.$(br2)Affinity: Earth$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.HASTE, "Haste", "After using earth spells for some time, you develop better block breaking skills. As you delve deeper, you notice that speed getting faster and faster.$(br2)Affinity: Earth$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.FALL_DAMAGE, "Fall Damage", "Having fun with earth magic has made you heavier. You take more fall damage.$(br2)Affinity: Earth$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.JUMP_BOOST, "Jump Boost", "After using air spells for some time, you develop better jumping skills. As you delve deeper, you notice your jump strength getting stronger and stronger.$(br2)Affinity: Air$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.FEATHER_FALLING, "Feather Falling", "After using air spells for some time, you develop better landing skills. As you delve deeper, you notice your fall damage taken getting weaker and weaker.$(br2)Affinity: Air$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.GRAVITY, "Gravity", "Having fun with air magic has made you accidentally bend gravity. You fall a lot faster.$(br2)Affinity: Air$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.FROST_PUNCH, "Frost Punch", "After using ice spells for some time, you develop a frost punch, slowing your enemies. As you delve deeper, you notice the frost getting stronger and stronger.$(br2)Affinity: Ice$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.FROST_WALKER, "Frost Walker", "Becoming part ice, water now freezes under your feet.$(br2)Affinity: Ice$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.SLOWNESS, "Slowness", "Having fun with ice magic has made you shiver. You move slower.$(br2)Affinity: Ice$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.SPEED, "Speed", "After using lightning spells for some time, you develop better running skills. As you delve deeper, you notice your speed becoming faster and faster.$(br2)Affinity: Lightning$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.STEP_ASSIST, "Step Assist", "Becoming part lightning, you are now able to step up 1-block slopes.$(br2)Affinity: Lightning$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.WATER_DAMAGE_LIGHTNING, "Damage in Water", "Having fun with lightning magic has made you less suitable for water. You will take damage in water, though not enough to kill you on its own.$(br2)Affinity: Lightning$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.THORNS, "Thorns", "After using nature spells for some time, you feel nourished. As you delve deeper, you notice nourishment getting stronger and stronger.$(br2)Affinity: Nature$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.SATURATION, "Saturation", "Becoming one with nature, enemies that hit you now take a bit of damage themselves.$(br2)Affinity: Nature$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.NETHER_DAMAGE_NATURE, "Damage in the Nether", "Having fun with nature magic has made you less suitable for fire. You will take damage in the nether, though not enough to kill you on its own.$(br2)Affinity: Nature$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.SMITE, "Smite", "After using life spells for some time, you feel an urge to slay the undead. As you delve deeper, you notice your damage towards undeads getting stronger and stronger.$(br2)Affinity: Life$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.REGENERATION, "Regeneration", "Becoming one with life, you get a permanent regeneration effect.$(br2)Affinity: Life$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.NAUSEA, "Nausea", "Having fun with life magic has made you less suitable for killing. You will receive a nausea effect when killing a non-undead enemy.$(br2)Affinity: Life$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.BURNOUT_REDUCTION, "Burnout Reduction", "After using arcane spells for some time, your spells cause you to burn out less. As you delve deeper, you notice the burnout generation becoming lower and lower.$(br2)Affinity: Arcane$(br)Range: 1 - 100 %%");
        abilityTranslation(AMAbilities.CLARITY, "Clarity", "Becoming one with the arcane, you have a chance of receiving the Clarity effect upon casting, which allows you to cast your next spell for free.$(br2)Affinity: Arcane$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.MAGIC_DAMAGE, "Magic Damage", "Having fun with arcane magic has made you vulnerable against the very thing you use. You will receive more damage from magic sources.$(br2)Affinity: Arcane$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.POISON_RESISTANCE, "Poison Resistance", "After using ender spells for quite some time, you develop a resistance against toxins.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.NIGHT_VISION, "Night Vision", "After using ender spells for quite some time, you gain permanent night vision.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%");
        abilityTranslation(AMAbilities.ENDERMAN_PUMPKIN, "Enderman Pumpkin", "Becoming one with the end, endermen will treat you as one of their own and not attack you anymore when staring into their eyes.$(br2)Affinity: Ender$(br)Range: 100 %%");
        abilityTranslation(AMAbilities.LIGHT_HEALTH_REDUCTION, "Light Health Reduction", "Toying around with ender magic consumes your life in light. When in direct sunlight, your maximum health decreases.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%$(br)There have been rumors among the villagers of true ender mages that managed to nullify this effect...");
        abilityTranslation(AMAbilities.WATER_HEALTH_REDUCTION, "Water Health Reduction", "Toying around with ender magic consumes your life in water. When in water, your maximum health decreases.$(br2)Affinity: Ender$(br)Range: 50 - 100 %%$(br)There have been rumors among the villagers of true ender mages that managed to nullify this effect...");
        configTranslation("magic_advancement", "Magic Advancement", "Completing this advancement will unlock magic for the player. Leave empty to not require an advancement and have magic unlocked from the start.");
        configTranslation("mana_to_burnout_ratio", "Mana : Burnout Ratio", "The default mana to burnout ratio, used in calculating spell costs.");
        configTranslation("blocks", "Blocks", "Configuration for the various blocks.");
        configTranslation("altar_check_interval", "Altar Check Interval", "The time in ticks between multiblock checks for the altar.");
        configTranslation("inscription_table_in_world_upgrading", "Inscription Table In-World Upgrading", "Whether inscription table upgrading is allowed in-world. If disabled, the upgrades must be applied through crafting.");
        configTranslation("obelisk_max_etherium", "Obelisk Max Etherium", "The maximum etherium an Obelisk can store.");
        configTranslation("celestial_prism_max_etherium", "Celestial Prism Max Etherium", "The maximum etherium a Celestial Prism can store.");
        configTranslation("black_aurem_max_etherium", "Black Aurem Max Etherium", "The maximum etherium a Black Aurem can store.");
        configTranslation("redstone_inlay_speed_multiplier", "Redstone Inlay Speed Multiplier", "The speed multiplier used by the Redstone Inlay, multiplied by the regular rail's speed.");
        configTranslation("gold_inlay_range", "Gold Inlay Range", "The teleportation range of the Gold Inlay, in blocks.");
        configTranslation("items", "Items", "Configuration for the various items.");
        configTranslation("arcane_compendium_conversion_check_interval", "Arcane Compendium Conversion Check Interval", "The time in ticks between checks for the Arcane Compendium conversion. Set to 0 to disable the conversion entirely.");
        configTranslation("arcane_compendium_conversion_duration", "Arcane Compendium Conversion Duration", "The time in ticks that the Arcane Compendium conversion takes.");
        configTranslation("arcane_compendium_conversion_horizontal_range", "Arcane Compendium Conversion Horizontal Range", "The horizontal range of the Arcane Compendium conversion.");
        configTranslation("arcane_compendium_conversion_vertical_range", "Arcane Compendium Conversion Vertical Range", "The vertical range of the Arcane Compendium conversion.");
        configTranslation("entities", "Entities", "Configuration for the various entities.");
        configTranslation("boss_player_check_distance", "Boss Player Check Distance", "The distance from a boss within which the boss bar will be shown.");
        configTranslation("boss_player_check_interval", "Boss Player Check Interval", "The time in ticks between the boss checking for players in its range to show the boss bar to.");
        configTranslation("dryad_grow_interval", "Dryad Grow Interval", "The time in ticks between a Dryad growing nearby plants.");
        configTranslation("dryad_grow_chance", "Dryad Grow Chance", "The chance of a Dryad growing nearby plants successfully.");
        configTranslation("dryad_grow_radius", "Dryad Grow Radius", "The radius of a Dryad's growing effect.");
        configTranslation("dryad_kill_cooldown", "Dryad Kill Cooldown", "If enough dryads are killed during this amount of time in ticks, the Nature Guardian will spawn. Set to 0 to disable this way of summoning the Nature Guardian.");
        configTranslation("dryad_kills_for_nature_guardian_spawn", "Dryad Kills for Nature Guardian Spawn", "The amount of dryads to be killed within the cooldown in order for the Nature Guardian to spawn.");
        configTranslation("mana_vortex_damage", "Mana Vortex Damage", "The amount of damage the Mana Vortex deals per stolen mana point.");
        configTranslation("mana_vortex_max_damage", "Mana Vortex Max Damage", "The maximum damage the Mana Vortex can deal.");
        configTranslation("mana_vortex_range", "Mana Vortex Range", "The range of the Mana Vortex.");
        configTranslation("mana_vortex_steal", "Mana Vortex Steal", "The amount of mana a Mana Vortex will steal each tick, as a multiplier of the target's max mana.");
        configTranslation("mana", "Mana", "Configuration for the mana leveling and regeneration of players.");
        configTranslation("mana_base", "Mana Base", "The base value for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana_multiplier", "Mana Multiplier", "The multiplier for mana calculation. Mana is calculated as base + multiplier * (level - 1).");
        configTranslation("mana_regeneration", "Mana Regeneration", "The multiplier for mana regeneration. Mana regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.");
        configTranslation("burnout", "Burnout", "Configuration for the burnout leveling and regeneration of players.");
        configTranslation("burnout_base", "Burnout Base", "The base value for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).");
        configTranslation("burnout_multiplier", "Burnout Multiplier", "The multiplier for burnout calculation. Burnout is calculated as base + multiplier * (level - 1).");
        configTranslation("burnout_regeneration", "Burnout Regeneration", "The multiplier for burnout regeneration. Burnout regeneration is calculated as (base + multiplier * (level - 1)) * regeneration.");
        configTranslation("level", "Leveling", "Configuration for the magic leveling of players.");
        configTranslation("level_base", "Level Base", "The base value for leveling calculation. XP cost per level is calculated as multiplier * base ^ (level - 1).");
        configTranslation("level_multiplier", "Level Multiplier", "The multiplier for leveling calculation. XP cost per level is calculated as multiplier * base ^ (level - 1).");
        configTranslation("extra_skill_points", "Extra Skill Points", "The extra blue skill points a player gets at level 1, in addition to the one they already get.");
        configTranslation("affinity", "Affinity", "Configuration for the affinity shifting of players.");
        configTranslation("affinity_to_xp_ratio", "Affinity : XP Ratio", "The affinity to xp ratio. When awarding xp, the amount of used affinities will be multiplied with this modifier.");
        configTranslation("continuous_modifier", "Continuous Modifier", "By what factor affinity and xp gain will be amplified when a continuous spell shape is used.");
        configTranslation("direct_opposite_multiplier", "Direct Opposite Multiplier", "When an affinity shift is applied, what portion of it is subtracted from the direct opposite affinity.");
        configTranslation("major_opposite_multiplier", "Major Opposite Multiplier", "When an affinity shift is applied, what portion of it is subtracted from the major opposite affinities.");
        configTranslation("minor_opposite_multiplier", "Minor Opposite Multiplier", "When an affinity shift is applied, what portion of it is subtracted from the minor opposite affinities.");
        configTranslation("adjacent_multiplier", "Adjacent Multiplier", "When an affinity shift is applied, what portion of it is added to the adjacent affinities.");
        configTranslation("affinity_gains_modifier", "Affinity Gains Modifier", "When the Affinity Gains talent is learned, by what factor affinity gain will be amplified.");
        configTranslation("affinity_gains_xp_modifier", "Affinity Gains XP Modifier", "When the Affinity Gains talent is learned, by what factor XP gain will be amplified.");
        configTranslation("affinity_tome_shift", "Affinity Tome Shift", "The amount to add to an affinity when using an Affinity Tome.");
        configTranslation("affinity_tome_reduction", "Affinity Tome Reduction", "The amount to subtract from all other affinities when using an Affinity Tome.");
        configTranslation("components", "Components", "Configuration of various component-specific values.");
        configTranslation("damage_damage", "Damage Damage", "The damage of damage-based components, in half hearts. May be amplified by spell modifiers.");
        configTranslation("effect_duration", "Effect Duration", "The duration of effect-based components, in ticks. May be amplified by spell modifiers.");
        configTranslation("effect_particles", "Effect Particles", "Whether to show effect particles for effect-based components.");
        configTranslation("attract_range", "Attract Range", "The range of the Attract component. May be amplified by spell modifiers.");
        configTranslation("attract_speed", "Attract Speed", "The speed of the Attract component. May be amplified by spell modifiers.");
        configTranslation("banish_rain_duration", "Banish Rain Duration", "The duration used by the Banish Rain component. May be amplified by spell modifiers.");
        configTranslation("blink_range", "Blink Range", "The range of the Attract component. May be amplified by spell modifiers.");
        configTranslation("blizzard_damage", "Blizzard Damage", "The damage of the Blizzard component. May be amplified by spell modifiers.");
        configTranslation("blizzard_duration", "Blizzard Duration", "The duration of the Blizzard component. May be amplified by spell modifiers.");
        configTranslation("blizzard_range", "Blizzard Range", "The range of the Blizzard component. May be amplified by spell modifiers.");
        configTranslation("blizzard_height", "Blizzard Height", "The height of the Blizzard component, relative to its width.");
        configTranslation("blizzard_frost_duration", "Blizzard Frost Duration", "The duration of the frost applied by the Blizzard component. May be amplified by spell modifiers.");
        configTranslation("blizzard_tick_interval", "Blizzard Tick Interval", "The tick interval used by the Blizzard component.");
        configTranslation("dig_mana_factor", "Dig Mana Factor", "The mana factor of the Dig component. The mana cost factor will be multiplied with the block's hardness.");
        configTranslation("dig_tool_tier", "Dig Tool Tier", "The tool tier of the Dig component. May be amplified by spell modifiers.");
        configTranslation("explosion_range", "Explosion Range", "The range of the Explosion component. May be amplified by spell modifiers.");
        configTranslation("falling_star_damage", "Falling Star Damage", "The damage of the Falling Star component. May be amplified by spell modifiers.");
        configTranslation("falling_star_range", "Falling Star Range", "The range of the Falling Star component. May be amplified by spell modifiers.");
        configTranslation("falling_star_speed", "Falling Star Speed", "The falling speed of the Falling Star component. May be amplified by spell modifiers.");
        configTranslation("falling_star_height", "Falling Star Height", "The height used by the Falling Star component.");
        configTranslation("falling_star_spawn_height", "Falling Star Spawn Height", "The height in which the Falling Star will spawn.");
        configTranslation("fire_rain_damage", "Fire Rain Damage", "The damage of the Fire Rain component. May be amplified by spell modifiers.");
        configTranslation("fire_rain_duration", "Fire Rain Duration", "The duration of the Fire Rain component. May be amplified by spell modifiers.");
        configTranslation("fire_rain_range", "Fire Rain Range", "The range of the Fire Rain component. May be amplified by spell modifiers.");
        configTranslation("fire_rain_height", "Fire Rain Height", "The height of the Fire Rain component, relative to its width.");
        configTranslation("fire_rain_fire_duration", "Fire Rain Frost Duration", "The duration of the fire applied by the Fire Rain component. May be amplified by spell modifiers.");
        configTranslation("fire_rain_tick_interval", "Fire Rain Tick Interval", "The tick interval used by the Fire Rain component.");
        configTranslation("fling_speed", "Fling Speed", "The speed of the Fling component. May be amplified by spell modifiers.");
        configTranslation("forge_smelts_villagers", "Forge Smelts Villagers", "Whether the Forge component instantly kills villagers, dropping emeralds.");
        configTranslation("frost_duration", "Frost Duration", "The duration of the Frost component, in ticks. May be amplified by spell modifiers.");
        configTranslation("life_drain_damage", "Life Drain Damage", "The damage of the Life Drain component, in ticks. May be amplified by spell modifiers.");
        configTranslation("life_tap_damage", "Life Tap Damage", "The damage of the Life Tap component, in ticks. May be amplified by spell modifiers.");
        configTranslation("life_tap_factor", "Life Tap Factor", "When the Life Tap component is cast, the caster regenerates the damage dealt, times their max mana, times this factor.");
        configTranslation("mana_blast_factor", "Mana Blast Factor", "When the Mana Blast component is cast, the damage is the caster's current mana, times this factor, potentially amplified by spell modifiers.");
        configTranslation("mana_drain_max", "Mana Drain Max", "The maximum amount of mana drained by the Mana Drain component.");
        configTranslation("melt_armor_factor", "Melt Armor Factor", "When the Melt Armor component is cast, what factor the armor's durability will be multiplied with.");
        configTranslation("random_teleport_max_tries", "Random Teleport Max Tries", "How many times the Random Teleport component will try to find a position.");
        configTranslation("random_teleport_range", "Random Teleport Range", "The range of the Random Teleport component. May be amplified by spell modifiers.");
        configTranslation("repel_range", "Repel Range", "The range of the Repel component. May be amplified by spell modifiers.");
        configTranslation("repel_speed", "Repel Speed", "The speed of the Repel component. May be amplified by spell modifiers.");
        configTranslation("storm_duration", "Storm Duration", "The duration used by the Storm component. May be amplified by spell modifiers.");
        configTranslation("storm_range", "Storm Range", "The range used by the Storm component. May be amplified by spell modifiers.");
        configTranslation("storm_lightning_bolt_chance", "Storm Lightning Bolt Chance", "The chance for the Storm component to summon a lightning bolt somewhere in range.");
        configTranslation("storm_lightning_bolt_target_chance", "Storm Lightning Bolt Target Chance", "The chance for the Storm component to summon a target-seeking lightning bolt somewhere in range.");
        configTranslation("summon_count", "Summon Count", "The amount of summons a player can have at the same time.");
        configTranslation("summon_mana_cost", "Summon Mana Cost", "The amount of mana, multiplied by the summons's health, that is consumed when using the Summon component.");
        configTranslation("wizards_autumn_range", "Wizard's Autumn Range", "The range used by the Wizard's Autumn component. May be amplified by spell modifiers.");
        configTranslation("shapes", "Shapes", "Configuration of various shape-specific values.");
        configTranslation("beam_range", "Beam Range", "The range used by the Beam shape.");
        configTranslation("chain_range", "Chain Range", "The range used by the Chain shape.");
        configTranslation("chain_extra_targets", "Chain Extra Targets", "The amount of extra targets the Chain shape can hit.");
        configTranslation("chain_extra_targets_range", "Chain Extra Targets Range", "The range used by the Chain shape when seeking extra targets. May be amplified by spell modifiers.");
        configTranslation("projectile_duration", "Projectile Duration", "The duration used by the Projectile shape. May be amplified by spell modifiers.");
        configTranslation("projectile_gravity", "Projectile Gravity", "If a Gravity modifier is present on the Projectile, by how much gravity will be increased.");
        configTranslation("projectile_speed", "Projectile Speed", "The speed used by the Projectile shape. May be amplified by spell modifiers.");
        configTranslation("wall_duration", "Wall Duration", "The duration used by the Wall shape. May be amplified by spell modifiers.");
        configTranslation("wall_range", "Wall Range", "The range used by the Wall shape. May be amplified by spell modifiers.");
        configTranslation("wall_height", "Wall Height", "The height used by the Wall shape, relative to its width.");
        configTranslation("wall_tick_interval", "Wall Tick Interval", "The time in ticks between the Wall shape applying its effect.");
        configTranslation("wave_duration", "Wave Duration", "The duration used by the Wave shape. May be amplified by spell modifiers.");
        configTranslation("wave_gravity", "Wave Gravity", "If a Gravity modifier is present on the Wave, by how much gravity will be increased.");
        configTranslation("wave_range", "Wave Range", "The range used by the Wave shape. May be amplified by spell modifiers.");
        configTranslation("wave_speed", "Wave Speed", "The speed used by the Wave shape. May be amplified by spell modifiers.");
        configTranslation("wave_tick_interval", "Wave Tick Interval", "The tick interval used by the Wave shape.");
        configTranslation("zone_duration", "Zone Duration", "The duration used by the Zone shape. May be amplified by spell modifiers.");
        configTranslation("zone_gravity", "Zone Gravity", "If a Gravity modifier is present on the Zone, by how much gravity will be increased.");
        configTranslation("zone_range", "Zone Range", "The range used by the Zone shape. May be amplified by spell modifiers.");
        configTranslation("zone_height", "Zone Height", "The height used by the Zone shape.");
        configTranslation("zone_tick_interval", "Zone Tick Interval", "The tick interval used by the Zone shape.");
        configTranslation("talents", "Talents", "Configuration of various talent-specific values.");
        configTranslation("augmented_casting_multiplier", "Augmented Casting Multiplier", "The multiplier to various stats used by the Augmented Casting talent.");
        configTranslation("extra_summons_count", "Extra Summons Count", "The amount of additional summons a player can have at the same time when they have the Extra Summons talent.");
        configTranslation("mana_regeneration_1_multiplier", "Mana Regeneration 1 Multiplier", "The multiplier to mana regeneration used by the Mana Regeneration 1 talent.");
        configTranslation("mana_regeneration_2_multiplier", "Mana Regeneration 2 Multiplier", "The multiplier to mana regeneration used by the Mana Regeneration 2 talent.");
        configTranslation("mana_regeneration_3_multiplier", "Mana Regeneration 3 Multiplier", "The multiplier to mana regeneration used by the Mana Regeneration 3 talent.");
        configTranslation("shield_overload_multiplier", "Shield Overload Multiplier", "If the player is at full mana and has the Shield Overload talent, the multiplier to incoming damage that will be applied.");
        configTranslation("crystal_phylactery_model_quality", "Crystal Phylactery Model Quality", "The 'quality' value of the algorithm that calculates the crystal phylactery colors. Lower value means more accurate results and more calculation cost, however lower values have diminishing returns.");
        configTranslation("gui_layers", "GUI Layers", "Configuration for the various GUI layers in this mod.");
        configTranslation("bars", "Mana/Burnout/Level Bars", "Configuration for the mana, burnout and level bars. The size of the layer is 80x40.");
        configTranslation("bars_x", "Horizontal Position", "Horizontal position of the mana, burnout and level bars.");
        configTranslation("bars_y", "Vertical Position", "Vertical position of the mana, burnout and level bars.");
        configTranslation("bars_anchor_x", "Horizontal Anchor", "Horizontal anchor of the mana, burnout and level bars.");
        configTranslation("bars_anchor_y", "Vertical Anchor", "Vertical anchor of the mana, burnout and level bars.");
        configTranslation("render_level_at_top", "Render Level At Top", "If true, renders the bars in order level number -> level bar -> mana bar -> burnout bar.\nIf false, renders the bars in order mana bar -> burnout bar -> level bar -> level number.");
        configTranslation("show_values", "Show Values", "Whether to show the exact values for mana, burnout and xp.");
        configTranslation("shape_groups", "Shape Groups", "Configuration for the shape groups GUI layer. The size of the layer is 180x36.");
        configTranslation("shape_groups_x", "Horizontal Position", "Horizontal position of the shape groups GUI layer.");
        configTranslation("shape_groups_y", "Vertical Position", "Vertical position of the shape groups GUI layer.");
        configTranslation("shape_groups_anchor_x", "Horizontal Anchor", "Horizontal anchor of the shape groups GUI layer.");
        configTranslation("shape_groups_anchor_y", "Vertical Anchor", "Vertical anchor of the shape groups GUI layer.");
        configTranslation("spell_book", "Spell Book", "Configuration for the spell book GUI layer. The size of the layer is 111x17.");
        configTranslation("spell_book_x", "Horizontal Position", "Horizontal position of the spell book GUI layer.");
        configTranslation("spell_book_y", "Vertical Position", "Vertical position of the spell book GUI layer.");
        configTranslation("spell_book_anchor_x", "Horizontal Anchor", "Horizontal anchor of the spell book GUI layer.");
        configTranslation("spell_book_anchor_y", "Vertical Anchor", "Vertical anchor of the spell book GUI layer.");
        add("enchantment", "dismembering", "Dismembering");
        add("enchantment", "dismembering.desc", "Adds a drop chance for heads for certain mobs.");
        add("affinity", "none", "None");
        add("affinity", "water", "Water");
        add("affinity", "fire", "Fire");
        add("affinity", "earth", "Earth");
        add("affinity", "air", "Air");
        add("affinity", "ice", "Ice");
        add("affinity", "lightning", "Lightning");
        add("affinity", "nature", "Nature");
        add("affinity", "life", "Life");
        add("affinity", "arcane", "Arcane");
        add("affinity", "ender", "Ender");
        add("etherium", "light", "Light Etherium");
        add("etherium", "neutral", "Neutral Etherium");
        add("etherium", "dark", "Dark Etherium");
        add("occulus_tab", "offense", "Offense");
        add("occulus_tab", "defense", "Defense");
        add("occulus_tab", "utility", "Utility");
        add("occulus_tab", "talent", "Talent");
        add("occulus_tab", "affinity", "Affinity");
        add("skill_point", "blue", "Blue");
        add("skill_point", "green", "Green");
        add("skill_point", "red", "Red");
        add("death.attack", "falling_star", "%1$s was disintegrated into stardust by %2$s");
        add("death.attack", "nature_scythe", "%1$s was ripped apart by %2$s's scythe");
        add("death.attack", "shockwave", "%1$s was obliterated by %2$s's shockwave");
        add("death.attack", "thrown_rock", "%1$s was crushed under a rock by %2$s");
        add("death.attack", "whirlwind", "%1$s was torn apart by %2$s's whirlwind");
        add("spell_prefab", "water_bolt", "Water Bolt");
        add("spell_prefab", "fire_bolt", "Fire Bolt");
        add("spell_prefab", "earth_bolt", "Earth Bolt");
        add("spell_prefab", "ice_bolt", "Ice Bolt");
        add("spell_prefab", "lightning_bolt", "Lightning Bolt");
        add("spell_prefab", "arcane_bolt", "Arcane Bolt");
        add("spell_prefab", "strong_water_bolt", "Strong Water Bolt");
        add("spell_prefab", "strong_fire_bolt", "Strong Fire Bolt");
        add("spell_prefab", "strong_earth_bolt", "Strong Earth Bolt");
        add("spell_prefab", "strong_ice_bolt", "Strong Ice Bolt");
        add("spell_prefab", "strong_lightning_bolt", "Strong Lightning Bolt");
        add("spell_prefab", "strong_arcane_bolt", "Strong Arcane Bolt");
        add("spell_prefab", "area_lightning", "Area Lightning");
        add("spell_prefab", "blink", "Blink");
        add("spell_prefab", "chaos_water_bolt", "Chaos Water Bolt");
        add("spell_prefab", "debuff", "Debuff");
        add("spell_prefab", "dispel", "Dispel");
        add("spell_prefab", "ender_bolt", "Ender Bolt");
        add("spell_prefab", "ender_torrent", "Ender Torrent");
        add("spell_prefab", "ender_wave", "Ender Wave");
        add("spell_prefab", "heal_self", "Heal Self");
        add("spell_prefab", "lightning_rune", "Lightning Rune");
        add("spell_prefab", "melt_armor", "Melt Armor");
        add("spell_prefab", "nausea", "Nausea");
        add("spell_prefab", "otherworldly_roar", "Otherworldly Roar");
        add("spell_prefab", "scramble_synapses", "Scramble Synapses");
        arcaneCompendiumTranslation("affinities.fire.page0.text", "The fire affinity is associated with lava, explosions and the Nether. Fire components are usually offensive ones, like $(l:components/fire_damage)Fire Damage$(), $(l:components/ignition)Ignition$() or $(l:components/explosion)Explosion$().");
        arcaneCompendiumTranslation("affinities.water.page0.text", "The water affinity is associated with swimming, drowning and potions. Its components therefore often use effects, such as $(l:components/water_breathing)Water Breathing$(), $(l:components/swift_swim)Swift Swim$() or $(l:components/watery_grave)Watery Grave$().");
        arcaneCompendiumTranslation("affinities.earth.page0.text", "The earth affinity is associated with mining, protection and physical attacks. Earth components usually have some kind of physical interaction, like $(l:components/physical_damage)Physical Damage$(), $(l:components/dig)Dig$() or $(l:components/shield)Shield$().");
        arcaneCompendiumTranslation("affinities.air.page0.text", "The air affinity is associated with jumping, flying and falling. Many of them use effects, such as $(l:components/jump_boost)Jump Boost$(), $(l:components/levitation)Levitation$() or $(l:components/slow_falling)Slow Falling$().");
        arcaneCompendiumTranslation("affinities.ice.page0.text", "The ice affinity is associated with snow, frost and slowness. Popular examples include $(l:components/frost_damage)Frost Damage$(), $(l:components/frost)Frost$() and $(l:components/slowness)Slowness$().");
        arcaneCompendiumTranslation("affinities.lightning.page0.text", "The lightning affinity is associated with speed, power and weather. Notable examples are $(l:components/lightning_damage)Lightning Damage$(), $(l:components/haste)Haste$() and $(l:components/storm)Storm$().");
        arcaneCompendiumTranslation("affinities.nature.page0.text", "The nature affinity is associated with attraction, growth and harvest. As such, the most common components are $(l:components/attract)Attract$(), $(l:components/grow)Grow$() and $(l:components/harvest)Harvest$().");
        arcaneCompendiumTranslation("affinities.life.page0.text", "The life affinity is associated with healing, resurrection and anti-undead measures. They are usually defensive, like $(l:components/heal)Heal$(), $(l:components/regeneration)Regeneration$() and $(l:components/summon)Summon$().");
        arcaneCompendiumTranslation("affinities.arcane.page0.text", "The arcane affinity is associated with mana, enchantment and trickery. Arcane components are indirectly offensive for the most part, seen for example with $(l:components/invisibility)Invisibility$(), $(l:components/disarm)Disarm$() and $(l:components/mana_drain)Mana Drain$().");
        arcaneCompendiumTranslation("affinities.ender.page0.text", "The ender affinity is associated with teleportation, darkness and the night. Ender components are the most powerful, but also the most expensive, with examples such as $(l:components/blindness)Blindness$(), $(l:components/astral_distortion)Astral Distortion$() and $(l:components/transplace)Transplace$().");
        arcaneCompendiumTranslation("components.summon.page1.text", "Then, summon creatures by using the spell normally. The stronger the creature, the more mana this requires!$(br2)Tamable creatures such as wolves and cats are automatically tamed to their owner upon summoning. Other creatures are not, however they will still fight for you like a wolf would: attacking what you attack, and defending you from attackers.");
        arcaneCompendiumTranslation("components.summon.page2.text", "Be aware that they will still have their usual weaknesses. For example, summoned zombies will still burn in sunlight, so you'll have to take precautions for that.$(br2)Summoned creatures drop no loot other than what was given to them (such as armor), as well as no experience. However, they can be interacted with normally otherwise, such as breeding, milking cows, or riding horses.");
        arcaneCompendiumTranslation("shapes.chain.page1.text", "When jumping, the spell will try to prefer monsters of the same type. So for example, if you have a group of 4 zombies and 2 skeletons, and you target a zombie, you will always hit the 4 zombies and one of the skeletons.");
        add(AMTranslations.ABILITY_INTO_MULTIPLE_KEY, "Shifted into abilities %s!");
        add(AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_MULTIPLE_KEY, "Shifted into abilities %s and out of abilities %s!");
        add(AMTranslations.ABILITY_INTO_MULTIPLE_OUT_OF_SINGLE_KEY, "Shifted into abilities %s and out of ability %s!");
        add(AMTranslations.ABILITY_INTO_SINGLE_KEY, "Shifted into ability %s!");
        add(AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_MULTIPLE_KEY, "Shifted into ability %s and out of abilities %s!");
        add(AMTranslations.ABILITY_INTO_SINGLE_OUT_OF_SINGLE_KEY, "Shifted into ability %s and out of ability %s!");
        add(AMTranslations.ABILITY_OUT_OF_MULTIPLE_KEY, "Shifted out of abilities %s!");
        add(AMTranslations.ABILITY_OUT_OF_SINGLE_KEY, "Shifted out of ability %s!");
        add(AMTranslations.ABILITY_SEPARATOR_KEY, ", ");
        add(AMTranslations.ALTAR_CORE_POWER_KEY, "Power: %s");
        add(AMTranslations.ALTAR_CORE_LOW_POWER_KEY, "Altar does not have enough power!");
        add(AMTranslations.ANY_ETHERIUM_KEY, "Any Etherium");
        add(AMTranslations.BARS_VALUE_BURNOUT_KEY, "%s / %s");
        add(AMTranslations.BARS_VALUE_MANA_KEY, "%s / %s");
        add(AMTranslations.BARS_VALUE_XP_KEY, "%s / %s");
        add(AMTranslations.COMMAND_AFFINITY_ADD_MULTIPLE_KEY, "Added %s affinity depth to %s players for %s");
        add(AMTranslations.COMMAND_AFFINITY_ADD_SINGLE_KEY, "Added %s affinity depth to player %s for %s");
        add(AMTranslations.COMMAND_AFFINITY_GET_KEY, "Affinity depth of %s for player %s is %s");
        add(AMTranslations.COMMAND_AFFINITY_SET_MULTIPLE_KEY, "Set %s affinity depth on %s players for %s");
        add(AMTranslations.COMMAND_AFFINITY_SET_SINGLE_KEY, "Set %s affinity depth on player %s for %s");
        add(AMTranslations.COMMAND_MAGIC_XP_ADD_LEVELS_MULTIPLE_KEY, "Gave %s magic xp levels to %s players");
        add(AMTranslations.COMMAND_MAGIC_XP_ADD_LEVELS_SINGLE_KEY, "Gave %s magic xp levels to %s");
        add(AMTranslations.COMMAND_MAGIC_XP_ADD_POINTS_MULTIPLE_KEY, "Gave %s magic xp to %s players");
        add(AMTranslations.COMMAND_MAGIC_XP_ADD_POINTS_SINGLE_KEY, "Gave %s magic xp to %s");
        add(AMTranslations.COMMAND_MAGIC_XP_GET_LEVELS_KEY, "%s has %s magic xp levels");
        add(AMTranslations.COMMAND_MAGIC_XP_GET_POINTS_KEY, "%s has %s magic xp points");
        add(AMTranslations.COMMAND_MAGIC_XP_SET_LEVELS_MULTIPLE_KEY, "Set %s magic xp levels on %s players");
        add(AMTranslations.COMMAND_MAGIC_XP_SET_LEVELS_SINGLE_KEY, "Set %s magic xp levels on %s");
        add(AMTranslations.COMMAND_MAGIC_XP_SET_POINTS_MULTIPLE_KEY, "Set %s magic xp on %s players");
        add(AMTranslations.COMMAND_MAGIC_XP_SET_POINTS_SINGLE_KEY, "Set %s magic xp on %s");
        add(AMTranslations.COMMAND_SKILL_FORGET_ALL_MULTIPLE_KEY, "Took all skill knowledge from %s players");
        add(AMTranslations.COMMAND_SKILL_FORGET_ALL_SINGLE_KEY, "Took all skill knowledge from player %s");
        add(AMTranslations.COMMAND_SKILL_FORGET_MULTIPLE_KEY, "Took knowledge of skill %s from %s players");
        add(AMTranslations.COMMAND_SKILL_FORGET_SINGLE_KEY, "Took knowledge of skill %s from player %s");
        add(AMTranslations.COMMAND_SKILL_LEARN_ALL_MULTIPLE_KEY, "Gave all skill knowledge to %s players");
        add(AMTranslations.COMMAND_SKILL_LEARN_ALL_SINGLE_KEY, "Gave all skill knowledge to player %s");
        add(AMTranslations.COMMAND_SKILL_LEARN_MULTIPLE_KEY, "Gave knowledge of skill %s to %s players");
        add(AMTranslations.COMMAND_SKILL_LEARN_SINGLE_KEY, "Gave knowledge of skill %s to player %s");
        add(AMTranslations.COMMAND_SKILL_LIST_ALL_KEY, "The following skills are currently registered: %s");
        add(AMTranslations.COMMAND_SKILL_LIST_KNOWN_KEY, "%s knows the following skills: %s");
        add(AMTranslations.COMMAND_SKILL_LIST_SEPARATOR_KEY, ", ");
        add(AMTranslations.COMMAND_SKILL_LIST_UNKNOWN_KEY, "%s does not yet know the following skills: %s");
        add(AMTranslations.COMMAND_SKILL_POINT_ADD_MULTIPLE_KEY, "Added %s skill points of type %s to %s players");
        add(AMTranslations.COMMAND_SKILL_POINT_ADD_SINGLE_KEY, "Added %s skill points of type %s to player %s");
        add(AMTranslations.COMMAND_SKILL_POINT_GET_KEY, "Player %s has %s skill points of type %s");
        add(AMTranslations.COMMAND_SKILL_POINT_SET_MULTIPLE_KEY, "Set %s skill points of type %s on %s players");
        add(AMTranslations.COMMAND_SKILL_POINT_SET_SINGLE_KEY, "Set %s skill points of type %s on player %s");
        add(AMTranslations.CRYSTAL_PHYLACTERY_KEY, "%s: %s / %s");
        add(AMTranslations.CRYSTAL_PHYLACTERY_EMPTY_KEY, "Empty");
        add(AMTranslations.ETHERIUM_KEY, "%s: %s / %s");
        add(AMTranslations.INSCRIPTION_TABLE_CLEAR_KEY, "Clear");
        add(AMTranslations.INSCRIPTION_TABLE_GIVE_SPELL_KEY, "Give Spell");
        add(AMTranslations.INSCRIPTION_TABLE_KEY, "Inscription Table");
        add(AMTranslations.INSCRIPTION_TABLE_NAME_KEY, "Name");
        add(AMTranslations.INSCRIPTION_TABLE_SEARCH_KEY, "Search");
        add(AMTranslations.JEI_SKILL_AFFINITY_BREAKDOWN_KEY, "Affinity Breakdown:");
        add(AMTranslations.JEI_SKILL_INGREDIENTS_KEY, "Ingredients:");
        add(AMTranslations.JEI_SKILL_MODIFIED_BY_KEY, "Modified By:");
        add(AMTranslations.JEI_SKILL_TITLE_KEY, "Spell Parts");
        add(AMTranslations.KEY_CATEGORY_KEY, "Ars Magica: Legacy");
        add(AMTranslations.KEY_NEXT_SHAPE_GROUP_KEY, "Next Shape Group");
        add(AMTranslations.KEY_PREV_SHAPE_GROUP_KEY, "Previous Shape Group");
        add(AMTranslations.KEY_SPELL_CUSTOMIZATION_KEY, "Customize Spell");
        add(AMTranslations.NO_TELEPORT_KEY, "You are too distorted to teleport!");
        add(AMTranslations.NO_TELEPORT_NETHER_KEY, "The nether's force forbids to simply teleport out of it!");
        add(AMTranslations.NO_TELEPORT_OTHER_KEY, "The target is too distorted to be teleported!");
        add(AMTranslations.NOT_YET_IMPLEMENTED_KEY, "Not yet implemented!");
        add(AMTranslations.OCCULUS_ABILITY_KEY, "%s (%s - %s)");
        add(AMTranslations.OCCULUS_DETAILS_KEY, "Hold Shift for details");
        add(AMTranslations.OCCULUS_FORGET_ALL_KEY, "Forget All");
        add(AMTranslations.OCCULUS_FORGET_ALL_TOOLTIP_KEY, "Costs 1 Vinteum Block. Your invested skill points will be returned.");
        add(AMTranslations.OCCULUS_KEY, "Occulus");
        add(AMTranslations.OCCULUS_MISSING_KEY, "You lack the skill points or parent skills to learn this skill!");
        add(AMTranslations.OCCULUS_NEXT_KEY, ">");
        add(AMTranslations.OCCULUS_PREV_KEY, "<");
        add(AMTranslations.PREVENT_BLOCK_KEY, "Mystical forces prevent you from using this block! Try crafting an Arcane Compendium to learn more.");
        add(AMTranslations.PREVENT_ITEM_KEY, "Mystical forces prevent you from using this item! Try crafting an Arcane Compendium to learn more.");
        add(AMTranslations.RIFT_KEY, "Rift");
        add(AMTranslations.SPELL_BOOK_NO_SPELL_SELECTED_KEY, "No spell selected.");
        add(AMTranslations.SPELL_BOOK_SELECTED_SPELL_KEY, "Selected spell: %s");
        add(AMTranslations.SPELL_FAIL_BURNED_OUT_KEY, "Burned out!");
        add(AMTranslations.SPELL_FAIL_COMPONENT_BANISH_RAIN_KEY, "Banish Rain can only be cast during rain");
        add(AMTranslations.SPELL_FAIL_COMPONENT_CHARM_KEY, "Charm can only be cast on animals");
        add(AMTranslations.SPELL_FAIL_COMPONENT_DAMAGE_PVP_KEY, "Damage components cannot be cast on players");
        add(AMTranslations.SPELL_FAIL_COMPONENT_DAYLIGHT_KEY, "Daylight can only be cast during the night in the Overworld");
        add(AMTranslations.SPELL_FAIL_COMPONENT_DIVINE_INTERVENTION_KEY, "The target is already in the Overworld");
        add(AMTranslations.SPELL_FAIL_COMPONENT_ENDER_INTERVENTION_KEY, "The target is already in the End");
        add(AMTranslations.SPELL_FAIL_COMPONENT_FALLING_STAR_KEY, "Falling Star can only be cast in dimensions with a sky");
        add(AMTranslations.SPELL_FAIL_COMPONENT_MOONRISE_KEY, "Moonrise can only be cast during the day in the Overworld");
        add(AMTranslations.SPELL_FAIL_COMPONENT_PLACE_BLOCK_NO_BLOCK_KEY, "No block in inventory!");
        add(AMTranslations.SPELL_FAIL_COMPONENT_PLACE_BLOCK_NO_SELECTION_KEY, "No block selected! Use the Spell Customization menu to select a block.");
        add(AMTranslations.SPELL_FAIL_COMPONENT_RANDOM_TELEPORT_KEY, "Random Teleport could not find a suitable location");
        add(AMTranslations.SPELL_FAIL_COMPONENT_RECALL_KEY, "No recall position set! Use the Spell Customization menu to set a position.");
        add(AMTranslations.SPELL_FAIL_COMPONENT_SUMMON_NO_SELECTION_KEY, "No summon inserted! Use the Spell Customization menu to insert a filled Crystal Phylactery.");
        add(AMTranslations.SPELL_FAIL_MALFORMED_KEY, "This spell is malformed and cannot be cast!");
        add(AMTranslations.SPELL_FAIL_NO_BLOCK_KEY, "This spell must be used on a block for some of its effects to apply");
        add(AMTranslations.SPELL_FAIL_NO_CASTER_KEY, "This spell must be used by a real player for some of its effects to apply");
        add(AMTranslations.SPELL_FAIL_NO_ENTITY_KEY, "This spell must be used on an entity for some of its effects to apply");
        add(AMTranslations.SPELL_FAIL_NO_HIT_KEY, "This spell must be used on something for some of its effects to apply");
        add(AMTranslations.SPELL_FAIL_NOT_ENOUGH_MANA_KEY, "Not enough mana!");
        add(AMTranslations.SPELL_FAIL_SILENCED_KEY, "Silenced!");
        add(AMTranslations.SPELL_CUSTOMIZATION_ACTIVE_KEY, "%s (Click to Modify)");
        add(AMTranslations.SPELL_CUSTOMIZATION_COLOR_CLEAR_KEY, "Clear Color");
        add(AMTranslations.SPELL_CUSTOMIZATION_COLOR_KEY, "Customize Color");
        add(AMTranslations.SPELL_CUSTOMIZATION_KEY, "Spell Name");
        add(AMTranslations.SPELL_CUSTOMIZATION_PLACE_BLOCK_KEY, "Customize Place Block");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR_KEY, "Clear Position");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR_SUCCESS_KEY, "Recall position cleared!");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_KEY, "Customize Recall");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE_KEY, "Restore Position");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE_SUCCESS_KEY, "Recall position restored!");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET_KEY, "Set Position");
        add(AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET_SUCCESS_KEY, "Recall position set to your current position!");
        add(AMTranslations.SPELL_CUSTOMIZATION_SUMMON_KEY, "Customize Summon");
        add(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, "x %s");
        add(AMTranslations.SPELL_INVALID_KEY, "Spell is malformed and cannot be cast!");
        add(AMTranslations.SPELL_MANA_COST_KEY, "Mana Cost: %s");
        add(AMTranslations.SPELL_RECIPE_AFFINITIES_KEY, "Affinities");
        add(AMTranslations.SPELL_RECIPE_GRAMMAR_KEY, "Grammar");
        add(AMTranslations.SPELL_RECIPE_INGREDIENTS_KEY, "Ingredients");
        add(AMTranslations.SPELL_RECIPE_SHAPE_GROUP_KEY, "Shape Group %s");
        add(AMTranslations.TIER_KEY, "Tier: %s");
        add("config.jade.plugin_" + ArsMagicaApi.MOD_ID + ".altar", "Altar");
        add("config.jade.plugin_" + ArsMagicaApi.MOD_ID + ".etherium", "Etherium");
        add("config.jade.plugin_" + ArsMagicaApi.MOD_ID + ".tier", "Tier");
        add("key.category", "main", "Ars Magica: Legacy");
        add("itemGroup", "main", "Ars Magica: Legacy");
        add("itemGroup", "spell_prefabs", "Ars Magica: Legacy - Spell Prefabs");
        add("potion.potency.5", "VI");
        add("potion.potency.6", "VII");
        add("potion.potency.7", "VIII");
        add("potion.potency.8", "IX");
        add("potion.potency.9", "X");
        for (ChatFormatting chatFormatting : ChatFormatting.values()) {
            if (chatFormatting.getColor() == null) continue;
            add("color." + chatFormatting.getName(), idTranslation(chatFormatting.getName()));
        }
        for (DyeColor dyeColor : DyeColor.values()) {
            add("color." + dyeColor.getName() + "_dye", idTranslation(dyeColor.getName()) + " Dye");
        }
    }

    /// Adds a cached translation, for use prior to this provider running.
    ///
    /// @param key         The translation key to use.
    /// @param translation The translation to use.
    public void addCached(String key, String translation) {
        cached.put(key, translation);
    }

    /// Adds a block translation that matches the block id.
    ///
    /// @param block The block to generate the translation for.
    private void blockIdTranslation(DeferredBlock<?> block) {
        addBlock(block, idTranslation(block.getId().getPath()));
    }

    /// Adds an item translation that matches the item id.
    ///
    /// @param item The item to generate the translation for.
    private void itemIdTranslation(DeferredItem<?> item) {
        addItem(item, idTranslation(item.getId().getPath()));
    }

    /// Adds an item with variants appended to the regular translation key.
    ///
    /// @param item        The item to generate the translation for.
    /// @param variant     The variant to use.
    /// @param translation The translation to add.
    @SuppressWarnings("SameParameterValue")
    private void itemWithVariantTranslation(DeferredItem<?> item, Identifier variant, String translation) {
        add(Util.makeDescriptionId(item.get().getDescriptionId(), variant), translation);
    }

    /// Adds an entity translation that matches the entity id.
    ///
    /// @param entity The entity to generate the translation for.
    private void entityIdTranslation(DeferredHolder<EntityType<?>, ?> entity) {
        addEntityType(entity, idTranslation(entity.getKey().identifier().getPath()));
    }

    /// Adds an attribute translation that matches the attribute id.
    ///
    /// @param attribute The attribute to generate the translation for.
    @SuppressWarnings("DataFlowIssue")
    private void attributeIdTranslation(Holder<Attribute> attribute) {
        add(Util.makeDescriptionId("attribute", attribute.getKey().identifier()), idTranslation(attribute.getKey().identifier().getPath()));
    }

    /// Adds an effect translation that matches the effect id.
    ///
    /// @param effect The effect to generate the translation for.
    @SuppressWarnings("DataFlowIssue")
    private void effectIdTranslation(Holder<MobEffect> effect) {
        add(effect.value(), idTranslation(effect.getKey().identifier().getPath()));
    }

    /// Adds a potion translation that matches the potion id. Also covers splash potion, lingering potion and tipped arrow translations.
    ///
    /// @param potion The potion to generate the translation for.
    @SuppressWarnings("DataFlowIssue")
    private void potionIdTranslation(Holder<Potion> potion) {
        String path = potion.getKey().identifier().getPath();
        add("item.minecraft.potion.effect." + path, "Potion of " + idTranslation(path));
        add("item.minecraft.splash_potion.effect." + path, "Splash Potion of " + idTranslation(path));
        add("item.minecraft.lingering_potion.effect." + path, "Lingering Potion of " + idTranslation(path));
        add("item.minecraft.tipped_arrow.effect." + path, "Arrow of " + idTranslation(path));
    }

    /// Adds an advancement translation.
    ///
    /// @param name        The name of the advancement to generate the translation for.
    /// @param title       The translation of the advancement's title.
    /// @param description The translation of the advancement's description.
    private void advancementTranslation(String name, String title, String description) {
        add("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title", title);
        add("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description", description);
    }

    /// Adds a config translation.
    ///
    /// @param name        The name of the config value to generate the translation for.
    /// @param translation The translation of the config value.
    /// @param tooltip     The translation of the config value's tooltip.
    private void configTranslation(String name, String translation, String tooltip) {
        add(AMTranslations.CONFIG_KEY + name, translation);
        add(AMTranslations.CONFIG_KEY + name + ".tooltip", tooltip);
    }

    /// Adds a subtitle translation.
    ///
    /// @param sound       The sound to add the translation for.
    /// @param translation The translation to use.
    private void subtitleTranslation(DeferredHolder<SoundEvent, SoundEvent> sound, String translation) {
        add(Util.makeDescriptionId("subtitle", sound.getKey().identifier()), translation);
    }

    /// Adds a skill translation, including its compendium description.
    ///
    /// @param skill          The skill id.
    /// @param name           The skill name.
    /// @param description    The skill description.
    /// @param compendiumType The compendium category ("shapes", "components" or "modifiers") this skill is in
    /// @param compendiumText The description in the compendium.
    private void skillTranslation(Identifier skill, String name, String description, String compendiumType, String compendiumText) {
        add(Util.makeDescriptionId("skill", skill) + ".name", name);
        add(Util.makeDescriptionId("skill", skill) + ".description", description);
        arcaneCompendiumTranslation(compendiumType + "." + skill.getPath() + ".page0.text", compendiumText);
    }

    /// Adds an ability translation, including its compendium description.
    ///
    /// @param ability        The ability resource key.
    /// @param name           The ability name.
    /// @param description    The ability description.
    private void abilityTranslation(ResourceKey<Ability> ability, String name, String description) {
        add(Util.makeDescriptionId("ability", ability.identifier()), name);
        add(Util.makeDescriptionId("ability", ability.identifier()) + ".name", name);
        add(Util.makeDescriptionId("ability", ability.identifier()) + ".description", description);
    }

    /// Adds an arcane compendium entry translation.
    ///
    /// @param compendiumEntry The compendium entry to add the translation for.
    /// @param translation     The translation to use.
    private void arcaneCompendiumTranslation(String compendiumEntry, String translation) {
        add("item", "arcane_compendium." + compendiumEntry, translation);
    }

    /// Adds a translation with the key format "[type].arsmagicalegacy.[name]".
    /// @param type        The type part of the key.
    /// @param name        The name part of the key.
    /// @param translation The translation to add.
    private void add(String type, String name, String translation) {
        add(Util.makeDescriptionId(type, ArsMagicaApi.id(name)), translation);
    }

    /// @param id A string of format "word\_word\_word".
    /// @return A string of format "Word Word Word".
    private static String idTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_")) {
            result.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
