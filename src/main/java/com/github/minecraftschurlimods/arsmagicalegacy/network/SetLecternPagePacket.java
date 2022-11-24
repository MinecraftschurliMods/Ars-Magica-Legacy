package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;

public record SetLecternPagePacket(BlockPos pos, int page) implements IPacket {
    public SetLecternPagePacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readInt());
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
        friendlyByteBuf.writeInt(page);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> {
            BlockEntity blockEntity = Objects.requireNonNull(context.getSender()).getLevel().getBlockEntity(pos);
            if (blockEntity instanceof LecternBlockEntity lectern) {
                lectern.setPage(page);
            }
        });
    }
}
