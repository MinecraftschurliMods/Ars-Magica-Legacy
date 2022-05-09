package com.github.minecraftschurlimods.arsmagicalegacy.server;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.AMFeatures;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;

public final class ServerInit {
    /**
     * Registers the server event handlers.
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(AMPermissions::registerPermissionNodes);
        MinecraftForge.EVENT_BUS.addListener(AMCommands::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, AMFeatures::biomeLoading);
        MinecraftForge.EVENT_BUS.addListener(NatureGuardianSpawnHandler::dryadDeath);
    }
}
