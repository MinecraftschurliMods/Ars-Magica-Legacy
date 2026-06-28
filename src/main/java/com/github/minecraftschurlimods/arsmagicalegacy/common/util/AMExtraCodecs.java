package com.github.minecraftschurlimods.arsmagicalegacy.common.util;

import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;

public final class AMExtraCodecs {
    public static final Codec<Integer> STRING_ENCODED_INT_CODEC = Codec.STRING.comapFlatMap(s -> {
        try {
            return DataResult.success(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return DataResult.error(e::getMessage);
        }
    }, String::valueOf);
    public static final Codec<Double> NON_NEGATIVE_DOUBLE_CODEC = Codec.DOUBLE.validate(d -> d >= 0 ? DataResult.success(d) : DataResult.error(() -> "Value must be non-negative: " + d));
    public static final Codec<Double> POSITIVE_DOUBLE_CODEC = Codec.DOUBLE.validate(d -> d > 0 ? DataResult.success(d) : DataResult.error(() -> "Value must be positive: " + d));
    public static final StreamCodec<ByteBuf, InteractionHand> INTERACTION_HAND_STREAM_CODEC = ByteBufCodecs.BOOL.map(
        bool -> bool ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND,
        hand -> hand == InteractionHand.MAIN_HAND
    );
    public static final StreamCodec<ByteBuf, Vec3> VEC3_STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.DOUBLE, Vec3::x,
        ByteBufCodecs.DOUBLE, Vec3::y,
        ByteBufCodecs.DOUBLE, Vec3::z,
        Vec3::new);

    private AMExtraCodecs() {
    }

    public static Codec<Double> doubleRangeCodec(double min, double max) {
        return Codec.DOUBLE.validate(d -> d.compareTo(min) >= 0 && d.compareTo(max) <= 0 ? DataResult.success(d) : DataResult.error(() -> "Value must be within range [" + min + ";" + max + "]: " + d));
    }

    public static <B extends ByteBuf, K, V> StreamCodec<B, Map<K, V>> mapStreamCodec(StreamCodec<? super B, K> keyStreamCodec, StreamCodec<? super B, V> valueStreamCodec) {
        return ByteBufCodecs.map(HashMap::new, keyStreamCodec, valueStreamCodec);
    }

    public static <B extends ByteBuf, F, S> StreamCodec<B, Pair<F, S>> pairStreamCodec(StreamCodec<? super B, F> firstStreamCodec, StreamCodec<? super B, S> secondStreamCodec) {
        return new StreamCodec<>() {
            @Override
            public Pair<F, S> decode(B buffer) {
                F first = firstStreamCodec.decode(buffer);
                S second = secondStreamCodec.decode(buffer);
                return Pair.of(first, second);
            }

            @Override
            public void encode(B buffer, Pair<F, S> value) {
                firstStreamCodec.encode(buffer, value.getFirst());
                secondStreamCodec.encode(buffer, value.getSecond());
            }
        };
    }

    public static <T> StreamCodec<FriendlyByteBuf, T> toStreamCodec(Codec<T> codec) {
        return new StreamCodec<>() {
            @Override
            public T decode(FriendlyByteBuf buffer) {
                return codec.decode(JsonOps.INSTANCE, JsonParser.parseString(buffer.readUtf())).getOrThrow().getFirst();
            }

            @Override
            public void encode(FriendlyByteBuf buffer, T value) {
                buffer.writeUtf(codec.encodeStart(JsonOps.INSTANCE, value).getOrThrow().toString());
            }
        };
    }
}
