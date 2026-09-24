package at.minecraftschurli.mods.arsmagicalegacy.packet;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AirSled;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AirSledMovementPacket(int flags) implements CustomPacketPayload {
    public static final Type<AirSledMovementPacket> TYPE = new Type<>(ArsMagicaApi.id("air_sled_movement"));
    public static final StreamCodec<ByteBuf, AirSledMovementPacket> STREAM_CODEC = ByteBufCodecs.INT.map(AirSledMovementPacket::new, AirSledMovementPacket::flags);
    private static final int W = 0b1;
    private static final int S = 0b10;
    private static final int A = 0b100;
    private static final int D = 0b1000;
    private static final int SPACE = 0b10000;
    private static final int SHIFT = 0b100000;
    private static final int CTRL = 0b1000000;

    public AirSledMovementPacket(boolean w, boolean s, boolean a, boolean d, boolean space, boolean shift, boolean ctrl) {
        int flags = 0;
        if (w) {
            flags |= W;
        }
        if (s) {
            flags |= S;
        }
        if (a) {
            flags |= A;
        }
        if (d) {
            flags |= D;
        }
        if (space) {
            flags |= SPACE;
        }
        if (shift) {
            flags |= SHIFT;
        }
        if (ctrl) {
            flags |= CTRL;
        }
        this(flags);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        Player player = context.player();
        if (player.isPassenger() && player.getControlledVehicle() instanceof AirSled airSled) {
            airSled.control((flags & W) == W, (flags & S) == S, (flags & A) == A, (flags & D) == D, (flags & SPACE) == SPACE, (flags & SHIFT) == SHIFT, (flags & CTRL) == CTRL);
        }
    }
}
