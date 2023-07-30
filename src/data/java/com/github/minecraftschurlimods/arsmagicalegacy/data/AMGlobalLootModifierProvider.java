package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.AddPoolToTableModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.List;

public class AMGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public AMGlobalLootModifierProvider(DataGenerator gen) {
        super(gen, ArsMagicaAPI.MOD_ID);
    }

    @Override
    protected void start() {
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

    private void addTomeModifier(ResourceLocation table) {
        String path = table.getPath();
        add(path.replace("chests/", ""), new AddPoolToTableModifier(
                new LootItemCondition[]{LootTableIdCondition.builder(table).build()},
                new ResourceLocation(ArsMagicaAPI.MOD_ID, "chests/modify/" + path),
                List.of(table)));
    }
}
