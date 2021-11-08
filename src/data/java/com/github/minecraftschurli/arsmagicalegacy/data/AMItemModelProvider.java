package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinityItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems.*;

class AMItemModelProvider extends ItemModelProvider {
    AMItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture("arcane_compendium", new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, "item/arcane_compendium"));
        blockItem(ALTAR_CORE);
        blockItem(MAGIC_WALL);
        blockItem(CHIMERITE_ORE);
        blockItem(DEEPSLATE_CHIMERITE_ORE);
        itemGenerated(CHIMERITE);
        blockItem(CHIMERITE_BLOCK);
        blockItem(TOPAZ_ORE);
        blockItem(DEEPSLATE_TOPAZ_ORE);
        itemGenerated(TOPAZ);
        blockItem(TOPAZ_BLOCK);
        blockItem(VINTEUM_ORE);
        blockItem(DEEPSLATE_VINTEUM_ORE);
        itemGenerated(VINTEUM_DUST);
        blockItem(VINTEUM_BLOCK);
        blockItem(MOONSTONE_ORE);
        blockItem(DEEPSLATE_MOONSTONE_ORE);
        itemGenerated(MOONSTONE);
        blockItem(MOONSTONE_BLOCK);
        blockItem(SUNSTONE_ORE);
        itemGenerated(SUNSTONE);
        blockItem(SUNSTONE_BLOCK);
        blockItem(WITCHWOOD_LOG);
        blockItem(WITCHWOOD);
        blockItem(STRIPPED_WITCHWOOD_LOG);
        blockItem(STRIPPED_WITCHWOOD);
        blockItem(WITCHWOOD_LEAVES);
        itemGenerated(WITCHWOOD_SAPLING, "block/witchwood_sapling");
        blockItem(WITCHWOOD_PLANKS);
        blockItem(WITCHWOOD_SLAB);
        blockItem(WITCHWOOD_STAIRS);
        withExistingParent(WITCHWOOD_FENCE, "witchwood_fence_inventory");
        blockItem(WITCHWOOD_FENCE_GATE);
        itemGenerated(WITCHWOOD_DOOR);
        withExistingParent(WITCHWOOD_TRAPDOOR, "witchwood_trapdoor_bottom");
        withExistingParent(WITCHWOOD_BUTTON, "witchwood_button_inventory");
        blockItem(WITCHWOOD_PRESSURE_PLATE);
        itemGenerated(BLANK_RUNE);
        for (DyeColor color : DyeColor.values()) {
            itemGenerated(COLORED_RUNE.registryObject(color));
        }
        itemGenerated(RUNE_BAG);
        itemGenerated(ARCANE_COMPOUND);
        itemGenerated(ARCANE_ASH);
        itemGenerated(PURIFIED_VINTEUM_DUST);
        itemGenerated(AUM, "block/aum");
        itemGenerated(CERUBLOSSOM, "block/cerublossom");
        itemGenerated(DESERT_NOVA, "block/desert_nova");
        itemGenerated(TARMA_ROOT, "block/tarma_root");
        itemGenerated(WAKEBLOOM, "block/wakebloom");
        itemGenerated(VINTEUM_TORCH, "block/vinteum_torch");
        itemGenerated(WIZARDS_CHALK);
        affinityItem(AFFINITY_ESSENCE);
        affinityItem(AFFINITY_TOME);
    }

    /**
     * Adds a 2D flat item model. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemGenerated(RegistryObject<? extends Item> item) {
        itemGenerated(item, "item/" + item.getId().getPath());
    }

    /**
     * Adds a 2D flat item model.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemGenerated(RegistryObject<? extends Item> item, String name) {
        singleTexture(item.getId().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds a handheld item model, for tools and similar. Uses the item id as the item texture.
     *
     * @param item The item to generate the model for.
     */
    private void itemHandheld(RegistryObject<? extends Item> item) {
        itemHandheld(item, "item/" + item.getId().getPath());
    }

    /**
     * Adds a handheld item model, for tools and similar.
     *
     * @param item The item to generate the model for.
     * @param name The texture id to use.
     */
    private void itemHandheld(RegistryObject<? extends Item> item, String name) {
        singleTexture(item.getId().getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(ArsMagicaAPI.MOD_ID, name));
    }

    /**
     * Adds an item model that uses a parent model.
     *
     * @param item   The item to generate the model for.
     * @param parent The parent model to use.
     */
    private void withExistingParent(RegistryObject<? extends Item> item, String parent) {
        withExistingParent(item.getId().getPath(), new ResourceLocation(ArsMagicaAPI.MOD_ID, "block/" + parent));
    }

    /**
     * Adds an item model that uses the corresponding block model as the parent model.
     *
     * @param item  The item to generate the model for.
     * @param block The block model to use.
     */
    private void blockItem(RegistryObject<? extends Item> item, Block block) {
        withExistingParent(item, block.getRegistryName().getPath());
    }

    private void blockItem(RegistryObject<? extends BlockItem> blockItem) {
        blockItem(blockItem, blockItem.get().getBlock());
    }
    
    private <T extends Item & IAffinityItem> void affinityItem(RegistryObject<T> item) {
        getBuilder(item.getId().toString());
        for (IAffinity affinity : ArsMagicaAPI.get().getAffinityRegistry()) {
            if (affinity.getId().equals(IAffinity.NONE)) continue;
            var rl = new ResourceLocation(affinity.getId().getNamespace(), item.getId().getPath()+"_"+affinity.getId().getPath());;
            singleTexture(rl.toString(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(rl.getNamespace(), "item/"+rl.getPath()));
        }
    }
}
