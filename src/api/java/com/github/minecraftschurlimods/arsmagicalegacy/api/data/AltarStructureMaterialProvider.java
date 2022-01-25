package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class AltarStructureMaterialProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    protected final DataGenerator generator;
    private final String namespace;
    private final Map<ResourceLocation, AltarCapMaterial> capMaterials = new HashMap<>();
    private final Map<ResourceLocation, AltarStructureMaterial> structureMaterials = new HashMap<>();

    public AltarStructureMaterialProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createStructureMaterials();

    @Override
    public void run(HashCache pCache) throws IOException {
        createStructureMaterials();
        Path path = generator.getOutputFolder();
        for (Map.Entry<ResourceLocation, AltarStructureMaterial> entry : structureMaterials.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            AltarStructureMaterial altarStructureMaterial = entry.getValue();
            DataProvider.save(GSON, pCache, AltarStructureMaterial.CODEC.encodeStart(JsonOps.INSTANCE, altarStructureMaterial).getOrThrow(false, LOGGER::warn), path.resolve("data/" + resourceLocation.getNamespace() + "/altar/structure/" + resourceLocation.getPath() + ".json"));
        }
        for (Map.Entry<ResourceLocation, AltarCapMaterial> entry : capMaterials.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            AltarCapMaterial altarStructureMaterial = entry.getValue();
            DataProvider.save(GSON, pCache, AltarCapMaterial.CODEC.encodeStart(JsonOps.INSTANCE, altarStructureMaterial).getOrThrow(false, LOGGER::warn), path.resolve("data/" + resourceLocation.getNamespace() + "/altar/cap/" + resourceLocation.getPath() + ".json"));
        }
    }

    @Override
    public String getName() {
        return "Altar Structure Materials";
    }

    /**
     * Adds a new cap material.
     *
     * @param name  The name of the new cap material.
     * @param cap   The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addCapMaterial(String name, Block cap, int power) {
        addCapMaterial(new ResourceLocation(namespace, name), cap, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param name  The name of the new cap material.
     * @param block The block for the new cap material.
     * @param stair The stair block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addStructureMaterial(String name, Block block, StairBlock stair, int power) {
        addStructureMaterial(new ResourceLocation(namespace, name), block, stair, power);
    }

    /**
     * Adds a new cap material.
     *
     * @param id    The id of the new cap material.
     * @param cap   The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addCapMaterial(ResourceLocation id, Block cap, int power) {
        capMaterials.put(id, new AltarCapMaterial(cap, power));
    }

    /**
     * Adds a new structure material.
     *
     * @param id    The id of the new cap material.
     * @param block The block for the new cap material.
     * @param stair The stair block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addStructureMaterial(ResourceLocation id, Block block, StairBlock stair, int power) {
        structureMaterials.put(id, new AltarStructureMaterial(block, stair, power));
    }
}
