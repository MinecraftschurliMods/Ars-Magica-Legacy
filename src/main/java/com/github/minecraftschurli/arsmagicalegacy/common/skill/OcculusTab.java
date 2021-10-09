package com.github.minecraftschurli.arsmagicalegacy.common.skill;

import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTab;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Supplier;

public final class OcculusTab implements IOcculusTab {
    private static final String DEFAULT_RENDERER = "com.github.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusSkillTreeTabRenderer";

    //@formatter:off
    public static final Codec<IOcculusTab> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.fieldOf("renderer").orElse(DEFAULT_RENDERER).forGetter(IOcculusTab::getRenderer),
            ResourceLocation.CODEC.optionalFieldOf("background_texture")
                                  .forGetter((IOcculusTab occulusTab) -> ((OcculusTab) occulusTab).getBackgroundOpt()),
            ResourceLocation.CODEC.optionalFieldOf("icon_texture")
                                  .forGetter((IOcculusTab occulusTab) -> ((OcculusTab) occulusTab).getIconOpt()),
            Codec.INT.fieldOf("texture_width").orElse(IOcculusTab.TEXTURE_WIDTH)
                     .forGetter(IOcculusTab::getWidth),
            Codec.INT.fieldOf("texture_height").orElse(IOcculusTab.TEXTURE_HEIGHT)
                     .forGetter(IOcculusTab::getHeight),
            Codec.BYTE.fieldOf("index").xmap(Number::intValue, Number::byteValue)
                      .forGetter(IOcculusTab::getOcculusIndex)
    ).apply(inst, OcculusTab::new));

    public static final Codec<IOcculusTab> NETWORK_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(IOcculusTab::getId),
            Codec.STRING.fieldOf("renderer").orElse(DEFAULT_RENDERER).forGetter(IOcculusTab::getRenderer),
            ResourceLocation.CODEC.fieldOf("background_texture").forGetter(IOcculusTab::getBackground),
            ResourceLocation.CODEC.fieldOf("icon_texture").forGetter(IOcculusTab::getIcon),
            Codec.INT.fieldOf("texture_width").orElse(IOcculusTab.TEXTURE_WIDTH)
                     .forGetter(IOcculusTab::getWidth),
            Codec.INT.fieldOf("texture_height").orElse(IOcculusTab.TEXTURE_HEIGHT)
                     .forGetter(IOcculusTab::getHeight),
            Codec.BYTE.fieldOf("index").xmap(Number::intValue, Number::byteValue)
                      .forGetter(IOcculusTab::getOcculusIndex)
    ).apply(inst, OcculusTab::new));
    //@formatter:on

    private final Lazy<OcculusTabRendererFactory> rendererFactory;
    private final String                          rendererClass;
    private final ResourceLocation                background;
    private final ResourceLocation                icon;
    private final int                             width;
    private final int                             height;
    private final int                             index;
    private       ResourceLocation                id;

    public OcculusTab(String rendererClass, @Nullable ResourceLocation background, @Nullable ResourceLocation icon,
                      int width, int height, int index) {
        this.rendererClass = rendererClass;
        this.background = background;
        this.icon = icon;
        this.width = width;
        this.height = height;
        this.index = index;
        this.rendererFactory = Lazy.concurrentOf(OcculusTabRendererFactory.of(getRenderer()));
    }

    private OcculusTab(String rendererClass, Optional<ResourceLocation> background, Optional<ResourceLocation> icon,
                       int width, int height, int index) {
        this(rendererClass, background.orElse(null), icon.orElse(null), width, height, index);
    }

    public OcculusTab(ResourceLocation id, String rendererClass, ResourceLocation background, ResourceLocation icon,
                      int width, int height, int index) {
        this(rendererClass, background, icon, width, height, index);
        setId(id);
    }

    void setId(ResourceLocation id) {
        this.id = id;
    }

    private Optional<ResourceLocation> getIconOpt() {
        return Optional.of(getIcon());
    }

    private Optional<ResourceLocation> getBackgroundOpt() {
        return Optional.of(getBackground());
    }

    @Override
    public ResourceLocation getBackground() {
        if (this.background != null) {
            return this.background;
        }
        return new ResourceLocation(getId().getNamespace(), "textures/gui/occulus/" + getId().getPath() + ".png");
    }

    @Override
    public ResourceLocation getIcon() {
        if (this.icon != null) {
            return this.icon;
        }
        return new ResourceLocation(getId().getNamespace(), "textures/icon/" + getId().getPath() + ".png");
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getOcculusIndex() {
        return this.index;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public String getRenderer() {
        return this.rendererClass;
    }

    @Override
    public Supplier<OcculusTabRendererFactory> getRendererFactory() {
        return this.rendererFactory;
    }
}
