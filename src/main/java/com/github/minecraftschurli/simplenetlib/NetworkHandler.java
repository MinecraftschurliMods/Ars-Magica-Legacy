package com.github.minecraftschurli.simplenetlib;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class makes communication between server and client easier by wrapping the vanilla SimpleChannel network implementation
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unused"})
public final class NetworkHandler {
    private static final Map<ResourceLocation, NetworkHandler> HANDLERS = new ConcurrentHashMap<>();
    private static final Marker SEND_MARKER = MarkerManager.getMarker("NETWORK_SEND");
    private static final Marker REGISTER_MARKER = MarkerManager.getMarker("NETWORK_REGISTER");

    private final Logger logger;
    private final SimpleChannel channel;
    private final AtomicInteger id = new AtomicInteger();

    /**
     * Create or get the {@link NetworkHandler} for the modid, and the channel name with the given version
     *
     * @param modid       the modid
     * @param channelName the name for the network channel
     * @param version     the version for the network channel
     * @return the {@link NetworkHandler} associated with the modid, and the channel name
     */
    public static NetworkHandler create(String modid, String channelName, int version) {
        return create(modid, channelName, String.valueOf(version));
    }

    /**
     * Create or get the {@link NetworkHandler} for the modid, and the channel name with the given version
     *
     * @param modid       the modid
     * @param channelName the name for the network channel
     * @param version     the version for the network channel
     * @return the {@link NetworkHandler} associated with the modid, and the channel name
     */
    public static NetworkHandler create(String modid, String channelName, String version) {
        return create(new ResourceLocation(modid, channelName), version);
    }

    /**
     * Create or get the {@link NetworkHandler} for the resourceLocation with the given version
     *
     * @param rl      the {@link ResourceLocation} for the network channel
     * @param version the version for the network channel
     * @return the {@link NetworkHandler} associated with the {@link ResourceLocation}
     */
    public static NetworkHandler create(ResourceLocation rl, String version) {
        return HANDLERS.computeIfAbsent(rl, resourceLocation -> new NetworkHandler(resourceLocation, version));
    }

    /**
     * Create or get the {@link NetworkHandler} for the modid, and the channel name with the given version supplier and predicate
     *
     * @param modid         the modid
     * @param channelName   the name for the network channel
     * @param version       the supplier for the version string
     * @param acceptVersion the predicate to check if the version is valid
     * @return the {@link NetworkHandler} associated with the modid, and the channel name
     */
    public static NetworkHandler create(String modid,
                                        String channelName,
                                        Supplier<String> version,
                                        Predicate<String> acceptVersion) {
        return create(new ResourceLocation(modid, channelName), version, acceptVersion);
    }

    /**
     * Create or get the {@link NetworkHandler} for the resourceLocation with the given version supplier and predicate
     *
     * @param rl            the {@link ResourceLocation} for the network channel
     * @param version       the supplier for the version string
     * @param acceptVersion the predicate to check if the version is valid
     * @return the {@link NetworkHandler} associated with the {@link ResourceLocation}
     */
    public static NetworkHandler create(ResourceLocation rl,
                                        Supplier<String> version,
                                        Predicate<String> acceptVersion) {
        return HANDLERS.computeIfAbsent(rl, resourceLocation -> new NetworkHandler(resourceLocation, version, acceptVersion));
    }

    /**
     * Create or get the {@link NetworkHandler} for the modid, and the channel name with the given version supplier and predicates
     *
     * @param modid               the modid
     * @param channelName         the name for the network channel
     * @param version             the supplier for the version string
     * @param acceptVersionClient the predicate to check if the client version is valid
     * @param acceptVersionServer the predicate to check if the server version is valid
     * @return the {@link NetworkHandler} associated with the modid, and the channel name
     */
    public static NetworkHandler create(String modid,
                                        String channelName,
                                        Supplier<String> version,
                                        Predicate<String> acceptVersionClient,
                                        Predicate<String> acceptVersionServer) {
        return create(new ResourceLocation(modid, channelName), version, acceptVersionClient, acceptVersionServer);
    }

