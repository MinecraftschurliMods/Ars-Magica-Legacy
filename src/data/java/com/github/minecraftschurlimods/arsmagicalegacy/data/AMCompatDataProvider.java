package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.AMTags;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.easydatagenlib.CompatDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

class AMCompatDataProvider extends CompatDataProvider {
    AMCompatDataProvider(GatherDataEvent event) {
        super(ArsMagicaAPI.MOD_ID, event);
    }

    @Override
    protected CompletableFuture<?> generate() {
        addGemOreProcessing(AMItems.CHIMERITE_ORE.get(), AMItems.DEEPSLATE_CHIMERITE_ORE.get(), AMTags.Items.ORES_CHIMERITE, AMItems.CHIMERITE.get(), AMItems.CHIMERITE_BLOCK.get());
        addGemOreProcessing(AMItems.TOPAZ_ORE.get(), AMItems.DEEPSLATE_TOPAZ_ORE.get(), AMTags.Items.ORES_TOPAZ, AMItems.TOPAZ.get(), AMItems.TOPAZ_BLOCK.get());
        addGemOreProcessing(AMItems.VINTEUM_ORE.get(), AMItems.DEEPSLATE_VINTEUM_ORE.get(), AMTags.Items.ORES_VINTEUM, AMItems.VINTEUM_DUST.get(), AMItems.VINTEUM_BLOCK.get());
        addGemOreProcessing(AMItems.MOONSTONE_ORE.get(), AMItems.DEEPSLATE_MOONSTONE_ORE.get(), AMTags.Items.ORES_MOONSTONE, AMItems.MOONSTONE.get(), AMItems.MOONSTONE_BLOCK.get());
        CREATE_CRUSHING.builder("sunstone_ore", 500)
                .addInput(Ingredient.of(AMItems.SUNSTONE_ORE.get()))
                .addOutput(AMItems.SUNSTONE.get())
                .addOutput(AMItems.SUNSTONE.get(), 0.75f)
                .addOutput(new ResourceLocation("create", "powdered_obsidian"), 0.125f).build();
        addFlowerProcessing(AMItems.AUM.get(), Items.PINK_DYE, 2, itemId(Items.LIME_DYE), 0.05f, itemId(Items.WHITE_DYE), 0.05f);
        addFlowerProcessing(AMItems.CERUBLOSSOM.get(), Items.BLUE_DYE, 2, itemId(Items.GREEN_DYE), 0.05f, itemId(Items.CYAN_DYE), 0.05f);
        addFlowerProcessing(AMItems.DESERT_NOVA.get(), Items.RED_DYE, 1, itemId(Items.GREEN_DYE), 0.75f, itemId(Items.WHITE_DYE), 0.05f);
        addFlowerProcessing(AMItems.TARMA_ROOT.get(), Items.BROWN_DYE, 2, itemId(Items.BROWN_DYE), 0.5f);
        addFlowerProcessing(AMItems.WAKEBLOOM.get(), Items.MAGENTA_DYE, 2, itemId(Items.PINK_DYE), 0.5f);
        addLogsProcessing(AMItems.WITCHWOOD_LOG.get(), AMItems.WITCHWOOD.get(), AMItems.STRIPPED_WITCHWOOD_LOG.get(), AMItems.STRIPPED_WITCHWOOD.get(), AMItems.WITCHWOOD_PLANKS.getId(), AMItems.WITCHWOOD_LEAVES.get(), AMItems.WITCHWOOD_SAPLING.get());
        addWoodenProcessing(AMBlockFamilies.WITCHWOOD_PLANKS.get(), AMTags.Items.WITCHWOOD_LOGS);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public String getName() {
        return "AMLCompat";
    }
}
