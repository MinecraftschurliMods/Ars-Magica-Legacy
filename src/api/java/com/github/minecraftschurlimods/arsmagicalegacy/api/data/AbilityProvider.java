package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbilityProvider implements DataProvider {
    private final String namespace;
    private final Map<ResourceLocation, Ability> abilities = new HashMap<>();
    private final JsonCodecProvider<Ability> provider;

    protected AbilityProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this.namespace = namespace;
        this.provider = JsonCodecProvider.forDatapackRegistry(generator, existingFileHelper, namespace, RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get()), Ability.REGISTRY_KEY, abilities);
    }

    /**
     * Get the abilities associated with the given affinity.
     *
     * @param affinity The affinity for which to get the abilities.
     * @return The abilities for the given affinity.
     */
    public Collection<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity) {
        return abilities.entrySet().stream().filter(ability -> ability.getValue().affinity().getId().equals(affinity)).map(Map.Entry::getKey).toList();
    }

    protected abstract void createAbilities(Consumer<AbilityBuilder> consumer);

    @Internal
    @Override
    public void run(CachedOutput pCache) throws IOException {
        createAbilities(builder -> abilities.put(builder.getId(), builder.build()));
        provider.run(pCache);
    }

    @Override
    public String getName() {
        return "Abilities[" + namespace + "]";
    }

    public Ability get(ResourceLocation id) {
        return abilities.get(id);
    }

    /**
     * @param ability  The id of the ability.
     * @param affinity The id of the ability's affinity.
     * @param bounds   The ability's bounds.
     * @return A new ability.
     */
    protected AbilityBuilder createAbility(ResourceLocation ability, ResourceLocation affinity, MinMaxBounds.Doubles bounds) {
        return AbilityBuilder.create(ability).withAffinity(affinity).withBounds(bounds);
    }

    /**
     * @param ability  The id of the ability.
     * @param affinity The ability's affinity.
     * @param bounds   The ability's bounds.
     * @return A new ability.
     */
    protected AbilityBuilder createAbility(ResourceLocation ability, Affinity affinity, MinMaxBounds.Doubles bounds) {
        return AbilityBuilder.create(ability).withAffinity(affinity.getId()).withBounds(bounds);
    }
}
