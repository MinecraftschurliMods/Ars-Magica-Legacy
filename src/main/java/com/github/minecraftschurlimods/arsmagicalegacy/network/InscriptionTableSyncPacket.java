package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

public record InscriptionTableSyncPacket(BlockPos blockPos, String name, ISpell spell) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_sync");

    public InscriptionTableSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readUtf(), buf.readWithCodec(ISpell.CODEC));
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeUtf(name);
        buf.writeWithCodec(ISpell.CODEC, spell);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> ((InscriptionTableBlockEntity) context.getSender().getLevel().getBlockEntity(blockPos)).onSync(name, spell));
    }
}
