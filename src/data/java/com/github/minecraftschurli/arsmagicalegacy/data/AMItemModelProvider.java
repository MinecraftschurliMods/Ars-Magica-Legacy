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
    public AMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
        blockItem(CHIMERITE_ORE, AMBlocks.CHIMERITE_ORE);
        blockItem(CHIMERITE_BLOCK, AMBlocks.CHIMERITE_BLOCK);
        blockItem(TOPAZ_ORE, AMBlocks.TOPAZ_ORE);
        blockItem(TOPAZ_BLOCK, AMBlocks.TOPAZ_BLOCK);
        blockItem(VINTEUM_ORE, AMBlocks.VINTEUM_ORE);
        blockItem(VINTEUM_BLOCK, AMBlocks.VINTEUM_BLOCK);
    }

    private void itemGenerated(Supplier<? extends Item> item) {
        itemGenerated(item, "item/" + item.get().getRegistryName().getPath());
    }

    private void itemGenerated(Supplier<? extends Item> item, String name) {
        singleTexture(item.get().getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    private void itemHandheld(Supplier<? extends Item> item) {
        itemHandheld(item, "item/" + item.get().getRegistryName().getPath());
    }

    private void itemHandheld(Supplier<? extends Item> item, String name) {
        singleTexture(item.get().getRegistryName().getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    private void withExistingParent(Supplier<? extends Item> item, String name) {
        withExistingParent(item.get().getRegistryName().getPath(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + name));
    }

    private void blockItem(Supplier<? extends Item> item, Supplier<? extends Block> block) {
        withExistingParent(item, block.get().getRegistryName().getPath());
    }
}
