package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent;

public record NextShapeGroupPacket(InteractionHand hand) implements IPacket {
    public NextShapeGroupPacket(FriendlyByteBuf buf) {
        this(buf.readEnum(InteractionHand.class));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeEnum(hand);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null) return;
            ArsMagicaAPI.get().getSpellHelper().nextShapeGroup(player.getItemInHand(hand));
        });
    }
}
