package com.github.minecraftschurli.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

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
    default Component getDisplayName() {
        return new TranslatableComponent(getTranslationKey());
    }

    /**
     * Like {@link ITranslatable} but also has a description
     */
    interface WithDescription extends ITranslatable {
        @Override
        default Component getDisplayName() {
            return new TranslatableComponent(getTranslationKey()+".name");
        }

        /**
         * Get the {@link Component} containing the description of this object
         *
         * @return the {@link Component} containing the description of this object
         */
        default Component getDescription() {
            return new TranslatableComponent(getTranslationKey()+".description");
        }
    }
}
