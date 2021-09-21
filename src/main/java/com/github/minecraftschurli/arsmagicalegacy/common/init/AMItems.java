package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AMSpawnEggItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.ColoredRuneItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.RuneBagItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
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
    RegistryObject<Item> MOONSTONE_ORE = ITEMS.register("moonstone_ore", () -> new BlockItem(AMBlocks.MOONSTONE_ORE.get(), ITEM_64));
    RegistryObject<Item> MOONSTONE = ITEMS.register("moonstone_dust", () -> new Item(ITEM_64));
    RegistryObject<Item> MOONSTONE_BLOCK = ITEMS.register("moonstone_block", () -> new BlockItem(AMBlocks.MOONSTONE_BLOCK.get(), ITEM_64));
    RegistryObject<Item> SUNSTONE_ORE = ITEMS.register("sunstone_ore", () -> new BlockItem(AMBlocks.SUNSTONE_ORE.get(), ITEM_64));
    RegistryObject<Item> SUNSTONE = ITEMS.register("sunstone_dust", () -> new Item(ITEM_64));
    RegistryObject<Item> SUNSTONE_BLOCK = ITEMS.register("sunstone_block", () -> new BlockItem(AMBlocks.SUNSTONE_BLOCK.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_LOG = ITEMS.register("witchwood_log", () -> new BlockItem(AMBlocks.WITCHWOOD_LOG.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD = ITEMS.register("witchwood", () -> new BlockItem(AMBlocks.WITCHWOOD.get(), ITEM_64));
    RegistryObject<Item> STRIPPED_WITCHWOOD_LOG = ITEMS.register("stripped_witchwood_log", () -> new BlockItem(AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), ITEM_64));
    RegistryObject<Item> STRIPPED_WITCHWOOD = ITEMS.register("stripped_witchwood", () -> new BlockItem(AMBlocks.STRIPPED_WITCHWOOD.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_LEAVES = ITEMS.register("witchwood_leaves", () -> new BlockItem(AMBlocks.WITCHWOOD_LEAVES.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_SAPLING = ITEMS.register("witchwood_sapling", () -> new BlockItem(AMBlocks.WITCHWOOD_SAPLING.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_PLANKS = ITEMS.register("witchwood_planks", () -> new BlockItem(AMBlocks.WITCHWOOD_PLANKS.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_SLAB = ITEMS.register("witchwood_slab", () -> new BlockItem(AMBlocks.WITCHWOOD_SLAB.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_STAIRS = ITEMS.register("witchwood_stairs", () -> new BlockItem(AMBlocks.WITCHWOOD_STAIRS.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_FENCE = ITEMS.register("witchwood_fence", () -> new BlockItem(AMBlocks.WITCHWOOD_FENCE.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_FENCE_GATE = ITEMS.register("witchwood_fence_gate", () -> new BlockItem(AMBlocks.WITCHWOOD_FENCE_GATE.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_DOOR = ITEMS.register("witchwood_door", () -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_TRAPDOOR = ITEMS.register("witchwood_trapdoor", () -> new BlockItem(AMBlocks.WITCHWOOD_TRAPDOOR.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_BUTTON = ITEMS.register("witchwood_button", () -> new BlockItem(AMBlocks.WITCHWOOD_BUTTON.get(), ITEM_64));
    RegistryObject<Item> WITCHWOOD_PRESSURE_PLATE = ITEMS.register("witchwood_pressure_plate", () -> new BlockItem(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get(), ITEM_64));
    RegistryObject<Item> BLANK_RUNE = ITEMS.register("blank_rune", () -> new Item(ITEM_64));
    RegistryObject<Item> WHITE_RUNE = ITEMS.register("white_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> ORANGE_RUNE = ITEMS.register("orange_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> MAGENTA_RUNE = ITEMS.register("magenta_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> LIGHT_BLUE_RUNE = ITEMS.register("light_blue_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> YELLOW_RUNE = ITEMS.register("yellow_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> LIME_RUNE = ITEMS.register("lime_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> PINK_RUNE = ITEMS.register("pink_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> GRAY_RUNE = ITEMS.register("gray_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> LIGHT_GRAY_RUNE = ITEMS.register("light_gray_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> CYAN_RUNE = ITEMS.register("cyan_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> PURPLE_RUNE = ITEMS.register("purple_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> BLUE_RUNE = ITEMS.register("blue_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> BROWN_RUNE = ITEMS.register("brown_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> GREEN_RUNE = ITEMS.register("green_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> RED_RUNE = ITEMS.register("red_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> BLACK_RUNE = ITEMS.register("black_rune", () -> new ColoredRuneItem(ITEM_64));
    RegistryObject<Item> RUNE_BAG = ITEMS.register("rune_bag", () -> new RuneBagItem(ITEM_64));

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
