package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Base class for spell part data generators.
 */
public abstract class SpellPartDataProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();
    private final String namespace;
    private final DataGenerator generator;

    public SpellPartDataProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createSpellPartData();

    @Override
    public void run(CachedOutput pCache) {
        createSpellPartData();
        for (Map.Entry<ResourceLocation, JsonObject> entry : data.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            JsonObject jsonObject = entry.getValue();
            try {
                DataProvider.saveStable(pCache, jsonObject, generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/spell_parts/" + resourceLocation.getPath() + ".json"));
            } catch (IOException e) {
                LOGGER.error("Couldn't save spell part data {}", resourceLocation, e);
            }
        }
    }

    @Override
    public String getName() {
        return "Spell Part Data[" + namespace + "]";
    }

    /**
     * @param spellPart The id of the new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(ResourceLocation spellPart, float manaCost) {
        return new SpellPartDataBuilder(spellPart, manaCost);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(ISpellPart spellPart, float manaCost) {
        return createSpellPartData(spellPart.getId(), manaCost);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(Supplier<? extends ISpellPart> spellPart, float manaCost) {
        return createSpellPartData(spellPart.get(), manaCost);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(RegistryObject<? extends ISpellPart> spellPart, float manaCost) {
        return createSpellPartData(spellPart.getId(), manaCost);
    }

    /**
     * @param spellPart The id of the new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(ResourceLocation spellPart, float manaCost, float burnout) {
        return new SpellPartDataBuilder(spellPart, manaCost, burnout);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(ISpellPart spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.getId(), manaCost, burnout);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(Supplier<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.get(), manaCost, burnout);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public SpellPartDataBuilder createSpellPartData(RegistryObject<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.getId(), manaCost, burnout);
    }

    public class SpellPartDataBuilder {
        private final ResourceLocation id;
        private final float manaCost;
        private final Float burnout;
        private final List<ISpellIngredient> recipe = new ArrayList<>();
        private final List<ItemFilter> reagents = new ArrayList<>();
        private final Map<ResourceLocation, Float> affinities = new HashMap<>();

        /**
         * Creates a new spell part builder entry.
         *
         * @param id       The spell part id.
         * @param manaCost The spell part mana cost.
         * @param burnout  The spell part burnout.
         */
        public SpellPartDataBuilder(ResourceLocation id, float manaCost, float burnout) {
            this.id = id;
            this.manaCost = manaCost;
            this.burnout = burnout;
        }

        /**
         * Creates a new spell part builder entry.
         *
         * @param id       The spell part id.
         * @param manaCost The spell part mana cost.
         */
        public SpellPartDataBuilder(ResourceLocation id, float manaCost) {
            this.id = id;
            this.manaCost = manaCost;
            this.burnout = null;
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(Ingredient ingredient) {
            return withReagent(1, ingredient);
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(int amount, Ingredient ingredient) {
            return withReagent(ItemFilter.exactly(amount).is(ingredient));
        }

        /**
         * Adds a spell reagent.
         *
         * @param stack The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(ItemStack stack) {
            return withReagent(ItemFilter.exactly(stack));
        }

        /**
         * Adds a spell reagent.
         *
         * @param filter The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(ItemFilter filter) {
            reagents.add(filter);
            return this;
        }

        /**
         * Adds a spell ingredient.
         *
         * @param ingredient The spell ingredient to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withIngredient(ISpellIngredient ingredient) {
            recipe.add(ingredient);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(Supplier<Affinity> affinity, float shift) {
            return withAffinity(affinity.get(), shift);
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(Affinity affinity, float shift) {
            affinities.put(affinity.getId(), shift);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(ResourceLocation affinity, float shift) {
            affinities.put(affinity, shift);
            return this;
        }

        /**
         * Builds the spell part data object.
         */
        public void build() {
            data.put(id, serialize());
        }

        JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.addProperty("manaCost", this.manaCost);
            if (burnout != null) {
                json.addProperty("burnout", this.burnout);
            }
            JsonArray reagentsJson = new JsonArray();
            reagents.forEach(filter -> reagentsJson.add(ItemFilter.CODEC.encodeStart(JsonOps.INSTANCE, filter).getOrThrow(false, LOGGER::error)));
            json.add("reagents", reagentsJson);
            JsonObject affinitiesJson = new JsonObject();
            affinities.forEach((resourceLocation, shift) -> affinitiesJson.addProperty(resourceLocation.toString(), shift));
            json.add("affinities", affinitiesJson);
            JsonArray recipeJson = new JsonArray();
            recipe.forEach(ingredient -> recipeJson.add(ISpellIngredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow(false, s -> {})));
            json.add("recipe", recipeJson);
            return json;
        }
    }
}
