package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.common.item.SpellRecipeItem;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;

public record TakeSpellRecipeFromLecternPacket(BlockPos pos) implements IPacket {
    public TakeSpellRecipeFromLecternPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos());
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeBlockPos(pos);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> SpellRecipeItem.takeFromLectern(Objects.requireNonNull(context.getSender()), context.getSender().getLevel(), pos, context.getSender().getLevel().getBlockState(pos)));
    }
}
