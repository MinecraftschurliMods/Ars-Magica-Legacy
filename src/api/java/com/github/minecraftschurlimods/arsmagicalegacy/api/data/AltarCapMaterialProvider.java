package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.SerializationException;

import java.util.Objects;

public abstract class AltarCapMaterialProvider extends AbstractDataProvider<AltarCapMaterial, AltarCapMaterialProvider.Builder> {
    protected AltarCapMaterialProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(AltarCapMaterial.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
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

    /**
     * Adds a new cap material.
     *
     * @param block The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected Builder builder(Block block, int power) {
        return builder(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(), block, power);
    }

    protected static class Builder extends AbstractDataBuilder<AltarCapMaterial, Builder> {
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
        protected AltarCapMaterial build() {
            if (block == null) throw new SerializationException("A cap material needs a block!");
            if (power <= 0) throw new SerializationException("A cap material needs a power greater than 0!");
            return new AltarCapMaterial(block, power);
        }
    }
}
