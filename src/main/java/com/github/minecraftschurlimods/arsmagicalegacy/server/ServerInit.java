package com.github.minecraftschurlimods.arsmagicalegacy.server;

import net.neoforged.neoforge.common.NeoForge;

public final class ServerInit {
    /**
     * Registers the server event handlers.
     */
    public static void init() {
        NeoForge.EVENT_BUS.addListener(AMPermissions::registerPermissionNodes);
        NeoForge.EVENT_BUS.addListener(AMCommands::registerCommands);
        NeoForge.EVENT_BUS.addListener(NatureGuardianSpawnHandler::dryadDeath);
    }
}
