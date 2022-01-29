package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ObeliskFuelProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    protected final DataGenerator generator;
    private final String namespace;
    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();

    public ObeliskFuelProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createFuels();

    @Override
    public void run(HashCache pCache) {
        createFuels();
        data.forEach((resourceLocation, jsonObject) -> save(pCache, jsonObject, generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/obelisk_fuel/" + resourceLocation.getPath() + ".json")));
    }

    @Override
    public String getName() {
        return "Obelisk Fuels";
    }

    /**
     * @param name         The id of the obelisk fuel.
     * @param item         The item to use.
     * @param burntime     How many ticks the ingredient burns.
     * @param valuepertick How much etherium is generated per burning tick.
     */
    protected void forItem(String name, Item item, int burntime, int valuepertick) {
        forIngredient(name, Ingredient.of(item), burntime, valuepertick);
    }

    /**
     * @param name         The id of the obelisk fuel.
     * @param tag          The tag to use.
     * @param burntime     How many ticks the ingredient burns.
     * @param valuepertick How much etherium is generated per burning tick.
     */
    protected void forTag(String name, Tag.Named<Item> tag, int burntime, int valuepertick) {
        forIngredient(name, Ingredient.of(tag), burntime, valuepertick);
    }

    /**
     * @param name         The id of the obelisk fuel.
     * @param ingredient   The ingredient to use.
     * @param burntime     How many ticks the ingredient burns.
     * @param valuepertick How much etherium is generated per burning tick.
     */
    protected void forIngredient(String name, Ingredient ingredient, int burntime, int valuepertick) {
        new ObeliskFuelBuilder(new ResourceLocation(namespace, name)).setIngredient(ingredient).setBurntime(burntime).setValuePerTick(valuepertick).build();
    }

    private static void save(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson(pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());
                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
                    bufferedwriter.write(s);
                }
            }
            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save obelisk fuel {}", pPath, ioexception);
        }
    }

    public class ObeliskFuelBuilder {
        private final ResourceLocation id;
        private Ingredient ingredient;
        private int burntime;
        private int valuepertick;

        public ObeliskFuelBuilder(ResourceLocation id) {
            this.id = id;
        }

        /**
         * Sets the ingredient of this obelisk fuel entry.
         *
         * @param ingredient The ingredient to set.
         * @return This builder, for chaining.
         */
        public ObeliskFuelBuilder setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        /**
         * Sets the burn time of this obelisk fuel entry.
         *
         * @param burntime The burn time to set.
         * @return This builder, for chaining.
         */
        public ObeliskFuelBuilder setBurntime(int burntime) {
            this.burntime = burntime;
            return this;
        }

        /**
         * Sets the etherium generation per tick of this obelisk fuel entry.
         *
         * @param valuepertick The etherium generation per tick to set.
         * @return This builder, for chaining.
         */
        public ObeliskFuelBuilder setValuePerTick(int valuepertick) {
            this.valuepertick = valuepertick;
            return this;
        }

        /**
         * Builds this builder.
         */
        public void build() {
            data.put(id, serialize());
        }

        JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.add("input", ingredient.toJson());
            json.addProperty("burn_time", burntime);
            json.addProperty("etherium_per_tick", valuepertick);
            return json;
        }
    }
}
