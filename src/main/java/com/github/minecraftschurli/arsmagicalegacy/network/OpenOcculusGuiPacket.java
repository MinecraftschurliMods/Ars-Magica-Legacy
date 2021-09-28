package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public record OpenOcculusGuiPacket() implements IPacket {
    @Override
    public void deserialize(FriendlyByteBuf buf) {
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(ClientHelper::openOcculusGui);
    }
}
