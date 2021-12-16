package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AffinityEssenceItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.AffinityTomeItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.ColoredRuneItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.InfinityOrbItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.MagitechGogglesItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.WizardsChalkItem;
import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagItem;
import com.github.minecraftschurli.arsmagicalegacy.common.util.ColoredRegistryObject;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.WaterLilyBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.ITEMS;

@NonExtendable
public interface AMItems {
    CreativeModeTab TAB = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @NotNull
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
    Item.Properties ITEM_64 = new Item.Properties().stacksTo(64).tab(TAB);
    Item.Properties ITEM_1  = new Item.Properties().stacksTo(1).tab(TAB);

    RegistryObject<InfinityOrbItem>              INFINITY_ORB             = ITEMS.register("infinity_orb", InfinityOrbItem::new);
    RegistryObject<BlockItem>                    OCCULUS                  = ITEMS.register("occulus", () -> new BlockItem(AMBlocks.OCCULUS.get(), ITEM_1));
    RegistryObject<BlockItem>                    INSCRIPTION_TABLE        = registerBlockItem64(AMBlocks.INSCRIPTION_TABLE);
    RegistryObject<BlockItem>                    ALTAR_CORE               = registerBlockItem64(AMBlocks.ALTAR_CORE);
    RegistryObject<BlockItem>                    MAGIC_WALL               = registerBlockItem64(AMBlocks.MAGIC_WALL);
    RegistryObject<WizardsChalkItem>             WIZARDS_CHALK            = ITEMS.register("wizards_chalk", () -> new WizardsChalkItem(new Item.Properties().stacksTo(64).tab(TAB).durability(100)));
    RegistryObject<MagitechGogglesItem>          MAGITECH_GOGGLES         = ITEMS.register("magitech_goggles", MagitechGogglesItem::new);
    RegistryObject<BlockItem>                    CHIMERITE_ORE            = registerBlockItem64(AMBlocks.CHIMERITE_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_CHIMERITE_ORE  = registerBlockItem64(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
    RegistryObject<Item>                         CHIMERITE                = registerItem64("chimerite");
    RegistryObject<BlockItem>                    CHIMERITE_BLOCK          = registerBlockItem64(AMBlocks.CHIMERITE_BLOCK);
    RegistryObject<BlockItem>                    TOPAZ_ORE                = registerBlockItem64(AMBlocks.TOPAZ_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_TOPAZ_ORE      = registerBlockItem64(AMBlocks.DEEPSLATE_TOPAZ_ORE);
    RegistryObject<Item>                         TOPAZ                    = registerItem64("topaz");
    RegistryObject<BlockItem>                    TOPAZ_BLOCK              = registerBlockItem64(AMBlocks.TOPAZ_BLOCK);
    RegistryObject<BlockItem>                    VINTEUM_ORE              = registerBlockItem64(AMBlocks.VINTEUM_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_VINTEUM_ORE    = registerBlockItem64(AMBlocks.DEEPSLATE_VINTEUM_ORE);
    RegistryObject<Item>                         VINTEUM_DUST             = registerItem64("vinteum_dust");
    RegistryObject<BlockItem>                    VINTEUM_BLOCK            = registerBlockItem64(AMBlocks.VINTEUM_BLOCK);
    RegistryObject<BlockItem>                    MOONSTONE_ORE            = registerBlockItem64(AMBlocks.MOONSTONE_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_MOONSTONE_ORE  = registerBlockItem64(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
    RegistryObject<Item>                         MOONSTONE                = registerItem64("moonstone");
    RegistryObject<BlockItem>                    MOONSTONE_BLOCK          = registerBlockItem64(AMBlocks.MOONSTONE_BLOCK);
    RegistryObject<BlockItem>                    SUNSTONE_ORE             = registerBlockItem64(AMBlocks.SUNSTONE_ORE);
    RegistryObject<Item>                         SUNSTONE                 = registerItem64("sunstone");
    RegistryObject<BlockItem>                    SUNSTONE_BLOCK           = registerBlockItem64(AMBlocks.SUNSTONE_BLOCK);
    RegistryObject<BlockItem>                    WITCHWOOD_LOG            = registerBlockItem64(AMBlocks.WITCHWOOD_LOG);
    RegistryObject<BlockItem>                    WITCHWOOD                = registerBlockItem64(AMBlocks.WITCHWOOD);
    RegistryObject<BlockItem>                    STRIPPED_WITCHWOOD_LOG   = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD_LOG);
    RegistryObject<BlockItem>                    STRIPPED_WITCHWOOD       = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD);
    RegistryObject<BlockItem>                    WITCHWOOD_LEAVES         = registerBlockItem64(AMBlocks.WITCHWOOD_LEAVES);
    RegistryObject<BlockItem>                    WITCHWOOD_SAPLING        = registerBlockItem64(AMBlocks.WITCHWOOD_SAPLING);
    RegistryObject<BlockItem>                    WITCHWOOD_PLANKS         = registerBlockItem64(AMBlocks.WITCHWOOD_PLANKS);
    RegistryObject<BlockItem>                    WITCHWOOD_SLAB           = registerBlockItem64(AMBlocks.WITCHWOOD_SLAB);
    RegistryObject<BlockItem>                    WITCHWOOD_STAIRS         = registerBlockItem64(AMBlocks.WITCHWOOD_STAIRS);
    RegistryObject<BlockItem>                    WITCHWOOD_FENCE          = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE);
    RegistryObject<BlockItem>                    WITCHWOOD_FENCE_GATE     = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE_GATE);
    RegistryObject<BlockItem>                    WITCHWOOD_DOOR           = ITEMS.register("witchwood_door", () -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    RegistryObject<BlockItem>                    WITCHWOOD_TRAPDOOR       = registerBlockItem64(AMBlocks.WITCHWOOD_TRAPDOOR);
    RegistryObject<BlockItem>                    WITCHWOOD_BUTTON         = registerBlockItem64(AMBlocks.WITCHWOOD_BUTTON);
    RegistryObject<BlockItem>                    WITCHWOOD_PRESSURE_PLATE = registerBlockItem64(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
    RegistryObject<Item>                         BLANK_RUNE               = registerItem64("blank_rune");
    ColoredRegistryObject<Item, ColoredRuneItem> COLORED_RUNE             = registerColoredItem("rune", color -> new ColoredRuneItem(ITEM_64, color));
    RegistryObject<RuneBagItem>                  RUNE_BAG                 = ITEMS.register("rune_bag", () -> new RuneBagItem(ITEM_1));
    RegistryObject<BlockItem>                    AUM                      = registerBlockItem64(AMBlocks.AUM);
    RegistryObject<BlockItem>                    CERUBLOSSOM              = registerBlockItem64(AMBlocks.CERUBLOSSOM);
    RegistryObject<BlockItem>                    DESERT_NOVA              = registerBlockItem64(AMBlocks.DESERT_NOVA);
    RegistryObject<BlockItem>                    TARMA_ROOT               = registerBlockItem64(AMBlocks.TARMA_ROOT);
    RegistryObject<BlockItem>                    WAKEBLOOM                = ITEMS.register("wakebloom", () -> new WaterLilyBlockItem(AMBlocks.WAKEBLOOM.get(), ITEM_64));
    RegistryObject<Item>                         ARCANE_COMPOUND          = registerItem64("arcane_compound");
    RegistryObject<Item>                         ARCANE_ASH               = registerItem64("arcane_ash");
    RegistryObject<Item>                         PURIFIED_VINTEUM_DUST    = registerItem64("purified_vinteum_dust");
    RegistryObject<StandingAndWallBlockItem>     VINTEUM_TORCH            = ITEMS.register("vinteum_torch", () -> new StandingAndWallBlockItem(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    RegistryObject<AffinityEssenceItem>          AFFINITY_ESSENCE         = ITEMS.register("affinity_essence", () -> new AffinityEssenceItem(ITEM_64));
    RegistryObject<AffinityTomeItem>             AFFINITY_TOME            = ITEMS.register("affinity_tome", () -> new AffinityTomeItem(ITEM_64));
    RegistryObject<Item>                         SPELL_PARCHMENT          = registerItem64("spell_parchment");
    RegistryObject<SpellItem>                    SPELL                    = ITEMS.register("spell", SpellItem::new);

    @SuppressWarnings("SameParameterValue")
    private static <T extends Item> ColoredRegistryObject<Item, T> registerColoredItem(String suffix, Function<DyeColor, ? extends T> creator) {
        return new ColoredRegistryObject<>(ITEMS, suffix, creator);
    }

    @SuppressWarnings("unchecked")
    private static RegistryObject<BlockItem> registerBlockItem64(RegistryObject<? extends Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_64));
    }

    private static RegistryObject<Item> registerItem64(String name) {
        return ITEMS.register(name, () -> new Item(ITEM_64));
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
