package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
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
        return Component.translatable(getTranslationKey());
    }

    /**
     * ITranslatable that also has a description.
     */
    interface WithDescription extends ITranslatable {
        @Override
        default Component getDisplayName() {
            return Component.translatable(getNameTranslationKey());
        }

        /**
         * @return A component containing the description of this object
         */
        default Component getDescription() {
            return Component.translatable(getDescriptionTranslationKey());
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
}
