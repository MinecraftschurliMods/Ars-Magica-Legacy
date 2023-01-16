package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.data.AltarCapMaterialProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

class AMAltarCapMaterialProvider extends AltarCapMaterialProvider {
    AMAltarCapMaterialProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void generate(Consumer<Builder> consumer) {
        add(consumer, builder("glass", Blocks.GLASS, 1));
        add(consumer, builder("coal", Blocks.COAL_BLOCK, 2));
        add(consumer, builder("copper", Blocks.COPPER_BLOCK, 3));
        add(consumer, builder("exposed_copper", Blocks.EXPOSED_COPPER, 3));
        add(consumer, builder("weathered_copper", Blocks.WEATHERED_COPPER, 3));
        add(consumer, builder("oxidized_copper", Blocks.OXIDIZED_COPPER, 3));
        add(consumer, builder("waxed_copper", Blocks.WAXED_COPPER_BLOCK, 3));
        add(consumer, builder("waxed_exposed_copper", Blocks.WAXED_EXPOSED_COPPER, 3));
        add(consumer, builder("waxed_weathered_copper", Blocks.WAXED_WEATHERED_COPPER, 3));
        add(consumer, builder("waxed_oxidized_copper", Blocks.WAXED_OXIDIZED_COPPER, 3));
        add(consumer, builder("iron", Blocks.IRON_BLOCK, 4));
        add(consumer, builder("redstone", Blocks.REDSTONE_BLOCK, 5));
        add(consumer, builder("vinteum", AMBlocks.VINTEUM_BLOCK.get(), 6));
        add(consumer, builder("chimerite", AMBlocks.CHIMERITE_BLOCK.get(), 7));
        add(consumer, builder("lapis", Blocks.LAPIS_BLOCK, 8));
        add(consumer, builder("gold", Blocks.GOLD_BLOCK, 9));
        add(consumer, builder("topaz", AMBlocks.TOPAZ_BLOCK.get(), 10));
        add(consumer, builder("diamond", Blocks.DIAMOND_BLOCK, 11));
        add(consumer, builder("emerald", Blocks.EMERALD_BLOCK, 12));
        add(consumer, builder("netherite", Blocks.NETHERITE_BLOCK, 13));
        add(consumer, builder("moonstone", AMBlocks.MOONSTONE_BLOCK.get(), 14));
        add(consumer, builder("sunstone", AMBlocks.SUNSTONE_BLOCK.get(), 15));
    }
}
