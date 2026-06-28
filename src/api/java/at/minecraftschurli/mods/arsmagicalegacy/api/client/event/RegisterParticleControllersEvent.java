package at.minecraftschurli.mods.arsmagicalegacy.api.client.event;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.particle.ParticleController;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.event.IModBusEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/// Event that fires when [ParticleController.Type]s are registered.
///
/// This event is not cancelable. This event is fired on the mod event bus, only on the physical client.
public class RegisterParticleControllersEvent extends Event implements IModBusEvent {
    private final Map<Identifier, ParticleController.Type> controllers = new HashMap<>();

    /// Registers a [ParticleController.Type].
    ///
    /// @param key   The id of the [ParticleController.Type].
    /// @param codec A [MapCodec] for the [ParticleController.Type].
    public synchronized void register(Identifier key, MapCodec<? extends ParticleController> codec) {
        controllers.put(key, new ParticleController.Type(key, codec));
    }

    /// @return An unmodifiable view of all registered [ParticleController]s.
    public Map<Identifier, ParticleController.Type> getControllers() {
        return Collections.unmodifiableMap(controllers);
    }
}
