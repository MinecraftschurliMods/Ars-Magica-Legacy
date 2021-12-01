package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public record SpellIconSelectPacket(String name, ResourceLocation icon) implements IPacket {
    public SpellIconSelectPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readResourceLocation());
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeUtf(name());
        buf.writeResourceLocation(icon());
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            assert sender != null;
            ItemStack item = sender.getMainHandItem();
            if (item.isEmpty()) {
                item = sender.getOffhandItem();
            }
            SpellItem.setSpellIcon(item, icon());
            SpellItem.setSpellName(item, name());
        });
    }
}
