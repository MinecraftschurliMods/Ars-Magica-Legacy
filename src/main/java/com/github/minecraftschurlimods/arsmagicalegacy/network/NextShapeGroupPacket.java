package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NextShapeGroupPacket(InteractionHand hand, boolean reverse) implements CustomPacketPayload {
    static final Type<NextShapeGroupPacket> TYPE = new Type<>(ArsMagicaAPI.resource("next_shape_group"));
    static final StreamCodec<FriendlyByteBuf, NextShapeGroupPacket> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.enumCodec(InteractionHand.class),
            NextShapeGroupPacket::hand,
            ByteBufCodecs.BOOL,
            NextShapeGroupPacket::reverse,
            NextShapeGroupPacket::new
    );

    void handle(IPayloadContext context) {
        Player player = context.player();
        if (reverse) {
            ArsMagicaAPI.get().getSpellHelper().prevShapeGroup(player.getItemInHand(hand));
        } else {
            ArsMagicaAPI.get().getSpellHelper().nextShapeGroup(player.getItemInHand(hand));
        }
    }

    @Override
    public Type<NextShapeGroupPacket> type() {
        return TYPE;
    }
}
