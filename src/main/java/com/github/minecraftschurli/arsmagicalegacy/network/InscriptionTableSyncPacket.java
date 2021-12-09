package com.github.minecraftschurli.arsmagicalegacy.network;

import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.spell.Spell;
import com.github.minecraftschurli.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public record InscriptionTableSyncPacket(BlockPos blockPos, String name, Spell spell) implements IPacket {
    public InscriptionTableSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readUtf(), buf.readWithCodec(Spell.CODEC));
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeUtf(name);
        buf.writeWithCodec(Spell.CODEC, spell);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> ((InscriptionTableBlockEntity)context.getSender().getLevel().getBlockEntity(blockPos)).onSync(name, spell));
    }
}
