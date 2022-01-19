package com.github.minecraftschurlimods.arsmagicalegacy.compat;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public interface ICompatHandler {
    default void preInit() {
    }

    default void init(FMLCommonSetupEvent event) {
    }

    default void clientInit(FMLClientSetupEvent event) {
    }

    default void imcEnqueue(InterModEnqueueEvent event) {
    }
}
