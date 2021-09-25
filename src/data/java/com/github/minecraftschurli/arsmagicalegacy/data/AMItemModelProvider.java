package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems.*;

class AMItemModelProvider extends ItemModelProvider {
    AMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("arcane_compendium", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/arcane_compendium"));
        blockItem(CHIMERITE_ORE, AMBlocks.CHIMERITE_ORE);
//        blockItem(DEEPSLATE_CHIMERITE_ORE, AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemGenerated(CHIMERITE);
        blockItem(CHIMERITE_BLOCK, AMBlocks.CHIMERITE_BLOCK);
        blockItem(TOPAZ_ORE, AMBlocks.TOPAZ_ORE);
//        blockItem(DEEPSLATE_TOPAZ_ORE, AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemGenerated(TOPAZ);
        blockItem(TOPAZ_BLOCK, AMBlocks.TOPAZ_BLOCK);
        blockItem(VINTEUM_ORE, AMBlocks.VINTEUM_ORE);
//        blockItem(DEEPSLATE_VINTEUM_ORE, AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemGenerated(VINTEUM_DUST);
        blockItem(VINTEUM_BLOCK, AMBlocks.VINTEUM_BLOCK);
        blockItem(MOONSTONE_ORE, AMBlocks.MOONSTONE_ORE);
//        blockItem(DEEPSLATE_MOONSTONE_ORE, AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemGenerated(MOONSTONE);
        blockItem(MOONSTONE_BLOCK, AMBlocks.MOONSTONE_BLOCK);
        blockItem(SUNSTONE_ORE, AMBlocks.SUNSTONE_ORE);
        itemGenerated(SUNSTONE);
        blockItem(SUNSTONE_BLOCK, AMBlocks.SUNSTONE_BLOCK);
        blockItem(WITCHWOOD_LOG, AMBlocks.WITCHWOOD_LOG);
        blockItem(WITCHWOOD, AMBlocks.WITCHWOOD);
        blockItem(STRIPPED_WITCHWOOD_LOG, AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockItem(STRIPPED_WITCHWOOD, AMBlocks.STRIPPED_WITCHWOOD);
        blockItem(WITCHWOOD_LEAVES, AMBlocks.WITCHWOOD_LEAVES);
        itemGenerated(WITCHWOOD_SAPLING, "block/witchwood_sapling");
        blockItem(WITCHWOOD_PLANKS, AMBlocks.WITCHWOOD_PLANKS);
        blockItem(WITCHWOOD_SLAB, AMBlocks.WITCHWOOD_SLAB);
        blockItem(WITCHWOOD_STAIRS, AMBlocks.WITCHWOOD_STAIRS);
        withExistingParent(WITCHWOOD_FENCE, "witchwood_fence_inventory");
        blockItem(WITCHWOOD_FENCE_GATE, AMBlocks.WITCHWOOD_FENCE_GATE);
        itemGenerated(WITCHWOOD_DOOR);
        withExistingParent(WITCHWOOD_TRAPDOOR, "witchwood_trapdoor_bottom");
        itemGenerated(BLANK_RUNE);
        itemGenerated(WHITE_RUNE);
        itemGenerated(ORANGE_RUNE);
        itemGenerated(MAGENTA_RUNE);
        itemGenerated(LIGHT_BLUE_RUNE);
        itemGenerated(YELLOW_RUNE);
        itemGenerated(LIME_RUNE);
        itemGenerated(PINK_RUNE);
        itemGenerated(GRAY_RUNE);
        itemGenerated(LIGHT_GRAY_RUNE);
        itemGenerated(CYAN_RUNE);
        itemGenerated(PURPLE_RUNE);
        itemGenerated(BLUE_RUNE);
        itemGenerated(BROWN_RUNE);
        itemGenerated(GREEN_RUNE);
        itemGenerated(RED_RUNE);
        itemGenerated(BLACK_RUNE);
        itemGenerated(RUNE_BAG);
        itemGenerated(ARCANE_COMPOUND);
        itemGenerated(ARCANE_ASH);
        itemGenerated(PURIFIED_VINTEUM_DUST);
        itemGenerated(VINTEUM_TORCH, "block/vinteum_torch");
    }

    /**
     * Adds a 2D flat item model. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemGenerated(Supplier<? extends Item> item) {
        itemGenerated(item, "item/" + item.get().getRegistryName().getPath());
    }

    /**
     * Adds a 2D flat item model.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemGenerated(Supplier<? extends Item> item, String name) {
        singleTexture(item.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds a handheld item model, for tools and similar. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemHandheld(Supplier<? extends Item> item) {
        itemHandheld(item, "item/" + item.get().getRegistryName().getPath());
    }

    /**
     * Adds a handheld item model, for tools and similar.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemHandheld(Supplier<? extends Item> item, String name) {
        singleTexture(item.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds an item model that uses a parent model.
     *
     * @param item   The item to generate the model for.
     * @param parent The parent model to use.
     */
    private void withExistingParent(Supplier<? extends Item> item, String parent) {
        withExistingParent(item.get().getRegistryName().getPath(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + parent));
    }

    /**
     * Adds an item model that uses the corresponding block model as the parent model.
     *
     * @param item  The item to generate the model for.
     * @param block The block model to use.
     */
    private void blockItem(Supplier<? extends Item> item, Supplier<? extends Block> block) {
        withExistingParent(item, block.get().getRegistryName().getPath());
    }
}
