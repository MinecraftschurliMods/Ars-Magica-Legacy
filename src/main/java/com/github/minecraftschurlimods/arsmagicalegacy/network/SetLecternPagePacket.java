package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetLecternPagePacket(BlockPos pos, int page) implements CustomPacketPayload {
    public static final Type<SetLecternPagePacket> TYPE = new Type<>(ArsMagicaApi.id("set_lectern_page"));
    public static final StreamCodec<ByteBuf, SetLecternPagePacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, SetLecternPagePacket::pos,
        ByteBufCodecs.INT, SetLecternPagePacket::page,
        SetLecternPagePacket::new);

    public void handle(IPayloadContext context) {
        Level level = context.player().level();
        if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) {
            lectern.setPage(page);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
