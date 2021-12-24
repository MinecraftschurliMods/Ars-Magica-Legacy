package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public record LearnSkillPacket(ResourceLocation id) implements IPacket {
    public LearnSkillPacket(FriendlyByteBuf buf) {
        this(buf.readResourceLocation());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeResourceLocation(id());
    }

    @Override
    public void handle(NetworkEvent.Context ctx) {
        ArsMagicaAPI.get().getSkillHelper().learn(ctx.getSender(), id());
    }
}
