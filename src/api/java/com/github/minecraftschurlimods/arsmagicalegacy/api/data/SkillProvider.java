package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.occulus.IOcculusTab;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Base class for skill data generators.
 */
public abstract class SkillProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final String namespace;
    private Set<ResourceLocation> data;

    protected SkillProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    protected abstract void createSkills(Consumer<SkillBuilder> consumer);

    @Internal
    @Override
    public void run(CachedOutput pCache) {
        data = new HashSet<>();
        createSkills(skill -> {
            if (!data.add(skill.getId())) throw new IllegalStateException("Duplicate skill " + skill.getId());
            else {
                try {
                    DataProvider.saveStable(pCache, skill.serialize(), generator.getOutputFolder().resolve("data/" + skill.getId().getNamespace() + "/am_skills/" + skill.getId().getPath() + ".json"));
                } catch (IOException e) {
                    LOGGER.error("Couldn't save skill {}", skill.getId(), e);
                }
            }
        });
    }

    @Override
    public String getName() {
        return "Skills[" + namespace + "]";
    }

    public Set<ResourceLocation> getSkills() {
        return Collections.unmodifiableSet(data);
    }

    /**
     * @param name       The skill name.
     * @param occulusTab The occulus tab to display the skill in.
     * @return A new skill.
     */
    protected SkillBuilder createSkill(String name, ResourceLocation occulusTab) {
        return SkillBuilder.create(new ResourceLocation(namespace, name), occulusTab);
    }

    /**
     * @param name       The skill name.
     * @param occulusTab The occulus tab to display the skill in.
     * @return A new skill.
     */
    protected SkillBuilder createSkill(String name, IOcculusTab occulusTab) {
        return SkillBuilder.create(new ResourceLocation(namespace, name), occulusTab);
    }
}
