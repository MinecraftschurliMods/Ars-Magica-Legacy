package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpellBookNextSpellPacket(boolean backwards) implements CustomPacketPayload {
    static final Type<SpellBookNextSpellPacket> TYPE = new Type<>(ArsMagicaAPI.resource("spell_book_next_spell"));
    static final StreamCodec<ByteBuf, SpellBookNextSpellPacket> STREAM_CODEC = ByteBufCodecs.BOOL.map(SpellBookNextSpellPacket::new, SpellBookNextSpellPacket::backwards);

    void handle(IPayloadContext context) {
        Player sender = context.player();
        ItemStack item = sender.getMainHandItem();
        if (!(item.getItem() instanceof SpellBookItem)) {
            item = sender.getOffhandItem();
        }
        if (!(item.getItem() instanceof SpellBookItem)) return;
        if (backwards()) {
            SpellBookItem.prevSelectedSlot(item);
        } else {
            SpellBookItem.nextSelectedSlot(item);
        }
    }

    @Override
    public Type<SpellBookNextSpellPacket> type() {
        return TYPE;
    }
}
