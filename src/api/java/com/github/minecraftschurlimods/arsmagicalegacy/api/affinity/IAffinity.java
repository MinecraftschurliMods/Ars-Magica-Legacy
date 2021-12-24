package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;
import java.util.Set;

/**
 * Interface representing an affinity
 */
public interface IAffinity extends IForgeRegistryEntry<IAffinity>, Comparable<IAffinity>, ITranslatable.OfRegistryEntry<IAffinity> {
    String AFFINITY = "affinity";
    ResourceLocation NONE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "none");
    ResourceLocation ARCANE    = new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane");
    ResourceLocation WATER     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "water");
    ResourceLocation FIRE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire");
    ResourceLocation EARTH     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth");
    ResourceLocation AIR       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "air");
    ResourceLocation LIGHTNING = new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning");
    ResourceLocation ICE       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice");
    ResourceLocation NATURE    = new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature");
    ResourceLocation LIFE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "life");
    ResourceLocation ENDER     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender");

    /**
     * Will return the color used by the occulus to render this affinity depth.
     *
     * @return the color of the affinity
     */
    int getColor();

    /**
     * Get the minor opposing affinities for this affinity.
     *
     * @return the minor opposing affinities for this affinity
     */
    Set<ResourceLocation> getMinorOpposingAffinities();

    /**
     * Get the major opposing affinities for this affinity.
     *
     * @return the major opposing affinities for this affinity
     */
    Set<ResourceLocation> getMajorOpposingAffinities();

    /**
     * Get the adjacent affinities for this affinity.
     *
     * @return the adjacent affinities for this affinity
     */
    Set<ResourceLocation> getAdjacentAffinities();

    /**
     * Get the direct opposing affinity for this affinity.
     *
     * @return the direct opposing affinity for this affinity
     */
    ResourceLocation getDirectOpposingAffinity();

    @Override
    default String getType() {
        return AFFINITY;
    }

    @Override
    default int compareTo(IAffinity o) {
        if (o.getRegistryName() == null && getRegistryName() != null) return 1;
        return Objects.compare(getRegistryName(), o.getRegistryName(), ResourceLocation::compareTo);
    }
}
