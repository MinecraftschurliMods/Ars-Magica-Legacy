package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public final class AltarMaterialManager {
    private static final Lazy<AltarMaterialManager> INSTANCE = Lazy.concurrentOf(AltarMaterialManager::new);
    private final Logger logger = LogManager.getLogger();
    public final CodecDataManager<AltarStructureMaterial> structure = new CodecDataManager<>("altar/structure", AltarStructureMaterial.CODEC, logger).subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);
    public final CodecDataManager<AltarCapMaterial> cap = new CodecDataManager<>("altar/cap", AltarCapMaterial.CODEC, logger).subscribeAsSyncable(ArsMagicaLegacy.NETWORK_HANDLER);

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
        return structure.values().stream().filter(mat -> mat.block() == block || mat.stair() == block).findFirst();
    }

    /**
     * @param block The block to get the cap material of.
     * @return An optional containing the cap material of the given block, or an empty optional if no cap material was found.
     */
    public Optional<AltarCapMaterial> getCapMaterial(Block block) {
        return cap.values().stream().filter(mat -> mat.cap() == block).findFirst();
    }

    /**
     * @param r The random value to use.
     * @return A random structure material.
     */
    @Nullable
    public AltarStructureMaterial getRandomStructureMaterial(int r) {
        return structure.size() > 0 ? structure.values().toArray(AltarStructureMaterial[]::new)[r % structure.size()] : null;
    }

    /**
     * @param r The random value to use.
     * @return A random cap material.
     */
    @Nullable
    public AltarCapMaterial getRandomCapMaterial(int r) {
        return cap.size() > 0 ? cap.values().toArray(AltarCapMaterial[]::new)[r % cap.size()] : null;
    }

    /**
     * @param structureMaterial The structure material to get the id for.
     * @return The id of the given structure material.
     */
    @Nullable
    public ResourceLocation getId(AltarStructureMaterial structureMaterial) {
        return structure.entrySet().stream().filter(entry -> entry.getValue().equals(structureMaterial)).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    /**
     * @param capMaterial The cap material to get the id for.
     * @return The id of the given cap material.
     */
    @Nullable
    public ResourceLocation getId(AltarCapMaterial capMaterial) {
        return cap.entrySet().stream().filter(entry -> entry.getValue().equals(capMaterial)).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    /**
     * @param location The id to get the structure material for.
     * @return The structure material for the given id.
     */
    @Nullable
    public AltarStructureMaterial getStructureMaterial(ResourceLocation location) {
        return structure.get(location);
    }

    /**
     * @param location The id to get the cap material for.
     * @return The cap material for the given id.
     */
    @Nullable
    public AltarCapMaterial getCapMaterial(ResourceLocation location) {
        return cap.get(location);
    }
}
