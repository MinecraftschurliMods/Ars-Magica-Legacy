package com.github.minecraftschurlimods.arsmagicalegacy.datagen;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import at.minecraftschurli.mods.easydatagenlib.CompatDataProvider;
import at.minecraftschurli.mods.easydatagenlib.api.ICompatHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public final class AMCompatDataProvider extends CompatDataProvider {
    public AMCompatDataProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(ArsMagicaApi.MOD_ID, output, registries);
    }

    @Override
    protected void generate(HolderLookup.Provider context) {
        addGemOreProcessing(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get(), AMTags.Items.ORES_CHIMERITE, AMItems.CHIMERITE.get(), AMItems.CHIMERITE_BLOCK.get());
        addGemOreProcessing(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get(), AMTags.Items.ORES_TOPAZ, AMItems.TOPAZ.get(), AMItems.TOPAZ_BLOCK.get());
        addGemOreProcessing(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get(), AMTags.Items.ORES_VINTEUM, AMItems.VINTEUM_DUST.get(), AMItems.VINTEUM_BLOCK.get());
        addGemOreProcessing(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get(), AMTags.Items.ORES_MOONSTONE, AMItems.MOONSTONE.get(), AMItems.MOONSTONE_BLOCK.get());
/* TODO
        CREATE_CRUSHING.builder("sunstone_ore", 500)
            .addInput(Ingredient.of(AMItems.SUNSTONE_ORE.get()))
            .addOutput(AMItems.SUNSTONE.get())
            .addOutput(AMItems.SUNSTONE.get(), 0.75f)
            .addOutput(Identifier.fromNamespaceAndPath("create", "powdered_obsidian"), 0.125f).build();
*/
        addFlowerProcessing(AMItems.AUM.get(), Items.PINK_DYE, 2, ICompatHandler.itemId(Items.LIME_DYE), 0.05f, ICompatHandler.itemId(Items.WHITE_DYE), 0.05f);
        addFlowerProcessing(AMItems.CERUBLOSSOM.get(), Items.BLUE_DYE, 2, ICompatHandler.itemId(Items.GREEN_DYE), 0.05f, ICompatHandler.itemId(Items.CYAN_DYE), 0.05f);
        addFlowerProcessing(AMItems.DESERT_NOVA.get(), Items.RED_DYE, 1, ICompatHandler.itemId(Items.GREEN_DYE), 0.75f, ICompatHandler.itemId(Items.WHITE_DYE), 0.05f);
        addFlowerProcessing(AMItems.TARMA_ROOT.get(), Items.BROWN_DYE, 2, ICompatHandler.itemId(Items.BROWN_DYE), 0.5f);
        addFlowerProcessing(AMItems.WAKEBLOOM.get(), Items.MAGENTA_DYE, 2, ICompatHandler.itemId(Items.PINK_DYE), 0.5f);
        addLogsProcessing(AMItems.WITCHWOOD_LOG.get(), AMItems.WITCHWOOD.get(), AMItems.STRIPPED_WITCHWOOD_LOG.get(), AMItems.STRIPPED_WITCHWOOD.get(), AMItems.WITCHWOOD_PLANKS.getId(), AMItems.WITCHWOOD_LEAVES.get(), AMItems.WITCHWOOD_SAPLING.get());
        addWoodenProcessing(AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), AMTags.Items.WITCHWOOD_LOGS);
    }

    @Override
    public String getName() {
        return "Ars Magica: Legacy Compat";
    }
}
