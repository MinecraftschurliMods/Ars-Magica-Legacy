package at.minecraftschurli.mods.arsmagicalegacy.api.ability;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

/// Represents an ability effect. One [Ability] may have multiple ability effects.
public interface AbilityEffect {
    Codec<AbilityEffect> CODEC = Codec.lazyInitialized(() -> AMRegistries.ABILITY_EFFECTS.byNameCodec().dispatch(AbilityEffect::codec, Function.identity()));

    /// @return The registered [MapCodec] of the ability effect.
    MapCodec<? extends AbilityEffect> codec();

    /// Called when a [Player] shifts into an [Ability] with the effect.
    ///
    /// @param player  The [Player] shifting into the [Ability].
    /// @param ability The [Ability] the player is shifting into.
    default void shiftInto(Player player, Holder<Ability> ability) {
    }

    /// Called when a [Player] shifts into an [Ability] with the effect.
    ///
    /// @param player  The [Player] shifting into the [Ability].
    /// @param ability The [Ability] the player is shifting into.
    default void shiftOutOf(Player player, Holder<Ability> ability) {
    }

    /// Called every tick when an [Ability] with the effect is active on the given [Player].
    ///
    /// @param player  The [Player] the [Ability] is active on.
    /// @param ability The [Ability] that is active.
    default void tick(Player player, Holder<Ability> ability) {
    }
}
