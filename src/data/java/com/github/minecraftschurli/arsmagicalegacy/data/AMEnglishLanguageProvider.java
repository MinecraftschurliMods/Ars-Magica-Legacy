package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.Util;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.function.Supplier;

class AMEnglishLanguageProvider extends AMLanguageProvider {
    AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        creativeTabTranslation(AMItems.TAB, ArsMagicaLegacy.getModName());
        blockIdTranslation(AMBlocks.CHIMERITE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
        itemIdTranslation(AMItems.CHIMERITE);
        blockIdTranslation(AMBlocks.CHIMERITE_BLOCK);
        blockIdTranslation(AMBlocks.TOPAZ_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_TOPAZ_ORE);
        itemIdTranslation(AMItems.TOPAZ);
        blockIdTranslation(AMBlocks.TOPAZ_BLOCK);
        blockIdTranslation(AMBlocks.VINTEUM_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_VINTEUM_ORE);
        itemIdTranslation(AMItems.VINTEUM_DUST);
        blockIdTranslation(AMBlocks.VINTEUM_BLOCK);
        blockIdTranslation(AMBlocks.MOONSTONE_ORE);
        blockIdTranslation(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
        itemIdTranslation(AMItems.MOONSTONE);
        blockIdTranslation(AMBlocks.MOONSTONE_BLOCK);
        blockIdTranslation(AMBlocks.SUNSTONE_ORE);
        itemIdTranslation(AMItems.SUNSTONE);
        blockIdTranslation(AMBlocks.SUNSTONE_BLOCK);
        blockIdTranslation(AMBlocks.WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.WITCHWOOD);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD_LOG);
        blockIdTranslation(AMBlocks.STRIPPED_WITCHWOOD);
        blockIdTranslation(AMBlocks.WITCHWOOD_LEAVES);
        blockIdTranslation(AMBlocks.WITCHWOOD_SAPLING);
        blockIdTranslation(AMBlocks.WITCHWOOD_PLANKS);
        blockIdTranslation(AMBlocks.WITCHWOOD_SLAB);
        blockIdTranslation(AMBlocks.WITCHWOOD_STAIRS);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE);
        blockIdTranslation(AMBlocks.WITCHWOOD_FENCE_GATE);
        blockIdTranslation(AMBlocks.WITCHWOOD_DOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_TRAPDOOR);
        blockIdTranslation(AMBlocks.WITCHWOOD_BUTTON);
        blockIdTranslation(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
        itemIdTranslation(AMItems.BLANK_RUNE);
        for (DyeColor color : DyeColor.values()) {
            itemIdTranslation(AMItems.COLORED_RUNE.registryObject(color));
        }
        itemIdTranslation(AMItems.RUNE_BAG);
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        blockIdTranslation(AMBlocks.AUM);
        blockIdTranslation(AMBlocks.CERUBLOSSOM);
        blockIdTranslation(AMBlocks.DESERT_NOVA);
        blockIdTranslation(AMBlocks.TARMA_ROOT);
        blockIdTranslation(AMBlocks.WAKEBLOOM);
        blockIdTranslation(AMBlocks.VINTEUM_TORCH);
        addBlock(AMBlocks.WIZARDS_CHALK, "Wizard's Chalk");
        advancementTranslation(new ResourceLocation(ArsMagicaAPI.MOD_ID, "root"), ArsMagicaLegacy.getModName(), "A renewed look into Minecraft with a splash of magic...");
    }

    /**
     * Adds a block translation that matches the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void blockIdTranslation(RegistryObject<? extends Block> block) {
        addBlock(block, idToTranslation(block.getId().getPath()));
    }

    /**
     * Adds an item translation that matches the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void itemIdTranslation(RegistryObject<? extends Item> item) {
        addItem(item, idToTranslation(item.getId().getPath()));
    }

    /**
     * Adds an effect translation that matches the effect id.
     *
     * @param effect The effect to generate the translation for.
     */
    private void effectIdTranslation(RegistryObject<? extends MobEffect> effect) {
        addEffect(effect, idToTranslation(effect.getId().getPath()));
    }

    /**
     * Adds an enchantment translation that matches the enchantment id.
     *
     * @param enchantment The enchantment to generate the translation for.
     */
    private void enchantmentIdTranslation(RegistryObject<? extends Enchantment> enchantment) {
        addEnchantment(enchantment, idToTranslation(enchantment.getId().getPath()));
    }

    /**
     * Adds an entity translation that matches the entity id.
     *
     * @param entity The entity to generate the translation for.
     */
    private void entityIdTranslation(RegistryObject<? extends EntityType<?>> entity) {
        addEntityType(entity, idToTranslation(entity.getId().getPath()));
    }

    /**
     * Adds a creative tab translation that matches the effect id.
     *
     * @param translation The creative tab to generate the translation for.
     */
    private void creativeTabTranslation(CreativeModeTab tab, String translation) {
        add("itemGroup." + tab.getRecipeFolderName(), translation);
    }

    /**
     * Adds an advancement translation.
     *
     * @param advancement The advancement id.
     * @param title       The advancement title.
     * @param description The advancement description.
     */
    private void advancementTranslation(ResourceLocation advancement, String title, String description) {
        add(Util.makeDescriptionId("advancement", advancement) + ".title", title);
        add(Util.makeDescriptionId("advancement", advancement) + ".description", description);
    }

    /**
     * @param id A string of format "word_word_word".
     * @return A string of format "Word Word Word".
     */
    private static String idToTranslation(String id) {
        StringBuilder result = new StringBuilder();
        for (String string : id.split("_"))
            result.append(string.substring(0, 1).toUpperCase()).append(string.substring(1)).append(" ");
        return result.substring(0, result.length() - 1);
    }
}
