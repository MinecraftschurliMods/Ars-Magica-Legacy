package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.EtheriumOutlineRenderer;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;

public record EtheriumPositionsSyncPacket(ChunkPos chunkPos, List<BlockPos> positions) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "etherium_positions_sync");

    public EtheriumPositionsSyncPacket(FriendlyByteBuf buf) {
        this(buf.readChunkPos(), buf.readList(FriendlyByteBuf::readBlockPos));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeChunkPos(chunkPos);
        buf.writeCollection(positions, FriendlyByteBuf::writeBlockPos);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        EtheriumOutlineRenderer.updatePositions(chunkPos, positions);
    }
}
