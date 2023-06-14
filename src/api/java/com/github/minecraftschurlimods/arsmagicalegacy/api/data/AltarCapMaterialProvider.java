package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.google.gson.JsonElement;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public abstract class AltarCapMaterialProvider extends AbstractRegistryDataProvider<AltarCapMaterial, AltarCapMaterialProvider.Builder> {
    protected AltarCapMaterialProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(AltarCapMaterial.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Altar Cap Materials[" + namespace + "]";
    }

    /**
     * @param id    The id of the new cap material.
     * @param block The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected Builder builder(ResourceLocation id, Block block, int power) {
        return new Builder(id, this, block, power);
    }

    /**
     * @param id    The id of the new cap material.
     * @param block The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected Builder builder(String id, Block block, int power) {
        return builder(new ResourceLocation(namespace, id), block, power);
    }

    /**
     * @param block The block for the new cap material.
     * @param power The power of the new cap material.
     */
    protected Builder builder(Block block, int power) {
        return builder(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(), block, power);
    }

    protected static class Builder extends AbstractRegistryDataProvider.Builder<AltarCapMaterial, Builder> {
        private final Block block;
        private final int power;

        public Builder(ResourceLocation id, AltarCapMaterialProvider provider, Block block, int power) {
            super(id, provider, AltarCapMaterial.CODEC);
            this.block = block;
            this.power = power;
        }

        @Override
        protected AltarCapMaterial get() {
            return new AltarCapMaterial(block, power);
        }
    }
}
