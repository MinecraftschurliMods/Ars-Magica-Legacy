package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
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
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.ColoredRegistryObject;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.function.Function;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ITEMS;

@NonExtendable
public interface AMItems {
    CreativeModeTab TAB = new CreativeModeTab(ArsMagicaAPI.MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return ArsMagicaAPI.get().getBookStack();
        }
    };
    Item.Properties HIDDEN_ITEM_1 = new Item.Properties().stacksTo(1);
    Item.Properties ITEM_1        = new Item.Properties().stacksTo(1).tab(TAB);
    Item.Properties ITEM_64       = new Item.Properties().stacksTo(64).tab(TAB);

    RegistryObject<InfinityOrbItem>              INFINITY_ORB                     = ITEMS.register("infinity_orb", InfinityOrbItem::new);
    RegistryObject<BlockItem>                    OCCULUS                          = registerBlockItem64(AMBlocks.OCCULUS);
    RegistryObject<BlockItem>                    INSCRIPTION_TABLE                = registerBlockItem64(AMBlocks.INSCRIPTION_TABLE);
    RegistryObject<InscriptionTableUpgradeItem>  INSCRIPTION_TABLE_UPGRADE_TIER_1 = ITEMS.register("inscription_table_upgrade_tier_1", () -> new InscriptionTableUpgradeItem(ITEM_64, 1));
    RegistryObject<InscriptionTableUpgradeItem>  INSCRIPTION_TABLE_UPGRADE_TIER_2 = ITEMS.register("inscription_table_upgrade_tier_2", () -> new InscriptionTableUpgradeItem(ITEM_64, 2));
    RegistryObject<InscriptionTableUpgradeItem>  INSCRIPTION_TABLE_UPGRADE_TIER_3 = ITEMS.register("inscription_table_upgrade_tier_3", () -> new InscriptionTableUpgradeItem(ITEM_64, 3));
    RegistryObject<BlockItem>                    ALTAR_CORE                       = registerBlockItem64(AMBlocks.ALTAR_CORE);
    RegistryObject<BlockItem>                    MAGIC_WALL                       = registerBlockItem64(AMBlocks.MAGIC_WALL);
    RegistryObject<BlockItem>                    OBELISK                          = registerBlockItem64(AMBlocks.OBELISK);
    RegistryObject<BlockItem>                    CELESTIAL_PRISM                  = registerBlockItem64(AMBlocks.CELESTIAL_PRISM);
    RegistryObject<BlockItem>                    BLACK_AUREM                      = registerBlockItem64(AMBlocks.BLACK_AUREM);
    RegistryObject<WizardsChalkItem>             WIZARDS_CHALK                    = ITEMS.register("wizards_chalk", WizardsChalkItem::new);
    RegistryObject<MagitechGogglesItem>          MAGITECH_GOGGLES                 = ITEMS.register("magitech_goggles", MagitechGogglesItem::new);
    RegistryObject<CrystalWrenchItem>            CRYSTAL_WRENCH                   = ITEMS.register("crystal_wrench", CrystalWrenchItem::new);
    RegistryObject<BlockItem>                    CHIMERITE_ORE                    = registerBlockItem64(AMBlocks.CHIMERITE_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_CHIMERITE_ORE          = registerBlockItem64(AMBlocks.DEEPSLATE_CHIMERITE_ORE);
    RegistryObject<Item>                         CHIMERITE                        = registerItem64("chimerite");
    RegistryObject<BlockItem>                    CHIMERITE_BLOCK                  = registerBlockItem64(AMBlocks.CHIMERITE_BLOCK);
    RegistryObject<BlockItem>                    TOPAZ_ORE                        = registerBlockItem64(AMBlocks.TOPAZ_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_TOPAZ_ORE              = registerBlockItem64(AMBlocks.DEEPSLATE_TOPAZ_ORE);
    RegistryObject<Item>                         TOPAZ                            = registerItem64("topaz");
    RegistryObject<BlockItem>                    TOPAZ_BLOCK                      = registerBlockItem64(AMBlocks.TOPAZ_BLOCK);
    RegistryObject<BlockItem>                    VINTEUM_ORE                      = registerBlockItem64(AMBlocks.VINTEUM_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_VINTEUM_ORE            = registerBlockItem64(AMBlocks.DEEPSLATE_VINTEUM_ORE);
    RegistryObject<Item>                         VINTEUM_DUST                     = registerItem64("vinteum_dust");
    RegistryObject<BlockItem>                    VINTEUM_BLOCK                    = registerBlockItem64(AMBlocks.VINTEUM_BLOCK);
    RegistryObject<BlockItem>                    MOONSTONE_ORE                    = registerBlockItem64(AMBlocks.MOONSTONE_ORE);
    RegistryObject<BlockItem>                    DEEPSLATE_MOONSTONE_ORE          = registerBlockItem64(AMBlocks.DEEPSLATE_MOONSTONE_ORE);
    RegistryObject<Item>                         MOONSTONE                        = registerItem64("moonstone");
    RegistryObject<BlockItem>                    MOONSTONE_BLOCK                  = registerBlockItem64(AMBlocks.MOONSTONE_BLOCK);
    RegistryObject<BlockItem>                    SUNSTONE_ORE                     = registerBlockItem64(AMBlocks.SUNSTONE_ORE);
    RegistryObject<Item>                         SUNSTONE                         = registerItem64("sunstone");
    RegistryObject<BlockItem>                    SUNSTONE_BLOCK                   = registerBlockItem64(AMBlocks.SUNSTONE_BLOCK);
    RegistryObject<BlockItem>                    WITCHWOOD_LOG                    = registerBlockItem64(AMBlocks.WITCHWOOD_LOG);
    RegistryObject<BlockItem>                    WITCHWOOD                        = registerBlockItem64(AMBlocks.WITCHWOOD);
    RegistryObject<BlockItem>                    STRIPPED_WITCHWOOD_LOG           = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD_LOG);
    RegistryObject<BlockItem>                    STRIPPED_WITCHWOOD               = registerBlockItem64(AMBlocks.STRIPPED_WITCHWOOD);
    RegistryObject<BlockItem>                    WITCHWOOD_LEAVES                 = registerBlockItem64(AMBlocks.WITCHWOOD_LEAVES);
    RegistryObject<BlockItem>                    WITCHWOOD_SAPLING                = registerBlockItem64(AMBlocks.WITCHWOOD_SAPLING);
    RegistryObject<BlockItem>                    WITCHWOOD_PLANKS                 = registerBlockItem64(AMBlocks.WITCHWOOD_PLANKS);
    RegistryObject<BlockItem>                    WITCHWOOD_SLAB                   = registerBlockItem64(AMBlocks.WITCHWOOD_SLAB);
    RegistryObject<BlockItem>                    WITCHWOOD_STAIRS                 = registerBlockItem64(AMBlocks.WITCHWOOD_STAIRS);
    RegistryObject<BlockItem>                    WITCHWOOD_FENCE                  = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE);
    RegistryObject<BlockItem>                    WITCHWOOD_FENCE_GATE             = registerBlockItem64(AMBlocks.WITCHWOOD_FENCE_GATE);
    RegistryObject<BlockItem>                    WITCHWOOD_DOOR                   = ITEMS.register("witchwood_door", () -> new DoubleHighBlockItem(AMBlocks.WITCHWOOD_DOOR.get(), ITEM_64));
    RegistryObject<BlockItem>                    WITCHWOOD_TRAPDOOR               = registerBlockItem64(AMBlocks.WITCHWOOD_TRAPDOOR);
    RegistryObject<BlockItem>                    WITCHWOOD_BUTTON                 = registerBlockItem64(AMBlocks.WITCHWOOD_BUTTON);
    RegistryObject<BlockItem>                    WITCHWOOD_PRESSURE_PLATE         = registerBlockItem64(AMBlocks.WITCHWOOD_PRESSURE_PLATE);
    RegistryObject<SignItem>                     WITCHWOOD_SIGN                   = ITEMS.register("witchwood_sign", () -> new SignItem(ITEM_64, AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get()));
    RegistryObject<Item>                         BLANK_RUNE                       = registerItem64("blank_rune");
    ColoredRegistryObject<Item, ColoredRuneItem> COLORED_RUNE                     = registerColoredItem("rune", color -> new ColoredRuneItem(ITEM_64, color));
    RegistryObject<RuneBagItem>                  RUNE_BAG                         = ITEMS.register("rune_bag", () -> new RuneBagItem(ITEM_1));
    RegistryObject<BlockItem>                    AUM                              = registerBlockItem64(AMBlocks.AUM);
    RegistryObject<BlockItem>                    CERUBLOSSOM                      = registerBlockItem64(AMBlocks.CERUBLOSSOM);
    RegistryObject<BlockItem>                    DESERT_NOVA                      = registerBlockItem64(AMBlocks.DESERT_NOVA);
    RegistryObject<BlockItem>                    TARMA_ROOT                       = registerBlockItem64(AMBlocks.TARMA_ROOT);
    RegistryObject<BlockItem>                    WAKEBLOOM                        = ITEMS.register("wakebloom", () -> new PlaceOnWaterBlockItem(AMBlocks.WAKEBLOOM.get(), ITEM_64));
    RegistryObject<Item>                         ARCANE_COMPOUND                  = registerItem64("arcane_compound");
    RegistryObject<Item>                         ARCANE_ASH                       = registerItem64("arcane_ash");
    RegistryObject<Item>                         PURIFIED_VINTEUM_DUST            = registerItem64("purified_vinteum_dust");
    RegistryObject<StandingAndWallBlockItem>     VINTEUM_TORCH                    = ITEMS.register("vinteum_torch", () -> new StandingAndWallBlockItem(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get(), ITEM_64));
    RegistryObject<BlockItem>                    IRON_INLAY                       = registerBlockItem64(AMBlocks.IRON_INLAY);
    RegistryObject<BlockItem>                    REDSTONE_INLAY                   = registerBlockItem64(AMBlocks.REDSTONE_INLAY);
    RegistryObject<BlockItem>                    GOLD_INLAY                       = registerBlockItem64(AMBlocks.GOLD_INLAY);
    RegistryObject<AffinityEssenceItem>          AFFINITY_ESSENCE                 = ITEMS.register("affinity_essence", () -> new AffinityEssenceItem(ITEM_64));
    RegistryObject<AffinityTomeItem>             AFFINITY_TOME                    = ITEMS.register("affinity_tome", () -> new AffinityTomeItem(ITEM_64));
    RegistryObject<EtheriumPlaceholderItem>      ETHERIUM_PLACEHOLDER             = ITEMS.register("etherium_placeholder", () -> new EtheriumPlaceholderItem(HIDDEN_ITEM_1));
    RegistryObject<Item>                         SPELL_PARCHMENT                  = registerItem64("spell_parchment");
    RegistryObject<SpellRecipeItem>              SPELL_RECIPE                     = ITEMS.register("spell_recipe", SpellRecipeItem::new);
    RegistryObject<SpellItem>                    SPELL                            = ITEMS.register("spell", SpellItem::new);
    RegistryObject<SpellBookItem>                SPELL_BOOK                       = ITEMS.register("spell_book", SpellBookItem::new);
    RegistryObject<Item>                         MANA_CAKE                        = ITEMS.register("mana_cake", () -> new Item(new Item.Properties().stacksTo(64).tab(TAB).food(new FoodProperties.Builder().nutrition(3).saturationMod(0.6f).alwaysEat().effect(AMMobEffects.MANA_REGEN.lazyMap(e -> new MobEffectInstance(e, 600)), 1f).build())));
    RegistryObject<ManaMartiniItem>              MANA_MARTINI                     = ITEMS.register("mana_martini", () -> new ManaMartiniItem(ITEM_64));
    RegistryObject<MageArmorItem>                MAGE_HELMET                      = ITEMS.register("mage_helmet", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, EquipmentSlot.HEAD, 5));
    RegistryObject<MageArmorItem>                MAGE_CHESTPLATE                  = ITEMS.register("mage_chestplate", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, EquipmentSlot.CHEST, 5));
    RegistryObject<MageArmorItem>                MAGE_LEGGINGS                    = ITEMS.register("mage_leggings", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, EquipmentSlot.LEGS, 5));
    RegistryObject<MageArmorItem>                MAGE_BOOTS                       = ITEMS.register("mage_boots", () -> new MageArmorItem(MageArmorItem.MAGE_ARMOR_MATERIAL, EquipmentSlot.FEET, 5));
    RegistryObject<MageArmorItem>                BATTLEMAGE_HELMET                = ITEMS.register("battlemage_helmet", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, EquipmentSlot.HEAD, 10));
    RegistryObject<MageArmorItem>                BATTLEMAGE_CHESTPLATE            = ITEMS.register("battlemage_chestplate", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, EquipmentSlot.CHEST, 10));
    RegistryObject<MageArmorItem>                BATTLEMAGE_LEGGINGS              = ITEMS.register("battlemage_leggings", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, EquipmentSlot.LEGS, 10));
    RegistryObject<MageArmorItem>                BATTLEMAGE_BOOTS                 = ITEMS.register("battlemage_boots", () -> new MageArmorItem(MageArmorItem.BATTLEMAGE_ARMOR_MATERIAL, EquipmentSlot.FEET, 10));
    RegistryObject<ForgeSpawnEggItem>            WATER_GUARDIAN_SPAWN_EGG         = ITEMS.register("water_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.WATER_GUARDIAN, 0x324fac, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            FIRE_GUARDIAN_SPAWN_EGG          = ITEMS.register("fire_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.FIRE_GUARDIAN, 0xcb5420, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            EARTH_GUARDIAN_SPAWN_EGG         = ITEMS.register("earth_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.EARTH_GUARDIAN, 0x999999, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            AIR_GUARDIAN_SPAWN_EGG           = ITEMS.register("air_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.AIR_GUARDIAN, 0xc1e1dd, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            ICE_GUARDIAN_SPAWN_EGG           = ITEMS.register("ice_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.ICE_GUARDIAN, 0x36d5d7, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            LIGHTNING_GUARDIAN_SPAWN_EGG     = ITEMS.register("lightning_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.LIGHTNING_GUARDIAN, 0x006677, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            NATURE_GUARDIAN_SPAWN_EGG        = ITEMS.register("nature_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.NATURE_GUARDIAN, 0x2f9821, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            LIFE_GUARDIAN_SPAWN_EGG          = ITEMS.register("life_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.LIFE_GUARDIAN, 0x12e780, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            ARCANE_GUARDIAN_SPAWN_EGG        = ITEMS.register("arcane_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.ARCANE_GUARDIAN, 0x7f3280, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            ENDER_GUARDIAN_SPAWN_EGG         = ITEMS.register("ender_guardian_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.ENDER_GUARDIAN, 0x000000, 0xc9bc2f, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            DRYAD_SPAWN_EGG                  = ITEMS.register("dryad_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.DRYAD, 0x166822, 0x683d16, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            MAGE_SPAWN_EGG                   = ITEMS.register("mage_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.MAGE, 0x777777, 0x7b1a7c, ITEM_64));
    RegistryObject<ForgeSpawnEggItem>            MANA_CREEPER_SPAWN_EGG           = ITEMS.register("mana_creeper_spawn_egg", () -> new ForgeSpawnEggItem(AMEntities.MANA_CREEPER, 0x1abfb5, 0x368580, ITEM_64));
    RegistryObject<BucketItem>                   LIQUID_ESSENCE_BUCKET            = ITEMS.register("liquid_essence_bucket", () -> new BucketItem(AMFluids.LIQUID_ESSENCE, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(TAB)));
    //TODO
//    RegistryObject<NatureScytheItem>             NATURE_SCYTHE                = ITEMS.register("nature_scythe", NatureScytheItem::new);
//    RegistryObject<WintersGraspItem>             WINTERS_GRASP                = ITEMS.register("winters_grasp", WintersGraspItem::new);

    private static <T extends Item> ColoredRegistryObject<Item, T> registerColoredItem(String suffix, Function<DyeColor, ? extends T> creator) {
        return new ColoredRegistryObject<>(ITEMS, suffix, creator);
    }

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
