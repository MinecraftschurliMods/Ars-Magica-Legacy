package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellBookItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpellBookScrollPacket(boolean backwards) implements CustomPacketPayload {
    public static final Type<SpellBookScrollPacket> TYPE = new Type<>(ArsMagicaApi.id("spell_book_scroll"));
    public static final StreamCodec<ByteBuf, SpellBookScrollPacket> STREAM_CODEC = ByteBufCodecs.BOOL.map(SpellBookScrollPacket::new, SpellBookScrollPacket::backwards);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        if (!player.isSecondaryUseActive()) return;
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(AMItems.SPELL_BOOK)) {
            stack = player.getOffhandItem();
            if (!stack.is(AMItems.SPELL_BOOK)) return;
        }
        SpellBookItem.scroll(stack, backwards);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
