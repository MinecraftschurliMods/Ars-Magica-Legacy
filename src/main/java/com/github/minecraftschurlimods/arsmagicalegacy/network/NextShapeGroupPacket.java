package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

public record NextShapeGroupPacket(InteractionHand hand, boolean reverse) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "next_shape_group");

    public NextShapeGroupPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(InteractionHand.class), buf.readBoolean());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeEnum(hand);
        buf.writeBoolean(reverse);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            if (reverse) {
                ArsMagicaAPI.get().getSpellHelper().prevShapeGroup(player.getItemInHand(hand));
            } else {
                ArsMagicaAPI.get().getSpellHelper().nextShapeGroup(player.getItemInHand(hand));
            }
        });
    }
}
