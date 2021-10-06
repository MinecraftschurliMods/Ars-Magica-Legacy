package com.github.minecraftschurli.codeclib;

import com.github.minecraftschurli.simplenetlib.IPacket;
import com.github.minecraftschurli.simplenetlib.NetworkHandler;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public final class CodecPacket {
    private static final Map<Integer, Pair<Codec<?>, BiConsumer<?, NetworkEvent.Context>>> CODECS = new HashMap<>();

    public static synchronized <T> CodecPacketFactory<T> create(Codec<T> codec, BiConsumer<T, NetworkEvent.Context> handler) {
        var i = CODECS.size();
        CODECS.put(i, Pair.of(codec, handler));
        return data -> new Packet<>(i, data);
    }

    public static void register(NetworkHandler handler) {
        handler.register(Packet.class, null);
    }

    @SuppressWarnings("unchecked")
    private static <T> Codec<T> getCodec(int codecId) {
        return (Codec<T>) CODECS.get(codecId).getFirst();
    }

    @SuppressWarnings("unchecked")
    private static <T> BiConsumer<T, NetworkEvent.Context> getHandler(int codecId) {
        return (BiConsumer<T, NetworkEvent.Context>) CODECS.get(codecId).getSecond();
    }

    public static final class Packet<T> implements IPacket {
        private final int codecId;
        private final T data;

        private Packet(int codecId, T data) {
            this.codecId = codecId;
            this.data = data;
        }

        public Packet(FriendlyByteBuf buf) {
            this.codecId = buf.readInt();
            this.data = buf.readWithCodec(CodecPacket.getCodec(this.codecId));
        }

        public void serialize(FriendlyByteBuf buf) {
            buf.writeInt(this.codecId);
            buf.writeWithCodec(CodecPacket.getCodec(this.codecId), this.data);
        }

        @Override
        public void handle(NetworkEvent.Context ctx) {
            ctx.enqueueWork(() -> CodecPacket.getHandler(this.codecId).accept(this.data, ctx));
        }
    }

    public interface CodecPacketFactory<T> {
        Packet<T> create(T data);
    }
}
