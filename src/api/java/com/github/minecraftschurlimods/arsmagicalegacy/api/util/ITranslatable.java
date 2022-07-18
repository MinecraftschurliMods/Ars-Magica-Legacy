package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Utility interface for providing easy localization.
 * @apiNote At least one of the getId() methods must be implemented.
 */
public interface ITranslatable {
    /**
     * @return The id of this object.
     */
    default ResourceLocation getId(RegistryAccess access) {
        return getId();
    }

    /**
     * @return The id of this object.
     */
    default ResourceLocation getId() {
        return getId(RegistryAccess.BUILTIN.get());
    }

    /**
     * @return The type of this object, for example "block" or "item".
     */
    String getType();

    /**
     * @return The translation key for this object.
     */
    default String getTranslationKey(RegistryAccess access) {
        return Util.makeDescriptionId(getType(), getId(access));
    }

    /**
     * @return The translation key for this object.
     */
    default String getTranslationKey() {
        return Util.makeDescriptionId(getType(), getId());
    }

    /**
     * @return A component containing the display name of this object.
     */
    default Component getDisplayName(RegistryAccess access) {
        return Component.translatable(getTranslationKey(access));
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
        default Component getDisplayName(RegistryAccess access) {
            return Component.translatable(getNameTranslationKey(access));
        }

        @Override
        default Component getDisplayName() {
            return Component.translatable(getNameTranslationKey());
        }

        /**
         * @return A component containing the description of this object
         */
        default Component getDescription(RegistryAccess access) {
            return Component.translatable(getDescriptionTranslationKey(access));
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
        default String getNameTranslationKey(RegistryAccess access) {
            return ITranslatable.super.getTranslationKey(access) + ".name";
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
        default String getDescriptionTranslationKey(RegistryAccess access) {
            return ITranslatable.super.getTranslationKey(access) + ".description";
        }

        /**
         * @return The translation key for the description
         */
        default String getDescriptionTranslationKey() {
            return ITranslatable.super.getTranslationKey() + ".description";
        }
    }
}
