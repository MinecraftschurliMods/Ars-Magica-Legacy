package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ToolTierProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public final class AMToolTierProvider extends ToolTierProvider {
    public AMToolTierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate() {
        add(0, BlockTags.INCORRECT_FOR_WOODEN_TOOL);
        add(1, BlockTags.INCORRECT_FOR_STONE_TOOL);
        add(2, BlockTags.INCORRECT_FOR_IRON_TOOL);
        add(3, BlockTags.INCORRECT_FOR_DIAMOND_TOOL);
        add(4, BlockTags.INCORRECT_FOR_NETHERITE_TOOL);
    }
}
