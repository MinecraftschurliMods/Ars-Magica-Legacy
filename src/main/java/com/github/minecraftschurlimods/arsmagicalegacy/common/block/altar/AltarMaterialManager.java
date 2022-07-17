package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class AltarMaterialManager {
    private static final Lazy<AltarMaterialManager> INSTANCE = Lazy.concurrentOf(AltarMaterialManager::new);

    private AltarMaterialManager() {}

    /**
     * @return The only instance of this class.
     */
    public static AltarMaterialManager instance() {
        return INSTANCE.get();
    }

    /**
     * @param block The block to get the structure material of.
     * @return An optional containing the structure material of the given block, or an empty optional if no structure material was found.
     */
    public Optional<AltarStructureMaterial> getStructureMaterial(Block block) {
        return getStructureRegistry().getValues().stream().filter(mat -> mat.block() == block || mat.stair() == block).findFirst();
    }

    /**
     * @param block The block to get the cap material of.
     * @return An optional containing the cap material of the given block, or an empty optional if no cap material was found.
     */
    public Optional<AltarCapMaterial> getCapMaterial(Block block) {
        return getCapRegistry().getValues().stream().filter(mat -> mat.cap() == block).findFirst();
    }

    /**
     * @param r The random value to use.
     * @return A random structure material.
     */
    @Nullable
    public AltarStructureMaterial getRandomStructureMaterial(int r) {
        int size = getStructureRegistry().getValues().size();
        return size > 0 ? getStructureRegistry().getValues().toArray(AltarStructureMaterial[]::new)[r % size] : null;
    }

    /**
     * @param r The random value to use.
     * @return A random cap material.
     */
    @Nullable
    public AltarCapMaterial getRandomCapMaterial(int r) {
        int size = getCapRegistry().getValues().size();
        return size > 0 ? getCapRegistry().getValues().toArray(AltarCapMaterial[]::new)[r % size] : null;
    }

    /**
     * @param structureMaterial The structure material to get the id for.
     * @return The id of the given structure material.
     */
    @Nullable
    public ResourceLocation getId(AltarStructureMaterial structureMaterial) {
        return getStructureRegistry().getKey(structureMaterial);
    }

    /**
     * @param capMaterial The cap material to get the id for.
     * @return The id of the given cap material.
     */
    @Nullable
    public ResourceLocation getId(AltarCapMaterial capMaterial) {
        return getCapRegistry().getKey(capMaterial);
    }

    /**
     * @param location The id to get the structure material for.
     * @return The structure material for the given id.
     */
    @Nullable
    public AltarStructureMaterial getStructureMaterial(ResourceLocation location) {
        return getStructureRegistry().getValue(location);
    }

    /**
     * @param location The id to get the cap material for.
     * @return The cap material for the given id.
     */
    @Nullable
    public AltarCapMaterial getCapMaterial(ResourceLocation location) {
        return getCapRegistry().getValue(location);
    }

    private static IForgeRegistry<AltarStructureMaterial> getStructureRegistry() {
        return AMRegistries.ALTAR_STRUCTURE_MATERIAL_REGISTRY.get();
    }

    private static IForgeRegistry<AltarCapMaterial> getCapRegistry() {
        return AMRegistries.ALTAR_CAP_MATERIAL_REGISTRY.get();
    }
}
