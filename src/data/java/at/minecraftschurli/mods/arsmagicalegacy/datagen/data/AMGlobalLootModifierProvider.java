package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.loot.AddConditionsModifier;
import at.minecraftschurli.mods.arsmagicalegacy.loot.IsSummonCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class AMGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public AMGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ArsMagicaApi.MOD_ID);
    }

    @Override
    protected void start() {
        add("disable_summon_drops", new AddConditionsModifier(
            new LootItemCondition[0],
            1000,
             new InvertedLootItemCondition(IsSummonCondition.INSTANCE)));
        addDismemberingModifier(EntityType.CREEPER);
        addDismemberingModifier(EntityType.PIGLIN);
        addDismemberingModifier(EntityType.SKELETON);
        addDismemberingModifier(EntityType.WITHER_SKELETON);
        addDismemberingModifier(EntityType.ZOMBIE);
        addTomeModifier(BuiltInLootTables.ANCIENT_CITY);
        addTomeModifier(BuiltInLootTables.ANCIENT_CITY_ICE_BOX);
        addTomeModifier(BuiltInLootTables.SHIPWRECK_TREASURE);
        addTomeModifier(BuiltInLootTables.UNDERWATER_RUIN_BIG);
        addTomeModifier(BuiltInLootTables.UNDERWATER_RUIN_SMALL);
        addTomeModifier(BuiltInLootTables.BASTION_TREASURE);
        addTomeModifier(BuiltInLootTables.NETHER_BRIDGE);
        addTomeModifier(BuiltInLootTables.ABANDONED_MINESHAFT);
        addTomeModifier(BuiltInLootTables.SIMPLE_DUNGEON);
        addTomeModifier(BuiltInLootTables.DESERT_PYRAMID);
        addTomeModifier(BuiltInLootTables.VILLAGE_DESERT_HOUSE);
        addTomeModifier(BuiltInLootTables.IGLOO_CHEST);
        addTomeModifier(BuiltInLootTables.VILLAGE_SNOWY_HOUSE);
        addTomeModifier(BuiltInLootTables.VILLAGE_TAIGA_HOUSE);
        addTomeModifier(BuiltInLootTables.PILLAGER_OUTPOST);
        addTomeModifier(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE);
        addTomeModifier(BuiltInLootTables.JUNGLE_TEMPLE);
        addTomeModifier(BuiltInLootTables.VILLAGE_PLAINS_HOUSE);
        addTomeModifier(BuiltInLootTables.STRONGHOLD_LIBRARY);
        addTomeModifier(BuiltInLootTables.WOODLAND_MANSION);
        addTomeModifier(BuiltInLootTables.VILLAGE_TEMPLE);
        addTomeModifier(BuiltInLootTables.END_CITY_TREASURE);
    }

    private void addDismemberingModifier(EntityType<?> entityType) {
        Optional<ResourceKey<LootTable>> optional = entityType.getDefaultLootTable();
        if (optional.isEmpty()) return;
        ResourceKey<LootTable> table = optional.get();
        String path = table.identifier().getPath();
        addModifier(table, path, ArsMagicaApi.id(path.replace("entities/", "entities/modify/")).withSuffix("_dismembering"));
    }

    private void addTomeModifier(ResourceKey<LootTable> table) {
        String path = table.identifier().getPath();
        addModifier(table, path, ArsMagicaApi.id(path.replace("chests/", "chests/modify/")).withSuffix("_affinity_tome"));
    }

    private void addModifier(ResourceKey<LootTable> table, String modifier, Identifier identifier) {
        add(modifier, new AddTableLootModifier(new LootItemCondition[]{LootTableIdCondition.builder(table.identifier()).build()}, 1000, ResourceKey.create(table.registryKey(), identifier)));
    }
}
