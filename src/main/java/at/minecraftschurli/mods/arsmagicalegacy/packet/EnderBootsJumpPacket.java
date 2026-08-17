package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.item.EnderBootsItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record EnderBootsJumpPacket() implements CustomPacketPayload {
    public static final Type<EnderBootsJumpPacket> TYPE = new Type<>(ArsMagicaApi.id("ender_boots_jump"));
    public static final StreamCodec<ByteBuf, EnderBootsJumpPacket> STREAM_CODEC = StreamCodec.unit(new EnderBootsJumpPacket());

    public void handle(IPayloadContext context) {
        EnderBootsItem.toggle(context.player());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
