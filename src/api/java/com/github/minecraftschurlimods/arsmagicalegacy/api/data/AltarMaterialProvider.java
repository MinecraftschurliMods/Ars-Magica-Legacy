package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import net.minecraft.core.Registry;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.ForgeRegistries;

public abstract sealed class AltarMaterialProvider<T> extends AbstractRegistryDataProvider<T> {

    public AltarMaterialProvider(ResourceKey<? extends Registry<T>> registryKey, String namespace) {
        super(registryKey, namespace);
    }

    public static abstract non-sealed class Structure extends AltarMaterialProvider<AltarStructureMaterial> {
        public Structure(String namespace) {
            super(AltarStructureMaterial.REGISTRY_KEY, namespace);
        }

        /**
         * Adds a new structure material.
         *
         * @param blockFamily The block family for the new structure material.
         * @param power The power of the new structure material.
         */
        protected void addStructureMaterial(BlockFamily blockFamily, int power) {
            addStructureMaterial(blockFamily.getBaseBlock(), (StairBlock) blockFamily.get(BlockFamily.Variant.STAIRS), power);
        }

        /**
         * Adds a new structure material.
         *
         * @param block The block for the new structure material.
         * @param stair The stair block for the new structure material.
         * @param power The power of the new structure material.
         */
        protected void addStructureMaterial(Block block, StairBlock stair, int power) {
            addStructureMaterial(getNameFor(block), block, stair, power);
        }

        /**
         * Adds a new structure material.
         *
         * @param id      The id of the new structure material.
         * @param block   The block for the new structure material.
         * @param stair   The stair block for the new structure material.
         * @param power   The power of the new structure material.
         */
        protected void addStructureMaterial(String id, Block block, StairBlock stair, int power) {
            add(id, new AltarStructureMaterial(block, stair, power));
        }
    }

    public static abstract non-sealed class Cap extends AltarMaterialProvider<AltarCapMaterial> {
        public Cap(String namespace) {
            super(AltarCapMaterial.REGISTRY_KEY, namespace);
        }

        /**
         * Adds a new cap material.
         *
         * @param cap   The block for the new cap material.
         * @param power The power of the new cap material.
         */
        protected void addCapMaterial(Block cap, int power) {
            addCapMaterial(getNameFor(cap), cap, power);
        }

        /**
         * Adds a new cap material.
         *
         * @param id      The id of the new cap material.
         * @param cap     The block for the new cap material.
         * @param power   The power of the new cap material.
         */
        protected void addCapMaterial(String id, Block cap, int power) {
            add(id, new AltarCapMaterial(cap, power));
        }
    }

    String getNameFor(Block baseBlock) {
        return ForgeRegistries.BLOCKS.getKey(baseBlock).getPath();
    }
}
