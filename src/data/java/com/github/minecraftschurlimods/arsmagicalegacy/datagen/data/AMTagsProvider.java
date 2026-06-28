package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDamageTypes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEnchantments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMFluids;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagCopyingItemTagProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public final class AMTagsProvider {
    private AMTagsProvider() {}

    public static final class Blocks extends BlockTagsProvider {
        public Blocks(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(AMTags.Blocks.ORES_CHIMERITE).add(AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get());
            tag(AMTags.Blocks.ORES_TOPAZ).add(AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get());
            tag(AMTags.Blocks.ORES_VINTEUM).add(AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get());
            tag(AMTags.Blocks.ORES_MOONSTONE).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
            tag(AMTags.Blocks.ORES_SUNSTONE).add(AMBlocks.SUNSTONE_ORE.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE).add(AMBlocks.CHIMERITE_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ).add(AMBlocks.TOPAZ_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM).add(AMBlocks.VINTEUM_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE).add(AMBlocks.MOONSTONE_BLOCK.get());
            tag(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE).add(AMBlocks.SUNSTONE_BLOCK.get());
            tag(Tags.Blocks.ORES).addTags(AMTags.Blocks.ORES_CHIMERITE, AMTags.Blocks.ORES_TOPAZ, AMTags.Blocks.ORES_VINTEUM, AMTags.Blocks.ORES_MOONSTONE, AMTags.Blocks.ORES_SUNSTONE);
            tag(Tags.Blocks.STORAGE_BLOCKS).addTags(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE);
            tag(AMTags.Blocks.WITCHWOOD_LOGS).add(AMBlocks.WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD_WOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.STRIPPED_WITCHWOOD_WOOD.get());
            tag(BlockTags.LOGS).addTag(AMTags.Blocks.WITCHWOOD_LOGS);
            tag(BlockTags.LEAVES).add(AMBlocks.WITCHWOOD_LEAVES.get());
            tag(BlockTags.SAPLINGS).add(AMBlocks.WITCHWOOD_SAPLING.get());
            tag(BlockTags.PLANKS).add(AMBlocks.WITCHWOOD_PLANKS.get());
            tag(BlockTags.WOODEN_SLABS).add(AMBlocks.WITCHWOOD_SLAB.get());
            tag(BlockTags.WOODEN_STAIRS).add(AMBlocks.WITCHWOOD_STAIRS.get());
            tag(BlockTags.WOODEN_FENCES).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(Tags.Blocks.FENCES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(BlockTags.FENCE_GATES).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            tag(BlockTags.WOODEN_DOORS).add(AMBlocks.WITCHWOOD_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            tag(BlockTags.WOODEN_BUTTONS).add(AMBlocks.WITCHWOOD_BUTTON.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            tag(BlockTags.STANDING_SIGNS).add(AMBlocks.WITCHWOOD_SIGN.get());
            tag(BlockTags.WALL_SIGNS).add(AMBlocks.WITCHWOOD_WALL_SIGN.get());
            tag(BlockTags.CEILING_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            tag(BlockTags.WALL_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
            tag(AMTags.Blocks.AUM_PLANTABLE_ON).addTag(BlockTags.SUBSTRATE_OVERWORLD);
            tag(AMTags.Blocks.CERUBLOSSOM_PLANTABLE_ON).addTag(BlockTags.SUBSTRATE_OVERWORLD);
            tag(AMTags.Blocks.DESERT_NOVA_PLANTABLE_ON).addTag(BlockTags.SAND);
            tag(AMTags.Blocks.TARMA_ROOT_PLANTABLE_ON).add(net.minecraft.world.level.block.Blocks.CLAY, net.minecraft.world.level.block.Blocks.GRAVEL).addTags(BlockTags.SUBSTRATE_OVERWORLD, BlockTags.SAND, BlockTags.STONE_ORE_REPLACEABLES, BlockTags.DEEPSLATE_ORE_REPLACEABLES);
            tag(AMTags.Blocks.DRYADS_SPAWNABLE_ON).addTag(BlockTags.ANIMALS_SPAWNABLE_ON);
            tag(BlockTags.RAILS).add(AMBlocks.REDSTONE_INLAY.get(), AMBlocks.IRON_INLAY.get(), AMBlocks.GOLD_INLAY.get());
            tag(BlockTags.SMALL_FLOWERS).add(AMBlocks.AUM.get(), AMBlocks.CERUBLOSSOM.get(), AMBlocks.DESERT_NOVA.get(), AMBlocks.TARMA_ROOT.get(), AMBlocks.WAKEBLOOM.get());
            tag(BlockTags.FLOWER_POTS).add(AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_AUM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), AMBlocks.POTTED_DESERT_NOVA.get(), AMBlocks.POTTED_TARMA_ROOT.get(), AMBlocks.POTTED_WAKEBLOOM.get());
            tag(BlockTags.CAULDRONS).add(AMBlocks.LIQUID_ETHERIUM_CAULDRON.get());
            tag(AMTags.Blocks.ETHERIUM_PROVIDERS).add(AMBlocks.OBELISK.get(), AMBlocks.CELESTIAL_PRISM.get(), AMBlocks.BLACK_AUREM.get());
            tag(AMTags.Blocks.ETHERIUM_CONSUMERS).add(AMBlocks.ALTAR_CORE.get());
            tag(AMTags.Blocks.WIZARDS_AUTUMN_LEAVES).addTag(BlockTags.LEAVES);
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AMBlocks.OCCULUS.get(), AMBlocks.ALTAR_CORE.get(), AMBlocks.MAGIC_WALL.get(), AMBlocks.OBELISK.get(), AMBlocks.CELESTIAL_PRISM.get(), AMBlocks.BLACK_AUREM.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_ORE.get(), AMBlocks.SUNSTONE_BLOCK.get());
            tag(BlockTags.MINEABLE_WITH_AXE).add(AMBlocks.INSCRIPTION_TABLE.get());
            tag(BlockTags.NEEDS_STONE_TOOL).add(AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_BLOCK.get());
            tag(BlockTags.NEEDS_IRON_TOOL).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(AMBlocks.SUNSTONE_ORE.get());
        }
    }

    public static final class Items extends BlockTagCopyingItemTagProvider {
        public Items(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blocks) {
            super(output, lookupProvider, blocks, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            copy(AMTags.Blocks.ORES_CHIMERITE, AMTags.Items.ORES_CHIMERITE);
            copy(AMTags.Blocks.ORES_TOPAZ, AMTags.Items.ORES_TOPAZ);
            copy(AMTags.Blocks.ORES_VINTEUM, AMTags.Items.ORES_VINTEUM);
            copy(AMTags.Blocks.ORES_MOONSTONE, AMTags.Items.ORES_MOONSTONE);
            copy(AMTags.Blocks.ORES_SUNSTONE, AMTags.Items.ORES_SUNSTONE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_CHIMERITE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_TOPAZ);
            copy(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_VINTEUM);
            copy(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_MOONSTONE);
            copy(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
            tag(AMTags.Items.GEMS_CHIMERITE).add(AMItems.CHIMERITE.get());
            tag(AMTags.Items.GEMS_TOPAZ).add(AMItems.TOPAZ.get());
            tag(AMTags.Items.DUSTS_VINTEUM).add(AMItems.VINTEUM_DUST.get());
            tag(AMTags.Items.GEMS_MOONSTONE).add(AMItems.MOONSTONE.get());
            tag(AMTags.Items.GEMS_SUNSTONE).add(AMItems.SUNSTONE.get());
            tag(AMTags.Items.DUSTS_ARCANE_COMPOUND).add(AMItems.ARCANE_COMPOUND.get());
            tag(AMTags.Items.DUSTS_ARCANE_ASH).add(AMItems.ARCANE_ASH.get());
            tag(AMTags.Items.DUSTS_PURIFIED_VINTEUM).add(AMItems.PURIFIED_VINTEUM_DUST.get());
            tag(Tags.Items.ORES).addTags(AMTags.Items.ORES_CHIMERITE, AMTags.Items.ORES_TOPAZ, AMTags.Items.ORES_VINTEUM, AMTags.Items.ORES_MOONSTONE, AMTags.Items.ORES_SUNSTONE);
            tag(Tags.Items.STORAGE_BLOCKS).addTags(AMTags.Items.STORAGE_BLOCKS_CHIMERITE, AMTags.Items.STORAGE_BLOCKS_TOPAZ, AMTags.Items.STORAGE_BLOCKS_VINTEUM, AMTags.Items.STORAGE_BLOCKS_MOONSTONE, AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
            tag(Tags.Items.GEMS).addTags(AMTags.Items.GEMS_CHIMERITE, AMTags.Items.GEMS_TOPAZ, AMTags.Items.GEMS_MOONSTONE, AMTags.Items.GEMS_SUNSTONE);
            tag(Tags.Items.DUSTS).addTags(AMTags.Items.DUSTS_VINTEUM, AMTags.Items.DUSTS_ARCANE_COMPOUND, AMTags.Items.DUSTS_ARCANE_ASH, AMTags.Items.DUSTS_PURIFIED_VINTEUM);
            copy(AMTags.Blocks.WITCHWOOD_LOGS, AMTags.Items.WITCHWOOD_LOGS);
            tag(ItemTags.LOGS).addTag(AMTags.Items.WITCHWOOD_LOGS);
            tag(ItemTags.LEAVES).add(AMItems.WITCHWOOD_LEAVES.get());
            tag(ItemTags.SAPLINGS).add(AMItems.WITCHWOOD_SAPLING.get());
            tag(ItemTags.PLANKS).add(AMItems.WITCHWOOD_PLANKS.get());
            tag(ItemTags.WOODEN_SLABS).add(AMItems.WITCHWOOD_SLAB.get());
            tag(ItemTags.WOODEN_STAIRS).add(AMItems.WITCHWOOD_STAIRS.get());
            tag(ItemTags.WOODEN_FENCES).add(AMItems.WITCHWOOD_FENCE.get());
            tag(Tags.Items.FENCES_WOODEN).add(AMItems.WITCHWOOD_FENCE.get());
            tag(ItemTags.FENCE_GATES).add(AMItems.WITCHWOOD_FENCE_GATE.get());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(AMItems.WITCHWOOD_FENCE_GATE.get());
            tag(ItemTags.WOODEN_DOORS).add(AMItems.WITCHWOOD_DOOR.get());
            tag(ItemTags.WOODEN_TRAPDOORS).add(AMItems.WITCHWOOD_TRAPDOOR.get());
            tag(ItemTags.WOODEN_BUTTONS).add(AMItems.WITCHWOOD_BUTTON.get());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(AMItems.WITCHWOOD_PRESSURE_PLATE.get());
            tag(ItemTags.SIGNS).add(AMItems.WITCHWOOD_SIGN.get());
            tag(ItemTags.HANGING_SIGNS).add(AMItems.WITCHWOOD_HANGING_SIGN.get());
            tag(ItemTags.BOATS).add(AMItems.WITCHWOOD_BOAT.get());
            tag(ItemTags.CHEST_BOATS).add(AMItems.WITCHWOOD_CHEST_BOAT.get());
            tag(ItemTags.SMALL_FLOWERS).add(AMItems.AUM.get(), AMItems.CERUBLOSSOM.get(), AMItems.DESERT_NOVA.get(), AMItems.TARMA_ROOT.get(), AMItems.WAKEBLOOM.get());
            tag(ItemTags.RAILS).add(AMItems.REDSTONE_INLAY.get(), AMItems.IRON_INLAY.get(), AMItems.GOLD_INLAY.get());
            tag(AMTags.Items.MAGITECH_GOGGLES_REPAIR_ITEMS).addTag(AMTags.Items.GEMS_TOPAZ);
            tag(AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS).add(AMItems.BLANK_RUNE.get());
            tag(AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS).add(AMItems.BLANK_RUNE.get());
            tag(ItemTags.HEAD_ARMOR).add(AMItems.MAGITECH_GOGGLES.get(), AMItems.MAGE_HELMET.get(), AMItems.BATTLEMAGE_HELMET.get());
            tag(ItemTags.CHEST_ARMOR).add(AMItems.MAGE_CHESTPLATE.get(), AMItems.BATTLEMAGE_CHESTPLATE.get());
            tag(ItemTags.LEG_ARMOR).add(AMItems.MAGE_LEGGINGS.get(), AMItems.BATTLEMAGE_LEGGINGS.get());
            tag(ItemTags.FOOT_ARMOR).add(AMItems.MAGE_BOOTS.get(), AMItems.BATTLEMAGE_BOOTS.get());
            tag(ItemTags.BOOKSHELF_BOOKS).add(AMItems.SPELL_BOOK.get(), AMItems.AFFINITY_TOME.get());
            tag(ItemTags.LECTERN_BOOKS).add(AMItems.SPELL_RECIPE.get());
            tag(ItemTags.CAULDRON_CAN_REMOVE_DYE).add(AMItems.SPELL_BOOK.get());
            tag(AMTags.Items.ARCANE_COMPENDIUM_BOOKS).add(net.minecraft.world.item.Items.BOOK);
            tag(AMTags.Items.INSCRIPTION_TABLE_BOOKS).add(net.minecraft.world.item.Items.WRITABLE_BOOK, AMItems.SPELL_RECIPE.get());
            tag(AMTags.Items.OCCULUS_FORGET_ALL).addTag(AMTags.Items.STORAGE_BLOCKS_VINTEUM);
            tag(AMTags.Items.RUNES).add(AMItems.BLANK_RUNE.get(), AMItems.WHITE_RUNE.get(), AMItems.ORANGE_RUNE.get(), AMItems.MAGENTA_RUNE.get(), AMItems.LIGHT_BLUE_RUNE.get(), AMItems.YELLOW_RUNE.get(), AMItems.LIME_RUNE.get(), AMItems.PINK_RUNE.get(), AMItems.GRAY_RUNE.get(), AMItems.LIGHT_GRAY_RUNE.get(), AMItems.CYAN_RUNE.get(), AMItems.PURPLE_RUNE.get(), AMItems.BLUE_RUNE.get(), AMItems.BROWN_RUNE.get(), AMItems.GREEN_RUNE.get(), AMItems.RED_RUNE.get(), AMItems.BLACK_RUNE.get());
            tag(AMTags.Items.SHOWS_BARS_LAYER).add(AMItems.SPELL.get(), AMItems.SPELL_BOOK.get(), AMItems.MAGE_HELMET.get(), AMItems.MAGE_CHESTPLATE.get(), AMItems.MAGE_LEGGINGS.get(), AMItems.MAGE_BOOTS.get(), AMItems.BATTLEMAGE_HELMET.get(), AMItems.BATTLEMAGE_CHESTPLATE.get(), AMItems.BATTLEMAGE_LEGGINGS.get(), AMItems.BATTLEMAGE_BOOTS.get());
            tag(AMTags.Items.SHOWS_SPELL_VISUALS).add(AMItems.SPELL.get(), AMItems.SPELL_BOOK.get());
            tag(AMTags.Items.SPELLCRAFTING_START).add(AMItems.BLANK_RUNE.get());
            tag(AMTags.Items.SPELLCRAFTING_END).add(AMItems.SPELL_PARCHMENT.get());
            tag(CuriosTags.HEAD).add(AMItems.MAGITECH_GOGGLES.get());
        }
    }

    public static final class Fluids extends FluidTagsProvider {
        public Fluids(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(FluidTags.WATER).add(AMFluids.LIQUID_ETHERIUM.get(), AMFluids.FLOWING_LIQUID_ETHERIUM.get());
        }
    }

    public static final class EntityTypes extends EntityTypeTagsProvider {
        public EntityTypes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(Tags.EntityTypes.BOATS).add(AMEntities.WITCHWOOD_BOAT.get(), AMEntities.WITCHWOOD_CHEST_BOAT.get());
            tag(Tags.EntityTypes.BOSSES).add(AMEntities.WATER_GUARDIAN.get(), AMEntities.FIRE_GUARDIAN.get(), AMEntities.EARTH_GUARDIAN.get(), AMEntities.AIR_GUARDIAN.get(), AMEntities.ICE_GUARDIAN.get(), AMEntities.LIGHTNING_GUARDIAN.get(), AMEntities.NATURE_GUARDIAN.get(), AMEntities.LIFE_GUARDIAN.get(), AMEntities.ARCANE_GUARDIAN.get(), AMEntities.ENDER_GUARDIAN.get());
            tag(AMTags.EntityTypes.BLACK_AUREM_IMMUNE).addTags(EntityTypeTags.UNDEAD, Tags.EntityTypes.BOSSES);
            tag(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY).add(EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.SHULKER);
            tag(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY).addTag(EntityTypeTags.UNDEAD);
            tag(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY).addTag(EntityTypeTags.UNDEAD);
            tag(AMTags.EntityTypes.ENDER_GUARDIAN_SACRIFICES).add(EntityType.ENDERMAN);
            tag(AMTags.EntityTypes.SUMMONING_NOT_SUPPORTED)
                .addTags(Tags.EntityTypes.BOSSES, Tags.EntityTypes.CAPTURING_NOT_SUPPORTED)
                .add(EntityType.ARMOR_STAND, EntityType.GIANT, EntityType.ILLUSIONER, EntityType.MANNEQUIN, EntityType.PLAYER, EntityType.WARDEN, EntityType.CREAKING)
                .add(TagEntry.optionalElement(Identifier.fromNamespaceAndPath("bibliocraft", "fancy_armor_stand")));
        }
    }

    public static final class DamageTypes extends DamageTypeTagsProvider {
        public DamageTypes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(AMDamageTypes.SPELL_MAGIC);
            tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(AMDamageTypes.SPELL_MAGIC);
            tag(DamageTypeTags.BYPASSES_ARMOR).add(AMDamageTypes.SPELL_DROWNING, AMDamageTypes.SPELL_FROST, AMDamageTypes.SPELL_MAGIC);
            tag(DamageTypeTags.BYPASSES_INVULNERABILITY).addTag(AMTags.DamageTypes.IS_SPELL);
            tag(DamageTypeTags.IGNITES_ARMOR_STANDS).add(AMDamageTypes.SPELL_FIRE);
            tag(DamageTypeTags.IS_DROWNING).add(AMDamageTypes.SPELL_DROWNING);
            tag(DamageTypeTags.IS_FIRE).add(AMDamageTypes.SPELL_FIRE);
            tag(DamageTypeTags.IS_FREEZING).add(AMDamageTypes.SPELL_FROST);
            tag(DamageTypeTags.IS_LIGHTNING).add(AMDamageTypes.SPELL_LIGHTNING);
            tag(DamageTypeTags.IS_PROJECTILE).add(AMDamageTypes.NATURE_SCYTHE, AMDamageTypes.THROWN_ROCK);
            tag(DamageTypeTags.WITHER_IMMUNE_TO).add(AMDamageTypes.SPELL_DROWNING);
            tag(DamageTypeTags.WITCH_RESISTANT_TO).add(AMDamageTypes.SPELL_MAGIC);
            tag(Tags.DamageTypes.IS_MAGIC).add(AMDamageTypes.SPELL_MAGIC);
            tag(Tags.DamageTypes.IS_PHYSICAL).add(AMDamageTypes.SPELL_PHYSICAL, AMDamageTypes.SPELL_PHYSICAL_PLAYER);
            tag(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY).addTag(DamageTypeTags.IS_FIRE);
            tag(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY).addTag(Tags.DamageTypes.IS_PHYSICAL).remove(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY).addTag(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY).addTag(DamageTypeTags.IS_FALL);
            tag(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY).addTag(Tags.DamageTypes.IS_MAGIC).remove(Tags.DamageTypes.IS_POISON);
            tag(AMTags.DamageTypes.BYPASSES_SHIELD_OVERLOAD).addTag(DamageTypeTags.BYPASSES_INVULNERABILITY);
            tag(AMTags.DamageTypes.IS_SPELL).add(AMDamageTypes.SPELL_DROWNING, AMDamageTypes.SPELL_FIRE, AMDamageTypes.SPELL_FROST, AMDamageTypes.SPELL_LIGHTNING, AMDamageTypes.SPELL_MAGIC, AMDamageTypes.SPELL_PHYSICAL, AMDamageTypes.SPELL_PHYSICAL_PLAYER);
            tag(AMTags.DamageTypes.WATER_GUARDIAN_IS_VULNERABLE_TO).addTag(DamageTypeTags.IS_LIGHTNING);
            tag(AMTags.DamageTypes.WATER_GUARDIAN_IS_IMMUNE_TO).addTag(DamageTypeTags.IS_DROWNING);
            tag(AMTags.DamageTypes.WATER_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.FIRE_GUARDIAN_IS_VULNERABLE_TO).addTag(DamageTypeTags.IS_DROWNING);
            tag(AMTags.DamageTypes.FIRE_GUARDIAN_IS_IMMUNE_TO).addTags(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_FREEZING);
            tag(AMTags.DamageTypes.FIRE_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.EARTH_GUARDIAN_IS_VULNERABLE_TO).addTags(DamageTypeTags.IS_DROWNING, DamageTypeTags.IS_FREEZING);
            tag(AMTags.DamageTypes.EARTH_GUARDIAN_IS_IMMUNE_TO).addTags(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_LIGHTNING);
            tag(AMTags.DamageTypes.EARTH_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.AIR_GUARDIAN_IS_VULNERABLE_TO).addTag(DamageTypeTags.IS_LIGHTNING);
            tag(AMTags.DamageTypes.AIR_GUARDIAN_IS_IMMUNE_TO).addTags(DamageTypeTags.IS_FALL, DamageTypeTags.IS_PROJECTILE);
            tag(AMTags.DamageTypes.AIR_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.ICE_GUARDIAN_IS_VULNERABLE_TO).addTag(DamageTypeTags.IS_FIRE);
            tag(AMTags.DamageTypes.ICE_GUARDIAN_IS_IMMUNE_TO).addTag(DamageTypeTags.IS_FREEZING);
            tag(AMTags.DamageTypes.ICE_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_VULNERABLE_TO).addTag(DamageTypeTags.IS_DROWNING);
            tag(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_IMMUNE_TO);
            tag(AMTags.DamageTypes.LIGHTNING_GUARDIAN_IS_HEAL_TO).addTag(DamageTypeTags.IS_LIGHTNING);
            tag(AMTags.DamageTypes.NATURE_GUARDIAN_IS_VULNERABLE_TO).addTags(DamageTypeTags.IS_FIRE, DamageTypeTags.IS_FREEZING);
            tag(AMTags.DamageTypes.NATURE_GUARDIAN_IS_IMMUNE_TO);
            tag(AMTags.DamageTypes.NATURE_GUARDIAN_IS_HEAL_TO).addTag(DamageTypeTags.IS_DROWNING);
            tag(AMTags.DamageTypes.LIFE_GUARDIAN_IS_VULNERABLE_TO);
            tag(AMTags.DamageTypes.LIFE_GUARDIAN_IS_IMMUNE_TO);
            tag(AMTags.DamageTypes.LIFE_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_VULNERABLE_TO);
            tag(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_IMMUNE_TO);
            tag(AMTags.DamageTypes.ARCANE_GUARDIAN_IS_HEAL_TO);
            tag(AMTags.DamageTypes.ENDER_GUARDIAN_IS_VULNERABLE_TO).addTags(DamageTypeTags.IS_DROWNING, Tags.DamageTypes.IS_MAGIC);
            tag(AMTags.DamageTypes.ENDER_GUARDIAN_IS_IMMUNE_TO);
            tag(AMTags.DamageTypes.ENDER_GUARDIAN_IS_HEAL_TO);
        }
    }

    public static final class Enchantments extends EnchantmentTagsProvider {
        public Enchantments(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(Tags.Enchantments.INCREASE_ENTITY_DROPS).add(AMEnchantments.DISMEMBERING);
        }
    }

    public static final class Biomes extends BiomeTagsProvider {
        public Biomes(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
            super(output, provider, ArsMagicaApi.MOD_ID);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            tag(AMTags.Biomes.CAN_SUMMON_WATER_GUARDIAN).addTags(Tags.Biomes.IS_AQUATIC, Tags.Biomes.IS_BEACH, Tags.Biomes.IS_STONY_SHORES, Tags.Biomes.IS_SWAMP);
        }
    }
}
