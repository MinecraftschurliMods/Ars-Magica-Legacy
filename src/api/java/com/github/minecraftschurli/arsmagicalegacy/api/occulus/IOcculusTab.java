package com.github.minecraftschurli.arsmagicalegacy.api.occulus;

import com.github.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurli.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * Interface representing a tab inside the occulus
 */
public interface IOcculusTab extends ITranslatable {
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

    /**
     * Get the width of the background texture of this occulus tab.
     *
     * @return the width of the background texture
     */
    int getWidth();

    /**
     * Get the height of the background texture of this occulus tab.
     *
     * @return the height of the background texture
     */
    int getHeight();

    /**
     * Get the path of the renderer class.
     *
     * @return the path of the renderer class
     */
    String getRenderer();

    /**
     * Get the lazy renderer factory.
     * <p>WARNING! only call on client</p>
     *
     * @side client
     * @return the lazy renderer factory
     */
    Supplier<OcculusTabRendererFactory> getRendererFactory();

    @Override
    default String getType() {
        return OCCULUS_TAB;
    }

    @SuppressWarnings("unchecked")
    interface OcculusTabRendererFactory {
        static Supplier<OcculusTabRendererFactory> of(String clazz) {
            return () -> {
                try {
                    Constructor<? extends OcculusTabRenderer> constructor = ((Class<? extends OcculusTabRenderer>) Class.forName(clazz)).getConstructor(IOcculusTab.class);
                    return tab -> {
                        try {
                            return constructor.newInstance(tab);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        OcculusTabRenderer create(IOcculusTab tab);
    }
}
