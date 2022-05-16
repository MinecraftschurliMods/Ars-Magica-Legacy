package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IRegistryEntry;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

/**
 * Interface representing an affinity.
 */
public interface IAffinity extends IRegistryEntry, Comparable<IAffinity>, ITranslatable.OfRegistryEntry<IAffinity> {
    ResourceKey<Registry<IAffinity>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "affinity"));
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
     * @return The color of this affinity.
     */
    int getColor();

    /**
     * @return The minor opposing affinities for this affinity.
     */
    Set<ResourceLocation> getMinorOpposingAffinities();

    /**
     * @return The major opposing affinities for this affinity.
     */
    Set<ResourceLocation> getMajorOpposingAffinities();

    /**
     * @return The adjacent affinities for this affinity.
     */
    Set<ResourceLocation> getAdjacentAffinities();

    /**
     * @return The direct opposing affinity for this affinity.
     */
    ResourceLocation getDirectOpposingAffinity();

    /**
     * @return The sound that should be played when casting a spell with this affinity.
     */
    @Nullable
    SoundEvent getCastSound();

    /**
     * @return The sound that should be played when casting a continuous spell with this affinity.
     */
    @Nullable
    SoundEvent getLoopSound();

    @Override
    default ResourceKey<Registry<IAffinity>> getRegistryKey() {
        return REGISTRY_KEY;
    }

    @Override
    default int compareTo(IAffinity o) {
        return Objects.compare(getRegistryName(), o.getRegistryName(), ResourceLocation::compareTo);
    }
}
