package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record SpellBookNextSpellPacket(boolean backwards) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_book_next_spell");

    SpellBookNextSpellPacket(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(backwards());
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            Player sender = context.player().orElseThrow();
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
        });
    }
}
