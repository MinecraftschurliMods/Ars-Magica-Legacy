package com.github.minecraftschurlimods.arsmagicalegacy.network;
/*
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

public record BEClientSyncPacket(BlockPos pos, CompoundTag tag) implements CustomPacketPayload {
    public static final Type<?> TYPE = new Type<>(ArsMagicaAPI.resource("block_entity_client_sync"));

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

    void handle(IPayloadContext ctx) {
        ctx.player().level().getBlockEntity(pos).load(tag());
        ctx.workHandler().execute(() -> 
    }
}*/
