package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarCapMaterialProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

class AMAltarCapMaterialProvider extends AltarCapMaterialProvider {
    AMAltarCapMaterialProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(ArsMagicaAPI.MOD_ID, generator, existingFileHelper, registryOps);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        builder("glass", Blocks.GLASS, 1).build(consumer);
        builder("coal", Blocks.COAL_BLOCK, 2).build(consumer);
        builder("copper", Blocks.COPPER_BLOCK, 3).build(consumer);
        builder("exposed_copper", Blocks.EXPOSED_COPPER, 3).build(consumer);
        builder("weathered_copper", Blocks.WEATHERED_COPPER, 3).build(consumer);
        builder("oxidized_copper", Blocks.OXIDIZED_COPPER, 3).build(consumer);
        builder("waxed_copper", Blocks.WAXED_COPPER_BLOCK, 3).build(consumer);
        builder("waxed_exposed_copper", Blocks.WAXED_EXPOSED_COPPER, 3).build(consumer);
        builder("waxed_weathered_copper", Blocks.WAXED_WEATHERED_COPPER, 3).build(consumer);
        builder("waxed_oxidized_copper", Blocks.WAXED_OXIDIZED_COPPER, 3).build(consumer);
        builder("iron", Blocks.IRON_BLOCK, 4).build(consumer);
        builder("redstone", Blocks.REDSTONE_BLOCK, 5).build(consumer);
        builder("vinteum", AMBlocks.VINTEUM_BLOCK.get(), 6).build(consumer);
        builder("chimerite", AMBlocks.CHIMERITE_BLOCK.get(), 7).build(consumer);
        builder("lapis", Blocks.LAPIS_BLOCK, 8).build(consumer);
        builder("gold", Blocks.GOLD_BLOCK, 9).build(consumer);
        builder("topaz", AMBlocks.TOPAZ_BLOCK.get(), 10).build(consumer);
        builder("diamond", Blocks.DIAMOND_BLOCK, 11).build(consumer);
        builder("emerald", Blocks.EMERALD_BLOCK, 12).build(consumer);
        builder("netherite", Blocks.NETHERITE_BLOCK, 13).build(consumer);
        builder("moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14).build(consumer);
        builder("sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15).build(consumer);
    }
}