    /**
     * Create or get the {@link NetworkHandler} for the resourceLocation with the given version supplier and predicates
     *
     * @param rl                  the {@link ResourceLocation} for the network channel
     * @param version             the supplier for the version string
     * @param acceptVersionClient the predicate to check if the client version is valid
     * @param acceptVersionServer the predicate to check if the server version is valid
     * @return the {@link NetworkHandler} associated with the {@link ResourceLocation}
     */
    public static NetworkHandler create(ResourceLocation rl,
                                        Supplier<String> version,
                                        Predicate<String> acceptVersionClient,
                                        Predicate<String> acceptVersionServer) {
        return HANDLERS.computeIfAbsent(rl, resourceLocation -> new NetworkHandler(resourceLocation, version, acceptVersionClient, acceptVersionServer));
    }

    private NetworkHandler(ResourceLocation rl, String version) {
        this(rl, () -> version, version::equals);
    }

    private NetworkHandler(ResourceLocation rl,
                           Supplier<String> version,
                           Predicate<String> acceptVersion) {
        this(rl, version, acceptVersion, acceptVersion);
    }

    private NetworkHandler(ResourceLocation rl,
                           Supplier<String> version,
                           Predicate<String> acceptVersionClient,
                           Predicate<String> acceptVersionServer) {
        this.logger = LogManager.getLogger(String.format("com.github.minecraftschurli.simplenetlib.NetworkHandler(%s)", rl));
        this.logger.debug("Created com.github.minecraftschurli.simplenetlib.NetworkHandler for mod with id {}", rl);
        this.channel = NetworkRegistry.newSimpleChannel(rl, version, acceptVersionClient, acceptVersionServer);
    }

    private int nextID() {
        synchronized (id) {
            return id.getAndIncrement();
        }
    }

    /**
     * Register a Packet for a {@link NetworkDirection}
     *
     * @param clazz the class of the Packet implementing {@link IPacket}
     * @param dir   the {@link NetworkDirection} for the Packet
     * @param <T>   the type of the Packet implementing {@link IPacket}
     */
    public <T extends IPacket> void register(Class<T> clazz, @Nullable NetworkDirection dir) {
        this.register(clazz, Optional.ofNullable(dir));
    }

    @SuppressWarnings("unchecked")
    private <T extends IPacket> void register(Class<T> clazz, Optional<NetworkDirection> dir) {
        BiConsumer<T, FriendlyByteBuf> encoder = IPacket::serialize;
        Function<FriendlyByteBuf, T> decoder = Arrays.stream(clazz.getConstructors())
                .map(constructor -> (Constructor<T>)constructor)
                .filter(constructor ->
                        constructor.getParameterCount() == 0 ||
                                (constructor.getParameterCount() == 1 &&
                                        constructor.getParameterTypes()[0].equals(FriendlyByteBuf.class)))
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .<ThrowingFunction<FriendlyByteBuf, T>>map(constructor -> {
                    if (constructor.getParameterCount() == 0) {
                        return (buf) -> {
                            T packet = constructor.newInstance();
                            packet.deserialize(buf);
                            return packet;
                        };
                    } else {
                        return constructor::newInstance;
                    }
                }).orElseThrow(() -> new IllegalArgumentException(String.format("%s does not supply a deserialization mechanism", clazz)));
        BiConsumer<T, Supplier<NetworkEvent.Context>> consumer = (msg, supp) -> {
            NetworkEvent.Context context = supp.get();
            if (context == null) {
                return;
            }
            if (dir.filter(d -> d == context.getDirection()).isEmpty()) {
                return;
            }
            context.setPacketHandled(msg.handle_(context));
        };
        this.register(clazz, encoder, decoder, consumer, dir);
    }

    /**
     * Register a Packet with defined encoder, decoder, consumer and direction
     *
     * @param clazz the class of the registered Packet
     * @param encoder the encoder for the Packet
     * @param decoder the decoder for the Packet
     * @param consumer the consumer that handles the Packet
     * @param dir the Optional NetworkDirection for the Packet
     * @param <T> the Type of the Packet
     */
    public <T extends IPacket> void register(Class<T> clazz,
                                              BiConsumer<T, FriendlyByteBuf> encoder,
                                              Function<FriendlyByteBuf, T> decoder,
                                              BiConsumer<T, Supplier<NetworkEvent.Context>> consumer,
                                              Optional<NetworkDirection> dir) {
        if (dir.isPresent()) {
            this.logger.debug(REGISTER_MARKER, "Registered package {} for direction {}", clazz.getName(), dir.get().name());
        } else {
            this.logger.debug(REGISTER_MARKER, "Registered package {} for all directions", clazz.getName());
        }
        this.channel.registerMessage(nextID(), clazz, encoder, decoder, consumer, dir);
    }

