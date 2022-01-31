package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.IPrefabSpell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class PrefabSpellProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final String namespace;
    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();

    public PrefabSpellProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createPrefabSpells();

    @Override
    public void run(HashCache pCache) throws IOException {
        createPrefabSpells();
        for (Map.Entry<ResourceLocation, JsonObject> entry : data.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonObject spell = entry.getValue();
            DataProvider.save(GSON, pCache, spell, generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/prefab_spells/" + id.getPath() + ".json"));
        }
    }

    @Override
    public String getName() {
        return "Prefab Spells";
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The display name of the prefab spell.
     * @param spell The prefab spell.
     */
    public void addPrefabSpell(String id, String name, IPrefabSpell spell) {
        new PrefabSpellBuilder(new ResourceLocation(this.namespace, id), name, spell).build();
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param spell The prefab spell.
     */
    public void addPrefabSpell(String id, IPrefabSpell spell) {
        new PrefabSpellBuilder(new ResourceLocation(this.namespace, id), spell).build();
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The display name of the prefab spell.
     * @param spell The prefab spell.
     */
    public void addPrefabSpell(ResourceLocation id, String name, IPrefabSpell spell) {
        new PrefabSpellBuilder(id, name, spell).build();
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param spell The prefab spell.
     */
    public void addPrefabSpell(ResourceLocation id, IPrefabSpell spell) {
        new PrefabSpellBuilder(id, spell).build();
    }

    public class PrefabSpellBuilder {
        private final ResourceLocation id;
        private final IPrefabSpell spell;
        private String name = null;

        public PrefabSpellBuilder(ResourceLocation id, IPrefabSpell spell) {
            this.id = id;
            this.spell = spell;
        }

        public PrefabSpellBuilder(ResourceLocation id, String name, IPrefabSpell spell) {
            this.id = id;
            this.name = name;
            this.spell = spell;
        }

        public void build() {
            data.put(id, serialize());
        }

        JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.add("spell", this.spell.getEncodedSpell().getOrThrow(false, LOGGER::warn));
            if (name != null) {
                json.addProperty("name", this.name);
            }
            return json;
        }
    }
}
