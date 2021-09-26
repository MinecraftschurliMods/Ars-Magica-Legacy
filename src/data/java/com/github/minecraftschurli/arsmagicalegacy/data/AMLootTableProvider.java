package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

class AMLootTableProvider extends LootTableProvider {
    public AMLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @NotNull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(BlockLootTableProvider::new, LootContextParamSets.BLOCK)/*, Pair.of(EntityLootTableProvider::new, LootContextParamSets.ENTITY)*/);
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker) {
    }

    public static final class BlockLootTableProvider extends BlockLoot {
        private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();
        @Override
        protected void addTables() {
            dropSelf(AMBlocks.WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.WITCHWOOD.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD.get());
            add(AMBlocks.WITCHWOOD_LEAVES.get(), p -> createLeavesDrops(p, AMBlocks.WITCHWOOD_SAPLING.get(), 0.05F, 0.0625F, 0.083333336F, 0.1F));
            dropSelf(AMBlocks.WITCHWOOD_SAPLING.get());
            dropSelf(AMBlocks.WITCHWOOD_PLANKS.get());
            dropSelf(AMBlocks.WITCHWOOD_SLAB.get());
            dropSelf(AMBlocks.WITCHWOOD_STAIRS.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            add(AMBlocks.WITCHWOOD_DOOR.get(), BlockLoot::createDoorTable);
            dropSelf(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            dropSelf(AMBlocks.WITCHWOOD_BUTTON.get());
            dropSelf(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
        }

        @Override
        public void accept(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            addTables();
            Set<ResourceLocation> set = new HashSet<>();
            for (Block block : getKnownBlocks()) {
                ResourceLocation resourcelocation = block.getLootTable();
                if (resourcelocation != LootTable.EMPTY.getLootTableId() && set.add(resourcelocation)) {
                    LootTable.Builder builder = lootTables.remove(resourcelocation);
                    if (builder == null) continue;
                    consumer.accept(resourcelocation, builder);
                }
            }
        }
    }

    public static final class EntityLootTableProvider extends EntityLoot {
        @Override
        protected void addTables() {

        }
    }
}