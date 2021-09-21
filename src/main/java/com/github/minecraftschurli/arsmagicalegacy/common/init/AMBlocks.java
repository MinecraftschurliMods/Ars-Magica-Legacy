package com.github.minecraftschurli.arsmagicalegacy.common.init;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.BLOCKS;

@NonExtendable
public interface AMBlocks {
    RegistryObject<Block> CHIMERITE_ORE = BLOCKS.register("chimerite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_CHIMERITE_ORE = BLOCKS.register("deepslate_chimerite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> CHIMERITE_BLOCK = BLOCKS.register("chimerite_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_TOPAZ_ORE = BLOCKS.register("deepslate_topaz_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> VINTEUM_ORE = BLOCKS.register("vinteum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_VINTEUM_ORE = BLOCKS.register("deepslate_vinteum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> VINTEUM_BLOCK = BLOCKS.register("vinteum_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> MOONSTONE_ORE = BLOCKS.register("moonstone_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> MOONSTONE_BLOCK = BLOCKS.register("moonstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> SUNSTONE_ORE = BLOCKS.register("sunstone_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> SUNSTONE_BLOCK = BLOCKS.register("sunstone_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> WITCHWOOD_LOG = BLOCKS.register("witchwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, s -> s.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MaterialColor.TERRACOTTA_LIGHT_BLUE : MaterialColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<Block> WITCHWOOD = BLOCKS.register("witchwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<Block> STRIPPED_WITCHWOOD_LOG = BLOCKS.register("stripped_witchwood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<Block> STRIPPED_WITCHWOOD = BLOCKS.register("stripped_witchwood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<Block> WITCHWOOD_LEAVES = BLOCKS.register("witchwood_leaves", () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES, MaterialColor.QUARTZ).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn((a, b, c, d) -> d == EntityType.OCELOT || d == EntityType.PARROT).isSuffocating((a, b, c) -> false).isViewBlocking((a, b, c) -> false)));
    RegistryObject<Block> WITCHWOOD_SAPLING = BLOCKS.register("witchwood_sapling", () -> new SaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))); //TODO tree type
    RegistryObject<Block> WITCHWOOD_PLANKS = BLOCKS.register("witchwood_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(2F).sound(SoundType.WOOD)));
    RegistryObject<Block> WITCHWOOD_SLAB = BLOCKS.register("witchwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<Block> WITCHWOOD_STAIRS = BLOCKS.register("witchwood_stairs", () -> new StairBlock(() -> AMBlocks.WITCHWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<Block> WITCHWOOD_FENCE = BLOCKS.register("witchwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<Block> WITCHWOOD_FENCE_GATE = BLOCKS.register("witchwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<Block> WITCHWOOD_DOOR = BLOCKS.register("witchwood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get()).noOcclusion()));
    RegistryObject<Block> WITCHWOOD_TRAPDOOR = BLOCKS.register("witchwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get()).noOcclusion().isValidSpawn((a, b, c, d) -> false)));
    RegistryObject<Block> WITCHWOOD_BUTTON = BLOCKS.register("witchwood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));
    RegistryObject<Block> WITCHWOOD_PRESSURE_PLATE = BLOCKS.register("witchwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(AMBlocks.WITCHWOOD_PLANKS.get())));

    /**
     * Empty method that is required for classloading
     */
    static void init() {}
}