package at.minecraftschurli.mods.arsmagicalegacy.api.client.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/// Event that fires when [OcculusTabRenderer.Factory]s are registered.
///
/// This event is not cancelable. This event is fired on the mod event bus, only on the physical client.
public class RegisterOcculusTabRenderersEvent extends Event implements IModBusEvent {
    private final Map<Identifier, OcculusTabRenderer.Factory> renderers = new HashMap<>();

    /// Registers an [OcculusTabRenderer.Factory].
    ///
    /// @param key     The id of the [OcculusTabRenderer.Factory]. May be referenced in [OcculusTab]s.
    /// @param factory The [OcculusTabRenderer.Factory] to register.
    public synchronized void register(Identifier key, OcculusTabRenderer.Factory factory) {
        renderers.put(key, factory);
    }

    /// @return An unmodifiable view of all registered [OcculusTabRenderer.Factory]s.
    public Map<Identifier, OcculusTabRenderer.Factory> getRenderers() {
        return Collections.unmodifiableMap(renderers);
    }
}
