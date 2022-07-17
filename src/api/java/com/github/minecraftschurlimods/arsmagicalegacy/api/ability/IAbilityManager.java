package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

/**
 * Provides and manages the data for all abilities and some helper methods.
 */
public interface IAbilityManager extends IDataManager<IAbilityData> {

    /**
     * @param player The player to check.
     * @return The abilities the given player currently has.
     */
    List<ResourceLocation> getAbilitiesForPlayer(Player player);

    /**
     * @param affinity The affinity to check.
     * @return All abilities for the given affinity.
     */
    List<ResourceLocation> getAbilitiesForAffinity(Affinity affinity);

    /**
     * @param affinity The id of the affinity to check.
     * @return All abilities for the given affinity.
     */
    List<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity);

    /**
     * @param player The player to check.
     * @param ability The ability to check.
     * @return Whether the player has the ability.
     */
    boolean hasAbility(Player player, ResourceLocation ability);
}
