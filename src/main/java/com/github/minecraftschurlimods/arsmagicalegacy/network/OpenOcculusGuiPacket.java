package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenOcculusGuiPacket() implements CustomPacketPayload {
    static final Type<OpenOcculusGuiPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "open_occulus_gui"));
    static final StreamCodec<ByteBuf, OpenOcculusGuiPacket> STREAM_CODEC = StreamCodec.unit(new OpenOcculusGuiPacket());

    void handle(IPayloadContext ctx) {
        ClientHelper.openOcculusGui();
    }

    @Override
    public Type<OpenOcculusGuiPacket> type() {
        return TYPE;
    }
}
