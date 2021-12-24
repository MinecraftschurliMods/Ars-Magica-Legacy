package com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.codeclib.CodecDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public final class AltarMaterialManager {
    private static final Lazy<AltarMaterialManager> INSTANCE = Lazy.concurrentOf(AltarMaterialManager::new);

    private final Logger logger = LogManager.getLogger();
    public final CodecDataManager<AltarStructureMaterial> structure = new CodecDataManager<>("altar/structure", AltarStructureMaterial.CODEC, logger);
    public final CodecDataManager<AltarCapMaterial> cap = new CodecDataManager<>("altar/cap", AltarCapMaterial.CODEC, logger);

    public static AltarMaterialManager instance() {
        return INSTANCE.get();
    }

    @NotNull
    public Optional<AltarStructureMaterial> getStructureMaterial(Block block) {
        return this.structure.values().stream().filter(mat -> mat.block() == block || mat.stair() == block).findFirst();
    }

    @NotNull
    public Optional<AltarCapMaterial> getCapMaterial(Block cap) {
        return this.cap.values().stream().filter(mat -> mat.cap() == cap).findFirst();
    }

    public AltarStructureMaterial getRandomStructureMaterial(int r) {
        return structure.values().toArray(AltarStructureMaterial[]::new)[r % structure.size()];
    }

    public AltarCapMaterial getRandomCapMaterial(int r) {
        return cap.values().toArray(AltarCapMaterial[]::new)[r % cap.size()];
    }

    @Nullable
    public ResourceLocation getId(AltarStructureMaterial structureMaterial) {
        return structure.entrySet().stream().filter(entry -> entry.getValue().equals(structureMaterial)).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    @Nullable
    public ResourceLocation getId(AltarCapMaterial capMaterial) {
        return cap.entrySet().stream().filter(entry -> entry.getValue().equals(capMaterial)).map(Map.Entry::getKey).findFirst().orElse(null);
    }

    @Nullable
    public AltarStructureMaterial getStructureMaterial(ResourceLocation location) {
        return structure.get(location);
    }

    @Nullable
    public AltarCapMaterial getCapMaterial(ResourceLocation location) {
        return cap.get(location);
    }
}
