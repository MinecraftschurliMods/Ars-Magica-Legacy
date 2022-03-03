package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

class AMTagsProvider {
    static void add(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        AMTagsProvider.Blocks blocks = new Blocks(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
        generator.addProvider(blocks);
        generator.addProvider(new Items(generator, blocks, ArsMagicaAPI.MOD_ID, existingFileHelper));
        generator.addProvider(new Fluids(generator, ArsMagicaAPI.MOD_ID, existingFileHelper));
        generator.addProvider(new EntityTypes(generator, ArsMagicaAPI.MOD_ID, existingFileHelper));
    }

    private static class Blocks extends BlockTagsProvider {
        Blocks(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(AMTags.Blocks.WITCHWOOD_LOGS).add(AMBlocks.WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD.get(), AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.STRIPPED_WITCHWOOD.get());
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
            tag(Tags.Blocks.ORES).addTag(AMTags.Blocks.ORES_CHIMERITE).addTag(AMTags.Blocks.ORES_TOPAZ).addTag(AMTags.Blocks.ORES_VINTEUM).addTag(AMTags.Blocks.ORES_MOONSTONE).addTag(AMTags.Blocks.ORES_SUNSTONE);
            tag(Tags.Blocks.STORAGE_BLOCKS).addTag(AMTags.Blocks.STORAGE_BLOCKS_CHIMERITE).addTag(AMTags.Blocks.STORAGE_BLOCKS_TOPAZ).addTag(AMTags.Blocks.STORAGE_BLOCKS_VINTEUM).addTag(AMTags.Blocks.STORAGE_BLOCKS_MOONSTONE).addTag(AMTags.Blocks.STORAGE_BLOCKS_SUNSTONE);
            tag(BlockTags.LOGS).addTag(AMTags.Blocks.WITCHWOOD_LOGS);
            tag(BlockTags.LEAVES).add(AMBlocks.WITCHWOOD_LEAVES.get());
            tag(BlockTags.SAPLINGS).add(AMBlocks.WITCHWOOD_SAPLING.get());
            tag(BlockTags.PLANKS).add(AMBlocks.WITCHWOOD_PLANKS.get());
            tag(BlockTags.WOODEN_SLABS).add(AMBlocks.WITCHWOOD_SLAB.get());
            tag(BlockTags.WOODEN_STAIRS).add(AMBlocks.WITCHWOOD_STAIRS.get());
            tag(BlockTags.WOODEN_FENCES).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(Tags.Blocks.FENCES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE.get());
            tag(Tags.Blocks.FENCE_GATES_WOODEN).add(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            tag(BlockTags.WOODEN_DOORS).add(AMBlocks.WITCHWOOD_DOOR.get());
            tag(BlockTags.WOODEN_TRAPDOORS).add(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            tag(BlockTags.WOODEN_BUTTONS).add(AMBlocks.WITCHWOOD_BUTTON.get());
            tag(BlockTags.WOODEN_PRESSURE_PLATES).add(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            tag(BlockTags.SMALL_FLOWERS).add(AMBlocks.AUM.get(), AMBlocks.CERUBLOSSOM.get(), AMBlocks.DESERT_NOVA.get(), AMBlocks.TARMA_ROOT.get(), AMBlocks.WAKEBLOOM.get());
            tag(BlockTags.FLOWER_POTS).add(AMBlocks.POTTED_AUM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), AMBlocks.POTTED_DESERT_NOVA.get(), AMBlocks.POTTED_TARMA_ROOT.get(), AMBlocks.POTTED_WAKEBLOOM.get());
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(AMBlocks.OCCULUS.get(), AMBlocks.ALTAR_CORE.get(), AMBlocks.MAGIC_WALL.get(), AMBlocks.OBELISK.get(), AMBlocks.CELESTIAL_PRISM.get(), AMBlocks.BLACK_AUREM.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), AMBlocks.SUNSTONE_BLOCK.get(), AMBlocks.SUNSTONE_ORE.get());
//            tag(BlockTags.MINEABLE_WITH_SHOVEL);
            tag(BlockTags.MINEABLE_WITH_AXE).add(AMBlocks.INSCRIPTION_TABLE.get(), AMBlocks.WITCHWOOD_FENCE_GATE.get());
//            tag(BlockTags.MINEABLE_WITH_HOE);
            tag(BlockTags.NEEDS_STONE_TOOL).add(AMBlocks.OCCULUS.get(), AMBlocks.ALTAR_CORE.get(), AMBlocks.CHIMERITE_BLOCK.get(), AMBlocks.CHIMERITE_ORE.get(), AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), AMBlocks.TOPAZ_BLOCK.get(), AMBlocks.TOPAZ_ORE.get(), AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), AMBlocks.VINTEUM_BLOCK.get(), AMBlocks.VINTEUM_ORE.get(), AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), AMBlocks.MOONSTONE_BLOCK.get(), AMBlocks.SUNSTONE_BLOCK.get());
            tag(BlockTags.NEEDS_IRON_TOOL).add(AMBlocks.MOONSTONE_ORE.get(), AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
            tag(BlockTags.NEEDS_DIAMOND_TOOL).add(AMBlocks.SUNSTONE_ORE.get());
        }
    }

