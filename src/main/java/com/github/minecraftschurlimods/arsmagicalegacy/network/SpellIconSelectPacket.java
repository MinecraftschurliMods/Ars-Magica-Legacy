package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record SpellIconSelectPacket(String name, ResourceLocation icon) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spell_icon_select");

    SpellIconSelectPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readResourceLocation());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(name());
        buf.writeResourceLocation(icon());
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            Player sender = context.player().orElseThrow();
            ItemStack item = sender.getMainHandItem();
            if (item.isEmpty()) {
                item = sender.getOffhandItem();
            }
            var helper = ArsMagicaAPI.get().getSpellHelper();
            helper.setSpellIcon(item, icon());
            helper.setSpellName(item, name());
        });
    }
}
