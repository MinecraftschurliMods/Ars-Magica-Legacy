package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

// Copied from https://github.com/neoforged/NeoForge/blob/1.20.x/src/main/java/net/neoforged/neoforge/common/data/ParticleDescriptionProvider.java
// TODO replace on newer versions
public abstract class ParticleDescriptionProvider implements DataProvider {
    private final ExistingFileHelper fileHelper;
    private final Map<ResourceLocation, List<String>> descriptions = new HashMap<>();
    private final DataGenerator.PathProvider particlesPath;

    protected ParticleDescriptionProvider(DataGenerator output, ExistingFileHelper fileHelper) {
        particlesPath = output.createPathProvider(DataGenerator.Target.RESOURCE_PACK, "particles");
        this.fileHelper = fileHelper;
    }

    protected abstract void addDescriptions();

    @Override
    public void run(CachedOutput cache) {
        addDescriptions();
                descriptions.forEach((k, v) -> {
                    var textures = new JsonArray();
                    v.forEach(textures::add);
                    try {
                        DataProvider.saveStable(cache, Util.make(new JsonObject(), obj -> obj.add("textures", textures)), particlesPath.json(k));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public String getName() {
        return "Particle Descriptions";
    }

    protected void sprite(ParticleType<?> type, ResourceLocation texture) {
        spriteSet(type, texture);
    }

    protected void spriteSet(ParticleType<?> type, ResourceLocation baseName, int numOfTextures, boolean reverse) {
        Preconditions.checkArgument(numOfTextures > 0, "The number of textures to generate must be positive");
        spriteSet(type, () -> new Iterator<>() {
            private int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < numOfTextures;
            }

            @Override
            public ResourceLocation next() {
                ResourceLocation texture = new ResourceLocation(baseName + "_" + (reverse ? numOfTextures - counter - 1 : counter));
                counter++;
                return texture;
            }
        });
    }

    protected void spriteSet(ParticleType<?> type, ResourceLocation texture, ResourceLocation... textures) {
        spriteSet(type, Stream.concat(Stream.of(texture), Arrays.stream(textures))::iterator);
    }

    protected void spriteSet(ParticleType<?> type, Iterable<ResourceLocation> textures) {
        ResourceLocation particle = Preconditions.checkNotNull(ForgeRegistries.PARTICLE_TYPES.getKey(type), "The particle type is not registered");
        List<String> desc = new ArrayList<>();
        for (ResourceLocation texture : textures) {
            Preconditions.checkArgument(fileHelper.exists(texture, PackType.CLIENT_RESOURCES, ".png", "textures/particle"), "Texture '%s' does not exist in any known resource pack", texture);
            desc.add(texture.toString());
        }
        Preconditions.checkArgument(desc.size() > 0, "The particle type '%s' must have one texture", particle);
        if (descriptions.putIfAbsent(particle, desc) != null)
            throw new IllegalArgumentException(String.format("The particle type '%s' already has a description associated with it", particle));
    }
}
