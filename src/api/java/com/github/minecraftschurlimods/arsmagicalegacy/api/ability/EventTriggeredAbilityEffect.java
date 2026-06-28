package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.Event;

/// Specialization of [AbilityEffect] that is triggered by an [Event].
///
/// @param <T> The type of the [Event].
public interface EventTriggeredAbilityEffect<T extends Event> extends AbilityEffect {
    /// Called when the effect is triggered from an [Event].
    ///
    /// @param event   The [Event] that triggered the effect.
    /// @param player  The [Player] the [Ability] is triggered on.
    /// @param ability The [Ability] that is triggered.
    void apply(T event, Player player, Holder<Ability> ability);
}
