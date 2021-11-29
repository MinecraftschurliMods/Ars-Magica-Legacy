package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.util.Lazy;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public final class SpellIconAtlas extends TextureAtlasHolder {
    private static final Lazy<SpellIconAtlas> INSTANCE           = Lazy.of(SpellIconAtlas::new);
    private static final String               PREFIX             = "icon/spell";
    private static final String               SUFFIX             = ".png";
    private static final Predicate<String>    RESOURCE_PREDICATE = s -> s.endsWith(SUFFIX);
    public  static final ResourceLocation     SPELL_ICON_ATLAS   = new ResourceLocation(ArsMagicaAPI.MOD_ID, "textures/atlas/spell_icons.png");

    private Collection<ResourceLocation> resourceLocations;

    /**
     * @return The only instance of this class.
     */
    public static SpellIconAtlas instance() {
        return INSTANCE.get();
    }

    private SpellIconAtlas() {
        super(Minecraft.getInstance().textureManager, SPELL_ICON_ATLAS, PREFIX);
    }

    /**
     * @return All registered spell icons.
     */
    public Collection<ResourceLocation> getRegisteredIcons() {
        return resourceLocations;
    }

    @Override
    protected Stream<ResourceLocation> getResourcesToLoad() {
        return resourceLocations.stream();
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        if (location == null) location = MissingTextureAtlasSprite.getLocation();
        return super.getSprite(location);
    }

    @Override
    protected TextureAtlas.Preparations prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        HashMap<String, ResourceLocation> acc = new HashMap<>();
        int length = ("textures/" + PREFIX + "/").length();
        int length1 = SUFFIX.length();
        for (ResourceLocation resourceLocation : resourceManager.listResources("textures/" + PREFIX, RESOURCE_PREDICATE)) {
            String path = resourceLocation.getPath();
            path = path.substring(length, path.length() - length1);
            acc.putIfAbsent(path, new ResourceLocation(resourceLocation.getNamespace(), path));
        }
        resourceLocations = acc.values();
        return super.prepare(resourceManager, profiler);
    }
}
