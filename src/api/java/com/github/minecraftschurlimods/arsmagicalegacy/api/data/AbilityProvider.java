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
import org.apache.commons.lang3.SerializationException;

import java.util.Collection;
import java.util.Collections;

public abstract class AbilityProvider extends AbstractDataProvider<Ability, AbilityProvider.Builder> {
    private final Multimap<ResourceLocation, ResourceLocation> abilitiesByAffinity = HashMultimap.create();

    protected AbilityProvider(String namespace, DataGenerator generator, ExistingFileHelper existingFileHelper, RegistryOps<JsonElement> registryOps) {
        super(Ability.REGISTRY_KEY, namespace, generator, existingFileHelper, registryOps);
    }

    @Override
    public String getName() {
        return "Abilities[" + namespace + "]";
    }

    @Override
    protected void onSave(ResourceLocation id, Ability object) {
        abilitiesByAffinity.put(object.affinity().getId(), id);
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
     * @return A builder for a new ability.
     */
    protected Builder builder(ResourceLocation id, Affinity affinity, MinMaxBounds.Doubles bounds) {
        return new Builder(id).setAffinity(affinity).setBounds(bounds);
    }

    protected static class Builder extends AbstractDataBuilder<Ability, Builder> {
        private Affinity affinity;
        private MinMaxBounds.Doubles bounds;

        public Builder(ResourceLocation id) {
            super(id);
        }

        @Override
        protected Ability build() {
            if (affinity == null) throw new SerializationException("An ability needs an affinity!");
            if (bounds == null) throw new SerializationException("An ability needs bounds!");
            return new Ability(affinity, bounds);
        }

        /**
         * Sets the affinity for this ability.
         *
         * @param affinity The affinity to set.
         * @return This builder, for chaining.
         */
        public Builder setAffinity(Affinity affinity) {
            this.affinity = affinity;
            return this;
        }

        /**
         * Sets the bounds for this ability.
         *
         * @param bounds The bounds to set.
         * @return This builder, for chaining.
         */
        public Builder setBounds(MinMaxBounds.Doubles bounds) {
            this.bounds = bounds;
            return this;
        }
    }
}
