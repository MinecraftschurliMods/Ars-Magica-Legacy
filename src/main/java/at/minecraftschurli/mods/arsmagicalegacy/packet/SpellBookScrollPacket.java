package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
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
        if (!SpellBookItem.isSpellBook(stack)) {
            stack = player.getOffhandItem();
            if (!SpellBookItem.isSpellBook(stack)) return;
        }
        SpellBookItem.scroll(stack, backwards);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
