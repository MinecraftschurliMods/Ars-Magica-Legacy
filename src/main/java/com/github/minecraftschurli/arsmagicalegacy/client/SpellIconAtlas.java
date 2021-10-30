package com.github.minecraftschurli.arsmagicalegacy.client;

import com.github.minecraftschurli.arsmagicalegacy.common.item.SpellItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SpellIconAtlas extends TextureAtlasHolder {
    private static final Lazy<SpellIconAtlas> INSTANCE           = Lazy.of(SpellIconAtlas::new);
    private static final Predicate<String>    RESOURCE_PREDICATE = s -> s.endsWith(".png");
    private static final String PREFIX                           = "icon/spell";

    public static SpellIconAtlas instance() {
        return INSTANCE.get();
    }

    private Collection<ResourceLocation> resourceLocations;

    public SpellIconAtlas() {
        super(Minecraft.getInstance().textureManager, SpellItem.SPELL_ICON_ATLAS, PREFIX);
    }

    @Override
    protected TextureAtlas.Preparations prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        HashMap<String, ResourceLocation> acc = new HashMap<>();
        var length = ("textures/"+PREFIX+"/").length();
        for (ResourceLocation resourceLocation :
                resourceManager.listResources("textures/"+PREFIX, RESOURCE_PREDICATE)) {
            var path = resourceLocation.getPath();
            path = path.substring(length, path.length() - 4);
            acc.putIfAbsent(path, new ResourceLocation(resourceLocation.getNamespace(), path));
        }
        resourceLocations = acc.values();
        return super.prepare(resourceManager, profiler);
    }

    @Override
    protected Stream<ResourceLocation> getResourcesToLoad() {
        return resourceLocations.stream();
    }

    public Collection<ResourceLocation> getRegisteredIcons() {
        return resourceLocations;
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        if (location == null) location = MissingTextureAtlasSprite.getLocation();
        return super.getSprite(location);
    }
}