    private static class Items extends ItemTagsProvider {
        Items(DataGenerator generator, BlockTagsProvider blockTagsProvider, String modId, ExistingFileHelper existingFileHelper) {
            super(generator, blockTagsProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {
            tag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("curios", "head"))).add(AMItems.MAGITECH_GOGGLES.get());
            copy(AMTags.Blocks.WITCHWOOD_LOGS, AMTags.Items.WITCHWOOD_LOGS);
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
            tag(Tags.Items.ORES).addTag(AMTags.Items.ORES_CHIMERITE).addTag(AMTags.Items.ORES_TOPAZ).addTag(AMTags.Items.ORES_VINTEUM).addTag(AMTags.Items.ORES_MOONSTONE).addTag(AMTags.Items.ORES_SUNSTONE);
            tag(Tags.Items.STORAGE_BLOCKS).addTag(AMTags.Items.STORAGE_BLOCKS_CHIMERITE).addTag(AMTags.Items.STORAGE_BLOCKS_TOPAZ).addTag(AMTags.Items.STORAGE_BLOCKS_VINTEUM).addTag(AMTags.Items.STORAGE_BLOCKS_MOONSTONE).addTag(AMTags.Items.STORAGE_BLOCKS_SUNSTONE);
            tag(AMTags.Items.GEMS_CHIMERITE).add(AMItems.CHIMERITE.get());
            tag(AMTags.Items.GEMS_TOPAZ).add(AMItems.TOPAZ.get());
            tag(AMTags.Items.DUSTS_VINTEUM).add(AMItems.VINTEUM_DUST.get());
            tag(AMTags.Items.GEMS_MOONSTONE).add(AMItems.MOONSTONE.get());
            tag(AMTags.Items.GEMS_SUNSTONE).add(AMItems.SUNSTONE.get());
            tag(AMTags.Items.DUSTS_ARCANE_COMPOUND).add(AMItems.ARCANE_COMPOUND.get());
            tag(AMTags.Items.DUSTS_ARCANE_ASH).add(AMItems.ARCANE_ASH.get());
            tag(AMTags.Items.DUSTS_PURIFIED_VINTEUM).add(AMItems.PURIFIED_VINTEUM_DUST.get());
            tag(Tags.Items.GEMS).addTag(AMTags.Items.GEMS_CHIMERITE).addTag(AMTags.Items.GEMS_TOPAZ).addTag(AMTags.Items.GEMS_MOONSTONE).addTag(AMTags.Items.GEMS_SUNSTONE);
            tag(Tags.Items.DUSTS).addTag(AMTags.Items.DUSTS_VINTEUM).addTag(AMTags.Items.DUSTS_ARCANE_COMPOUND).addTag(AMTags.Items.DUSTS_ARCANE_ASH).addTag(AMTags.Items.DUSTS_PURIFIED_VINTEUM);
            tag(AMTags.Items.RUNES_COLORLESS).add(AMItems.BLANK_RUNE.get());
            tag(AMTags.Items.RUNES_WHITE).add(AMItems.COLORED_RUNE.get(DyeColor.WHITE));
            tag(AMTags.Items.RUNES_ORANGE).add(AMItems.COLORED_RUNE.get(DyeColor.ORANGE));
            tag(AMTags.Items.RUNES_MAGENTA).add(AMItems.COLORED_RUNE.get(DyeColor.MAGENTA));
            tag(AMTags.Items.RUNES_LIGHT_BLUE).add(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_BLUE));
            tag(AMTags.Items.RUNES_YELLOW).add(AMItems.COLORED_RUNE.get(DyeColor.YELLOW));
            tag(AMTags.Items.RUNES_LIME).add(AMItems.COLORED_RUNE.get(DyeColor.LIME));
            tag(AMTags.Items.RUNES_PINK).add(AMItems.COLORED_RUNE.get(DyeColor.PINK));
            tag(AMTags.Items.RUNES_GRAY).add(AMItems.COLORED_RUNE.get(DyeColor.GRAY));
            tag(AMTags.Items.RUNES_LIGHT_GRAY).add(AMItems.COLORED_RUNE.get(DyeColor.LIGHT_GRAY));
            tag(AMTags.Items.RUNES_CYAN).add(AMItems.COLORED_RUNE.get(DyeColor.CYAN));
            tag(AMTags.Items.RUNES_PURPLE).add(AMItems.COLORED_RUNE.get(DyeColor.PURPLE));
            tag(AMTags.Items.RUNES_BLUE).add(AMItems.COLORED_RUNE.get(DyeColor.BLUE));
            tag(AMTags.Items.RUNES_BROWN).add(AMItems.COLORED_RUNE.get(DyeColor.BROWN));
            tag(AMTags.Items.RUNES_GREEN).add(AMItems.COLORED_RUNE.get(DyeColor.GREEN));
            tag(AMTags.Items.RUNES_RED).add(AMItems.COLORED_RUNE.get(DyeColor.RED));
            tag(AMTags.Items.RUNES_BLACK).add(AMItems.COLORED_RUNE.get(DyeColor.BLACK));
            tag(AMTags.Items.RUNES).addTag(AMTags.Items.RUNES_COLORLESS).addTag(AMTags.Items.RUNES_BLACK).addTag(AMTags.Items.RUNES_BLUE).addTag(AMTags.Items.RUNES_BROWN).addTag(AMTags.Items.RUNES_CYAN).addTag(AMTags.Items.RUNES_GRAY).addTag(AMTags.Items.RUNES_GREEN).addTag(AMTags.Items.RUNES_LIGHT_BLUE).addTag(AMTags.Items.RUNES_LIGHT_GRAY).addTag(AMTags.Items.RUNES_LIME).addTag(AMTags.Items.RUNES_MAGENTA).addTag(AMTags.Items.RUNES_ORANGE).addTag(AMTags.Items.RUNES_PINK).addTag(AMTags.Items.RUNES_PURPLE).addTag(AMTags.Items.RUNES_RED).addTag(AMTags.Items.RUNES_WHITE).addTag(AMTags.Items.RUNES_YELLOW);
            tag(ItemTags.LOGS).addTag(AMTags.Items.WITCHWOOD_LOGS);
            tag(ItemTags.LEAVES).add(AMItems.WITCHWOOD_LEAVES.get());
            tag(ItemTags.SAPLINGS).add(AMItems.WITCHWOOD_SAPLING.get());
            tag(ItemTags.PLANKS).add(AMItems.WITCHWOOD_PLANKS.get());
            tag(ItemTags.WOODEN_SLABS).add(AMItems.WITCHWOOD_SLAB.get());
            tag(ItemTags.WOODEN_STAIRS).add(AMItems.WITCHWOOD_STAIRS.get());
            tag(ItemTags.WOODEN_FENCES).add(AMItems.WITCHWOOD_FENCE.get());
            tag(Tags.Items.FENCES_WOODEN).add(AMItems.WITCHWOOD_FENCE.get());
            tag(Tags.Items.FENCE_GATES_WOODEN).add(AMItems.WITCHWOOD_FENCE_GATE.get());
            tag(ItemTags.WOODEN_DOORS).add(AMItems.WITCHWOOD_DOOR.get());
            tag(ItemTags.WOODEN_TRAPDOORS).add(AMItems.WITCHWOOD_TRAPDOOR.get());
            tag(ItemTags.WOODEN_BUTTONS).add(AMItems.WITCHWOOD_BUTTON.get());
            tag(ItemTags.WOODEN_PRESSURE_PLATES).add(AMItems.WITCHWOOD_PRESSURE_PLATE.get());
            tag(ItemTags.SMALL_FLOWERS).add(AMItems.AUM.get()).add(AMItems.CERUBLOSSOM.get()).add(AMItems.DESERT_NOVA.get()).add(AMItems.TARMA_ROOT.get()).add(AMItems.WAKEBLOOM.get());
        }
    }

    private static class Fluids extends FluidTagsProvider {
        Fluids(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {

        }
    }

    private static class EntityTypes extends EntityTypeTagsProvider {
        EntityTypes(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
            super(generator, modId, existingFileHelper);
        }

        @Override
        protected void addTags() {

        }
    }
}
