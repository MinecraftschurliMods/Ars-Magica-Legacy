package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

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
            return new TranslatableComponent(getTranslationKey() + ".name");
        }

        /**
         * @return A component containing the description of this object
         */
        default Component getDescription() {
            return new TranslatableComponent(getTranslationKey() + ".description");
        }
    }

    /**
     * ITranslatable that handles the id using a registry entry.
     */
    interface OfRegistryEntry<T extends IRegistryEntry> extends IRegistryEntry, ITranslatable {
        @Override
        default ResourceLocation getId() {
            return getRegistryName();
        }

        @Override
        default String getType() {
            return getRegistryKey().location().getPath().replace('/', '.');
        }

        /**
         * ITranslatable.OfRegistryEntry that also has a description.
         */
        interface WithDescription<T extends IRegistryEntry> extends OfRegistryEntry<T>, ITranslatable.WithDescription {
        }
    }
}
