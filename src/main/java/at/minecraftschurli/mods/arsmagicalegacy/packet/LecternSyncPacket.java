package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record LecternSyncPacket(BlockPos pos, ItemStack stack) implements CustomPacketPayload {
    public static final Type<LecternSyncPacket> TYPE = new Type<>(ArsMagicaApi.id("lectern_sync"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LecternSyncPacket> STREAM_CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, LecternSyncPacket::pos,
        ItemStack.OPTIONAL_STREAM_CODEC, LecternSyncPacket::stack,
        LecternSyncPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Level level = context.player().level();
        if (level.isLoaded(pos) && level.getBlockEntity(pos) instanceof LecternBlockEntity lectern) {
            lectern.setBook(stack);
        }
    }
}
