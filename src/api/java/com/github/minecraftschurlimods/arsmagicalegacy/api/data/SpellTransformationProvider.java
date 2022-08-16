package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellTransformation;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
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
    private final Map<ResourceLocation, SpellTransformation> data = new HashMap<>();
    private final String namespace;
    private final JsonCodecProvider<SpellTransformation> provider;

    public SpellTransformationProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), SpellTransformation.REGISTRY_KEY, data);
    }

    protected abstract void createSpellTransformations();

    @Override
    public void run(CachedOutput pCache) throws IOException {
        createSpellTransformations();
        provider.run(pCache);
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
    public void addSpellTransformation(String id, RuleTest from, BlockState to, ResourceLocation spellPart) {
        data.put(new ResourceLocation(namespace, id), new SpellTransformation(from, to, spellPart));
    }

    /**
     * Adds a new spell transformation.
     *
     * @param id        The id of the transformation.
     * @param from      The block to apply the transformation to.
     * @param to        The block state to transform to.
     * @param spellPart The spell part this transformation is for.
     */
    public void addSpellTransformation(String id, RuleTest from, BlockState to, ISpellPart spellPart) {
        data.put(new ResourceLocation(namespace, id), new SpellTransformation(from, to, spellPart.getId()));
    }
}
