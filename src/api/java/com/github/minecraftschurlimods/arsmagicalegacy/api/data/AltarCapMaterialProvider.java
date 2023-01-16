package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.SerializationException;

public abstract class AltarCapMaterialProvider extends AbstractDataProvider<AltarCapMaterialProvider.Builder> {
    protected AltarCapMaterialProvider(String namespace, DataGenerator generator) {
        super("altar/cap", namespace, generator);
    }

    @Override
    public String getName() {
        return "Altar Cap Materials[" + namespace + "]";
    }

    /**
     * Adds a new cap material.
     *
     * @param name  The name of the new cap material.
     * @param block The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected Builder builder(String name, Block block, int power) {
        return new Builder(new ResourceLocation(namespace, name)).setBlock(block).setPower(power);
    }

    protected static class Builder extends AbstractDataBuilder {
        private Block block;
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
            if (block == null) throw new SerializationException("A cap material needs a block!");
            if (power <= 0) throw new SerializationException("A cap material needs a power greater than 0!");
            return AltarCapMaterial.CODEC.encodeStart(JsonOps.INSTANCE, new AltarCapMaterial(block, power)).getOrThrow(false, LOGGER::warn).getAsJsonObject();
        }
    }
}
