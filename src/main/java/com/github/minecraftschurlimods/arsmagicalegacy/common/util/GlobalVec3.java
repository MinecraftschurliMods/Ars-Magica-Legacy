package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record GlobalVec3(ResourceKey<Level> dimension, Vec3 position) {
    public static final Codec<GlobalVec3> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(GlobalVec3::dimension),
        Vec3.CODEC.fieldOf("position").forGetter(GlobalVec3::position)
    ).apply(inst, GlobalVec3::new));
    public static final StreamCodec<ByteBuf, GlobalVec3> STREAM_CODEC = StreamCodec.composite(
        ResourceKey.streamCodec(Registries.DIMENSION), GlobalVec3::dimension,
        AMExtraCodecs.VEC3_STREAM_CODEC, GlobalVec3::position,
        GlobalVec3::new);
}
