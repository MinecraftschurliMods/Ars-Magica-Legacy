package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

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
