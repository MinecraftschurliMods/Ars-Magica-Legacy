package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

public record InscriptionTableSyncPacket(BlockPos blockPos, Component name, ISpell spell) implements IPacket {
    public InscriptionTableSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readComponent(), buf.readWithCodec(ISpell.CODEC));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeComponent(name);
        buf.writeWithCodec(ISpell.CODEC, spell);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> ((InscriptionTableBlockEntity) context.getSender().getLevel().getBlockEntity(blockPos)).onSync(name, spell));
    }
}
