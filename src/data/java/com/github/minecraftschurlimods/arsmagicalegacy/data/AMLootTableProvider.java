package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.celestialprism.CelestialPrismBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAffinities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyBlockState;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

class AMLootTableProvider extends LootTableProvider {
    AMLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(Block::new, LootContextParamSets.BLOCK), new SubProviderEntry(Entity::new, LootContextParamSets.ENTITY), new SubProviderEntry(Chest::new, LootContextParamSets.CHEST)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {}

    private static class Block extends BlockLootSubProvider {
        protected Block() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected Iterable<net.minecraft.world.level.block.Block> getKnownBlocks() {
            return AMRegistries.BLOCKS.getEntries().stream().map(Holder::value).toList();
        }

        @Override
        protected void generate() {
            dropSelf(AMBlocks.OCCULUS.get());
            add(AMBlocks.INSCRIPTION_TABLE.get(), p -> {
                LootPoolSingletonContainer.Builder<?> builder = LootItem.lootTableItem(p);
                builder.apply(CopyBlockState.copyState(p).copy(InscriptionTableBlock.TIER));
                LootPool.Builder pool = LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(p).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(InscriptionTableBlock.HALF, InscriptionTableBlock.Half.RIGHT)))
                        .when(ExplosionCondition.survivesExplosion())
                        .add(builder);
                return LootTable.lootTable().withPool(pool);
            });
            dropSelf(AMBlocks.ALTAR_CORE.get());
            dropSelf(AMBlocks.MAGIC_WALL.get());
            add(AMBlocks.OBELISK.get(), p -> createSinglePropConditionTable(p, ObeliskBlock.PART, ObeliskBlock.Part.LOWER));
            add(AMBlocks.CELESTIAL_PRISM.get(), p -> createSinglePropConditionTable(p, CelestialPrismBlock.HALF, DoubleBlockHalf.LOWER));
            dropSelf(AMBlocks.BLACK_AUREM.get());
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
            add(AMBlocks.WITCHWOOD_DOOR.get(), this::createDoorTable);
            dropSelf(AMBlocks.WITCHWOOD_TRAPDOOR.get());
            dropSelf(AMBlocks.WITCHWOOD_BUTTON.get());
            dropSelf(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get());
            dropSelf(AMBlocks.WITCHWOOD_SIGN.get());
            dropSelf(AMBlocks.WITCHWOOD_HANGING_SIGN.get());
            dropSelf(AMBlocks.AUM.get());
            dropSelf(AMBlocks.CERUBLOSSOM.get());
            dropSelf(AMBlocks.DESERT_NOVA.get());
            dropSelf(AMBlocks.TARMA_ROOT.get());
            dropSelf(AMBlocks.WAKEBLOOM.get());
            add(AMBlocks.POTTED_AUM.get(), p -> createPotFlowerItemTable(AMBlocks.AUM.get()));
            add(AMBlocks.POTTED_CERUBLOSSOM.get(), p -> createPotFlowerItemTable(AMBlocks.CERUBLOSSOM.get()));
            add(AMBlocks.POTTED_DESERT_NOVA.get(), p -> createPotFlowerItemTable(AMBlocks.DESERT_NOVA.get()));
            add(AMBlocks.POTTED_TARMA_ROOT.get(), p -> createPotFlowerItemTable(AMBlocks.TARMA_ROOT.get()));
            add(AMBlocks.POTTED_WAKEBLOOM.get(), p -> createPotFlowerItemTable(AMBlocks.WAKEBLOOM.get()));
            add(AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), p -> createPotFlowerItemTable(AMBlocks.WITCHWOOD_SAPLING.get()));
            dropSelf(AMBlocks.VINTEUM_TORCH.get());
            dropSelf(AMBlocks.VINTEUM_WALL_TORCH.get());
            add(AMBlocks.WIZARDS_CHALK.get(), noDrop());
            add(AMBlocks.SPELL_RUNE.get(), noDrop());
            dropSelf(AMBlocks.IRON_INLAY.get());
            dropSelf(AMBlocks.GOLD_INLAY.get());
            dropSelf(AMBlocks.REDSTONE_INLAY.get());
        }
    }

    private static class Entity extends EntityLootSubProvider {
        protected Entity() {
            super(FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected Stream<EntityType<?>> getKnownEntityTypes() {
            return AMRegistries.ENTITY_TYPES.getEntries().stream().map(Holder::value);
        }

        @SuppressWarnings("deprecation")
        @Override
        public void generate() {
            var helper = ArsMagicaAPI.get().getAffinityHelper();
            add(AMEntities.WATER_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.WATER.value()).getOrCreateTag())))));
            add(AMEntities.FIRE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.FIRE.value()).getOrCreateTag())))));
            add(AMEntities.EARTH_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.EARTH.value()).getOrCreateTag())))));
            add(AMEntities.AIR_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.AIR.value()).getOrCreateTag())))));
            add(AMEntities.ICE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.ICE.value()).getOrCreateTag())))));
            add(AMEntities.LIGHTNING_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.LIGHTNING.value()).getOrCreateTag())))));
            add(AMEntities.LIFE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.LIFE.value()).getOrCreateTag())))));
            add(AMEntities.NATURE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.NATURE.value()).getOrCreateTag())))));
            add(AMEntities.ARCANE_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.ARCANE.value()).getOrCreateTag())))));
            add(AMEntities.ENDER_GUARDIAN.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.AFFINITY_ESSENCE.get()).apply(SetNbtFunction.setTag(helper.getEssenceForAffinity(AMAffinities.ENDER.value()).getOrCreateTag())))));
            add(AMEntities.DRYAD.get(), LootTable.lootTable());
            add(AMEntities.MAGE.get(), LootTable.lootTable());
            add(AMEntities.MANA_CREEPER.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1F)).add(LootItem.lootTableItem(AMItems.VINTEUM_DUST.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 2F))).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0F, 1F))))));
        }
    }

    private static class Chest implements LootTableSubProvider {
        private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

        protected void generate() {
            addTomeLoot(BuiltInLootTables.ANCIENT_CITY, Affinity.NONE, 0.1f);
            addTomeLoot(BuiltInLootTables.ANCIENT_CITY_ICE_BOX, Affinity.NONE, 0.1f);
            addTomeLoot(BuiltInLootTables.SHIPWRECK_TREASURE, Affinity.WATER, 0.1f);
            addTomeLoot(BuiltInLootTables.UNDERWATER_RUIN_BIG, Affinity.WATER, 0.025f);
            addTomeLoot(BuiltInLootTables.UNDERWATER_RUIN_SMALL, Affinity.WATER, 0.025f);
            addTomeLoot(BuiltInLootTables.BASTION_TREASURE, Affinity.FIRE, 0.1f);
            addTomeLoot(BuiltInLootTables.NETHER_BRIDGE, Affinity.FIRE, 0.05f);
            addTomeLoot(BuiltInLootTables.ABANDONED_MINESHAFT, Affinity.EARTH, 0.05f);
            addTomeLoot(BuiltInLootTables.SIMPLE_DUNGEON, Affinity.EARTH, 0.05f);
            addTomeLoot(BuiltInLootTables.DESERT_PYRAMID, Affinity.AIR, 0.1f);
            addTomeLoot(BuiltInLootTables.VILLAGE_DESERT_HOUSE, Affinity.AIR, 0.02f);
            addTomeLoot(BuiltInLootTables.IGLOO_CHEST, Affinity.ICE, 0.1f);
            addTomeLoot(BuiltInLootTables.VILLAGE_SNOWY_HOUSE, Affinity.ICE, 0.02f);
            addTomeLoot(BuiltInLootTables.VILLAGE_TAIGA_HOUSE, Affinity.ICE, 0.02f);
            addTomeLoot(BuiltInLootTables.PILLAGER_OUTPOST, Affinity.LIGHTNING, 0.1f);
            addTomeLoot(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE, Affinity.LIGHTNING, 0.02f);
            addTomeLoot(BuiltInLootTables.JUNGLE_TEMPLE, Affinity.NATURE, 0.1f);
            addTomeLoot(BuiltInLootTables.VILLAGE_PLAINS_HOUSE, Affinity.NATURE, 0.02f);
            addTomeLoot(BuiltInLootTables.STRONGHOLD_LIBRARY, Affinity.ARCANE, 0.1f);
            addTomeLoot(BuiltInLootTables.WOODLAND_MANSION, Affinity.ARCANE, 0.05f);
            addTomeLoot(BuiltInLootTables.VILLAGE_TEMPLE, Affinity.ARCANE, 0.02f);
            addTomeLoot(BuiltInLootTables.END_CITY_TREASURE, Affinity.ENDER, 0.1f);
        }

        @Override
        public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            this.generate();
            lootTables.forEach(consumer);
        }

        protected void add(ResourceLocation resourceLocation, LootTable.Builder builder) {
            this.lootTables.put(resourceLocation, builder);
        }

        @SuppressWarnings("deprecation")
        protected void addTomeLoot(ResourceLocation lootTable, ResourceLocation affinity, float chance) {
            var helper = ArsMagicaAPI.get().getAffinityHelper();
            ItemStack tome = helper.getTomeForAffinity(affinity);
            ItemStack lifeTome = helper.getTomeForAffinity(Affinity.LIFE);
            add(new ResourceLocation(affinity.getNamespace(), lootTable.getPath().replace("chests/", "chests/modify/")), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(tome.getItem()).apply(SetNbtFunction.setTag(tome.getOrCreateTag())).setWeight(19))
                    .add(LootItem.lootTableItem(lifeTome.getItem()).apply(SetNbtFunction.setTag(lifeTome.getOrCreateTag())).setWeight(1))
                    .add(EmptyLootItem.emptyItem().setWeight((int) (20 / chance) - 20))));
        }
    }
}
