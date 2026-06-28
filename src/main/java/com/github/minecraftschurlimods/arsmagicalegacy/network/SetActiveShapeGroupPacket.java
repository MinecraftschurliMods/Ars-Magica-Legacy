package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SetActiveShapeGroupPacket(int activeShapeGroup) implements CustomPacketPayload {
    public static final Type<SetActiveShapeGroupPacket> TYPE = new Type<>(ArsMagicaApi.id("set_active_shape_group"));
    public static final StreamCodec<ByteBuf, SetActiveShapeGroupPacket> STREAM_CODEC = ByteBufCodecs.INT.map(SetActiveShapeGroupPacket::new, SetActiveShapeGroupPacket::activeShapeGroup);

    @SuppressWarnings("DataFlowIssue")
    public void handle(IPayloadContext context) {
        Player player = context.player();
        ItemStack stack = player.getMainHandItem();
        if (!stack.has(AMDataComponents.SPELL)) {
            stack = player.getOffhandItem();
        }
        if (stack.has(AMDataComponents.SPELL)) {
            stack.set(AMDataComponents.SPELL, stack.get(AMDataComponents.SPELL).setActiveShapeGroup(activeShapeGroup));
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
