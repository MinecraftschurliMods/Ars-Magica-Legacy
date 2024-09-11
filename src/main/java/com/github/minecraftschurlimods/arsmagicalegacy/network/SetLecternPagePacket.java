package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetLecternPagePacket(BlockPos pos, int page) implements CustomPacketPayload {
    static final Type<SetLecternPagePacket> TYPE = new Type<>(ArsMagicaAPI.resource("set_lectern_page"));
    static final StreamCodec<ByteBuf, SetLecternPagePacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            SetLecternPagePacket::pos,
            ByteBufCodecs.VAR_INT,
            SetLecternPagePacket::page,
            SetLecternPagePacket::new
    );

    void handle(IPayloadContext context) {
        if (context.player().level().getBlockEntity(pos()) instanceof LecternBlockEntity lectern) {
            lectern.setPage(page());
        }
    }

    @Override
    public Type<SetLecternPagePacket> type() {
        return TYPE;
    }
}
