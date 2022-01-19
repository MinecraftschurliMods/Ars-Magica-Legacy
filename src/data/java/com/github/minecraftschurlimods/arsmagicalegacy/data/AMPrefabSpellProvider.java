package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ShapeGroup;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellStack;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMSpellParts;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.PrefabSpellManager;
import com.github.minecraftschurlimods.arsmagicalegacy.common.spell.Spell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
        addPrefabSpells(prefabSpells::put);
        for (Map.Entry<String, PrefabSpellManager.PrefabSpell> entry : prefabSpells.entrySet()) {
            String name = entry.getKey();
            PrefabSpellManager.PrefabSpell prefabSpell = entry.getValue();
            DataProvider.save(GSON, cache, PrefabSpellManager.PrefabSpell.CODEC.encodeStart(JsonOps.INSTANCE, prefabSpell).getOrThrow(false, LogManager.getLogger()::warn), path.resolve("data/" + ArsMagicaAPI.MOD_ID + "/prefab_spells/" + name + ".json"));
        }
    }

    private void addPrefabSpells(final BiConsumer<String, PrefabSpellManager.PrefabSpell> put) {
        put.accept("test_spell1", new PrefabSpellManager.PrefabSpell("Test Spell 1", Spell.of(SpellStack.of(AMSpellParts.DIG.get()), ShapeGroup.of(AMSpellParts.TOUCH.get()), ShapeGroup.of(AMSpellParts.PROJECTILE.get())), new ResourceLocation(ArsMagicaAPI.MOD_ID, "air-burst-air-1")));
        put.accept("test_spell2", new PrefabSpellManager.PrefabSpell("Test Spell 2", Spell.of(SpellStack.of(AMSpellParts.RIFT.get()), ShapeGroup.of(AMSpellParts.SELF.get())), new ResourceLocation(ArsMagicaAPI.MOD_ID, "runes-blue-1")));
        put.accept("test_spell3", new PrefabSpellManager.PrefabSpell("Test Spell 3", Spell.of(SpellStack.of(AMSpellParts.HEAL.get()), ShapeGroup.of(AMSpellParts.SELF.get()), ShapeGroup.of(AMSpellParts.TOUCH.get()), ShapeGroup.of(AMSpellParts.PROJECTILE.get())), new ResourceLocation(ArsMagicaAPI.MOD_ID, "heal-royal-3")));
    }

    @Override
    public String getName() {
        return "Prefab Spell Provider";
    }
}
