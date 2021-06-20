package com.github.minecraftschurli.arsmagicalegacy.data;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Minecraftschurli
 * @version 2021-06-20
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArsMagicaLegacyDatagen {
    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent evt) {
        LOGGER.info("Running Datagens");
    }
}
