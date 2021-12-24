package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
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

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Data provider for skill jsons
 */
public abstract class SkillProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final DataGenerator      generator;
    private final String             namespace;
    private Set<ResourceLocation> ids;

    /**
     * Create a skill provider for the given namespace
     *
     * @param generator the data generator
     * @param namespace the namespace to use in data generation
     */
    protected SkillProvider(DataGenerator generator, String namespace) {
        this.generator = generator;
        this.namespace = namespace;
    }

    @Internal
    @Override
    public final void run(HashCache pCache) {
        Path path = generator.getOutputFolder();
        ids = Sets.newHashSet();
        createSkills(skill -> {
            if (!ids.add(skill.getId())) throw new IllegalStateException("Duplicate skill " + skill.getId());
            else {
                saveSkill(pCache, skill.serialize(), path.resolve("data/" + skill.getId().getNamespace() + "/am_skills/" + skill.getId().getPath() + ".json"));
            }
        });
    }

    public Set<ResourceLocation> getSkills() {
        return Collections.unmodifiableSet(ids);
    }

    /**
     * Implement to add your own skills
     * @param consumer provided by the datagen
     */
    protected abstract void createSkills(Consumer<SkillBuilder> consumer);

    /**
     * Creates a new skill.
     *
     * @param name       The skill name.
     * @param occulusTab The occulus tab to display the skill in.
     * @return A new skill.
     */
    protected SkillBuilder createSkill(String name, ResourceLocation occulusTab) {
        return SkillBuilder.create(new ResourceLocation(namespace, name), occulusTab);
    }

    /**
     * Creates a new skill.
     *
     * @param name       The skill name.
     * @param occulusTab The occulus tab to display the skill in.
     * @return A new skill.
     */
    protected SkillBuilder createSkill(String name, IOcculusTab occulusTab) {
        return SkillBuilder.create(new ResourceLocation(namespace, name), occulusTab);
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
