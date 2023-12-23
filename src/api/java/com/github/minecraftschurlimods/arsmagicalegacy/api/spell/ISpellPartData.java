package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ItemFilter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Interface representing spell part data.
 */
public interface ISpellPartData {
    /**
     * @return The recipe for this spell part.
     */
    List<ISpellIngredient> recipe();

    /**
     * @return The affinity shifts for this spell part.
     */
    Map<Affinity, Float> affinityShifts();

    /**
     * @return The affinities for this spell part.
     */
    Set<Affinity> affinities();

    /**
     * @return The reagents for this spell part.
     */
    List<ItemFilter> reagents();

    /**
     * @return The mana cost for this spell part.
     */
    float manaCost();

    /**
     * @return The burnout for this spell part.
     */
    float getBurnout();
}
