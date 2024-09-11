package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InscriptionTableSyncPacket(BlockPos blockPos, Component name, ISpell spell) implements CustomPacketPayload {
    static final Type<InscriptionTableSyncPacket> TYPE = new Type<>(ArsMagicaAPI.resource("inscription_table_sync"));
    static final StreamCodec<RegistryFriendlyByteBuf, InscriptionTableSyncPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            InscriptionTableSyncPacket::blockPos,
            ComponentSerialization.STREAM_CODEC,
            InscriptionTableSyncPacket::name,
            ISpell.STREAM_CODEC,
            InscriptionTableSyncPacket::spell,
            InscriptionTableSyncPacket::new
    );

    void handle(IPayloadContext context) {
        if (!(context.player().level().getBlockEntity(blockPos()) instanceof InscriptionTableBlockEntity inscriptionTable))
            return;
        inscriptionTable.onSync(name().getString().isEmpty() ? null : name(), spell());
    }

    @Override
    public Type<InscriptionTableSyncPacket> type() {
        return TYPE;
    }
}
