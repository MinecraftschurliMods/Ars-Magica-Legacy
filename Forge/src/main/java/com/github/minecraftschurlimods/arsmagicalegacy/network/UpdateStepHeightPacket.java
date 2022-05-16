package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record UpdateStepHeightPacket(float stepHeight) implements IPacket {
    public UpdateStepHeightPacket(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(stepHeight());
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> ClientHelper.updateStepHeight(ClientHelper.getLocalPlayer(), stepHeight()));
    }
}
