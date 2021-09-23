package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

class AMEnglishLanguageProvider extends AMLanguageProvider {
    public AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        idBlockTranslation(AMBlocks.CHIMERITE_ORE);
        idBlockTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        idItemTranslation(AMItems.CHIMERITE);
        idBlockTranslation(AMBlocks.CHIMERITE_BLOCK);
        idBlockTranslation(AMBlocks.TOPAZ_ORE);
        idBlockTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        idItemTranslation(AMItems.TOPAZ);
        idBlockTranslation(AMBlocks.TOPAZ_BLOCK);
        idBlockTranslation(AMBlocks.VINTEUM_ORE);
        idBlockTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        idItemTranslation(AMItems.VINTEUM_DUST);
        idBlockTranslation(AMBlocks.VINTEUM_BLOCK);
        idBlockTranslation(AMBlocks.MOONSTONE_ORE);
        idItemTranslation(AMItems.MOONSTONE);
        idBlockTranslation(AMBlocks.MOONSTONE_BLOCK);
        idBlockTranslation(AMBlocks.SUNSTONE_ORE);
        idItemTranslation(AMItems.SUNSTONE);
        idBlockTranslation(AMBlocks.SUNSTONE_BLOCK);
        idBlockTranslation(AMBlocks.WITCHWOOD_LOG);
        idBlockTranslation(AMBlocks.WITCHWOOD);
        idBlockTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        idBlockTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        idBlockTranslation(AMBlocks.WITCHWOOD_LEAVES);
        idBlockTranslation(AMBlocks.WITCHWOOD_SAPLING);
        idBlockTranslation(AMBlocks.WITCHWOOD_PLANKS);
        idBlockTranslation(AMBlocks.WITCHWOOD_SLAB);
        idBlockTranslation(AMBlocks.WITCHWOOD_STAIRS);
        idBlockTranslation(AMBlocks.WITCHWOOD_FENCE);
        idBlockTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        idBlockTranslation(AMBlocks.WITCHWOOD_DOOR);
        idBlockTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        idBlockTranslation(AMBlocks.WITCHWOOD_BUTTON);
        idBlockTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        idItemTranslation(AMItems.BLANK_RUNE);
        idItemTranslation(AMItems.WHITE_RUNE);
        idItemTranslation(AMItems.ORANGE_RUNE);
        idItemTranslation(AMItems.MAGENTA_RUNE);
        idItemTranslation(AMItems.LIGHT_BLUE_RUNE);
        idItemTranslation(AMItems.YELLOW_RUNE);
        idItemTranslation(AMItems.LIME_RUNE);
        idItemTranslation(AMItems.PINK_RUNE);
        idItemTranslation(AMItems.GRAY_RUNE);
        idItemTranslation(AMItems.LIGHT_GRAY_RUNE);
        idItemTranslation(AMItems.CYAN_RUNE);
        idItemTranslation(AMItems.PURPLE_RUNE);
        idItemTranslation(AMItems.BLUE_RUNE);
        idItemTranslation(AMItems.BROWN_RUNE);
        idItemTranslation(AMItems.GREEN_RUNE);
        idItemTranslation(AMItems.RED_RUNE);
        idItemTranslation(AMItems.BLACK_RUNE);
        idItemTranslation(AMItems.RUNE_BAG);
        idItemTranslation(AMItems.ARCANE_ASH);
        idItemTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        idItemTranslation(AMItems.VINTEUM_TORCH);


    }

    private void idBlockTranslation(Supplier<? extends Block> block) {
        addBlock(block, idToTranslation(block.get().getRegistryName().getPath()));
    }

    private void idItemTranslation(Supplier<? extends Item> item) {
        addItem(item, idToTranslation(item.get().getRegistryName().getPath()));
    }
    
    private static String idToTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_")) {
            String first = string.substring(0, 1);
            String other = string.substring(1);
            result.append(first.toUpperCase()).append(other).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
