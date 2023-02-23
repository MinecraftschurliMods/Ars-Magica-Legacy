package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.SerializationException;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class SpellPartDataProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final String namespace;
    private final DataGenerator generator;

    protected SpellPartDataProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void generate(Consumer<Builder> consumer);

    @Override
    public void run(CachedOutput cache) {
        Set<ResourceLocation> ids = new HashSet<>();
        generate(consumer -> {
            if (!ids.add(consumer.id)) throw new IllegalStateException("Duplicate datagenned object " + consumer.id);
            try {
                DataProvider.saveStable(cache, consumer.toJson(), generator.getOutputFolder().resolve("data/" + consumer.id.getNamespace() + "/spell_parts/" + consumer.id.getPath() + ".json"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
    public Builder builder(ResourceLocation spellPart, float manaCost) {
        return new Builder(spellPart).setManaCost(manaCost);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @return A new spell part data object.
     */
    public Builder builder(RegistryObject<? extends ISpellPart> spellPart, float manaCost) {
        return builder(spellPart.getId(), manaCost);
    }

    /**
     * @param spellPart The id of the new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public Builder builder(ResourceLocation spellPart, float manaCost, float burnout) {
        return builder(spellPart, manaCost).setBurnout(burnout);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     * @return A new spell part data object.
     */
    public Builder builder(RegistryObject<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return builder(spellPart.getId(), manaCost, burnout);
    }

    public static class Builder {
        private final ResourceLocation id;
        private Float manaCost;
        private Float burnout;
        private final List<ISpellIngredient> recipe = new ArrayList<>();
        private final List<ItemFilter> reagents = new ArrayList<>();
        private final Map<ResourceLocation, Float> affinities = new HashMap<>();

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        /**
         * Sets the mana cost.
         *
         * @param manaCost The mana cost to set.
         * @return This builder, for chaining.
         */
        public Builder setManaCost(float manaCost) {
            this.manaCost = manaCost;
            return this;
        }

        /**
         * Sets the burnout cost.
         *
         * @param burnout The burnout cost to set.
         * @return This builder, for chaining.
         */
        public Builder setBurnout(float burnout) {
            this.burnout = burnout;
            return this;
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         * @return This builder, for chaining.
         */
        public Builder addReagent(Ingredient ingredient) {
            return addReagent(1, ingredient);
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         * @return This builder, for chaining.
         */
        public Builder addReagent(int amount, Ingredient ingredient) {
            return addReagent(ItemFilter.exactly(amount).is(ingredient));
        }

        /**
         * Adds a spell reagent.
         *
         * @param stack The spell reagent to add.
         * @return This builder, for chaining.
         */
        public Builder addReagent(ItemStack stack) {
            return addReagent(ItemFilter.exactly(stack));
        }

        /**
         * Adds a spell reagent.
         *
         * @param filter The spell reagent to add.
         * @return This builder, for chaining.
         */
        public Builder addReagent(ItemFilter filter) {
            reagents.add(filter);
            return this;
        }

        /**
         * Adds a spell ingredient.
         *
         * @param ingredient The spell ingredient to add.
         * @return This builder, for chaining.
         */
        public Builder addIngredient(ISpellIngredient ingredient) {
            recipe.add(ingredient);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public Builder addAffinity(Supplier<Affinity> affinity, float shift) {
            return addAffinity(affinity.get(), shift);
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public Builder addAffinity(Affinity affinity, float shift) {
            affinities.put(affinity.getId(), shift);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public Builder addAffinity(ResourceLocation affinity, float shift) {
            affinities.put(affinity, shift);
            return this;
        }

        public void build(Consumer<Builder> consumer) {
            consumer.accept(this);
        }

        private JsonObject toJson() {
            JsonObject json = new JsonObject();
            if (manaCost == null) throw new SerializationException("A spell part needs a mana cost!");
            json.addProperty("manaCost", this.manaCost);
            if (burnout != null) {
                json.addProperty("burnout", this.burnout);
            }
            JsonArray reagentsJson = new JsonArray();
            reagents.forEach(filter -> reagentsJson.add(ItemFilter.CODEC.encodeStart(JsonOps.INSTANCE, filter).getOrThrow(false, LOGGER::error)));
            json.add("reagents", reagentsJson);
            JsonObject affinitiesJson = new JsonObject();
            affinities.forEach((id, shift) -> affinitiesJson.addProperty(id.toString(), shift));
            json.add("affinities", affinitiesJson);
            JsonArray recipeJson = new JsonArray();
            recipe.forEach(ingredient -> recipeJson.add(ISpellIngredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow(false, s -> {})));
            json.add("recipe", recipeJson);
            return json;
        }
    }
}
