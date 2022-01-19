package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

class AMPrefabSpellProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().create();
    private final DataGenerator generator;

    public AMPrefabSpellProvider(final DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(final HashCache cache) throws IOException {
        Path path = generator.getOutputFolder();
        Map<String, PrefabSpellManager.PrefabSpell> prefabSpells = new HashMap<>();
        for (Map.Entry<String, PrefabSpellManager.PrefabSpell> entry : prefabSpells.entrySet()) {
            String name = entry.getKey();
            PrefabSpellManager.PrefabSpell prefabSpell = entry.getValue();
            DataProvider.save(GSON, cache, PrefabSpellManager.PrefabSpell.CODEC.encodeStart(JsonOps.INSTANCE, prefabSpell).getOrThrow(false, LogManager.getLogger()::warn), path.resolve("data/" + ArsMagicaAPI.MOD_ID + "/prefab_spells/" + name + ".json"));
        }
    }

    @Override
    public String getName() {
        return "Prefab Spell Provider";
    }
}
