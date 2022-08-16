package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AltarStructureMaterialProvider implements DataProvider {
    protected final DataGenerator generator;
    private final String namespace;
    private final Map<ResourceLocation, AltarCapMaterial> capMaterials = new HashMap<>();
    private final Map<ResourceLocation, AltarStructureMaterial> structureMaterials = new HashMap<>();
    private final JsonCodecProvider<AltarCapMaterial> capProvider;
    private final JsonCodecProvider<AltarStructureMaterial> structureProvider;

    public AltarStructureMaterialProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.generator = generator;
        RegistryOps<JsonElement> registryOps = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
        this.capProvider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, registryOps, AltarCapMaterial.REGISTRY_KEY, capMaterials);
        this.structureProvider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, registryOps, AltarStructureMaterial.REGISTRY_KEY, structureMaterials);
    }

    protected abstract void createStructureMaterials();

    @Override
    public void run(CachedOutput pCache) throws IOException {
        createStructureMaterials();
        structureProvider.run(pCache);
        capProvider.run(pCache);
    }

    @Override
    public String getName() {
        return "Altar Structure Materials[" + namespace + "]";
    }

    /**
     * Adds a new cap material.
     *
     * @param name  The name of the new cap material.
     * @param cap   The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addCapMaterial(String name, Block cap, int power) {
        addCapMaterial(new ResourceLocation(this.namespace, name), cap, power);
    }

    /**
     * Adds a new cap material.
     *
     * @param cap   The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected void addCapMaterial(Block cap, int power) {
        addCapMaterial(new ResourceLocation(this.namespace, getNameFor(cap)), cap, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param name  The name of the new structure material.
     * @param block The block for the new structure material.
     * @param stair The stair block for the new structure material.
     * @param power The power of the new structure material.
     */
    protected void addStructureMaterial(String name, Block block, StairBlock stair, int power) {
        addStructureMaterial(new ResourceLocation(this.namespace, name), block, stair, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param block The block for the new structure material.
     * @param stair The stair block for the new structure material.
     * @param power The power of the new structure material.
     */
    protected void addStructureMaterial(Block block, StairBlock stair, int power) {
        addStructureMaterial(new ResourceLocation(this.namespace, getNameFor(block)), block, stair, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param name  The name of the new structure material.
     * @param blockFamily The block family for the new structure material.
     * @param power The power of the new structure material.
     */
    protected void addStructureMaterial(String name, BlockFamily blockFamily, int power) {
        addStructureMaterial(new ResourceLocation(this.namespace, name), blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power);
    }

    /**
     * Adds a new structure material.
     *
     * @param blockFamily The block family for the new structure material.
     * @param power The power of the new structure material.
     */
    protected void addStructureMaterial(BlockFamily blockFamily, int power) {
        addStructureMaterial(new ResourceLocation(this.namespace, getNameFor(blockFamily.getBaseBlock())), blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power);
    }

    private String getNameFor(Block baseBlock) {
        return ForgeRegistries.BLOCKS.getKey(baseBlock).getPath();
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
     * @param id    The id of the new structure material.
     * @param block The block for the new structure material.
     * @param stair The stair block for the new structure material.
     * @param power The power of the new structure material.
     */
    protected void addStructureMaterial(ResourceLocation id, Block block, StairBlock stair, int power) {
        structureMaterials.put(id, new AltarStructureMaterial(block, stair, power));
    }
}
