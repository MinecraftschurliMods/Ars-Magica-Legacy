package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.google.gson.JsonElement;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public abstract class AltarStructureMaterialProvider extends AbstractRegistryDataProvider<AltarStructureMaterial, AltarStructureMaterialProvider.Builder> {
    protected AltarStructureMaterialProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(AltarStructureMaterial.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Altar Structure Materials[" + namespace + "]";
    }

    /**
     * @param name       The name of the new structure material.
     * @param block      The block for the new structure material.
     * @param stairBlock The stair block for the new structure material.
     * @param power      The power of the new structure material.
     */
    protected Builder builder(String name, Block block, StairBlock stairBlock, int power) {
        return new Builder(new ResourceLocation(namespace, name), this, block, stairBlock, power);
    }

    /**
     * @param block      The block for the new structure material.
     * @param stairBlock The stair block for the new structure material.
     * @param power      The power of the new structure material.
     */
    protected Builder builder(Block block, StairBlock stairBlock, int power) {
        return builder(makeName(block), block, stairBlock, power);
    }

    /**
     * @param name        The name of the new structure material.
     * @param blockFamily The block family for the new structure material.
     * @param power       The power of the new structure material.
     */
    protected Builder builder(String name, BlockFamily blockFamily, int power) {
        return builder(name, blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power);
    }

    /**
     * @param blockFamily The block family for the new structure material.
     * @param power       The power of the new structure material.
     */
    protected Builder builder(BlockFamily blockFamily, int power) {
        return builder(makeName(blockFamily.getBaseBlock()), blockFamily, power);
    }

    private static String makeName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    protected static class Builder extends AbstractRegistryDataProvider.Builder<AltarStructureMaterial, Builder> {
        private final Block block;
        private final StairBlock stairBlock;
        private final int power;

        public Builder(ResourceLocation id, AltarStructureMaterialProvider provider, Block block, StairBlock stairBlock, int power) {
            super(id, provider, AltarStructureMaterial.CODEC);
            this.block = block;
            this.stairBlock = stairBlock;
            this.power = power;
        }

        @Override
        protected AltarStructureMaterial get() {
            return new AltarStructureMaterial(block, stairBlock, power);
        }
    }
}
