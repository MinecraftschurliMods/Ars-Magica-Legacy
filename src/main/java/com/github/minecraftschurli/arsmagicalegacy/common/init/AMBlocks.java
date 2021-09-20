package com.github.minecraftschurli.arsmagicalegacy.common.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.BLOCKS;

@NonExtendable
public interface AMBlocks {
    RegistryObject<Block> CHIMERITE_ORE = BLOCKS.register("chimerite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_CHIMERITE_ORE = BLOCKS.register("deepslate_chimerite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> CHIMERITE_BLOCK = BLOCKS.register("chimerite_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_TOPAZ_ORE = BLOCKS.register("deepslate_topaz_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> TOPAZ_BLOCK = BLOCKS.register("topaz_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> VINTEUM_ORE = BLOCKS.register("vinteum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));
    RegistryObject<Block> DEEPSLATE_VINTEUM_ORE = BLOCKS.register("deepslate_vinteum_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().color(MaterialColor.DEEPSLATE).strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)));
    RegistryObject<Block> VINTEUM_BLOCK = BLOCKS.register("vinteum_block", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3F, 3F)));

    /**
     * Empty method that is required for classloading
     */
    static void init() {}
}
