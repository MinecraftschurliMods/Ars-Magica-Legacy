package com.github.minecraftschurli.arsmagicalegacy.api.data;

import com.github.minecraftschurli.arsmagicalegacy.api.occulus.IOcculusTab;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Data provider for skill jsons
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class SkillProvider implements DataProvider {// TODO @IHH document
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
    private final DataGenerator generator;
    private final String modid;

    protected SkillProvider(DataGenerator generator, String modid) {
        this.generator = generator;
        this.modid = modid;
    }

    @Internal
    @Override
    public final void run(@NotNull HashCache pCache) {
        Path path = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        createSkills((p_125991_) -> {
            if (!set.add(p_125991_.getId())) {
                throw new IllegalStateException("Duplicate skill " + p_125991_.getId());
            } else {
                saveSkill(pCache, p_125991_.serialize(), path.resolve("data/" + p_125991_.getId().getNamespace() + "/am_skills/" + p_125991_.getId().getPath() + ".json"));
            }
        });
    }

    protected abstract void createSkills(Consumer<SkillBuilder> consumer);

    protected SkillBuilder createSkill(String name, ResourceLocation occulusTab, ResourceLocation icon) {
        return SkillBuilder.create(new ResourceLocation(modid, name), occulusTab, icon);
    }

    protected SkillBuilder createSkill(String name, IOcculusTab occulusTab, ResourceLocation icon) {
        return SkillBuilder.create(new ResourceLocation(modid, name), occulusTab, icon);
    }

    private static void saveSkill(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
        try {
            String s = GSON.toJson(pRecipeJson);
            String s1 = SHA1.hashUnencodedChars(s).toString();
            if (!Objects.equals(pCache.getHash(pPath), s1) || !Files.exists(pPath)) {
                Files.createDirectories(pPath.getParent());

                try (BufferedWriter bufferedwriter = Files.newBufferedWriter(pPath)) {
                    bufferedwriter.write(s);
                }
            }

            pCache.putNew(pPath, s1);
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save skill {}", pPath, ioexception);
        }
    }
}
