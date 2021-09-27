package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * Interface representing an affinity holder
 */
public interface IAffinityHolder {
    /**
     * Get the depths map for this affinity holder.
     *
     * @return the unmodifiable view of the affinity depths map
     */
    Map<ResourceLocation, Double> depths();

    /**
     * Get the depth for a given affinity.
     *
     * @param affinity the affinity to get the depth for
     * @return the depth of the requested affinity or 0 if not available
     */
    default double getAffinityDepth(ResourceLocation affinity) {
        return depths().getOrDefault(affinity, 0d);
    }

    /**
     * Get the depth for a given affinity.
     *
     * @param affinity the affinity to get the depth for
     * @return the depth of the requested affinity or 0 if not available
     */
    default double getAffinityDepth(IAffinity affinity) {
        return getAffinityDepth(affinity.getRegistryName());
    }
}
