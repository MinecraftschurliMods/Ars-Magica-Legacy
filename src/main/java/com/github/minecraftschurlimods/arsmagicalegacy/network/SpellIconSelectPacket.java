package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpellIconSelectPacket(String name, ResourceLocation icon) implements CustomPacketPayload {
    static final Type<SpellIconSelectPacket> TYPE = new Type<>(ArsMagicaAPI.resource("spell_icon_select"));
    static final StreamCodec<ByteBuf, SpellIconSelectPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SpellIconSelectPacket::name,
            ResourceLocation.STREAM_CODEC,
            SpellIconSelectPacket::icon,
            SpellIconSelectPacket::new
    );

    void handle(IPayloadContext context) {
        Player sender = context.player();
        ItemStack item = sender.getMainHandItem();
        if (item.isEmpty()) {
            item = sender.getOffhandItem();
        }
        item.set(AMDataComponents.SPELL_ICON, icon());
        item.set(AMDataComponents.SPELL_NAME, Component.nullToEmpty(name()));
    }

    @Override
    public Type<SpellIconSelectPacket> type() {
        return TYPE;
    }
}
