package com.github.minecraftschurli.codeclib;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.List;
import java.util.Map;

public final class CodecHelper {
    public static <K,V> Codec<Map<K,V>> mapOf(Codec<K> keyCodec, final Codec<V> valueCodec) {
        return Codec.compoundList(keyCodec, valueCodec).xmap(CodecHelper::pairListToMap, CodecHelper::mapToPairList);
    }

    private static <K, V> Map<K, V> pairListToMap(List<Pair<K, V>> pairs) {
        return pairs.stream().collect(Pair.toMap());
    }

    private static <K, V> List<Pair<K, V>> mapToPairList(Map<K, V> map) {
        return map.entrySet().stream().map(CodecHelper::entryToPair).toList();
    }

    private static <K, V> Pair<K, V> entryToPair(Map.Entry<K, V> e) {
        return Pair.of(e.getKey(), e.getValue());
    }
}
