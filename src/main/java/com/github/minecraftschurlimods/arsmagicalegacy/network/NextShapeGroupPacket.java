package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record NextShapeGroupPacket(InteractionHand hand, boolean reverse) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "next_shape_group");

    NextShapeGroupPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(InteractionHand.class), buf.readBoolean());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(hand);
        buf.writeBoolean(reverse);
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            Player player = context.player().orElse(null);
            if (player == null) return;
            if (reverse) {
                ArsMagicaAPI.get().getSpellHelper().prevShapeGroup(player.getItemInHand(hand));
            } else {
                ArsMagicaAPI.get().getSpellHelper().nextShapeGroup(player.getItemInHand(hand));
            }
        });
    }
}
