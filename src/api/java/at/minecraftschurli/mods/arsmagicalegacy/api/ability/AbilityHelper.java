package at.minecraftschurli.mods.arsmagicalegacy.api.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicAttachment;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

import java.util.List;
import java.util.stream.Stream;

/// Helper for operations related to a [Player]'s [Ability]s.
public interface AbilityHelper {
    /// Called when the [Player]'s [MagicAttachment] changes. Updates the [Ability]s accordingly.
    ///
    /// @param player  The [Player] whose [MagicAttachment] changes.
    /// @param oldData The old [MagicAttachment].
    /// @param newData The new [MagicAttachment].
    void onMagicChange(Player player, MagicAttachment oldData, MagicAttachment newData);

    /// @param player The [Player] to query.
    /// @return The active [Ability]s of the given [Player].
    Stream<? extends Holder<Ability>> getActiveAbilities(Player player);

    /// @param player      The [Player] to query.
    /// @param effectCodec The [AbilityEffect] type to filter for.
    /// @param <T>         The exact [AbilityEffect] type.
    /// @return A [Stream] of [Pair]s, each representing a [Ability] and its associated [AbilityEffect]s.
    <T extends AbilityEffect> Stream<? extends Pair<? extends Holder<Ability>, List<T>>> getActiveAbilitiesWithEffect(Player player, MapCodec<T> effectCodec);

    /// Triggers an [EventTriggeredAbilityEffect].
    ///
    /// @param event  The [Event] to trigger the [EventTriggeredAbilityEffect] from.
    /// @param player The [Player] causing the [Event].
    /// @param codec  The [AbilityEffect] type.
    /// @param <T>    The exact [Event] type.
    <T extends Event> void triggerEventEffect(T event, Player player, MapCodec<? extends EventTriggeredAbilityEffect<T>> codec);

    /// Linearly scales the given [Ability]'s range to the min and max provided, depending on the [Player]'s depth.
    /// If the depth is at the minimum bound, returns the min value. If the depth is at the maximum bound, returns the max value.
    /// If the ability is somewhere in between, linear interpolation is performed.
    ///
    /// @param player  The [Player] to query.
    /// @param ability The [Ability] to get the range from.
    /// @param min     The min value to use.
    /// @param max     The max value to use.
    /// @return A scaled value.
    double scaleToDepth(Player player, Ability ability, double min, double max);
}
