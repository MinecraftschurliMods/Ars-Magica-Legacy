package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenBookInLecternPacket(BlockPos pos, ItemStack stack) implements CustomPacketPayload {
    public static final Type<OpenBookInLecternPacket> TYPE = new Type<>(ArsMagicaApi.id("open_book_in_lectern"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenBookInLecternPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, OpenBookInLecternPacket::pos,
        ItemStack.STREAM_CODEC, OpenBookInLecternPacket::stack,
        OpenBookInLecternPacket::new);

    public void handle(IPayloadContext context) {
        Player player = context.player();
        Level level = player.level();
        if (!level.isLoaded(pos) || !(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern)) return;
        if (lectern.getBook().isEmpty()) {
            lectern.setBook(stack);
        }
        AMClientUtil.setSpellRecipeScreen(stack, false, lectern.getPage(), pos);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
