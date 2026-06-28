package com.github.minecraftschurlimods.arsmagicalegacy.datagen.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEnchantments;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMagic;
import com.github.minecraftschurlimods.arsmagicalegacy.common.loot.EnchantmentLevelFromItemProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public final class AMLootTableProvider extends LootTableProvider {
    public AMLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(
            new SubProviderEntry(AMBlockLootSubProvider::new, LootContextParamSets.BLOCK),
            new SubProviderEntry(AMChestLootSubProvider::new, LootContextParamSets.CHEST),
            new SubProviderEntry(AMEntityLootSubProvider::new, LootContextParamSets.ENTITY),
            new SubProviderEntry(AMEntityModifiedLootSubProvider::new, LootContextParamSets.ENTITY)
        ), registries);
    }

    private static class AMBlockLootSubProvider extends BlockLootSubProvider {
        private AMBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return AMBlocks.BLOCKS.getEntries().stream().map(Holder::value).toList();
        }

        @Override
        protected void generate() {
            dropOther(AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(), Items.CAULDRON);
            dropSelf(AMBlocks.OCCULUS.get());
            add(AMBlocks.INSCRIPTION_TABLE.get(), block -> LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(InscriptionTableBlock.HALF, InscriptionTableBlock.Half.RIGHT)))
                .when(ExplosionCondition.survivesExplosion())
                .add(LootItem.lootTableItem(block).apply(CopyBlockState.copyState(block).copy(InscriptionTableBlock.TIER)))));
            dropSelf(AMBlocks.ALTAR_CORE.get());
            dropSelf(AMBlocks.MAGIC_WALL.get());
            add(AMBlocks.OBELISK.get(), block -> createSinglePropConditionTable(block, ObeliskBlock.PART, ObeliskBlock.Part.LOWER));
            add(AMBlocks.CELESTIAL_PRISM.get(), block -> createSinglePropConditionTable(block, CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER));
            dropSelf(AMBlocks.BLACK_AUREM.get());
            add(AMBlocks.WIZARDS_CHALK.get(), noDrop());
            dropSelf(AMBlocks.REDSTONE_INLAY.get());
            dropSelf(AMBlocks.IRON_INLAY.get());
            dropSelf(AMBlocks.GOLD_INLAY.get());
            dropSelf(AMBlocks.VINTEUM_TORCH.get());
            dropOther(AMBlocks.VINTEUM_WALL_TORCH.get(), AMBlocks.VINTEUM_TORCH.get());
            add(AMBlocks.CHIMERITE_ORE.get(), block -> createOreDrop(block, AMItems.CHIMERITE.get()));
            add(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get(), block -> createOreDrop(block, AMItems.CHIMERITE.get()));
            dropSelf(AMBlocks.CHIMERITE_BLOCK.get());
            add(AMBlocks.TOPAZ_ORE.get(), block -> createOreDrop(block, AMItems.TOPAZ.get()));
            add(AMBlocks.DEEPSLATE_TOPAZ_ORE.get(), block -> createOreDrop(block, AMItems.TOPAZ.get()));
            dropSelf(AMBlocks.TOPAZ_BLOCK.get());
            add(AMBlocks.VINTEUM_ORE.get(), block -> createOreDrop(block, AMItems.VINTEUM_DUST.get()));
            add(AMBlocks.DEEPSLATE_VINTEUM_ORE.get(), block -> createOreDrop(block, AMItems.VINTEUM_DUST.get()));
            dropSelf(AMBlocks.VINTEUM_BLOCK.get());
            add(AMBlocks.MOONSTONE_ORE.get(), block -> createOreDrop(block, AMItems.MOONSTONE.get()));
            add(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get(), block -> createOreDrop(block, AMItems.MOONSTONE.get()));
            dropSelf(AMBlocks.MOONSTONE_BLOCK.get());
            add(AMBlocks.SUNSTONE_ORE.get(), block -> createOreDrop(block, AMItems.SUNSTONE.get()));
            dropSelf(AMBlocks.SUNSTONE_BLOCK.get());
            dropSelf(AMBlocks.WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.WITCHWOOD_WOOD.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD_LOG.get());
            dropSelf(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get());
            add(AMBlocks.WITCHWOOD_LEAVES.get(), block -> createLeavesDrops(block, AMBlocks.WITCHWOOD_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
            dropSelf(AMBlocks.WITCHWOOD_SAPLING.get());
            dropPottedContents(AMBlocks.POTTED_WITCHWOOD_SAPLING.get());
            dropSelf(AMBlocks.WITCHWOOD_PLANKS.get());
            dropSelf(AMBlocks.WITCHWOOD_SLAB.get());
            dropSelf(AMBlocks.WITCHWOOD_STAIRS.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE.get());
            dropSelf(AMBlocks.WITCHWOOD_FENCE_GATE.get());
            add(AMBlocks.WITCHWOOD_DOOR.get(), this::createDoorTable);
            dropSelf(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            dropSelf(AMBlocks.WITCHWOOD_BUTTON.get());
            dropSelf(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            dropSelf(AMBlocks.WITCHWOOD_SIGN.get());
            dropOther(AMBlocks.WITCHWOOD_WALL_SIGN.get(), AMBlocks.WITCHWOOD_SIGN.get());
            dropSelf(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            dropOther(AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            dropSelf(AMBlocks.AUM.get());
            dropPottedContents(AMBlocks.POTTED_AUM.get());
            dropSelf(AMBlocks.CERUBLOSSOM.get());
            dropPottedContents(AMBlocks.POTTED_CERUBLOSSOM.get());
            dropSelf(AMBlocks.DESERT_NOVA.get());
            dropPottedContents(AMBlocks.POTTED_DESERT_NOVA.get());
            dropSelf(AMBlocks.TARMA_ROOT.get());
            dropPottedContents(AMBlocks.POTTED_TARMA_ROOT.get());
            dropSelf(AMBlocks.WAKEBLOOM.get());
            dropPottedContents(AMBlocks.POTTED_WAKEBLOOM.get());
        }
    }

    private static class AMChestLootSubProvider implements LootTableSubProvider {
        private final HolderLookup.Provider registries;

        private AMChestLootSubProvider(HolderLookup.Provider registries) {
            this.registries = registries;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            addTomeLoot(output, BuiltInLootTables.ANCIENT_CITY, Affinity.NONE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.ANCIENT_CITY_ICE_BOX, Affinity.NONE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.SHIPWRECK_TREASURE, AMMagic.WATER, 0.1f);
            addTomeLoot(output, BuiltInLootTables.UNDERWATER_RUIN_BIG, AMMagic.WATER, 0.05f);
            addTomeLoot(output, BuiltInLootTables.UNDERWATER_RUIN_SMALL, AMMagic.WATER, 0.05f);
            addTomeLoot(output, BuiltInLootTables.BASTION_TREASURE, AMMagic.FIRE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.BASTION_BRIDGE, AMMagic.FIRE, 0.05f);
            addTomeLoot(output, BuiltInLootTables.NETHER_BRIDGE, AMMagic.FIRE, 0.05f);
            addTomeLoot(output, BuiltInLootTables.ABANDONED_MINESHAFT, AMMagic.EARTH, 0.05f);
            addTomeLoot(output, BuiltInLootTables.SIMPLE_DUNGEON, AMMagic.EARTH, 0.05f);
            addTomeLoot(output, BuiltInLootTables.TRIAL_CHAMBERS_REWARD, AMMagic.AIR, 0.1f);
            addTomeLoot(output, BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS, AMMagic.AIR, 0.3f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_DESERT_HOUSE, AMMagic.AIR, 0.02f);
            addTomeLoot(output, BuiltInLootTables.IGLOO_CHEST, AMMagic.ICE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_SNOWY_HOUSE, AMMagic.ICE, 0.02f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_TAIGA_HOUSE, AMMagic.ICE, 0.02f);
            addTomeLoot(output, BuiltInLootTables.DESERT_PYRAMID, AMMagic.LIGHTNING, 0.1f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, AMMagic.LIGHTNING, 0.02f);
            addTomeLoot(output, BuiltInLootTables.JUNGLE_TEMPLE, AMMagic.NATURE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_PLAINS_HOUSE, AMMagic.NATURE, 0.02f);
            addTomeLoot(output, BuiltInLootTables.WOODLAND_MANSION, AMMagic.ARCANE, 0.1f);
            addTomeLoot(output, BuiltInLootTables.PILLAGER_OUTPOST, AMMagic.ARCANE, 0.05f);
            addTomeLoot(output, BuiltInLootTables.VILLAGE_TEMPLE, AMMagic.ARCANE, 0.02f);
            addTomeLoot(output, BuiltInLootTables.END_CITY_TREASURE, AMMagic.ENDER, 0.1f);
            addTomeLoot(output, BuiltInLootTables.STRONGHOLD_LIBRARY, AMMagic.ENDER, 0.05f);
        }

        private void addTomeLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, ResourceKey<LootTable> lootTable, ResourceKey<Affinity> affinity, float chance) {
            HolderLookup.RegistryLookup<Affinity> lookup = registries.lookupOrThrow(AMRegistries.Keys.AFFINITY);
            output.accept(ResourceKey.create(lootTable.registryKey(), affinity.identifier().withPath(lootTable.identifier().getPath().replace("chests/", "chests/modify/")).withSuffix("_affinity_tome")), LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(AMItems.AFFINITY_TOME).apply(SetComponentsFunction.setComponent(AMDataComponents.AFFINITY.get(), lookup.getOrThrow(affinity))).setWeight(19))
                .add(LootItem.lootTableItem(AMItems.AFFINITY_TOME).apply(SetComponentsFunction.setComponent(AMDataComponents.AFFINITY.get(), lookup.getOrThrow(AMMagic.LIFE))).setWeight(1))
                .add(EmptyLootItem.emptyItem().setWeight((int) (20 / chance) - 20))
            ));
        }
    }

    private static class AMEntityLootSubProvider extends EntityLootSubProvider {
        private final HolderLookup.Provider registries;

        protected AMEntityLootSubProvider(HolderLookup.Provider registries) {
            super(FeatureFlags.REGISTRY.allFlags(), registries);
            this.registries = registries;
        }

        @Override
        public void generate() {
            add(AMEntities.DRYAD.get(), LootTable.lootTable());
            add(AMEntities.MANA_CREEPER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(AMItems.VINTEUM_DUST.get())
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(0, 1))))));
            addBoss(AMEntities.WATER_GUARDIAN, AMMagic.WATER);
            addBoss(AMEntities.FIRE_GUARDIAN, AMMagic.FIRE);
            addBoss(AMEntities.EARTH_GUARDIAN, AMMagic.EARTH);
            addBoss(AMEntities.AIR_GUARDIAN, AMMagic.AIR);
            addBoss(AMEntities.ICE_GUARDIAN, AMMagic.ICE);
            addBoss(AMEntities.LIGHTNING_GUARDIAN, AMMagic.LIGHTNING);
            addBoss(AMEntities.NATURE_GUARDIAN, AMMagic.NATURE);
            addBoss(AMEntities.LIFE_GUARDIAN, AMMagic.LIFE);
            addBoss(AMEntities.ARCANE_GUARDIAN, AMMagic.ARCANE);
            addBoss(AMEntities.ENDER_GUARDIAN, AMMagic.ENDER);
        }

        @SuppressWarnings("RedundantStreamOptionalCall")
        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return AMEntities.ENTITIES.getEntries()
                .stream()
                .map(Holder::value)
                .filter(e -> e.getCategory() != MobCategory.MISC)
                .map(e -> e);
        }

        private void addBoss(DeferredHolder<EntityType<?>, ?> boss, ResourceKey<Affinity> affinity) {
            add(boss.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE)
                    .apply(SetComponentsFunction.setComponent(AMDataComponents.AFFINITY.get(), registries.lookupOrThrow(AMRegistries.Keys.AFFINITY).getOrThrow(affinity))))));
        }
    }

    private static class AMEntityModifiedLootSubProvider implements LootTableSubProvider {
        private final HolderLookup.Provider registries;

        public AMEntityModifiedLootSubProvider(HolderLookup.Provider registries) {
            this.registries = registries;
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            addDismemberingLoot(output, EntityType.CREEPER.getDefaultLootTable().orElseThrow(), Items.CREEPER_HEAD, 0.5f);
            addDismemberingLoot(output, EntityType.PIGLIN.getDefaultLootTable().orElseThrow(), Items.PIGLIN_HEAD, 0.5f);
            addDismemberingLoot(output, EntityType.SKELETON.getDefaultLootTable().orElseThrow(), Items.SKELETON_SKULL, 0.5f);
            addDismemberingLoot(output, EntityType.WITHER_SKELETON.getDefaultLootTable().orElseThrow(), Items.WITHER_SKELETON_SKULL, 0.5f);
            addDismemberingLoot(output, EntityType.ZOMBIE.getDefaultLootTable().orElseThrow(), Items.ZOMBIE_HEAD, 0.5f);
        }

        @SuppressWarnings("SameParameterValue")
        private void addDismemberingLoot(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output, ResourceKey<LootTable> lootTable, ItemLike item, float chance) {
            output.accept(ResourceKey.create(lootTable.registryKey(), ArsMagicaApi.id(lootTable.identifier().getPath().replace("entities/", "entities/modify/")).withSuffix("_dismembering")), LootTable.lootTable().withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(item).when(LootItemRandomChanceCondition.randomChance(new EnchantmentLevelFromItemProvider(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(AMEnchantments.DISMEMBERING), LevelBasedValue.perLevel(chance)))))
                .apply(LimitCount.limitCount(IntRange.exact(1)))
            ));
        }
    }
}
