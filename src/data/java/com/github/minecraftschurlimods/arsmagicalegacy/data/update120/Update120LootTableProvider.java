package com.github.minecraftschurlimods.arsmagicalegacy.data.update120;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

/**
 *
 */
public class Update120LootTableProvider extends LootTableProvider {
    public Update120LootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(() -> new BlockLootSubProvider(Set.of(), FeatureFlagSet.of(FeatureFlags.UPDATE_1_20)) {
            @Override
            protected void generate() {
                dropSelf(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            }

            @Override
            protected Iterable<Block> getKnownBlocks() {
                return AMRegistries.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
            }
        }, LootContextParamSets.BLOCK)));
    }
}
