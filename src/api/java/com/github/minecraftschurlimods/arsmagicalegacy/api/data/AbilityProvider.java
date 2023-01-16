package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.SerializationException;

import java.util.Collection;
import java.util.Collections;

public abstract class AbilityProvider extends AbstractDataProvider<AbilityProvider.Builder> {
    private final Multimap<ResourceLocation, ResourceLocation> abilitiesByAffinity = HashMultimap.create();

    protected AbilityProvider(String namespace, DataGenerator generator) {
        super("affinity_abilities", namespace, generator);
    }

    @Override
    public String getName() {
        return "Abilities[" + namespace + "]";
    }

    @Override
    protected void onSave(Builder object) {
        abilitiesByAffinity.put(object.getAffinity(), object.id);
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
     * @param ability  The id of the ability.
     * @param affinity The ability's affinity.
     * @param bounds   The ability's bounds.
     * @return A new ability.
     */
    protected Builder builder(IAbility ability, IAffinity affinity, MinMaxBounds.Doubles bounds) {
        return new Builder(ability.getId()).setAffinity(affinity.getId()).setBounds(bounds);
    }

    protected static class Builder extends AbstractDataBuilder {
        private ResourceLocation affinity;
        private MinMaxBounds.Doubles bounds;

        public Builder(ResourceLocation id) {
            super(id);
        }

        /**
         * @return The affinity for this ability.
         */
        public ResourceLocation getAffinity() {
            if (affinity == null) throw new SerializationException("An ability needs an affinity!");
            return affinity;
        }

        /**
         * Sets the affinity for this ability.
         *
         * @param affinity The id of the affinity to set.
         * @return This builder, for chaining.
         */
        public Builder setAffinity(ResourceLocation affinity) {
            this.affinity = affinity;
            return this;
        }

        /**
         * Sets the affinity for this ability.
         *
         * @param affinity The affinity to set.
         * @return This builder, for chaining.
         */
        public Builder setAffinity(ForgeRegistryEntry<IAbility> affinity) {
            return setAffinity(affinity.getRegistryName());
        }

        /**
         * @return The bounds for this ability.
         */
        public MinMaxBounds.Doubles getBounds() {
            if (bounds == null) throw new SerializationException("An ability needs bounds!");
            return bounds;
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

        @Override
        protected JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("affinity", getAffinity().toString());
            json.add("bounds", getBounds().serializeToJson());
            return json;
        }
    }
}
