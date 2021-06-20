package com.github.minecraftschurli.simplenetlib;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class makes communication between server and client easier by wrapping the vanilla SimpleChannel network implementation
 *
 * @author Minecraftschurli
 * @version 2021-06-15
 */
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class NetworkHandler {
    private static final Map<ResourceLocation, NetworkHandler> HANDLERS = new HashMap<>();

    public final Lazy<SimpleChannel> channel;
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
        this.channel = Lazy.of(()->NetworkRegistry.newSimpleChannel(rl, version, acceptVersionClient, acceptVersionServer));
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

    private <T extends IPacket> void register(Class<T> clazz, Optional<NetworkDirection> dir) {
        BiConsumer<T, PacketBuffer> encoder = IPacket::serialize;
        Function<PacketBuffer, T> decoder = (buf) -> {
            try {
                T packet = clazz.getConstructor().newInstance();
                packet.deserialize(buf);
                return packet;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        BiConsumer<T, Supplier<NetworkEvent.Context>> consumer = (msg, supp) -> {
            NetworkEvent.Context context = supp.get();
            if (context == null) {
                return;
            }
            if (dir.filter(d -> context.getDirection() == d).isPresent()) {
                return;
            }
            context.setPacketHandled(msg.handleInt(context));
        };
        this.register(clazz, encoder, decoder, consumer, dir);
    }

    private <T extends IPacket> void register(Class<T> clazz,
                                              BiConsumer<T, PacketBuffer> encoder,
                                              Function<PacketBuffer, T> decoder,
                                              BiConsumer<T, Supplier<NetworkEvent.Context>> consumer,
                                              Optional<NetworkDirection> dir) {
        this.channel.get().registerMessage(nextID(), clazz, encoder, decoder, consumer, dir);
    }

    /**
     * Sends a Packet to all Players in world
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     */
    public void sendToWorld(IPacket packet, IWorld world) {
        if (world.isClientSide()) {
            return;
        }
        this.channel.get().send(PacketDistributor.DIMENSION.with(((World) world)::dimension), packet);
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
    public void sendToAllAround(IPacket packet, IWorld world, BlockPos pos, float radius) {
        if (world.isClientSide()) {
            return;
        }
        this.channel.get().send(PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), radius, ((World) world).dimension())), packet);
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     * @param pos    the position within the tracked chunk
     */
    public void sendToAllTracking(IPacket packet, IWorld world, BlockPos pos) {
        this.sendToAllTracking(packet, ((ServerWorld) world).getChunkAt(pos));
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param world  the World to send to
     * @param pos    the position of the chunk
     */
    public void sendToAllTracking(IPacket packet, IWorld world, ChunkPos pos) {
        this.sendToAllTracking(packet, ((ServerWorld) world).getChunkAt(pos.getWorldPosition()));
    }

    /**
     * Sends a Packet to all Players tracking the state of a chunk
     *
     * @side server
     * @param packet the Packet to send
     * @param chunk  the tracked Chunk
     */
    public void sendToAllTracking(IPacket packet, Chunk chunk) {
        if (chunk.getLevel().isClientSide()) {
            return;
        }
        this.channel.get().send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
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
            return;
        }
        this.channel.get().send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), packet);
    }

    /**
     * Sends a Packet to the specified Player
     *
     * @side server
     * @param packet the Packet to send
     * @param player the Player to send to
     */
    public void sendToPlayer(IPacket packet, PlayerEntity player) {
        if (player.level.isClientSide() || !(player instanceof ServerPlayerEntity)) {
            return;
        }
        this.channel.get().send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), packet);
    }

    /**
     * Sends a Packet to all connected clients
     *
     * @side server
     * @param packet the Packet to send
     */
    public void sendToAll(IPacket packet) {
        this.channel.get().send(PacketDistributor.ALL.noArg(), packet);
    }

    /**
     * Send a Packet to the server
     *
     * @side client
     * @param packet the Packet to send
     */
    public void sendToServer(IPacket packet) {
        this.channel.get().sendToServer(packet);
    }

    /**
     * Reply to a received message with packet
     *
     * @side both
     * @param packet  the Packet representing the replying message
     * @param context the context of the received Packet
     */
    public void reply(IPacket packet, NetworkEvent.Context context) {
        this.channel.get().reply(packet, context);
    }
}
