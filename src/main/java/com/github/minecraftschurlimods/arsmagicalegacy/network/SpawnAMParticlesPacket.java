package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellParticleSpawners;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SpawnAMParticlesPacket(int entity) implements CustomPacketPayload {
    static final Type<SpawnAMParticlesPacket> TYPE = new Type<>(new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_particles"));
    static final StreamCodec<ByteBuf, SpawnAMParticlesPacket> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(SpawnAMParticlesPacket::new, SpawnAMParticlesPacket::entity);

    public SpawnAMParticlesPacket(Entity entity) {
        this(entity.getId());
    }

    void handle(IPayloadContext context) {
        SpellParticleSpawners.handleReceivedPacket(entity());
    }

    @Override
    public Type<SpawnAMParticlesPacket> type() {
        return TYPE;
    }
}
