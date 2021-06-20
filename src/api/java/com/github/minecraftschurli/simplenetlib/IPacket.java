package com.github.minecraftschurli.simplenetlib;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

/**
 * The {@link IPacket} interface represents the base for all packets handled by simplenetlib.
 * All implementations need to have a default constructor
 *
 * @author Minecraftschurli
 * @version 2021-06-15
 */
public interface IPacket {
    /**
     * @param buf the {@link PacketBuffer} to put the information into
     */
    void serialize(PacketBuffer buf);

    /**
     * @param buf the {@link PacketBuffer} containing the information
     */
    void deserialize(PacketBuffer buf);

    /**
     * Handle the received message in this method
     * the data should already be deserialized when this is called
     *
     * @param ctx the {@link NetworkEvent.Context} of the received message
     */
    void handle(NetworkEvent.Context ctx);

    /**
     * Internal version of {@link this#handle} Override when you want to return something else than true
     *
     * @param ctx the {@link NetworkEvent.Context} of the received message
     * @return whether the packet was handled or not
     */
    default boolean handleInt(NetworkEvent.Context ctx) {
        this.handle(ctx);
        return true;
    }
}
