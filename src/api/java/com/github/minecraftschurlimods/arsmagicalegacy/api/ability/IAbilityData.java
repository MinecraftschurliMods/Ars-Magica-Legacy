package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.Range;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

/**
 * The data holding interface for an affinity ability.
 */
public interface IAbilityData extends Predicate<Player> {
    /**
     * @return The range of affinity depth the ability is active in.
     */
    Range range();

    /**
     * @return The affinity the ability belongs to.
     */
    IAffinity affinity();

    @Override
    default boolean test(Player player) {
        return range().test(ArsMagicaAPI.get().getAffinityHelper().getAffinityDepth(player, affinity()));
    }
}
