package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

class AMEnglishLanguageProvider extends AMLanguageProvider {
    AMEnglishLanguageProvider(DataGenerator generator) {
        super(generator, "en_us");
    }

    @Override
    protected void addTranslations() {
        creativeTab(AMItems.GROUP, ArsMagicaLegacy.getModName());
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
        itemIdTranslation(AMItems.WHITE_RUNE);
        itemIdTranslation(AMItems.ORANGE_RUNE);
        itemIdTranslation(AMItems.MAGENTA_RUNE);
        itemIdTranslation(AMItems.LIGHT_BLUE_RUNE);
        itemIdTranslation(AMItems.YELLOW_RUNE);
        itemIdTranslation(AMItems.LIME_RUNE);
        itemIdTranslation(AMItems.PINK_RUNE);
        itemIdTranslation(AMItems.GRAY_RUNE);
        itemIdTranslation(AMItems.LIGHT_GRAY_RUNE);
        itemIdTranslation(AMItems.CYAN_RUNE);
        itemIdTranslation(AMItems.PURPLE_RUNE);
        itemIdTranslation(AMItems.BLUE_RUNE);
        itemIdTranslation(AMItems.BROWN_RUNE);
        itemIdTranslation(AMItems.GREEN_RUNE);
        itemIdTranslation(AMItems.RED_RUNE);
        itemIdTranslation(AMItems.BLACK_RUNE);
        itemIdTranslation(AMItems.RUNE_BAG);
        itemIdTranslation(AMItems.ARCANE_COMPOUND);
        itemIdTranslation(AMItems.ARCANE_ASH);
        itemIdTranslation(AMItems.PURIFIED_VINTEUM_DUST);
        itemIdTranslation(AMItems.VINTEUM_TORCH);
    }

    /**
     * Adds a block translation that matches the block id.
     *
     * @param block The block to generate the translation for.
     */
    private void blockIdTranslation(Supplier<? extends Block> block) {
        addBlock(block, idToTranslation(block.get().getRegistryName().getPath()));
    }

    /**
     * Adds a block translation.
     *
     * @param block       The block to generate the translation for.
     * @param translation The translation to apply.
     */
    private void blockTranslation(Supplier<? extends Block> block, String translation) {
        addBlock(block, translation);
    }

    /**
     * Adds an item translation that matches the item id.
     *
     * @param item The item to generate the translation for.
     */
    private void itemIdTranslation(Supplier<? extends Item> item) {
        addItem(item, idToTranslation(item.get().getRegistryName().getPath()));
    }

    /**
     * Adds an item translation.
     *
     * @param item        The item to generate the translation for.
     * @param translation The translation to apply.
     */
    private void itemTranslation(Supplier<? extends Item> item, String translation) {
        addItem(item, translation);
    }

    /**
     * Adds an effect translation that matches the effect id.
     *
     * @param effect The effect to generate the translation for.
     */
    private void effectIdTranslation(Supplier<? extends MobEffect> effect) {
        addEffect(effect, effect.get().getRegistryName().getPath());
    }

    /**
     * Adds an enchantment translation that matches the enchantment id.
     *
     * @param enchantment The enchantment to generate the translation for.
     */
    private void enchantmentIdTranslation(Supplier<? extends Enchantment> enchantment) {
        addEnchantment(enchantment, enchantment.get().getRegistryName().getPath());
    }

    /**
     * Adds an entity translation that matches the entity id.
     *
     * @param entity The entity to generate the translation for.
     */
    private void entityIdTranslation(Supplier<? extends EntityType<?>> entity) {
        addEntityType(entity, entity.get().getRegistryName().getPath());
    }

    /**
     * Adds a creative tab translation that matches the effect id.
     *
     * @param translation The creative tab to generate the translation for.
     */
    private void creativeTab(CreativeModeTab tab, String translation) {
        add("itemGroup." + tab.getRecipeFolderName(), translation);
    }

    /**
     * Adds a translation.
     *
     * @param key         The translation key to use.
     * @param translation The translation to apply.
     */
    private void translate(String key, String translation) {
        add(key, translation);
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
