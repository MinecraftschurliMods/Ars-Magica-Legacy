package com.github.minecraftschurlimods.arsmagicalegacy.api.constants;

import net.minecraft.core.RegistryAccess;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/// Classloading barrier for getting the server-side [RegistryAccess].
final class ServerRegistryAccess {
    /// @return The server-side [RegistryAccess].
    @SuppressWarnings("DataFlowIssue")
    static RegistryAccess get() {
        return ServerLifecycleHooks.getCurrentServer().registryAccess();
    }
}
