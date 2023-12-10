package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.client.SpellParticleSpawners;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

public record SpawnAMParticlesPacket(int entity) implements IPacket {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_particles");

    public SpawnAMParticlesPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public SpawnAMParticlesPacket(Entity entity) {
        this(entity.getId());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeInt(entity);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        context.enqueueWork(() -> SpellParticleSpawners.handleReceivedPacket(entity()));
    }
}
