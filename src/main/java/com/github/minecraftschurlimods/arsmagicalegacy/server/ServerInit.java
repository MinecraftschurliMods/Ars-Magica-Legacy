package com.github.minecraftschurlimods.arsmagicalegacy.server;

import net.minecraftforge.common.MinecraftForge;

public final class ServerInit {
    /**
     * Registers the server event handlers.
     */
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(AMPermissions::registerPermissionNodes);
        MinecraftForge.EVENT_BUS.addListener(AMCommands::registerCommands);
        MinecraftForge.EVENT_BUS.addListener(NatureGuardianSpawnHandler::dryadDeath);
    }
}
