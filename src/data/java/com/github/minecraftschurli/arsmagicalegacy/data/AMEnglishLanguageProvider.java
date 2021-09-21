package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
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
    }

    private void idBlockTranslation(Supplier<? extends Block> block) {
        addBlock(block, idToTranslation(block.get().getRegistryName().getPath()));
    }

    private void idItemTranslation(Supplier<? extends Item> item) {
        addItem(item, idToTranslation(item.get().getRegistryName().getPath()));
    }
    
    private static String idToTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split(" ")) {
            String first = string.substring(0, 1);
            String other = string.substring(1);
            result.append(first.toUpperCase()).append(other).append(" ");
        }
        return result.substring(0, result.length() - 1);
    }
}
