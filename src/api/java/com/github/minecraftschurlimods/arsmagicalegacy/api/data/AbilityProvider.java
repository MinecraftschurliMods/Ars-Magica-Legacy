package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.MinMaxBounds;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

public abstract class AbilityProvider implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator generator;
    private final String namespace;
    private final Multimap<ResourceLocation, ResourceLocation> abilitiesByAffinity = HashMultimap.create();

    protected AbilityProvider(String namespace, DataGenerator generator) {
        this.namespace = namespace;
        this.generator = generator;
    }

    /**
     * Get the abilities associated with the given affinity.
     *
     * @param affinity The affinity for which to get the abilities.
     * @return The abilities for the given affinity.
     */
    public Collection<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity) {
        return Collections.unmodifiableCollection(abilitiesByAffinity.get(affinity));
    }

    protected abstract void createAbilities(Consumer<AbilityBuilder> consumer);

    @Internal
    @Override
    public void run(HashCache pCache) {
        Set<ResourceLocation> ids = new HashSet<>();
        createAbilities(consumer -> {
            if (!ids.add(consumer.getId())) throw new IllegalStateException("Duplicate ability " + consumer.getId());
            abilitiesByAffinity.put(consumer.getAffinity(), consumer.getId());
            save(pCache, consumer.serialize(), generator.getOutputFolder().resolve("data/" + consumer.getId().getNamespace() + "/affinity_abilities/" + consumer.getId().getPath() + ".json"));
        });
    }

    @Override
    public String getName() {
        return "Abilities[" + namespace + "]";
    }

    /**
     * @param ability  The id of the ability.
     * @param affinity The id of the ability's affinity.
     * @param bounds   The ability's bounds.
     * @return A new ability.
     */
    protected AbilityBuilder createAbility(ResourceLocation ability, ResourceLocation affinity, MinMaxBounds.Doubles bounds) {
        return AbilityBuilder.create(ability).setAffinity(affinity).setBounds(bounds);
    }

    /**
     * @param ability  The id of the ability.
     * @param affinity The ability's affinity.
     * @param bounds   The ability's bounds.
     * @return A new ability.
     */
    protected AbilityBuilder createAbility(ResourceLocation ability, IAffinity affinity, MinMaxBounds.Doubles bounds) {
        return AbilityBuilder.create(ability).setAffinity(affinity.getId()).setBounds(bounds);
    }

    private static void save(HashCache pCache, JsonObject pRecipeJson, Path pPath) {
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
            LOGGER.error("Couldn't save ability {}", pPath, ioexception);
        }
    }
}
