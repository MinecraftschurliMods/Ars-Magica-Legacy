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
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class AltarStructureMaterialProvider extends AbstractDataProvider<AltarStructureMaterial, AltarStructureMaterialProvider.Builder> {
    protected AltarStructureMaterialProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(AltarStructureMaterial.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Altar Structure Materials[" + namespace + "]";
    }

    /**
     * Adds a new structure material.
     *
     * @param name       The name of the new structure material.
     * @param block      The block for the new structure material.
     * @param stairBlock The stair block for the new structure material.
     * @param power      The power of the new structure material.
     */
    protected Builder builder(String name, Block block, StairBlock stairBlock, int power) {
        return new Builder(new ResourceLocation(namespace, name)).setBlock(block).setStairBlock(stairBlock).setPower(power);
    }

    /**
     * Adds a new structure material.
     *
     * @param block      The block for the new structure material.
     * @param stairBlock The stair block for the new structure material.
     * @param power      The power of the new structure material.
     */
    protected Builder builder(Block block, StairBlock stairBlock, int power) {
        return builder(inferName(block), block, stairBlock, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param blockFamily The block family for the new structure material.
     * @param power       The power of the new structure material.
     */
    protected Builder builder(BlockFamily blockFamily, int power) {
        return builder(inferName(blockFamily.getBaseBlock()), blockFamily, power);
    }

    /**
     * Adds a new structure material.
     *
     * @param name        The name of the new structure material.
     * @param blockFamily The block family for the new structure material.
     * @param power       The power of the new structure material.
     */
    protected Builder builder(String name, BlockFamily blockFamily, int power) {
        return builder(name, blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power);
    }

    @NotNull
    private static String inferName(Block block) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();
    }

    protected static class Builder extends AbstractDataBuilder<AltarStructureMaterial, Builder> {
        private Block block;
        private StairBlock stairBlock;
        private int power;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * Sets the block to use.
         *
         * @param block The block to use.
         * @return This builder, for chaining.
         */
        public Builder setBlock(Block block) {
            this.block = block;
            return this;
        }

        /**
         * Sets the stair block to use.
         *
         * @param stairBlock The stair block to use.
         * @return This builder, for chaining.
         */
        public Builder setStairBlock(StairBlock stairBlock) {
            this.stairBlock = stairBlock;
            return this;
        }

        /**
         * Sets the power to use.
         *
         * @param power The power to use.
         * @return This builder, for chaining.
         */
        public Builder setPower(int power) {
            this.power = power;
            return this;
        }

        @Override
        protected AltarStructureMaterial build() {
            if (block == null) throw new SerializationException("A structure material needs a block!");
            if (stairBlock == null) throw new SerializationException("A structure material needs a stair block!");
            if (power <= 0) throw new SerializationException("A structure material needs a power greater than 0!");
            return new AltarStructureMaterial(block, stairBlock, power);
        }
    }
}
