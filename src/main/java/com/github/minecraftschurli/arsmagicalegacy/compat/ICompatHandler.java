package com.github.minecraftschurli.arsmagicalegacy.compat;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface ICompatHandler {
    default void preInit() {
    }

    default void init(FMLCommonSetupEvent event) {
    }

    default void clientInit(FMLClientSetupEvent event) {
    }
}
