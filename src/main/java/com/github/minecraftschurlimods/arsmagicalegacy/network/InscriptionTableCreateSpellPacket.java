package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InscriptionTableCreateSpellPacket(BlockPos pos) implements CustomPacketPayload {
    static final Type<InscriptionTableCreateSpellPacket> TYPE = new Type<>(ArsMagicaAPI.resource("inscription_table_create_spell"));
    static final StreamCodec<ByteBuf, InscriptionTableCreateSpellPacket> STREAM_CODEC = BlockPos.STREAM_CODEC.map(InscriptionTableCreateSpellPacket::new, InscriptionTableCreateSpellPacket::pos);

    void handle(IPayloadContext context) {
        Player player = context.player();
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        if (!(player.level().getBlockEntity(pos) instanceof InscriptionTableBlockEntity inscriptionTable)) return;
        inscriptionTable.createSpell(serverPlayer);
    }

    @Override
    public Type<InscriptionTableCreateSpellPacket> type() {
        return TYPE;
    }
}
