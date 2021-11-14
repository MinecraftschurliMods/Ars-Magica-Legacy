package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarStructureMaterial;
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
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson   GSON   = (new GsonBuilder()).setPrettyPrinting().create();

    private   final Map<ResourceLocation, AltarStructureMaterial> structureMaterials = new HashMap<>();
    private   final Map<ResourceLocation, AltarCapMaterial>       capMaterials       = new HashMap<>();
    private   final String                                        namespace;
    protected final DataGenerator                                 generator;

    public AltarStructureMaterialProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

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
        return "StructureMaterialProvider["+this.namespace+"]";
    }

    protected abstract void createStructureMaterials();

    protected void addCapMaterial(String name, Block cap, int power) {
        addCapMaterial(new ResourceLocation(this.namespace, name), cap, power);
    }

    protected void addStructureMaterial(String name, Block block, StairBlock stair, int power) {
        addStructureMaterial(new ResourceLocation(this.namespace, name), block, stair, power);
    }

    protected void addCapMaterial(ResourceLocation id, Block cap, int power) {
        capMaterials.put(id, new AltarCapMaterial(cap, power));
    }

    protected void addStructureMaterial(ResourceLocation id, Block block, StairBlock stair, int power) {
        structureMaterials.put(id, new AltarStructureMaterial(block, stair, power));
    }
}
