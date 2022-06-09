package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public record OpenOcculusGuiPacket() implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "open_occulus_gui");

    @Override
    public void deserialize(FriendlyByteBuf buf) {
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ctx.enqueueWork(ClientHelper::openOcculusGui);
    }
}
