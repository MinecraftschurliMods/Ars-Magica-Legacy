package com.github.minecraftschurlimods.arsmagicalegacy.compat;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public interface ICompatHandler {
    /**
     * Pre-initializes the compat handler.
     */
    default void preInit(IEventBus modBus) {
    }

    /**
     * Initializes the compat handler.
     */
    default void init(FMLCommonSetupEvent event) {
    }

    /**
     * Initializes the client-only parts of the compat handler.
     */
    default void clientInit(FMLClientSetupEvent event) {
    }

    /**
     * Enqueues the compat handler to the InterModEnqueueEvent.
     */
    default void imcEnqueue(InterModEnqueueEvent event) {
    }
}
