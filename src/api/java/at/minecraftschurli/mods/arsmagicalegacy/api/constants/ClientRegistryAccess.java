package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;

/// Classloading barrier for getting the client-side [RegistryAccess].
final class ClientRegistryAccess {
    /// @return The client-side [RegistryAccess].
    @SuppressWarnings("DataFlowIssue")
    static RegistryAccess get() {
        return Minecraft.getInstance().getConnection().registryAccess();
    }
}
