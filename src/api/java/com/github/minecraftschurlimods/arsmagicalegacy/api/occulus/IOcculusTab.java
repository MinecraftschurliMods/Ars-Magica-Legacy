package com.github.minecraftschurlimods.arsmagicalegacy.api.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/**
 * Interface representing a tab inside the occulus.
 */
public interface IOcculusTab extends ITranslatable {
    String OCCULUS_TAB = "occulus_tab";
    int TEXTURE_WIDTH = 1024;
    int TEXTURE_HEIGHT = 1024;

    /**
     * @return The location of the background texture for this skill tree.
     */
    ResourceLocation getBackground();

    /**
     * @return The location of the icon texture for this skill tree.
     */
    ResourceLocation getIcon();

    /**
     * @return The index this tab should appear at in the occulus.
     */
    int getOcculusIndex();

    /**
     * @return The width of the background texture.
     */
    int getWidth();

    /**
     * @return The height of the background texture.
     */
    int getHeight();

    /**
     * @return The initial X coordinate of the background texture.
     */
    int getStartX();

    /**
     * @return The initial Y coordinate of the background texture.
     */
    int getStartY();

    /**
     * @return The path of the renderer class.
     */
    String getRenderer();

    /**
     * Only call this on the client.
     *
     * @return The lazy renderer factory.
     */
    Supplier<OcculusTabRendererFactory> getRendererFactory();

    @Override
    default String getType() {
        return OCCULUS_TAB;
    }

    /**
     * Factory interface to create occulus tab renderers.
     */
    interface OcculusTabRendererFactory {
        /**
         * Helper method to create occulus tab renderers.
         *
         * @param clazz The renderer class to use.
         * @return A new occulus tab renderer.
         */
        static Supplier<OcculusTabRendererFactory> of(String clazz) {
            return () -> {
                try {
                    Constructor<? extends OcculusTabRenderer> constructor = ((Class<? extends OcculusTabRenderer>) Class.forName(clazz)).getConstructor(IOcculusTab.class, Screen.class);
                    return (tab, parent) -> {
                        try {
                            return constructor.newInstance(tab, parent);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    };
                } catch (NoSuchMethodException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            };
        }

        /**
         * Creates a new occulus tab renderer. Functional method of this interface.
         *
         * @param tab    The occulus tab to create the renderer for.
         * @param parent The parent screen to create the renderer for.
         * @return A new occulus tab renderer.
         */
        OcculusTabRenderer create(IOcculusTab tab, Screen parent);
    }
}
