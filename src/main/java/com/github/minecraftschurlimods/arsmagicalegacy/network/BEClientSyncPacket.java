package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

public record BEClientSyncPacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "block_entity_client_sync");

    public BEClientSyncPacket(BlockEntity blockEntity) {
        this(blockEntity.getBlockPos(), blockEntity.getUpdateTag());
    }

    BEClientSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readNbt());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    void handle(PlayPayloadContext ctx) {
        ctx.workHandler().execute(() -> Optional.ofNullable(Minecraft.getInstance().level).map(level -> level.getBlockEntity(pos)).ifPresent(blockEntity -> blockEntity.load(tag())));
    }
}
