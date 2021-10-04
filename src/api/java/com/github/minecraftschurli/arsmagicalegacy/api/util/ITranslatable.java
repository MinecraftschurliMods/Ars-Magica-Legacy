package com.github.minecraftschurli.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

/**
 * Utility interface for providing easy localisation
 */
public interface ITranslatable {
    /**
     * Get the id for the current object<br>
     * used for generating the translation key
     *
     * @return the id of this object
     */
    ResourceLocation getId();

    /**
     * Get the type of the object (e.g. "item", "block", etc.)
     *
     * @return the type of this object
     */
    String getType();

    /**
     * Get the translation key for this object
     *
     * @return the translation key for this object
     */
    default String getTranslationKey() {
        return Util.makeDescriptionId(getType(), getId());
    }

    /**
     * Get the {@link Component} containing the display name of this object
     *
     * @return the {@link Component} containing the display name of this object
     */
    default MutableComponent getDisplayName() {
        return new TranslatableComponent(getTranslationKey());
    }

    /**
     * Like {@link ITranslatable} but also has a description
     */
    interface WithDescription extends ITranslatable {
        @Override
        default MutableComponent getDisplayName() {
            return new TranslatableComponent(getTranslationKey()+".name");
        }

        /**
         * Get the {@link Component} containing the description of this object
         *
         * @return the {@link Component} containing the description of this object
         */
        default MutableComponent getDescription() {
            return new TranslatableComponent(getTranslationKey()+".description");
        }
    }

    /**
     * Like {@link ITranslatable} but handles the id via {@link IForgeRegistryEntry#getRegistryName()}
     */
    interface OfRegistryEntry<T extends IForgeRegistryEntry<T>> extends IForgeRegistryEntry<T>, ITranslatable {
        @Override
        default ResourceLocation getId() {
            return Objects.requireNonNull(this.getRegistryName());
        }

        /**
         * Like {@link ITranslatable.OfRegistryEntry} but also has a description
         */
        interface WithDescription<T extends IForgeRegistryEntry<T>> extends OfRegistryEntry<T>, ITranslatable.WithDescription {
        }
    }
}
