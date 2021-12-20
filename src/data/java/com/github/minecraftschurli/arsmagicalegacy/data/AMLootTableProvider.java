package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMSkillPoints;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

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

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(Pair.of(BlockLootTableProvider::new, LootContextParamSets.BLOCK), Pair.of(EntityLootTableProvider::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
    }

    private static final class BlockLootTableProvider extends BlockLoot {
        private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

        @Override
        protected void addTables() {
            dropSelf(AMBlocks.OCCULUS.get());
            add(AMBlocks.INSCRIPTION_TABLE.get(), p -> BlockLoot.createSinglePropConditionTable(p, InscriptionTableBlock.HALF, InscriptionTableBlock.Half.RIGHT));
            dropSelf(AMBlocks.ALTAR_CORE.get());
            dropSelf(AMBlocks.MAGIC_WALL.get());
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
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
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
        private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

        @Override
        protected void addTables() {
/*
            add(AMEntities.WATER_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.WATER.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.GREEN.get()).getOrCreateTag())))));
            add(AMEntities.FIRE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.FIRE.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.EARTH_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.EARTH.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.GREEN.get()).getOrCreateTag())))));
            add(AMEntities.AIR_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.AIR.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.WINTER_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.ICE.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.LIGHTNING_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.LIGHTNING.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.LIFE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.LIFE.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.NATURE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.NATURE.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.ARCANE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.ARCANE.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.ENDER_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getAffinityHelper().getEssenceForAffinity(AMAffinities.ENDER.get()).getOrCreateTag())))).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.INFINITY_ORB.get()).apply(SetNbtFunction.setTag(ArsMagicaAPI.get().getSkillHelper().getOrbForSkillPoint(AMSkillPoints.RED.get()).getOrCreateTag())))));
            add(AMEntities.DRYAD.get(), LootTable.lootTable());
            add(AMEntities.MAGE.get(), LootTable.lootTable());
            add(AMEntities.MANA_CREEPER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.VINTEUM_DUST.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))));
*/
        }

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            addTables();
            Set<ResourceLocation> set = Sets.newHashSet();
            for(EntityType<?> entitytype : getKnownEntities()) {
                ResourceLocation resourcelocation = entitytype.getDefaultLootTable();
                if (isNonLiving(entitytype)) {
                    if (resourcelocation != BuiltInLootTables.EMPTY && lootTables.remove(resourcelocation) != null)
                        throw new IllegalStateException(String.format("Weird loottable '%s' for '%s', not a LivingEntity so should not have loot", resourcelocation, entitytype.getRegistryName()));
                } else if (resourcelocation != BuiltInLootTables.EMPTY && set.add(resourcelocation)) {
                    LootTable.Builder loottable$builder = lootTables.remove(resourcelocation);
                    if (loottable$builder == null) continue;
                    consumer.accept(resourcelocation, loottable$builder);
                }
            }
            lootTables.forEach(consumer);
        }

        @Override
        protected void add(EntityType<?> pEntityType, LootTable.Builder pLootTableBuilder) {
            lootTables.put(pEntityType.getDefaultLootTable(), pLootTableBuilder);
        }
    }
}
