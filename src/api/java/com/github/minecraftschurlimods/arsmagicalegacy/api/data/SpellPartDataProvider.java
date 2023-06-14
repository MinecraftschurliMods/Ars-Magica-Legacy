package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDataBuilder;
import com.github.minecraftschurlimods.easydatagenlib.api.AbstractDataProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

//This can't be an AbstractRegistryDataProvider because SpellPartData isn't accessible from here
public abstract class SpellPartDataProvider extends AbstractDataProvider<SpellPartDataProvider.Builder> {
    protected SpellPartDataProvider(String namespace, DataGenerator generator) {
        super("spell_parts", namespace, generator);
        generate();
    }

    @Override
    public String getName() {
        return "Spell Part Data[" + namespace + "]";
    }

    /**
     * Override this to add your own objects.
     */
    protected abstract void generate();

    /**
     * @param spellPart The id of the new spell part.
     * @param manaCost  The mana cost for the new spell part.
     */
    public Builder builder(ResourceLocation spellPart, float manaCost) {
        return new Builder(spellPart, this, manaCost);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     */
    public Builder builder(RegistryObject<? extends ISpellPart> spellPart, float manaCost) {
        return builder(spellPart.getId(), manaCost);
    }

    /**
     * @param spellPart The id of the new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     */
    public Builder builder(ResourceLocation spellPart, float manaCost, float burnout) {
        return builder(spellPart, manaCost).setBurnout(burnout);
    }

    /**
     * @param spellPart The new spell part.
     * @param manaCost  The mana cost for the new spell part.
     * @param burnout   The burnout for the new spell part.
     */
    public Builder builder(RegistryObject<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return builder(spellPart.getId(), manaCost, burnout);
    }

    public static class Builder extends AbstractDataBuilder<Builder> {
        private static final Logger LOGGER = LogManager.getLogger();
        private final List<ISpellIngredient> recipe = new ArrayList<>();
        private final List<ItemFilter> reagents = new ArrayList<>();
        private final Map<ResourceLocation, Float> affinities = new HashMap<>();
        private final float manaCost;
        private Float burnout;

        public Builder(ResourceLocation id, SpellPartDataProvider provider, float manaCost) {
            super(id, provider);
            this.manaCost = manaCost;
        }

        /**
         * Sets the burnout cost.
         *
         * @param burnout The burnout cost to set.
         */
        public Builder setBurnout(float burnout) {
            this.burnout = burnout;
            return this;
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         */
        public Builder addReagent(Ingredient ingredient) {
            return addReagent(1, ingredient);
        }

        /**
         * Adds a spell reagent.
         *
         * @param ingredient The spell reagent to add.
         */
        public Builder addReagent(int amount, Ingredient ingredient) {
            return addReagent(ItemFilter.exactly(amount).is(ingredient));
        }

        /**
         * Adds a spell reagent.
         *
         * @param stack The spell reagent to add.
         */
        public Builder addReagent(ItemStack stack) {
            return addReagent(ItemFilter.exactly(stack));
        }

        /**
         * Adds a spell reagent.
         *
         * @param filter The spell reagent to add.
         */
        public Builder addReagent(ItemFilter filter) {
            reagents.add(filter);
            return this;
        }

        /**
         * Adds a spell ingredient.
         *
         * @param ingredient The spell ingredient to add.
         */
        public Builder addIngredient(ISpellIngredient ingredient) {
            recipe.add(ingredient);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         */
        public Builder addAffinity(Supplier<Affinity> affinity, float shift) {
            return addAffinity(affinity.get(), shift);
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         */
        public Builder addAffinity(Affinity affinity, float shift) {
            affinities.put(affinity.getId(), shift);
            return this;
        }

        /**
         * Adds an affinity.
         *
         * @param affinity The affinity to add.
         */
        public Builder addAffinity(ResourceLocation affinity, float shift) {
            affinities.put(affinity, shift);
            return this;
        }

        @Override
        protected void toJson(JsonObject jsonObject) {
            jsonObject.addProperty("manaCost", manaCost);
            if (burnout != null) {
                jsonObject.addProperty("burnout", burnout);
            }
            JsonArray reagentsJson = new JsonArray();
            reagents.forEach(e -> reagentsJson.add(ItemFilter.CODEC.encodeStart(JsonOps.INSTANCE, e).getOrThrow(false, LOGGER::error)));
            jsonObject.add("reagents", reagentsJson);
            JsonObject affinitiesJson = new JsonObject();
            affinities.forEach((id, shift) -> affinitiesJson.addProperty(id.toString(), shift));
            jsonObject.add("affinities", affinitiesJson);
            JsonArray recipeJson = new JsonArray();
            recipe.forEach(e -> recipeJson.add(ISpellIngredient.CODEC.encodeStart(JsonOps.INSTANCE, e).getOrThrow(false, s -> {})));
            jsonObject.add("recipe", recipeJson);
        }
    }
}
