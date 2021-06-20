package com.github.minecraftschurli.arsmagicalegacy.server;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Minecraftschurli
 * @version 2021-06-19
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerInit {
    @SubscribeEvent
    static void registerCommands(final RegisterCommandsEvent event) {
    }
}
