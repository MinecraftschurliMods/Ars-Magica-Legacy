package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellIngredient;
import com.github.minecraftschurli.arsmagicalegacy.api.spell.ISpellPart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Data provider for spell part data jsons
 */
public abstract class SpellPartDataProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();
    private final DataGenerator generator;

    public SpellPartDataProvider(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(HashCache pCache) {
        Path path = generator.getOutputFolder();
        createSpellPartData();
        data.forEach((resourceLocation, jsonObject) -> save(pCache, jsonObject, path.resolve("data/" + resourceLocation.getNamespace() + "/spell_parts/" + resourceLocation.getPath() + ".json")));
    }

    protected abstract void createSpellPartData();

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(ResourceLocation spellPart, float manaCost) {
        return new SpellPartDataBuilder(spellPart, manaCost);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(ISpellPart spellPart, float manaCost) {
        return createSpellPartData(spellPart.getRegistryName(), manaCost);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(Supplier<? extends ISpellPart> spellPart, float manaCost) {
        return createSpellPartData(spellPart.get(), manaCost);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(RegistryObject<? extends ISpellPart> spellPart, float manaCost) {
        return createSpellPartData(spellPart.getId(), manaCost);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @param burnout   The spell part burnout.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(ResourceLocation spellPart, float manaCost, float burnout) {
        return new SpellPartDataBuilder(spellPart, manaCost, burnout);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @param burnout   The spell part burnout.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(ISpellPart spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.getRegistryName(), manaCost, burnout);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @param burnout   The spell part burnout.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(Supplier<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.get(), manaCost, burnout);
    }

    /**
     * Creates a new spell part entry.
     *
     * @param spellPart The spell part id.
     * @param manaCost  The spell part mana cost.
     * @param burnout   The spell part burnout.
     * @return A new spell part entry.
     */
    public SpellPartDataBuilder createSpellPartData(RegistryObject<? extends ISpellPart> spellPart, float manaCost, float burnout) {
        return createSpellPartData(spellPart.getId(), manaCost, burnout);
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
            LOGGER.error("Couldn't save spell part data {}", pPath, ioexception);
        }
    }

    public class SpellPartDataBuilder {
        private final ResourceLocation id;
        private final float manaCost;
        private final Float burnout;
        private final List<Either<Ingredient, ItemStack>> reagents = new ArrayList<>();
        private final List<ISpellIngredient> recipe = new ArrayList<>();
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
         * @param ingredient The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(Ingredient ingredient) {
            reagents.add(Either.left(ingredient));
            return this;
        }

        /**
         * Adds a spell reagent.
         * @param stack The spell reagent to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withReagent(ItemStack stack) {
            reagents.add(Either.right(stack));
            return this;
        }

        /**
         * Adds a spell ingredient.
         * @param ingredient The spell ingredient to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withIngredient(ISpellIngredient ingredient) {
            recipe.add(ingredient);
            return this;
        }

        /**
         * Adds an affinity.
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(Supplier<IAffinity> affinity, float shift) {
            return withAffinity(affinity.get(), shift);
        }

        /**
         * Adds an affinity.
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(IAffinity affinity, float shift) {
            affinities.put(affinity.getId(), shift);
            return this;
        }

        /**
         * Adds an affinity.
         * @param affinity The affinity to add.
         * @return This builder, for chaining.
         */
        public SpellPartDataBuilder withAffinity(ResourceLocation affinity, float shift) {
            affinities.put(affinity, shift);
            return this;
        }

        /**
         * Builds the entry.
         */
        public void build() {
            data.put(id, serialize());
        }

        protected JsonObject serialize() {
            var out = new JsonObject();
            out.addProperty("manaCost", manaCost);
            if (burnout != null) {
                out.addProperty("burnout", burnout);
            }
            var arr = new JsonArray();
            reagents.forEach(either -> arr.add(either.map(Ingredient::toJson, stack -> ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, stack).getOrThrow(false, s -> {}))));
            out.add("reagents", arr);
            var arr2 = new JsonObject();
            affinities.forEach((resourceLocation, shift) -> arr2.addProperty(resourceLocation.toString(), shift));
            out.add("affinities", arr2);
            var arr3 = new JsonArray();
            recipe.forEach(ingredient -> arr3.add(ISpellIngredient.CODEC.encodeStart(JsonOps.INSTANCE, ingredient).getOrThrow(false, s -> {})));
            out.add("recipe", arr3);
            return out;
        }
    }
}
