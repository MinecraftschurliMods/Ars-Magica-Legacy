package com.github.minecraftschurli.arsmagicalegacy.api.util;

import com.mojang.serialization.Codec;

/**
 * Represents a type that can be synced to the client
 */
public interface ISyncable<T> {
    Codec<T> getNetworkCodec();
}
