package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Objects;

/**
 * Utility interface for providing easy localization.
 */
public interface ITranslatable {
    /**
     * @return The id of this object.
     */
    ResourceLocation getId();

    /**
     * @return The type of this object, for example "block" or "item".
     */
    String getType();

    /**
     * @return The translation key for this object.
     */
    default String getTranslationKey() {
        return Util.makeDescriptionId(getType(), getId());
    }

    /**
     * @return A component containing the display name of this object.
     */
    default Component getDisplayName() {
        return new TranslatableComponent(getTranslationKey());
    }

    /**
     * ITranslatable that also has a description.
     */
    interface WithDescription extends ITranslatable {
        @Override
        default Component getDisplayName() {
            return new TranslatableComponent(getNameTranslationKey());
        }

        /**
         * @return A component containing the description of this object
         */
        default Component getDescription() {
            return new TranslatableComponent(getDescriptionTranslationKey());
        }

        /**
         * @return The translation key for the name
         */
        default String getNameTranslationKey() {
            return ITranslatable.super.getTranslationKey() + ".name";
        }

        /**
         * @return The translation key for the description
         */
        default String getDescriptionTranslationKey() {
            return ITranslatable.super.getTranslationKey() + ".description";
        }
    }

    /**
     * ITranslatable that handles the id using a registry entry.
     */
    interface OfRegistryEntry<T extends IForgeRegistryEntry<T>> extends IForgeRegistryEntry<T>, ITranslatable {
        @Override
        default ResourceLocation getId() {
            return Objects.requireNonNull(getRegistryName());
        }

        /**
         * ITranslatable.OfRegistryEntry that also has a description.
         */
        interface WithDescription<T extends IForgeRegistryEntry<T>> extends OfRegistryEntry<T>, ITranslatable.WithDescription {
        }
    }
}
