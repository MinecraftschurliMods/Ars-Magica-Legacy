package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookItem;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public record SpellBookNextSpellPacket(boolean backwards) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_book_next_spell");

    public SpellBookNextSpellPacket(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBoolean(backwards());
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            assert sender != null;
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
