package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpell;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record InscriptionTableSyncPacket(BlockPos blockPos, Component name, ISpell spell) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "inscription_table_sync");

    InscriptionTableSyncPacket(FriendlyByteBuf buf) {
        this(buf.readBlockPos(), buf.readComponent(), buf.readJsonWithCodec(ISpell.CODEC));
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
        buf.writeComponent(name != null ? name : Component.empty());
        buf.writeJsonWithCodec(ISpell.CODEC, spell);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> ((InscriptionTableBlockEntity) context.player().orElseThrow().level().getBlockEntity(blockPos())).onSync(name() == null || name().getString().isEmpty() ? null : name(), spell()));
    }
}
