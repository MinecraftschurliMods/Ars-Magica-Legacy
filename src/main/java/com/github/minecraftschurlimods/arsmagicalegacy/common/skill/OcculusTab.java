package com.github.minecraftschurlimods.arsmagicalegacy.common.skill;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;

import java.util.Optional;
import java.util.function.Supplier;

public final class OcculusTab implements IOcculusTab {
    private static final String DEFAULT_RENDERER = "com.github.minecraftschurlimods.arsmagicalegacy.client.gui.occulus.OcculusSkillTreeTabRenderer";
    //@formatter:off
    public static final Codec<IOcculusTab> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.STRING.fieldOf("renderer").orElse(DEFAULT_RENDERER).forGetter(IOcculusTab::getRenderer),
            ResourceLocation.CODEC.optionalFieldOf("background_texture").forGetter((IOcculusTab occulusTab) -> ((OcculusTab) occulusTab).getBackgroundOpt()),
            ResourceLocation.CODEC.optionalFieldOf("icon_texture").forGetter((IOcculusTab occulusTab) -> ((OcculusTab) occulusTab).getIconOpt()),
            Codec.INT.fieldOf("texture_width").orElse(IOcculusTab.TEXTURE_WIDTH).forGetter(IOcculusTab::getWidth),
            Codec.INT.fieldOf("texture_height").orElse(IOcculusTab.TEXTURE_HEIGHT).forGetter(IOcculusTab::getHeight),
            Codec.INT.fieldOf("start_x").orElse(0).forGetter(IOcculusTab::getStartX),
            Codec.INT.fieldOf("start_y").orElse(0).forGetter(IOcculusTab::getStartY),
            Codec.BYTE.fieldOf("index").xmap(Number::intValue, Number::byteValue).forGetter(IOcculusTab::getOcculusIndex)
    ).apply(inst, OcculusTab::new));
    public static final Codec<IOcculusTab> NETWORK_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(IOcculusTab::getId),
            Codec.STRING.fieldOf("renderer").orElse(DEFAULT_RENDERER).forGetter(IOcculusTab::getRenderer),
            ResourceLocation.CODEC.fieldOf("background_texture").forGetter(IOcculusTab::getBackground),
            ResourceLocation.CODEC.fieldOf("icon_texture").forGetter(IOcculusTab::getIcon),
            Codec.INT.fieldOf("texture_width").orElse(IOcculusTab.TEXTURE_WIDTH).forGetter(IOcculusTab::getWidth),
            Codec.INT.fieldOf("texture_height").orElse(IOcculusTab.TEXTURE_HEIGHT).forGetter(IOcculusTab::getHeight),
            Codec.INT.fieldOf("start_x").orElse(0).forGetter(IOcculusTab::getStartX),
            Codec.INT.fieldOf("start_y").orElse(0).forGetter(IOcculusTab::getStartY),
            Codec.BYTE.fieldOf("index").xmap(Number::intValue, Number::byteValue).forGetter(IOcculusTab::getOcculusIndex)
    ).apply(inst, OcculusTab::new));
    //@formatter:on
    private final Lazy<OcculusTabRendererFactory> rendererFactory;
    private final String rendererClass;
    private final ResourceLocation background;
    private final ResourceLocation icon;
    private final int width;
    private final int height;
    private final int startX;
    private final int startY;
    private final int index;
    private ResourceLocation id;

    public OcculusTab(String rendererClass, ResourceLocation background, ResourceLocation icon, int width, int height, int startX, int startY, int index) {
        this.rendererClass = rendererClass;
        this.background = background;
        this.icon = icon;
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.index = index;
        rendererFactory = Lazy.concurrentOf(OcculusTabRendererFactory.of(getRenderer()));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OcculusTab(String rendererClass, Optional<ResourceLocation> background, Optional<ResourceLocation> icon, int width, int height, int startX, int startY, int index) {
        this(rendererClass, background.orElse(null), icon.orElse(null), width, height, startX, startY, index);
    }

    public OcculusTab(ResourceLocation id, String rendererClass, ResourceLocation background, ResourceLocation icon, int width, int height, int startX, int startY, int index) {
        this(rendererClass, background, icon, width, height, startX, startY, index);
        setId(id);
    }

    private Optional<ResourceLocation> getIconOpt() {
        return Optional.of(getIcon());
    }

    private Optional<ResourceLocation> getBackgroundOpt() {
        return Optional.of(getBackground());
    }

    @Override
    public ResourceLocation getBackground() {
        if (background != null) return background;
        return new ResourceLocation(getId().getNamespace(), "textures/gui/occulus/" + getId().getPath() + ".png");
    }

    @Override
    public ResourceLocation getIcon() {
        if (icon != null) return icon;
        return new ResourceLocation(getId().getNamespace(), "textures/icon/" + getId().getPath() + ".png");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getStartX() {
        return startX;
    }

    @Override
    public int getStartY() {
        return startY;
    }

    @Override
    public int getOcculusIndex() {
        return index;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    void setId(ResourceLocation id) {
        this.id = id;
    }

    public String getRenderer() {
        return rendererClass;
    }

    @Override
    public Supplier<OcculusTabRendererFactory> getRendererFactory() {
        return rendererFactory;
    }
}
