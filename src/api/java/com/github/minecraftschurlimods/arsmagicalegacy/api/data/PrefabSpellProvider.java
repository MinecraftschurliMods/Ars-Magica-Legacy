package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.network.chat.Component;
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
    private final Map<ResourceLocation, JsonElement> data = new HashMap<>();

    public PrefabSpellProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createPrefabSpells();

    @Override
    public void run(CachedOutput pCache) {
        createPrefabSpells();
        for (Map.Entry<ResourceLocation, JsonElement> entry : data.entrySet()) {
            ResourceLocation id = entry.getKey();
            JsonElement spell = entry.getValue();
            try {
                DataProvider.saveStable(pCache, spell, generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/prefab_spells/" + id.getPath() + ".json"));
            } catch (IOException e) {
                LOGGER.error("Couldn't save prefab spell {}", id, e);
            }
        }
    }

    @Override
    public String getName() {
        return "Prefab Spells[" + namespace + "]";
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The name of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public void addPrefabSpell(String id, Component name, ResourceLocation icon, ISpell spell) {
        new PrefabSpellBuilder(new ResourceLocation(this.namespace, id)).withSpell(spell).withIcon(icon).withName(name).build();
    }

    /**
     * Adds a new prefab spell.
     *
     * @param id    The id of the prefab spell.
     * @param name  The name of the prefab spell.
     * @param icon  The icon of the prefab spell.
     * @param spell The spell of the prefab spell.
     */
    public void addPrefabSpell(String id, String name, ResourceLocation icon, ISpell spell) {
        new PrefabSpellBuilder(new ResourceLocation(this.namespace, id)).withSpell(spell).withIcon(icon).withName(name).build();
    }

    public class PrefabSpellBuilder {
        private final ResourceLocation id;
        private ISpell           spell;
        private Component        name;
        private ResourceLocation icon;

        public PrefabSpellBuilder(ResourceLocation id) {
            this.id = id;
        }

        public PrefabSpellBuilder withSpell(ISpell spell) {
            this.spell = spell;
            return this;
        }

        public PrefabSpellBuilder withName(String name) {
            return withName(Component.nullToEmpty(name));
        }

        public PrefabSpellBuilder withName(Component name) {
            this.name = name;
            return this;
        }

        public PrefabSpellBuilder withIcon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }

        public void build() {
            JsonObject json = new JsonObject();
            json.add("name", Component.Serializer.toJsonTree(name));
            json.addProperty("icon", icon.toString());
            json.add("spell", ISpell.CODEC.encodeStart(JsonOps.INSTANCE, spell).getOrThrow(false, LOGGER::warn));
            data.put(id, json);
        }
    }
}
