package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public record UpdateStepHeightPacket(float stepHeight) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "update_step_height");

    public UpdateStepHeightPacket(FriendlyByteBuf buf) {
        this(buf.readFloat());
    }

    @Override
    public ResourceLocation id() {
        return ID;
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
