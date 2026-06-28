package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.HarvestState;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.TallHarvestState;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEnchantments;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMEtheriumTypes;
import at.minecraftschurli.mods.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.ChorusGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.CropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingBushGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.HangingGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.StemGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.TallCropGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.plant.UpwardsGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.BlockStatePropertyMatchTest;
import at.minecraftschurli.mods.arsmagicalegacy.worldgen.CompositeMatchTest;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;
import java.util.Optional;

public final class AMDatapackBuiltinEntriesProvider {
    public static void addDamageTypes(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(AMDamageTypes.SPELL_DROWNING, new DamageType("drown", 0, DamageEffects.DROWNING));
        bootstrap.register(AMDamageTypes.SPELL_FIRE, new DamageType("inFire", 0.1f, DamageEffects.BURNING));
        bootstrap.register(AMDamageTypes.SPELL_FROST, new DamageType("freeze", 0.1f, DamageEffects.FREEZING));
        bootstrap.register(AMDamageTypes.SPELL_LIGHTNING, new DamageType("lightningBolt", 0.1f));
        bootstrap.register(AMDamageTypes.SPELL_MAGIC, new DamageType("magic", 0));
        bootstrap.register(AMDamageTypes.SPELL_PHYSICAL, new DamageType("mob", 0.1f));
        bootstrap.register(AMDamageTypes.SPELL_PHYSICAL_PLAYER, new DamageType("player", 0.1f));
        bootstrap.register(AMDamageTypes.FALLING_STAR, new DamageType(AMDamageTypes.FALLING_STAR.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.NATURE_SCYTHE, new DamageType(AMDamageTypes.NATURE_SCYTHE.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.SHOCKWAVE, new DamageType(AMDamageTypes.SHOCKWAVE.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.THROWN_ROCK, new DamageType(AMDamageTypes.THROWN_ROCK.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.WHIRLWIND, new DamageType(AMDamageTypes.WHIRLWIND.identifier().getPath(), 0.1f));
    }

    public static void addEnchantments(BootstrapContext<Enchantment> bootstrap) {
        bootstrap.register(AMEnchantments.DISMEMBERING, Enchantment.enchantment(
            Enchantment.definition(HolderSet.empty(), 1, 1, Enchantment.constantCost(0), Enchantment.constantCost(0), 0)
        ).build(AMEnchantments.DISMEMBERING.identifier()));
    }

    public static void addAltarCapMaterials(BootstrapContext<AltarCapMaterial> bootstrap) {
        altarCapMaterial(bootstrap, "glass", Blocks.GLASS, 1);
        altarCapMaterial(bootstrap, "coal", Blocks.COAL_BLOCK, 2);
        altarCapMaterial(bootstrap, "copper", Blocks.COPPER_BLOCK, 3);
        altarCapMaterial(bootstrap, "exposed_copper", Blocks.EXPOSED_COPPER, 3);
        altarCapMaterial(bootstrap, "weathered_copper", Blocks.WEATHERED_COPPER, 3);
        altarCapMaterial(bootstrap, "oxidized_copper", Blocks.OXIDIZED_COPPER, 3);
        altarCapMaterial(bootstrap, "waxed_copper", Blocks.WAXED_COPPER_BLOCK, 3);
        altarCapMaterial(bootstrap, "waxed_exposed_copper", Blocks.WAXED_EXPOSED_COPPER, 3);
        altarCapMaterial(bootstrap, "waxed_weathered_copper", Blocks.WAXED_WEATHERED_COPPER, 3);
        altarCapMaterial(bootstrap, "waxed_oxidized_copper", Blocks.WAXED_OXIDIZED_COPPER, 3);
        altarCapMaterial(bootstrap, "iron", Blocks.IRON_BLOCK, 4);
        altarCapMaterial(bootstrap, "redstone", Blocks.REDSTONE_BLOCK, 5);
        altarCapMaterial(bootstrap, "vinteum", AMBlocks.VINTEUM_BLOCK.get(), 6);
        altarCapMaterial(bootstrap, "chimerite", AMBlocks.CHIMERITE_BLOCK.get(), 7);
        altarCapMaterial(bootstrap, "lapis", Blocks.LAPIS_BLOCK, 8);
        altarCapMaterial(bootstrap, "gold", Blocks.GOLD_BLOCK, 9);
        altarCapMaterial(bootstrap, "topaz", AMBlocks.TOPAZ_BLOCK.get(), 10);
        altarCapMaterial(bootstrap, "diamond", Blocks.DIAMOND_BLOCK, 11);
        altarCapMaterial(bootstrap, "emerald", Blocks.EMERALD_BLOCK, 12);
        altarCapMaterial(bootstrap, "netherite", Blocks.NETHERITE_BLOCK, 13);
        altarCapMaterial(bootstrap, "moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14);
        altarCapMaterial(bootstrap, "sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15);
    }

    public static void addAltarMaterials(BootstrapContext<AltarMaterial> bootstrap) {
        altarMaterial(bootstrap, BlockFamilies.OAK_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.SPRUCE_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.BIRCH_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.JUNGLE_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.ACACIA_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.DARK_OAK_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.MANGROVE_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.BAMBOO_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.CHERRY_PLANKS, 1);
        altarMaterial(bootstrap, BlockFamilies.COBBLESTONE, 2);
        altarMaterial(bootstrap, BlockFamilies.STONE, 2);
        altarMaterial(bootstrap, BlockFamilies.MUD_BRICKS, 2);
        altarMaterial(bootstrap, BlockFamilies.MOSSY_COBBLESTONE, 2);
        altarMaterial(bootstrap, BlockFamilies.COBBLED_DEEPSLATE, 2);
        altarMaterial(bootstrap, BlockFamilies.ANDESITE, 2);
        altarMaterial(bootstrap, BlockFamilies.DIORITE, 2);
        altarMaterial(bootstrap, BlockFamilies.GRANITE, 2);
        altarMaterial(bootstrap, BlockFamilies.SANDSTONE, 2);
        altarMaterial(bootstrap, BlockFamilies.RED_SANDSTONE, 2);
        altarMaterial(bootstrap, BlockFamilies.BRICKS, 3);
        altarMaterial(bootstrap, BlockFamilies.STONE_BRICK, 3);
        altarMaterial(bootstrap, BlockFamilies.MOSSY_STONE_BRICKS, 3);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_DEEPSLATE, 3);
        altarMaterial(bootstrap, BlockFamilies.DEEPSLATE_BRICKS, 3);
        altarMaterial(bootstrap, BlockFamilies.DEEPSLATE_TILES, 3);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_ANDESITE, 3);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_DIORITE, 3);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_GRANITE, 3);
        altarMaterial(bootstrap, BlockFamilies.SMOOTH_SANDSTONE, 3);
        altarMaterial(bootstrap, BlockFamilies.SMOOTH_RED_SANDSTONE, 3);
        altarMaterial(bootstrap, BlockFamilies.CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.EXPOSED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.WEATHERED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.OXIDIZED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.WAXED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.WAXED_EXPOSED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.WAXED_WEATHERED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.WAXED_OXIDIZED_CUT_COPPER, 3);
        altarMaterial(bootstrap, BlockFamilies.PRISMARINE, 4);
        altarMaterial(bootstrap, BlockFamilies.PRISMARINE_BRICKS, 4);
        altarMaterial(bootstrap, BlockFamilies.DARK_PRISMARINE, 4);
        altarMaterial(bootstrap, BlockFamilies.CRIMSON_PLANKS, 4);
        altarMaterial(bootstrap, BlockFamilies.WARPED_PLANKS, 4);
        altarMaterial(bootstrap, AMBlocks.WITCHWOOD_BLOCK_FAMILY.get(), 4);
        altarMaterial(bootstrap, BlockFamilies.BLACKSTONE, 4);
        altarMaterial(bootstrap, BlockFamilies.QUARTZ, 4);
        altarMaterial(bootstrap, BlockFamilies.NETHER_BRICKS, 5);
        altarMaterial(bootstrap, BlockFamilies.RED_NETHER_BRICKS, 5);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_BLACKSTONE, 5);
        altarMaterial(bootstrap, BlockFamilies.POLISHED_BLACKSTONE_BRICKS, 5);
        altarMaterial(bootstrap, BlockFamilies.SMOOTH_QUARTZ, 5);
        altarMaterial(bootstrap, BlockFamilies.END_STONE_BRICKS, 6);
        altarMaterial(bootstrap, BlockFamilies.PURPUR, 6);
    }

    public static void addEtheriumTypes(BootstrapContext<EtheriumType> bootstrap) {
        bootstrap.register(AMEtheriumTypes.LIGHT, new EtheriumType(0x7fa7ef));
        bootstrap.register(AMEtheriumTypes.NEUTRAL, new EtheriumType(0x3fffbf));
        bootstrap.register(AMEtheriumTypes.DARK, new EtheriumType(0x800000));
    }

    public static void addPlants(BootstrapContext<Plant> bootstrap) {
        plant(bootstrap, "bamboo", new UpwardsGrowthType(1, 16, Blocks.BAMBOO), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.BAMBOO),
            new BlockMatchTest(Blocks.BAMBOO_SAPLING)
        )), Items.BAMBOO, Items.BAMBOO);
        plant(bootstrap, "beetroots", new CropGrowthType(List.of(new HarvestState(
            Blocks.BEETROOTS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3),
            Blocks.BEETROOTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.BEETROOTS), Items.BEETROOT_SEEDS, Items.BEETROOT);
        plant(bootstrap, "cactus", new UpwardsGrowthType(1, 4, Blocks.CACTUS_FLOWER, Blocks.CACTUS, false), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CACTUS),
            new BlockMatchTest(Blocks.CACTUS_FLOWER)
        )), Items.CACTUS, Items.CACTUS);
        plant(bootstrap, "carrots", new CropGrowthType(List.of(new HarvestState(
            Blocks.CARROTS.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.CARROTS.defaultBlockState()
        ))), new BlockMatchTest(Blocks.CARROTS), Items.CARROT, Items.CARROT);
        plant(bootstrap, "chorus", new ChorusGrowthType(), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CHORUS_FLOWER),
            new BlockMatchTest(Blocks.CHORUS_PLANT)
        )), Items.CHORUS_FLOWER, Items.CHORUS_FRUIT);
        plant(bootstrap, "cocoa", new CropGrowthType(List.of(
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)),
            new HarvestState(
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).setValue(BlockStateProperties.AGE_2, 2),
                Blocks.COCOA.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST))
        )), new BlockMatchTest(Blocks.COCOA), Items.COCOA_BEANS, Items.COCOA_BEANS);
        plant(bootstrap, "cave_vines", new HangingBushGrowthType(List.of(
            Blocks.CAVE_VINES_PLANT.defaultBlockState().setValue(BlockStateProperties.BERRIES, true),
            Blocks.CAVE_VINES.defaultBlockState().setValue(BlockStateProperties.BERRIES, true)
        ), 1, 0, Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.CAVE_VINES),
            new BlockMatchTest(Blocks.CAVE_VINES_PLANT)
        )), Items.GLOW_BERRIES, Items.GLOW_BERRIES);
        plant(bootstrap, "kelp", new UpwardsGrowthType(1, 26, Blocks.KELP, Blocks.KELP_PLANT), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.KELP),
            new BlockMatchTest(Blocks.KELP_PLANT)
        )), Items.KELP, Items.KELP);
        plant(bootstrap, "melon", new StemGrowthType(new BlockMatchTest(Blocks.MELON_STEM), Blocks.ATTACHED_MELON_STEM, Blocks.MELON.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.MELON_STEM),
            new BlockMatchTest(Blocks.ATTACHED_MELON_STEM),
            new BlockMatchTest(Blocks.MELON)
        )), Items.MELON_SEEDS, Items.MELON_SLICE);
        plant(bootstrap, "nether_wart", new CropGrowthType(List.of(
            new HarvestState(Blocks.NETHER_WART.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), Blocks.NETHER_WART.defaultBlockState())
        )), new BlockMatchTest(Blocks.NETHER_WART), Items.NETHER_WART, Items.NETHER_WART);
        plant(bootstrap, "pitcher_crop", new TallCropGrowthType(List.of(new TallHarvestState(
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER).setValue(BlockStateProperties.AGE_4, 4),
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState())),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER), List.of(BlockStateProperties.AGE_4)),
            new BlockStatePropertyMatchTest(Blocks.PITCHER_CROP.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), List.of(BlockStateProperties.AGE_4))
        ), new BlockMatchTest(Blocks.PITCHER_CROP));
        plant(bootstrap, "potatoes", new CropGrowthType(List.of(new HarvestState(
            Blocks.POTATOES.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.POTATOES.defaultBlockState()
        ))), new BlockMatchTest(Blocks.POTATOES), Items.POTATO, Items.POTATO);
        plant(bootstrap, "pumpkin", new StemGrowthType(new BlockMatchTest(Blocks.PUMPKIN_STEM), Blocks.ATTACHED_PUMPKIN_STEM, Blocks.PUMPKIN.defaultBlockState(), "age", 7), new CompositeMatchTest(List.of(
            new BlockMatchTest(Blocks.PUMPKIN_STEM),
            new BlockMatchTest(Blocks.ATTACHED_PUMPKIN_STEM),
            new BlockMatchTest(Blocks.PUMPKIN)
        )), Items.PUMPKIN_SEEDS, Items.PUMPKIN);
        plant(bootstrap, "sugar_cane", new UpwardsGrowthType(1, 3, Blocks.SUGAR_CANE), new BlockMatchTest(Blocks.SUGAR_CANE), Items.SUGAR_CANE, Items.SUGAR_CANE);
        plant(bootstrap, "sweet_berry_bush", new BushGrowthType(List.of(
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2),
            Blocks.SWEET_BERRY_BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3)
        )), new BlockMatchTest(Blocks.SWEET_BERRY_BUSH),
            Items.SWEET_BERRIES,
            Items.SWEET_BERRIES);
        plant(bootstrap, "torchflower", new CropGrowthType(List.of(new HarvestState(
            Blocks.TORCHFLOWER.defaultBlockState(),
            Blocks.AIR.defaultBlockState()
        ))), new BlockMatchTest(Blocks.TORCHFLOWER_CROP));
        plant(bootstrap, "vine", new HangingGrowthType(1, 0, Blocks.VINE, Blocks.VINE), new BlockMatchTest(Blocks.VINE),
            Items.VINE,
            Items.VINE,
            Items.SHEARS);
        plant(bootstrap, "wheat", new CropGrowthType(List.of(new HarvestState(
            Blocks.WHEAT.defaultBlockState().setValue(BlockStateProperties.AGE_7, 7),
            Blocks.WHEAT.defaultBlockState()
        ))), new BlockMatchTest(Blocks.WHEAT),
            Items.WHEAT_SEEDS,
            Items.WHEAT);
    }

    private static void altarCapMaterial(BootstrapContext<AltarCapMaterial> bootstrap, String name, Block block, int power) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.ALTAR_CAP_MATERIAL, ArsMagicaApi.id(name)), new AltarCapMaterial(block, power));
    }

    private static void altarMaterial(BootstrapContext<AltarMaterial> bootstrap, BlockFamily blockFamily, int power) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.ALTAR_MATERIAL, ArsMagicaApi.id(BuiltInRegistries.BLOCK.getKey(blockFamily.getBaseBlock()).getPath())), new AltarMaterial(blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power));
    }

    private static void plant(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.empty(), Optional.empty(), Optional.empty()));
    }

    private static void plant(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates, Item seed, Item crop) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.of(new ItemStackTemplate(seed)), Optional.of(new ItemStackTemplate(crop)), Optional.empty()));
    }

    @SuppressWarnings("SameParameterValue")
    private static void plant(BootstrapContext<Plant> bootstrap, String name, GrowthType growthType, RuleTest allStates, Item seed, Item crop, Item tool) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.PLANT, ArsMagicaApi.id(name)), new Plant(growthType, allStates, Optional.of(new ItemStackTemplate(seed)), Optional.of(new ItemStackTemplate(crop)), Optional.of(new ItemStackTemplate(tool))));
    }
}
