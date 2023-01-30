package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import org.apache.commons.lang3.SerializationException;

public abstract class AltarStructureMaterialProvider extends AbstractDataProvider<AltarStructureMaterialProvider.Builder> {
    protected AltarStructureMaterialProvider(String namespace, DataGenerator generator) {
        super("altar/structure", namespace, generator);
    }

    @Override
    public String getName() {
        return "Altar Structure Materials[" + namespace + "]";
    }

    /**
     * Adds a new structure material.
     *
     * @param name       The name of the new cap material.
     * @param block      The block for the new cap material.
     * @param stairBlock The stair block for the new cap material.
     * @param power      The power of the new cap material.
     */
    protected Builder builder(String name, Block block, StairBlock stairBlock, int power) {
        return new Builder(new ResourceLocation(namespace, name)).setBlock(block).setStairBlock(stairBlock).setPower(power);
    }

    protected static class Builder extends AbstractDataBuilder<Builder> {
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
        protected JsonObject toJson() {
            if (block == null) throw new SerializationException("A structure material needs a block!");
            if (stairBlock == null) throw new SerializationException("A structure material needs a stair block!");
            if (power <= 0) throw new SerializationException("A structure material needs a power greater than 0!");
            return AltarStructureMaterial.CODEC.encodeStart(JsonOps.INSTANCE, new AltarStructureMaterial(block, stairBlock, power)).getOrThrow(false, LOGGER::warn).getAsJsonObject();
        }
    }
}
