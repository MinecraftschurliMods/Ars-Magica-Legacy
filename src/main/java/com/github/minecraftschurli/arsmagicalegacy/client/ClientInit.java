package com.github.minecraftschurli.arsmagicalegacy.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author Minecraftschurli
 * @version 2021-06-19
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInit {
    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
    }
}
