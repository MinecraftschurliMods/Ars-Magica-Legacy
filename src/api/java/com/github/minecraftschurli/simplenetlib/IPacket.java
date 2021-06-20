package com.github.minecraftschurli.simplenetlib;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * @author Minecraftschurli
 * @version 2021-06-15
 */
public interface IPacket {
    void serialize(PacketBuffer buf);

    void deserialize(PacketBuffer buf);

    default boolean handleInt(NetworkEvent.Context ctx) {
        this.handle(ctx);
        return true;
    }

    void handle(NetworkEvent.Context ctx);
}
