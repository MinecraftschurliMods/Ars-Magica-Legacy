package com.github.minecraftschurli.arsmagicalegacy.api.affinity;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/**
 * TODO
 */
public interface IAffinityHolder {
    Map<ResourceLocation, Double> depths();
    default double getAffinityDepth(ResourceLocation affinity) {
        return depths().getOrDefault(affinity, 0d);
    }
    default double getAffinityDepth(IAffinity affinity) {
        return getAffinityDepth(affinity.getRegistryName());
    }
}
