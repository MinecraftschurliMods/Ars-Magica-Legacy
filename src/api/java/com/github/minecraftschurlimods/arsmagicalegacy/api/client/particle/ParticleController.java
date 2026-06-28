package com.github.minecraftschurlimods.arsmagicalegacy.api.client.particle;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

import java.util.Objects;
import java.util.Optional;

/// Represents a particle controller, as serialized from a [ParticleSpawner]. To make a tickable instance, see [ParticleControllerInstance].
///
/// Register [ParticleController]s during [RegisterParticleControllersEvent], using the [ParticleController.Type] record.
public interface ParticleController {
    Codec<ParticleController> CODEC = Identifier.CODEC.comapFlatMap(
        id -> Optional.ofNullable(ArsMagicaClientApi.particleController(id))
            .map(DataResult::success)
            .orElseGet(() -> DataResult.error(() -> "Unknown particle controller: " + id)),
        ParticleController.Type::id
    ).dispatch(controller -> Objects.requireNonNull(ArsMagicaClientApi.particleController(controller.id())), ParticleController.Type::codec);

    /// @param instance The [RecordCodecBuilder.Instance] to use.
    /// @return A codec builder with the base fields for every controller set. Call [Products.P3#and(App)] to add further fields.
    /// @param <T> The exact type of the controller.
    static <T extends ParticleController> Products.P2<RecordCodecBuilder.Mu<T>, Boolean, Boolean> baseFields(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
            Codec.BOOL.optionalFieldOf("stop_other_controllers", false).forGetter(ParticleController::stopOtherControllers),
            Codec.BOOL.optionalFieldOf("kill_on_finish", false).forGetter(ParticleController::killOnFinish));
    }

    /// Ticks the given [ParticleControllerInstance].
    ///
    /// @param instance The [ParticleControllerInstance] to tick.
    void tick(ParticleControllerInstance instance);

    /// Ticks the given [ParticleControllerInstance] on its first tick. Override this for special behavior on first tick.
    ///
    /// @param instance The [ParticleControllerInstance] to tick.
    default void tickFirst(ParticleControllerInstance instance) {
        tick(instance);
    }

    /// @return The registered id of the controller.
    Identifier id();

    /// @return Whether all further controllers are stopped when this controller is run.
    boolean stopOtherControllers();

    /// @return Whether the particle should be removed after this controller has finished.
    boolean killOnFinish();

    /// The registered type of a [ParticleController].
    ///
    /// @param id    The id of the controller.
    /// @param codec The [MapCodec] of the controller.
    record Type(Identifier id, MapCodec<? extends ParticleController> codec) {
    }
}
