package com.github.minecraftschurli.codeclib;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CodecCapabilityProvider<C> implements ICapabilitySerializable<Tag> {
    private final NonNullSupplier<C> emptySupplier;
    private final Capability<C> capability;
    private final Codec<C> codec;
    private LazyOptional<C> lazy;

    public CodecCapabilityProvider(Codec<C> codec, Capability<C> capability, NonNullSupplier<C> emptySupplier) {
        this.codec = codec;
        this.capability = capability;
        this.emptySupplier = emptySupplier;
        this.lazy = LazyOptional.of(this.emptySupplier);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return capability.orEmpty(cap, lazy);
    }

    @Nullable
    @Override
    public Tag serializeNBT() {
        return lazy.lazyMap(c -> codec.encodeStart(NbtOps.INSTANCE, c)
                                      .get()
                                      .mapRight(DataResult.PartialResult::message)
                                      .orThrow())
                   .resolve()
                   .orElse(null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        this.lazy = codec.decode(NbtOps.INSTANCE, nbt)
                .get()
                .mapLeft(Pair::getFirst)
                .mapLeft(c -> (NonNullSupplier<C>)(() -> c))
                .map(LazyOptional::of, pairPartialResult -> LazyOptional.of(emptySupplier));
    }
}
