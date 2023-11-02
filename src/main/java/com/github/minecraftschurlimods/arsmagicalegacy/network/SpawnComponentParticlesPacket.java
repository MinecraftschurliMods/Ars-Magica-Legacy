package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.github.minecraftschurlimods.simplenetlib.IPacket;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;

public record SpawnComponentParticlesPacket(ISpellComponent component, LivingEntity caster, Either<BlockHitResult, EntityHitResult> hit, int color) implements IPacket {
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

    public SpawnComponentParticlesPacket(FriendlyByteBuf buf) {
        this((ISpellComponent) Objects.requireNonNull(ArsMagicaAPI.get().getSpellPartRegistry().getValue(buf.readResourceLocation())),
                (LivingEntity) Objects.requireNonNull(Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(buf.readInt())),
                buf.readEither(FriendlyByteBuf::readBlockHitResult, ENTITY_HIT_RESULT_READER),
                buf.readInt());
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    @Override
    public void serialize(FriendlyByteBuf buf) {
        buf.writeResourceLocation(component.getId());
        buf.writeInt(caster.getId());
        buf.writeEither(hit, FriendlyByteBuf::writeBlockHitResult, ENTITY_HIT_RESULT_WRITER);
        buf.writeInt(color);
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.spawnParticles(component, helper.getSpell(AMUtil.getSpellStack(caster)), caster, hit.map(l -> l, r -> r), color);
    }
}
