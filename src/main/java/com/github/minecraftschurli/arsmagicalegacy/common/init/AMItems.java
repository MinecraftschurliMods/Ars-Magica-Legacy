package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AMSpawnEggItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ITEMS;

@NonExtendable
public interface AMItems {
    CreativeModeTab GROUP = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
    Item.Properties ITEM_64 = new Item.Properties().stacksTo(64).tab(GROUP);
    Item.Properties ITEM_1 = new Item.Properties().stacksTo(1).tab(GROUP);

    RegistryObject<Item> CHIMERITE_ORE = ITEMS.register("chimerite_ore", () -> new BlockItem(AMBlocks.CHIMERITE_ORE.get(), ITEM_64));
    RegistryObject<Item> DEEPSLATE_CHIMERITE_ORE = ITEMS.register("deepslate_chimerite_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), ITEM_64));
    RegistryObject<Item> CHIMERITE = ITEMS.register("chimerite", () -> new Item(ITEM_64));
    RegistryObject<Item> CHIMERITE_BLOCK = ITEMS.register("chimerite_block", () -> new BlockItem(AMBlocks.CHIMERITE_BLOCK.get(), ITEM_64));
    RegistryObject<Item> TOPAZ_ORE = ITEMS.register("topaz_ore", () -> new BlockItem(AMBlocks.TOPAZ_ORE.get(), ITEM_64));
    RegistryObject<Item> DEEPSLATE_TOPAZ_ORE = ITEMS.register("deepslate_topaz_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), ITEM_64));
    RegistryObject<Item> TOPAZ = ITEMS.register("topaz", () -> new Item(ITEM_64));
    RegistryObject<Item> TOPAZ_BLOCK = ITEMS.register("topaz_block", () -> new BlockItem(AMBlocks.TOPAZ_BLOCK.get(), ITEM_64));
    RegistryObject<Item> VINTEUM_ORE = ITEMS.register("vinteum_ore", () -> new BlockItem(AMBlocks.VINTEUM_ORE.get(), ITEM_64));
    RegistryObject<Item> DEEPSLATE_VINTEUM_ORE = ITEMS.register("deepslate_vinteum_ore", () -> new BlockItem(AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), ITEM_64));
    RegistryObject<Item> VINTEUM_DUST = ITEMS.register("vinteum_dust", () -> new Item(ITEM_64));
    RegistryObject<Item> VINTEUM_BLOCK = ITEMS.register("vinteum_block", () -> new BlockItem(AMBlocks.VINTEUM_BLOCK.get(), ITEM_64));

    /**
     * Empty method that is required for classloading
     */
    static void init() {}

    /**
     * Initializes the spawn eggs
     */
    static void initSpawnEggs() {
        ITEMS.getEntries()
                .stream()
                .flatMap(RegistryObject::stream)
                .filter(AMSpawnEggItem.class::isInstance)
                .map(AMSpawnEggItem.class::cast)
                .forEach(AMSpawnEggItem::init);
    }
}
