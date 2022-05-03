package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

/**
 * The data holding interface for an affinity ability.
 */
public interface IAbilityData extends Predicate<Player> {
    /**
     * @return The bounds of affinity depth the ability is active in.
     */
    MinMaxBounds.Doubles bounds();

    /**
     * @return The affinity the ability belongs to.
     */
    IAffinity affinity();

    @Override
    default boolean test(Player player) {
        return bounds().matches(ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity()));
    }
}
