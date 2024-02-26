package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.item.AffinityEssenceItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.AffinityTomeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.ColoredRuneItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.CrystalWrenchItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.EtheriumPlaceholderItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.InfinityOrbItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.InscriptionTableUpgradeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.MageArmorItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.MagitechGogglesItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.ManaMartiniItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.WizardsChalkItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.ColoredDeferredHolder;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ITEMS;

@NonExtendable
public interface AMItems {
    Item.Properties ITEM_1        = new Item.Properties().stacksTo(1);
    Item.Properties ITEM_64       = new Item.Properties();

    DeferredItem<InfinityOrbItem>                INFINITY_ORB                     = ITEMS.register("infinity_orb", InfinityOrbItem::new);
    DeferredItem<BlockItem>                      OCCULUS                          = registerBlockItem64(AMBlocks.OCCULUS);
    DeferredItem<BlockItem>                      INSCRIPTION_TABLE                = registerBlockItem64(AMBlocks.INSCRIPTION_TABLE);
    DeferredItem<InscriptionTableUpgradeItem>    INSCRIPTION_TABLE_UPGRADE_TIER_1 = ITEMS.register("inscription_table_upgrade_tier_1", () -> new InscriptionTableUpgradeItem(ITEM_64, 1));
    DeferredItem<InscriptionTableUpgradeItem>    INSCRIPTION_TABLE_UPGRADE_TIER_2 = ITEMS.register("inscription_table_upgrade_tier_2", () -> new InscriptionTableUpgradeItem(ITEM_64, 2));
    DeferredItem<InscriptionTableUpgradeItem>    INSCRIPTION_TABLE_UPGRADE_TIER_3 = ITEMS.register("inscription_table_upgrade_tier_3", () -> new InscriptionTableUpgradeItem(ITEM_64, 3));
    DeferredItem<BlockItem>                      ALTAR_CORE                       = registerBlockItem64(AMBlocks.ALTAR_CORE);
    DeferredItem<BlockItem>                      MAGIC_WALL                       = registerBlockItem64(AMBlocks.MAGIC_WALL);
    DeferredItem<BlockItem>                      OBELISK                          = registerBlockItem64(AMBlocks.OBELISK);
    DeferredItem<BlockItem>                      CELESTIAL_PRISM                  = registerBlockItem64(AMBlocks.CELESTIAL_PRISM);
    DeferredItem<BlockItem>                      BLACK_AUREM                      = registerBlockItem64(AMBlocks.BLACK_AUREM);
    DeferredItem<WizardsChalkItem>               WIZARDS_CHALK                    = ITEMS.register("wizards_chalk", WizardsChalkItem::new);
    DeferredItem<MagitechGogglesItem>            MAGITECH_GOGGLES                 = ITEMS.register("magitech_goggles", MagitechGogglesItem::new);
    DeferredItem<CrystalWrenchItem>              CRYSTAL_WRENCH                   = ITEMS.register("crystal_wrench", CrystalWrenchItem::new);
    DeferredItem<BlockItem>                      CHIMERITE_ORE                    = registerBlockItem64(AMBlocks.CHIMERITE_ORE);
    DeferredItem<BlockItem>                      DEEPSLATE_CHIMERITE_ORE          = registerBlockItem64(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
    DeferredItem<Item>                           CHIMERITE                        = registerItem64("chimerite");
    DeferredItem<BlockItem>                      CHIMERITE_BLOCK                  = registerBlockItem64(AMBlocks.CHIMERITE_BLOCK);
    DeferredItem<BlockItem>                      TOPAZ_ORE                        = registerBlockItem64(AMBlocks.TOPAZ_ORE);
    DeferredItem<BlockItem>                      DEEPSLATE_TOPAZ_ORE              = registerBlockItem64(AMBlocks.DEEPSLATE_TOPAZ_ORE);
    DeferredItem<Item>                           TOPAZ                            = registerItem64("topaz");
    DeferredItem<BlockItem>                      TOPAZ_BLOCK                      = registerBlockItem64(AMBlocks.TOPAZ_BLOCK);
    DeferredItem<BlockItem>                      VINTEUM_ORE                      = registerBlockItem64(AMBlocks.VINTEUM_ORE);
    DeferredItem<BlockItem>                      DEEPSLATE_VINTEUM_ORE            = registerBlockItem64(AMBlocks.DEEPSLATE_VINTEUM_ORE);
    DeferredItem<Item>                           VINTEUM_DUST                     = registerItem64("vinteum_dust");
    DeferredItem<BlockItem>                      VINTEUM_BLOCK                    = registerBlockItem64(AMBlocks.VINTEUM_BLOCK);
    DeferredItem<BlockItem>                      MOONSTONE_ORE                    = registerBlockItem64(AMBlocks.MOONSTONE_ORE);
    DeferredItem<BlockItem>                      DEEPSLATE_MOONSTONE_ORE          = registerBlockItem64(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
    DeferredItem<Item>                           MOONSTONE                        = registerItem64("moonstone");
    DeferredItem<BlockItem>                      MOONSTONE_BLOCK                  = registerBlockItem64(AMBlocks.MOONSTONE_BLOCK);
    DeferredItem<BlockItem>                      SUNSTONE_ORE                     = registerBlockItem64(AMBlocks.SUNSTONE_ORE);
    DeferredItem<Item>                           SUNSTONE                         = registerItem64("sunstone");
    DeferredItem<BlockItem>                      SUNSTONE_BLOCK                   = registerBlockItem64(AMBlocks.SUNSTONE_BLOCK);
    DeferredItem<BlockItem>                      WITCHWOOD_LOG                    = registerBlockItem64(AMBlocks.WITCHWOOD_LOG);
    DeferredItem<BlockItem>                      WITCHWOOD                        = registerBlockItem64(AMBlocks.WITCHWOOD);
    DeferredItem<BlockItem>                      STRIPPED_WITCHWOOD_LOG           = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD_LOG);
    DeferredItem<BlockItem>                      STRIPPED_WITCHWOOD               = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD);
    DeferredItem<BlockItem>                      WITCHWOOD_LEAVES                 = registerBlockItem64(AMBlocks.WITCHWOOD_LEAVES);
    DeferredItem<BlockItem>                      WITCHWOOD_SAPLING                = registerBlockItem64(AMBlocks.WITCHWOOD_SAPLING);
    DeferredItem<BlockItem>                      WITCHWOOD_PLANKS                 = registerBlockItem64(AMBlocks.WITCHWOOD_PLANKS);
    DeferredItem<BlockItem>                      WITCHWOOD_SLAB                   = registerBlockItem64(AMBlocks.WITCHWOOD_SLAB);
    DeferredItem<BlockItem>                      WITCHWOOD_STAIRS                 = registerBlockItem64(AMBlocks.WITCHWOOD_STAIRS);
    DeferredItem<BlockItem>                      WITCHWOOD_FENCE                  = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE);
    DeferredItem<BlockItem>                      WITCHWOOD_FENCE_GATE             = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE_GATE);
    DeferredItem<BlockItem>                      WITCHWOOD_DOOR                   = ITEMS.register("witchwood_door", () -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    DeferredItem<BlockItem>                      WITCHWOOD_TRAPDOOR               = registerBlockItem64(AMBlocks.WITCHWOOD_TRAPDOOR);
    DeferredItem<BlockItem>                      WITCHWOOD_BUTTON                 = registerBlockItem64(AMBlocks.WITCHWOOD_BUTTON);
    DeferredItem<BlockItem>                      WITCHWOOD_PRESSURE_PLATE         = registerBlockItem64(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
    DeferredItem<SignItem>                       WITCHWOOD_SIGN                   = ITEMS.register("witchwood_sign", () -> new SignItem(ITEM_64, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get()));
    DeferredItem<HangingSignItem>                WITCHWOOD_HANGING_SIGN           = ITEMS.register("witchwood_hanging_sign", () -> new HangingSignItem(AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get(), ITEM_64));
    DeferredItem<Item>                           BLANK_RUNE                       = registerItem64("blank_rune");
    ColoredDeferredHolder<Item, ColoredRuneItem> COLORED_RUNE                     = registerColoredItem("rune", color -> new ColoredRuneItem(ITEM_64, color));
    DeferredItem<RuneBagItem>                    RUNE_BAG                         = ITEMS.registerItem("rune_bag", RuneBagItem::new, ITEM_1);
    DeferredItem<BlockItem>                      AUM                              = registerBlockItem64(AMBlocks.AUM);
    DeferredItem<BlockItem>                      CERUBLOSSOM                      = registerBlockItem64(AMBlocks.CERUBLOSSOM);
    DeferredItem<BlockItem>                      DESERT_NOVA                      = registerBlockItem64(AMBlocks.DESERT_NOVA);
    DeferredItem<BlockItem>                      TARMA_ROOT                       = registerBlockItem64(AMBlocks.TARMA_ROOT);
    DeferredItem<BlockItem>                      WAKEBLOOM                        = ITEMS.register("wakebloom", () -> new PlaceOnWaterBlockItem(AMBlocks.WAKEBLOOM.get(), ITEM_64));
    DeferredItem<Item>                           ARCANE_COMPOUND                  = registerItem64("arcane_compound");
    DeferredItem<Item>                           ARCANE_ASH                       = registerItem64("arcane_ash");
    DeferredItem<Item>                           PURIFIED_VINTEUM_DUST            = registerItem64("purified_vinteum_dust");
    DeferredItem<StandingAndWallBlockItem>       VINTEUM_TORCH                    = ITEMS.register("vinteum_torch", () -> new StandingAndWallBlockItem(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64, Direction.DOWN));
    DeferredItem<BlockItem>                      IRON_INLAY                       = registerBlockItem64(AMBlocks.IRON_INLAY);
    DeferredItem<BlockItem>                      REDSTONE_INLAY                   = registerBlockItem64(AMBlocks.REDSTONE_INLAY);
    DeferredItem<BlockItem>                      GOLD_INLAY                       = registerBlockItem64(AMBlocks.GOLD_INLAY);
    DeferredItem<AffinityEssenceItem>            AFFINITY_ESSENCE                 = ITEMS.registerItem("affinity_essence", AffinityEssenceItem::new);
    DeferredItem<AffinityTomeItem>               AFFINITY_TOME                    = ITEMS.registerItem("affinity_tome", AffinityTomeItem::new);
    DeferredItem<EtheriumPlaceholderItem>        ETHERIUM_PLACEHOLDER             = ITEMS.registerItem("etherium_placeholder", EtheriumPlaceholderItem::new, ITEM_1);
    DeferredItem<Item>                           SPELL_PARCHMENT                  = registerItem64("spell_parchment");
    DeferredItem<SpellRecipeItem>                SPELL_RECIPE                     = ITEMS.register("spell_recipe", SpellRecipeItem::new);
    DeferredItem<SpellItem>                      SPELL                            = ITEMS.register("spell", SpellItem::new);
    DeferredItem<SpellBookItem>                  SPELL_BOOK                       = ITEMS.register("spell_book", SpellBookItem::new);
    DeferredItem<Item>                           MANA_CAKE                        = ITEMS.register("mana_cake", () -> new Item(new Item.Properties().stacksTo(64).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).alwaysEat().effect(() -> new MobEffectInstance(AMMobEffects.MANA_REGEN.value(), 600), 1f).build())));
    DeferredItem<ManaMartiniItem>                MANA_MARTINI                     = ITEMS.registerItem("mana_martini", ManaMartiniItem::new);
    DeferredItem<MageArmorItem>                  MAGE_HELMET                      = ITEMS.register("mage_helmet", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 5));
    DeferredItem<MageArmorItem>                  MAGE_CHESTPLATE                  = ITEMS.register("mage_chestplate", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 5));
    DeferredItem<MageArmorItem>                  MAGE_LEGGINGS                    = ITEMS.register("mage_leggings", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 5));
    DeferredItem<MageArmorItem>                  MAGE_BOOTS                       = ITEMS.register("mage_boots", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 5));
    DeferredItem<MageArmorItem>                  BATTLEMAGE_HELMET                = ITEMS.register("battlemage_helmet", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, ArmorItem.Type.HELMET, 10));
    DeferredItem<MageArmorItem>                  BATTLEMAGE_CHESTPLATE            = ITEMS.register("battlemage_chestplate", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, 10));
    DeferredItem<MageArmorItem>                  BATTLEMAGE_LEGGINGS              = ITEMS.register("battlemage_leggings", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, 10));
    DeferredItem<MageArmorItem>                  BATTLEMAGE_BOOTS                 = ITEMS.register("battlemage_boots", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, 10));
    DeferredItem<DeferredSpawnEggItem>           WATER_GUARDIAN_SPAWN_EGG         = ITEMS.register("water_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.WATER_GUARDIAN, 0x324fac, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           FIRE_GUARDIAN_SPAWN_EGG          = ITEMS.register("fire_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.FIRE_GUARDIAN, 0xcb5420, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           EARTH_GUARDIAN_SPAWN_EGG         = ITEMS.register("earth_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.EARTH_GUARDIAN, 0x999999, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           AIR_GUARDIAN_SPAWN_EGG           = ITEMS.register("air_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.AIR_GUARDIAN, 0xc1e1dd, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           ICE_GUARDIAN_SPAWN_EGG           = ITEMS.register("ice_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.ICE_GUARDIAN, 0x36d5d7, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           LIGHTNING_GUARDIAN_SPAWN_EGG     = ITEMS.register("lightning_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.LIGHTNING_GUARDIAN, 0x006677, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           NATURE_GUARDIAN_SPAWN_EGG        = ITEMS.register("nature_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.NATURE_GUARDIAN, 0x2f9821, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           LIFE_GUARDIAN_SPAWN_EGG          = ITEMS.register("life_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.LIFE_GUARDIAN, 0x12e780, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           ARCANE_GUARDIAN_SPAWN_EGG        = ITEMS.register("arcane_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.ARCANE_GUARDIAN, 0x7f3280, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           ENDER_GUARDIAN_SPAWN_EGG         = ITEMS.register("ender_guardian_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.ENDER_GUARDIAN, 0x000000, 0xc9bc2f, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           DRYAD_SPAWN_EGG                  = ITEMS.register("dryad_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.DRYAD, 0x166822, 0x683d16, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           MAGE_SPAWN_EGG                   = ITEMS.register("mage_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.MAGE, 0x777777, 0x7b1a7c, ITEM_64));
    DeferredItem<DeferredSpawnEggItem>           MANA_CREEPER_SPAWN_EGG           = ITEMS.register("mana_creeper_spawn_egg", () -> new DeferredSpawnEggItem(AMEntities.MANA_CREEPER, 0x1abfb5, 0x368580, ITEM_64));
    DeferredItem<BucketItem>                     LIQUID_ESSENCE_BUCKET            = ITEMS.register("liquid_essence_bucket", () -> new BucketItem(AMFluids.LIQUID_ESSENCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    //TODO
//    DeferredItem<NatureScytheItem>             NATURE_SCYTHE                = ITEMS.register("nature_scythe", NatureScytheItem::new);
//    DeferredItem<WintersGraspItem>             WINTERS_GRASP                = ITEMS.register("winters_grasp", WintersGraspItem::new);

    Set<DeferredHolder<Item, ? extends Item>> HIDDEN_ITEMS = Util.make(new HashSet<>(), suppliers -> {
        suppliers.add(AMItems.ETHERIUM_PLACEHOLDER);
        suppliers.add(AMItems.SPELL_RECIPE);
        suppliers.add(AMItems.SPELL);
    });

    private static <T extends Item> ColoredDeferredHolder<Item, T> registerColoredItem(String suffix, Function<DyeColor, ? extends T> creator) {
        return new ColoredDeferredHolder<>(ITEMS, suffix, creator);
    }

    private static DeferredItem<BlockItem> registerBlockItem64(Holder<Block> block) {
        return ITEMS.registerSimpleBlockItem(block, ITEM_64);
    }

    private static DeferredItem<Item> registerItem64(String name) {
        return ITEMS.registerSimpleItem(name, ITEM_64);
    }

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
