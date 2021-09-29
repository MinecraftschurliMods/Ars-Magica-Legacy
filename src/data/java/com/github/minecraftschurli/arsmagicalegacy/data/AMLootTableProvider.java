package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
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

    private static final class BlockLootTableProvider extends BlockLoot {
        private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();
        @Override
        protected void addTables() {
            add(AMBlocks.CHIMERITE_ORE.get(), p -> createOreDrop(p, AMItems.CHIMERITE.get()));
            add(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), p -> createOreDrop(p, AMItems.CHIMERITE.get()));
            dropSelf(AMBlocks.CHIMERITE_BLOCK.get());
            add(AMBlocks.TOPAZ_ORE.get(), p -> createOreDrop(p, AMItems.TOPAZ.get()));
            add(AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), p -> createOreDrop(p, AMItems.TOPAZ.get()));
            dropSelf(AMBlocks.TOPAZ_BLOCK.get());
            add(AMBlocks.VINTEUM_ORE.get(), p -> createOreDrop(p, AMItems.VINTEUM_DUST.get()));
            add(AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), p -> createOreDrop(p, AMItems.VINTEUM_DUST.get()));
            dropSelf(AMBlocks.VINTEUM_BLOCK.get());
            add(AMBlocks.MOONSTONE_ORE.get(), p -> createOreDrop(p, AMItems.MOONSTONE.get()));
            add(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), p -> createOreDrop(p, AMItems.MOONSTONE.get()));
            dropSelf(AMBlocks.MOONSTONE_BLOCK.get());
            add(AMBlocks.SUNSTONE_ORE.get(), p -> createOreDrop(p, AMItems.SUNSTONE.get()));
            dropSelf(AMBlocks.SUNSTONE_BLOCK.get());
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
            dropSelf(AMBlocks.AUM.get());
            dropSelf(AMBlocks.CERUBLOSSOM.get());
            dropSelf(AMBlocks.DESERT_NOVA.get());
            dropSelf(AMBlocks.TARMA_ROOT.get());
            dropSelf(AMBlocks.WAKEBLOOM.get());
            dropSelf(AMBlocks.VINTEUM_TORCH.get());
            dropSelf(AMBlocks.VINTEUM_WALL_TORCH.get());
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

        @Override
        protected void add(Block pBlock, LootTable.Builder pLootTableBuilder) {
            lootTables.put(pBlock.getLootTable(), pLootTableBuilder);
        }
    }

    private static final class EntityLootTableProvider extends EntityLoot {
        @Override
        protected void addTables() {

        }
    }
}
