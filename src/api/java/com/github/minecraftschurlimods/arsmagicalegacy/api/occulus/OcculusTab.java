package com.github.minecraftschurlimods.arsmagicalegacy.api.occulus;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.client.OcculusTabRenderer;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @param rendererClass   The path of the renderer class.
 * @param background      The location of the background texture for this skill tree.
 * @param icon            The location of the icon texture for this skill tree.
 * @param width           The width of the background texture.
 * @param height          The height of the background texture.
 * @param startX          The initial X coordinate of the background texture.
 * @param startY          The initial Y coordinate of the background texture.
 * @param index           The index this tab should appear at in the occulus.
 * @param rendererFactory The lazy renderer factory. (Only call on the client side)
 */
public record OcculusTab(String rendererClass, @Nullable ResourceLocation background, @Nullable ResourceLocation icon, int width, int height, int startX, int startY, int index, Supplier<OcculusTabRendererFactory> rendererFactory) implements ITranslatable {
    public static final String DEFAULT_RENDERER = "com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusSkillTreeTabRenderer";

    public static final String OCCULUS_TAB = "occulus_tab";
    public static final ResourceKey<Registry<OcculusTab>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, OCCULUS_TAB));
    public static final int TEXTURE_WIDTH = 1024;
    public static final int TEXTURE_HEIGHT = 1024;

    public static final Codec<OcculusTab> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.optionalFieldOf("renderer", DEFAULT_RENDERER).forGetter(OcculusTab::rendererClass),
            ResourceLocation.CODEC.optionalFieldOf("background_texture").forGetter(tab -> Optional.ofNullable(tab.background)),
            ResourceLocation.CODEC.optionalFieldOf("icon_texture").forGetter(tab -> Optional.ofNullable(tab.icon)),
            Codec.INT.optionalFieldOf("texture_width", TEXTURE_WIDTH).forGetter(OcculusTab::width),
            Codec.INT.optionalFieldOf("texture_height", TEXTURE_HEIGHT).forGetter(OcculusTab::height),
            Codec.INT.optionalFieldOf("start_x", 0).forGetter(OcculusTab::startX),
            Codec.INT.optionalFieldOf("start_y", 0).forGetter(OcculusTab::startY),
            Codec.BYTE.fieldOf("index").xmap(Number::intValue, Number::byteValue).forGetter(OcculusTab::index)
    ).apply(inst, (rendererClass, background, icon, width, height, startX, startY, index) -> new OcculusTab(rendererClass, background.orElse(null), icon.orElse(null), width, height, startX, startY, index, Lazy.concurrentOf(OcculusTabRendererFactory.of(rendererClass)))));

    /**
     * @return The location of the background texture for this skill tree.
     */
    public ResourceLocation background(RegistryAccess access) {
        if (background != null) return background;
        ResourceLocation id = getId(access);
        return new ResourceLocation(id.getNamespace(), "textures/gui/occulus/" + id.getPath() + ".png");
    }

    /**
     * @return The location of the icon texture for this skill tree.
     */
    public ResourceLocation icon(RegistryAccess access) {
        if (icon != null) return icon;
        ResourceLocation id = getId(access);
        return new ResourceLocation(id.getNamespace(), "textures/icon/" + id.getPath() + ".png");
    }

    @Override
    public ResourceLocation getId(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(REGISTRY_KEY).getKey(this);
    }

    @Override
    public String getType() {
        return OCCULUS_TAB;
    }

    /**
     * @return The name of this occulus tab's renderer class.
     */
    @Override
    public String rendererClass() {
        return rendererClass;
    }

    /**
     * Factory interface to create occulus tab renderers.
     */
    public interface OcculusTabRendererFactory {
        /**
         * Helper method to create occulus tab renderers.
         *
         * @param clazz The renderer class to use.
         * @return A new occulus tab renderer.
         */
        @SuppressWarnings("unchecked")
        static Supplier<OcculusTabRendererFactory> of(String clazz) {
            return () -> {
                try {
                    Constructor<? extends OcculusTabRenderer> constructor = ((Class<? extends OcculusTabRenderer>) Class.forName(clazz)).getConstructor(OcculusTab.class, Screen.class);
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
        OcculusTabRenderer create(OcculusTab tab, Screen parent);
    }
}
