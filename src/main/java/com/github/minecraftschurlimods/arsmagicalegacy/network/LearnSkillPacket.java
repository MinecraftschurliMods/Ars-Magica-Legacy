package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
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
        var api = ArsMagicaAPI.get();
        var skillHelper = api.getSkillHelper();
        ServerPlayer sender = ctx.getSender();
        api.getSkillManager().get(id()).getCost().forEach((resourceLocation, integer) -> skillHelper.consumeSkillPoint(sender, resourceLocation, integer));
        skillHelper.learn(sender, id());
    }
}
