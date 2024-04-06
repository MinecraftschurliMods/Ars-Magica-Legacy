package com.github.minecraftschurlimods.arsmagicalegacy.data.update120;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

/**
 *
 */
public class Update120BlockTagsProvider extends BlockTagsProvider {
    public Update120BlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArsMagicaAPI.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
    }
}