    /**
     * Sends a Packet to all Players in world
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     */
    public void sendToWorld(IPacket packet, LevelAccessor world) {
        if (world.isClientSide()) {
            this.logger.debug(SEND_MARKER, "Tried to send a message from the wrong side");
            return;
        }
        this.logger.debug(SEND_MARKER, "Sending packet {} from server to the world {}", packet.getClass().getName(), world);
        this.channel.send(PacketDistributor.DIMENSION.with(((Level) world)::dimension), packet);
    }

    /**
     * Sends a Packet to all Players in a defined radius
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     * @param pos    the center position around which the radius is calculated
     * @param radius the radius in which players receive the packet
     */
    public void sendToAllAround(IPacket packet, LevelAccessor world, BlockPos pos, float radius) {
        if (world.isClientSide()) {
            this.logger.debug(SEND_MARKER, "Tried to send a message from the wrong side");
            return;
        }
        this.logger.debug(SEND_MARKER, "Sending packet {} to all clients in the world {} in radius {} around position {}", packet.getClass().getName(), world, radius, pos);
        this.channel.send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), radius, ((Level) world).dimension())), packet);
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     * @param pos    the position within the tracked chunk
     */
    public void sendToAllTracking(IPacket packet, LevelAccessor world, BlockPos pos) {
        this.sendToAllTracking(packet, ((ServerLevel) world).getChunkAt(pos));
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     * @param pos    the position of the chunk
     */
    public void sendToAllTracking(IPacket packet, LevelAccessor world, ChunkPos pos) {
        this.sendToAllTracking(packet, ((ServerLevel) world).getChunkAt(pos.getWorldPosition()));
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param chunk  the tracked Chunk
     */
    public void sendToAllTracking(IPacket packet, LevelChunk chunk) {
        if (chunk.getLevel().isClientSide()) {
            this.logger.debug(SEND_MARKER, "Tried to send a message from the wrong side");
            return;
        }
        this.logger.debug(SEND_MARKER, "Sending packet {} to all clients tracking chunk {}", packet.getClass().getName(), chunk);
        this.channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
    }

    /**
     * Sends a Packet to all Players tracking the state of an entity
     *
     * @side server
     * @param packet the Packet to send
     * @param entity the tracked Entity
     */
    public void sendToAllTracking(IPacket packet, Entity entity) {
        if (entity.level.isClientSide()) {
            this.logger.debug(SEND_MARKER, "Tried to send a message from the wrong side");
            return;
        }
        this.logger.debug(SEND_MARKER, "Sending packet {} to all clients tracking entity {}", packet.getClass().getName(), entity);
        this.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    /**
     * Sends a Packet to the specified Player
     *
     * @side server
     * @param packet the Packet to send
     * @param player the Player to send to
     */
    public void sendToPlayer(IPacket packet, Player player) {
        if (player.level.isClientSide() || !(player instanceof ServerPlayer)) {
            this.logger.debug(SEND_MARKER, "Tried to send a message from the wrong side");
            return;
        }
        this.logger.debug(SEND_MARKER, "Sending packet {} to player {}", packet.getClass().getName(), player);
        this.channel.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
    }

    /**
     * Sends a Packet to all connected clients
     *
     * @side server
     * @param packet the Packet to send
     */
    public void sendToAll(IPacket packet) {
        this.logger.debug(SEND_MARKER, "Sending packet {} to all clients", packet.getClass().getName());
        this.channel.send(PacketDistributor.ALL.noArg(), packet);
    }

    /**
     * Send a Packet to the server
     *
     * @side client
     * @param packet the Packet to send
     */
    public void sendToServer(IPacket packet) {
        this.logger.debug(SEND_MARKER, "Sending packet {} to server", packet.getClass().getName());
        this.channel.sendToServer(packet);
    }

    /**
     * Reply to a received message with packet
     *
     * @side both
     * @param packet  the Packet representing the replying message
     * @param context the context of the received Packet
     */
    public void reply(IPacket packet, NetworkEvent.Context context) {
        this.logger.debug(SEND_MARKER, "Sending packet {} as reply to context NetworkEvent.Context[{}]", packet.getClass().getName(), context.getDirection().name());
        this.channel.reply(packet, context);
    }

    private interface ThrowingFunction<T, R> extends Function<T, R> {
        R applyThrowing(T t) throws Exception;

        @Override
        default R apply(T t) {
            try {
                return this.applyThrowing(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
