package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Collection;
import java.util.Collections;

public abstract class AbilityProvider extends AbstractRegistryDataProvider<Ability, AbilityProvider.Builder> {
    private final Multimap<ResourceLocation, ResourceLocation> abilitiesByAffinity;

    protected AbilityProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(Ability.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps, false);
        abilitiesByAffinity = HashMultimap.create();
        generate();
    }

    @Override
    public String getName() {
        return "Abilities[" + namespace + "]";
    }

    @Override
    protected void add(Builder builder) {
        super.add(builder);
        abilitiesByAffinity.put(builder.affinity.getId(), builder.id);
    }

    /**
     * Get the abilities generated associated with the given affinity.
     *
     * @param affinity The affinity for which to get the abilities.
     * @return The abilities generated for the given affinity.
     */
    public Collection<ResourceLocation> getAbilitiesForAffinity(ResourceLocation affinity) {
        return Collections.unmodifiableCollection(abilitiesByAffinity.get(affinity));
    }

    /**
     * @param id       The id of the ability.
     * @param affinity The ability's affinity.
     * @param bounds   The ability's bounds.
     */
    protected Builder builder(ResourceLocation id, Affinity affinity, MinMaxBounds.Doubles bounds) {
        return new Builder(id, this, affinity, bounds);
    }

    /**
     * @param id       The id of the ability.
     * @param affinity The ability's affinity.
     * @param bounds   The ability's bounds.
     */
    protected Builder builder(String id, Affinity affinity, MinMaxBounds.Doubles bounds) {
        return builder(new ResourceLocation(namespace, id), affinity, bounds);
    }

    protected static class Builder extends AbstractRegistryDataProvider.Builder<Ability, Builder> {
        private final Affinity affinity;
        private final MinMaxBounds.Doubles bounds;

        public Builder(ResourceLocation id, AbilityProvider provider, Affinity affinity, MinMaxBounds.Doubles bounds) {
            super(id, provider, Ability.DIRECT_CODEC);
            this.affinity = affinity;
            this.bounds = bounds;
        }

        @Override
        protected Ability get() {
            return new Ability(affinity, bounds);
        }
    }
}
