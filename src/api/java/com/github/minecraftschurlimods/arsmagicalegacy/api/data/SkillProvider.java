package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.OcculusTab;
import com.github.minecraftschurlimods.arsmagicalegacy.api.skill.Skill;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Base class for skill data generators.
 */
public abstract class SkillProvider implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String namespace;
    private final JsonCodecProvider<Skill> provider;
    private final Map<ResourceLocation, Skill> data = new HashMap<>();

    protected SkillProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), Skill.REGISTRY_KEY, data);
    }

    protected abstract void createSkills(Consumer<SkillBuilder> consumer);

    @Internal
    @Override
    public void run(CachedOutput pCache) throws IOException {
        createSkills(skill -> data.put(skill.getId(), skill.build()));
        provider.run(pCache);
    }

    @Override
    public String getName() {
        return provider.getName();
    }

    public Set<ResourceLocation> getSkills() {
        return Collections.unmodifiableSet(data.keySet());
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
    protected SkillBuilder createSkill(String name, OcculusTab occulusTab) {
        return SkillBuilder.create(new ResourceLocation(namespace, name), occulusTab);
    }
}
