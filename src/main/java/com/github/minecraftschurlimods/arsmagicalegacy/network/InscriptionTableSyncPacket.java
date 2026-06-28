package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.InscriptionTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InscriptionTableSyncPacket(BlockPos pos, InscriptionTableBlockEntity.MenuData data) implements CustomPacketPayload {
    public static final Type<InscriptionTableSyncPacket> TYPE = new Type<>(ArsMagicaApi.id("inscription_table_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InscriptionTableSyncPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, InscriptionTableSyncPacket::pos,
        InscriptionTableBlockEntity.MenuData.STREAM_CODEC, InscriptionTableSyncPacket::data,
        InscriptionTableSyncPacket::new);

    public void handle(IPayloadContext context) {
        Level level = context.player().level();
        if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof InscriptionTableBlockEntity blockEntity) {
            blockEntity.setMenuData(data);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
