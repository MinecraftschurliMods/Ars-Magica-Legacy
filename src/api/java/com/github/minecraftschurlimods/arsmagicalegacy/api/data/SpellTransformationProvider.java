package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Base class for spell part data generators.
 */
public abstract class SpellTransformationProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<ResourceLocation, JsonObject> data = new HashMap<>();
    private final String namespace;
    private final DataGenerator generator;

    public SpellTransformationProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createSpellTransformations();

    @Override
    public void run(CachedOutput pCache) {
        createSpellTransformations();
        for (Map.Entry<ResourceLocation, JsonObject> entry : data.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            JsonObject jsonObject = entry.getValue();
            try {
                DataProvider.saveStable(pCache, jsonObject, generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/spell_transformations/" + resourceLocation.getPath() + ".json"));
            } catch (IOException e) {
                LOGGER.error("Couldn't save spell transformation {}", resourceLocation, e);
            }
        }
    }

    @Override
    public String getName() {
        return "Spell Transformations[" + namespace + "]";
    }

    /**
     * Adds a new spell transformation.
     *
     * @param id        The id of the transformation.
     * @param from      The block to apply the transformation to.
     * @param to        The block state to transform to.
     * @param spellPart The spell part this transformation is for.
     */
    public void addSpellTransformation(ResourceLocation id, RuleTest from, BlockState to, ResourceLocation spellPart) {
        new SpellTransformationBuilder(id, from, to, spellPart).build();
    }

    public class SpellTransformationBuilder {
        private final ResourceLocation id;
        private final RuleTest from;
        private final BlockState to;
        private final ResourceLocation spellPart;

        public SpellTransformationBuilder(ResourceLocation id, RuleTest from, BlockState to, ResourceLocation spellPart) {
            this.id = id;
            this.from = from;
            this.to = to;
            this.spellPart = spellPart;
        }

        /**
         * Builds the spell part data object.
         */
        public void build() {
            data.put(id, serialize());
        }

        JsonObject serialize() {
            JsonObject json = new JsonObject();
            json.add("from", RuleTest.CODEC.encodeStart(JsonOps.INSTANCE, from).getOrThrow(false, s -> {}));
            json.add("to", BlockState.CODEC.encodeStart(JsonOps.INSTANCE, to).getOrThrow(false, s -> {}));
            json.addProperty("spell_part", spellPart.toString());
            return json;
        }
    }
}
