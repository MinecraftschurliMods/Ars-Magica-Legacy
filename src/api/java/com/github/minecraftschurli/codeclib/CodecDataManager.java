package com.github.minecraftschurli.codeclib;

import com.github.minecraftschurli.simplenetlib.IPacket;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Supplier;

public class CodecDataManager<T> extends SimpleJsonResourceReloadListener {
    private static final BiMap<Integer, CodecDataManager<?>> DATA_MANAGER = HashBiMap.create();
    private static final Gson GSON = new Gson();
    private final String folderName;
    private final Codec<T> codec;
    private final Codec<T> networkCodec;
    private final Validator<Map<ResourceLocation, T>> validator;
    private Map<ResourceLocation, T> data;
    protected final Logger logger;

    public CodecDataManager(String folderName, Codec<T> codec, Logger logger) {
        this(folderName, codec, codec, (m, l) -> {}, logger);
    }

    public CodecDataManager(String folderName, Codec<T> codec, Validator<Map<ResourceLocation, T>> validator, Logger logger) {
        this(folderName, codec, codec, validator, logger);
    }

    public CodecDataManager(String folderName, Codec<T> codec, Codec<T> networkCodec, Logger logger) {
        this(folderName, codec, networkCodec, (m, l) -> {}, logger);
    }

    public CodecDataManager(String folderName, Codec<T> codec, Codec<T> networkCodec, Validator<Map<ResourceLocation, T>> validator, Logger logger) {
        super(GSON, folderName);
        this.folderName = folderName;
        this.codec = codec;
        this.networkCodec = networkCodec;
        this.validator = validator;
        this.logger = logger;
        synchronized (DATA_MANAGER) {
            DATA_MANAGER.put(DATA_MANAGER.size(), this);
        }
    }

    @Override
    protected final void apply(Map<ResourceLocation, JsonElement> dataIn, ResourceManager resourceManager, ProfilerFiller profiler) {
        this.logger.info("Beginning loading of data for data loader: {}", this.folderName);
        profiler.push("data_manager_%s_deserialize".formatted(this.folderName));
        this.data = mapData(dataIn);
        profiler.pop();
        this.logger.info("Data loader for {} loaded {} jsons", this.folderName, this.data.size());
        this.logger.info("Beginning validation of data for data loader: {}", this.folderName);
        profiler.push("data_manager_%s_validate".formatted(this.folderName));
        try {
            this.validator.validate(this.data, logger);
            this.logger.info("Data loader for {} finished validation of {} entries", this.folderName, this.data.size());
        } catch (ValidationError e) {
            this.logger.error("Data loader for {} failed validation", this.folderName, e);
        }
        profiler.pop();
    }

    private Map<ResourceLocation, T> mapData(Map<ResourceLocation, JsonElement> dataIn) {
        Map<ResourceLocation, T> data = new HashMap<>();
        dataIn.forEach((key, jsonElement) -> this.codec.decode(JsonOps.INSTANCE, jsonElement)
                .get()
                .ifLeft(result -> data.put(key, result.getFirst()))
                .ifRight(partial -> this.logger.error("Failed to parse data json for {} due to: {}", key.toString(), partial.message()))
        );
        return data;
    }

    public final CodecDataManager<T> subscribeAsSyncable(NetworkHandler networkHandler) {
        networkHandler.register(SyncPacket.class, NetworkDirection.PLAY_TO_CLIENT);
        MinecraftForge.EVENT_BUS.addListener((OnDatapackSyncEvent event) -> networkHandler.sendToPlayerOrAll(
                new SyncPacket<>(DATA_MANAGER.inverse().get(this), this.data), event.getPlayer()
        ));
        return this;
    }

    public T get(@Nullable ResourceLocation id) {
        if (this.data == null) {
            throw this.logger.throwing(new IllegalStateException("CodecDataManager(%s) not loaded yet!".formatted(this.folderName)));
        }
        return this.getOptional(id).orElseThrow();
    }

    @Nullable
    public T getNullable(@Nullable ResourceLocation id) {
        if (id == null) return null;
        if (this.data == null) return null;
        return this.data.get(id);
    }

    public T getOrDefault(@Nullable ResourceLocation id, Supplier<T> defaultSupplier) {
        return this.getOptional(id).orElseGet(defaultSupplier);
    }

    public Optional<T> getOptional(@Nullable ResourceLocation id) {
        return Optional.ofNullable(this.getNullable(id));
    }

    public Map<ResourceLocation, T> getData() {
        return Collections.unmodifiableMap(this.data);
    }

    public Set<ResourceLocation> getKeys() {
        return Collections.unmodifiableSet(this.data.keySet());
    }

    public Collection<T> getValues() {
        return Collections.unmodifiableCollection(this.data.values());
    }

    public static final class SyncPacket<T> implements IPacket {
        private final Map<ResourceLocation, T> data;
        private final int index;

        private SyncPacket(int index, Map<ResourceLocation, T> data) {
            this.index = index;
            this.data = data;
        }

        public SyncPacket(FriendlyByteBuf buffer) {
            this.index = buffer.readInt();
            Codec<T> codec = getDataManager().networkCodec;
            this.data = buffer.readMap(FriendlyByteBuf::readResourceLocation, buf -> buf.readWithCodec(codec));
        }

        public void serialize(FriendlyByteBuf buffer) {
            Codec<T> codec = getDataManager().networkCodec;
            buffer.writeInt(this.index);
            buffer.writeMap(this.data, FriendlyByteBuf::writeResourceLocation, (buf, t) -> buf.writeWithCodec(codec, t));
        }

        @Override
        public void handle(NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> getDataManager().data = this.data);
        }

        @SuppressWarnings("unchecked")
        private CodecDataManager<T> getDataManager() {
            return (CodecDataManager<T>) CodecDataManager.DATA_MANAGER.get(this.index);
        }
    }

    public static class ValidationError extends Exception {}

    @FunctionalInterface
    public interface Validator<T> {
        void validate(T data, Logger logger) throws ValidationError;
    }
}
