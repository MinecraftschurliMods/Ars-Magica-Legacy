package at.minecraftschurli.mods.arsmagicalegacy.api.ritual;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/// Represents a ritual trigger. The triggers are to be called from code, and may have trigger-specific conditions.
///
/// @param <T> The object considered the context of the trigger.
public interface RitualTrigger<T> {
    Codec<RitualTrigger<?>> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_TRIGGERS.byNameCodec().dispatch(RitualTrigger::codec, Function.identity()));

    /// @return The registered [MapCodec].
    MapCodec<? extends RitualTrigger<T>> codec();

    /// @param player  The [Player] triggering the ritual.
    /// @param level   The [Level] the ritual is triggered in.
    /// @param vec     The [Vec3] the ritual is triggered at.
    /// @param context The context object.
    /// @return Whether the requirement should actually be triggered or not.
    boolean test(@Nullable Player player, Level level, Vec3 vec, T context);

    /// Consumes the trigger, if applicable and the ritual was successful. For example, the dropped item tick ritual trigger consumes the dropped items here.
    ///
    /// @param player  The [Player] triggering the ritual.
    /// @param level   The [Level] the ritual is triggered in.
    /// @param vec     The [Vec3] the ritual is triggered at.
    /// @param context The context object.
    default void consume(@Nullable Player player, Level level, Vec3 vec, T context) {
    }

    /// Adjusts the position of the ritual checks. For example, the set block ritual trigger uses this to apply a position offset.
    ///
    /// This runs after [RitualTrigger#test(Player, Level, Vec3, Object)] and before all other ritual methods, including [RitualTrigger#consume(Player, Level, Vec3, Object)].
    ///
    /// @param player  The [Player] triggering the ritual.
    /// @param level   The [Level] the ritual is triggered in.
    /// @param vec     The [Vec3] the ritual is triggered at.
    /// @param context The context object.
    /// @return The adjusted ritual position.
    default Vec3 adjustPosition(@Nullable Player player, Level level, Vec3 vec, T context) {
        return vec;
    }
}
