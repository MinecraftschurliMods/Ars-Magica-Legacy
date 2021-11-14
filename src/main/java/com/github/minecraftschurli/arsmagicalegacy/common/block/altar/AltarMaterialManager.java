package com.github.minecraftschurli.arsmagicalegacy.common.block.altar;

import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurli.codeclib.CodecDataManager;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public final class AltarMaterialManager {
    private static final Lazy<AltarMaterialManager> INSTANCE = Lazy.concurrentOf(AltarMaterialManager::new);

    private final Logger                                   logger    = LogManager.getLogger();
    public  final CodecDataManager<AltarStructureMaterial> structure = new CodecDataManager<>("altar/structure", AltarStructureMaterial.CODEC, logger);
    public  final CodecDataManager<AltarCapMaterial>       cap       = new CodecDataManager<>("altar/cap", AltarCapMaterial.CODEC, logger);

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
}
