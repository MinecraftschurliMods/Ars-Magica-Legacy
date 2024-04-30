package com.github.minecraftschurlimods.arsmagicalegacy.client.renderer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class EtheriumOutlineRenderer {
    private static final Map<ChunkPos, Collection<BlockPos>> POSITIONS = new HashMap<>();

    public static void updatePositions(ChunkPos chunkPos, Collection<BlockPos> positions) {
        POSITIONS.put(chunkPos, positions);
    }
}
