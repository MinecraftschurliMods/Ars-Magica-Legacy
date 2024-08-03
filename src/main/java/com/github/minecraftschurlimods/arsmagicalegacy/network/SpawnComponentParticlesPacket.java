package com.github.minecraftschurlimods.arsmagicalegacy.network;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellPart;
import com.github.minecraftschurlimods.arsmagicalegacy.client.ClientHelper;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.function.Function;

public record SpawnComponentParticlesPacket(ISpellComponent component, LivingEntity caster, Either<BlockHitResult, EntityHitResult> hit, int color) implements CustomPacketPayload {
    static final Type<SpawnComponentParticlesPacket> TYPE = new Type<>(ArsMagicaAPI.resource("spawn_component_particles"));
    static final StreamCodec<FriendlyByteBuf, BlockHitResult> BLOCK_HIT_RESULT_STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            BlockHitResult::getBlockPos,
            NeoForgeStreamCodecs.enumCodec(Direction.class),
            BlockHitResult::getDirection,
            ByteBufCodecs.VECTOR3F,
            (BlockHitResult bhr) -> {
                BlockPos blockpos = bhr.getBlockPos();
                Vec3 vec3 = bhr.getLocation();
                return new Vector3f(
                        (float)(vec3.x - (double)blockpos.getX()),
                        (float)(vec3.y - (double)blockpos.getY()),
                        (float)(vec3.z - (double)blockpos.getZ())
                );
            },
            ByteBufCodecs.BOOL,
            BlockHitResult::isInside,
            (blockpos, direction, vec, flag) -> new BlockHitResult(
                    new Vec3((double)blockpos.getX() + (double)vec.x, (double)blockpos.getY() + (double)vec.y, (double)blockpos.getZ() + (double)vec.z),
                    direction,
                    blockpos,
                    flag
            )
    );
    static final StreamCodec<FriendlyByteBuf, EntityHitResult> ENTITY_HIT_RESULT_STREAM_CODEC = StreamCodec.composite(
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE,
                    Vec3::x,
                    ByteBufCodecs.DOUBLE,
                    Vec3::y,
                    ByteBufCodecs.DOUBLE,
                    Vec3::y,
                    Vec3::new
            ),
            EntityHitResult::getLocation,
            ByteBufCodecs.VAR_INT,
            (EntityHitResult ehr) -> ehr.getEntity().getId(),
            (loc, eid) -> {
                Entity entity = Objects.requireNonNull(Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(eid));
                return new EntityHitResult(entity, loc);
            }
    );
    static final StreamCodec<RegistryFriendlyByteBuf, SpawnComponentParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(ISpellPart.REGISTRY_KEY).map(ISpellComponent.class::cast, Function.identity()),
            SpawnComponentParticlesPacket::component,
            ByteBufCodecs.VAR_INT.map(eid -> (LivingEntity) Objects.requireNonNull(Objects.requireNonNull(ClientHelper.getLocalLevel()).getEntity(eid)), Entity::getId),
            SpawnComponentParticlesPacket::caster,
            ByteBufCodecs.either(BLOCK_HIT_RESULT_STREAM_CODEC, ENTITY_HIT_RESULT_STREAM_CODEC),
            SpawnComponentParticlesPacket::hit,
            ByteBufCodecs.VAR_INT,
            SpawnComponentParticlesPacket::color,
            SpawnComponentParticlesPacket::new
    );

    void handle(IPayloadContext context) {
        var helper = ArsMagicaAPI.get().getSpellHelper();
        helper.spawnParticles(component, helper.getSpell(helper.getSpellItemStackFromEntity(caster)), caster, hit.map(l -> l, r -> r), Objects.requireNonNull(ClientHelper.getLocalLevel()).getRandom(), color);
    }

    @Override
    public Type<SpawnComponentParticlesPacket> type() {
        return TYPE;
    }
}
