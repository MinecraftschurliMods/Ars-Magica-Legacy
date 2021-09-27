package com.github.minecraftschurli.arsmagicalegacy.api.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Optional;

/**
 * Interface representing a tab inside the occulus
 */
public interface IOcculusTab extends IForgeRegistryEntry<IOcculusTab>, ITranslatable {
    String OCCULUS_TAB = "occulus_tab";
    int TEXTURE_WIDTH = 1024;
    int TEXTURE_HEIGHT = 1024;

    /**
     * Get the {@link ResourceLocation} of the background texture for this skill tree
     *
     * @return the {@link ResourceLocation} of the background texture for this skill tree
     */
    ResourceLocation getBackground();

    /**
     * Get the {@link ResourceLocation} of the icon texture for this skill tree
     *
     * @return the {@link ResourceLocation} of the icon texture for this skill tree
     */
    ResourceLocation getIcon();

    /**
     * Get the index this skill tree should appear at in the occulus
     *
     * @return the index this skill tree should appear at in the occulus
     */
    int getOcculusIndex();

    @Override
    default ResourceLocation getId() {
        return Optional.ofNullable(getRegistryName()).orElseThrow();
    }

    @Override
    default String getType() {
        return OCCULUS_TAB;
    }

    default int getWidth() {
        return TEXTURE_WIDTH;
    }

    default int getHeigth() {
        return TEXTURE_HEIGHT;
    }

    interface Reference<T> {
        void set(T val);
        T get();
    }
}
