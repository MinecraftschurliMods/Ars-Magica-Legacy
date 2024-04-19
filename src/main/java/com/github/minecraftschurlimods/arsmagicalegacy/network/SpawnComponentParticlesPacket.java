package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Objects;

public record SpawnComponentParticlesPacket(ISpellComponent component, LivingEntity caster, Either<BlockHitResult, EntityHitResult> hit, int color) implements CustomPacketPayload {
    public static final ResourceLocation ID = new ResourceLocation(ArsMagicaAPI.MOD_ID, "spawn_component_particles");
    private static final FriendlyByteBuf.Writer<EntityHitResult> ENTITY_HIT_RESULT_WRITER = (b, e) -> {
        Vec3 location = e.getLocation();
        b.writeDouble(location.x);
        b.writeDouble(location.y);
        b.writeDouble(location.z);
        b.writeInt(e.getEntity().getId());
    };
    private static final FriendlyByteBuf.Reader<EntityHitResult> ENTITY_HIT_RESULT_READER = b -> {
        Vec3 location = new Vec3(b.readDouble(), b.readDouble(), b.readDouble());
        Entity entity = Objects.requireNonNull(Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(b.readInt()));
        return new EntityHitResult(entity, location);
    };

    SpawnComponentParticlesPacket(FriendlyByteBuf buf) {
        this((ISpellComponent) Objects.requireNonNull(ArsMagicaAPI.get().getSpellPartRegistry().get(buf.readResourceLocation())),
                (LivingEntity) Objects.requireNonNull(Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(buf.readInt())),
                buf.readEither(FriendlyByteBuf::readBlockHitResult, ENTITY_HIT_RESULT_READER),
                buf.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(component.getId());
        buf.writeInt(caster.getId());
        buf.writeEither(hit, FriendlyByteBuf::writeBlockHitResult, ENTITY_HIT_RESULT_WRITER);
        buf.writeInt(color);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    void handle(PlayPayloadContext context) {
        context.workHandler().execute(() -> {
            var helper = ArsMagicaAPI.get().getSpellHelper();
            helper.spawnParticles(component, helper.getSpell(helper.getSpellItemStackFromEntity(caster)), caster, hit.map(l -> l, r -> r), Objects.requireNonNull(ClientHelper.getLocalLevel()).getRandom(), color);
        });
    }
}
