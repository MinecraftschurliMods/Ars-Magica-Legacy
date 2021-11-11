package com.github.minecraftschurli.arsmagicalegacy.common.affinity;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.affinity.IAffinity;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class Affinity extends ForgeRegistryEntry<IAffinity> implements IAffinity {
    private final int color;
    private final Set<ResourceLocation> minorOpposites;
    private final Set<ResourceLocation> majorOpposites;
    private final ResourceLocation directOpposite;

    public Affinity(int color, Set<ResourceLocation> minorOpposites, Set<ResourceLocation> majorOpposites, ResourceLocation directOpposite) {
        this.color = color;
        this.minorOpposites = minorOpposites;
        this.majorOpposites = majorOpposites;
        this.directOpposite = directOpposite;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public Set<ResourceLocation> getMinorOpposingAffinities() {
        return Collections.unmodifiableSet(minorOpposites);
    }

    @Override
    public Set<ResourceLocation> getMajorOpposingAffinities() {
        return Collections.unmodifiableSet(majorOpposites);
    }

    @Override
    public Set<ResourceLocation> getAdjacentAffinities() {
        return ArsMagicaAPI.get().getAffinityRegistry().getValues().stream().filter(iAffinity -> !getMinorOpposingAffinities().contains(iAffinity.getRegistryName()) && !getMajorOpposingAffinities().contains(iAffinity.getRegistryName()) && !getDirectOpposingAffinity().equals(iAffinity.getRegistryName())).map(IForgeRegistryEntry::getRegistryName).collect(Collectors.toSet());
    }

    @Override
    public ResourceLocation getDirectOpposingAffinity() {
        return directOpposite;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer color;
        private final Set<ResourceLocation> minorOpposites = new HashSet<>();
        private final Set<ResourceLocation> majorOpposites = new HashSet<>();
        private ResourceLocation directOpposite;

        /**
         * Sets the color for this builder.
         *
         * @param color The color to set.
         * @return This builder, for chaining.
         */
        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        /**
         * Adds a minor opposite to this builder.
         *
         * @param minorOpposite The minor opposite to add.
         * @return This builder, for chaining.
         */
        public Builder addMinorOpposite(ResourceLocation minorOpposite) {
            minorOpposites.add(minorOpposite);
            return this;
        }

        /**
         * Adds a minor opposite to this builder.
         *
         * @param majorOpposite The major opposite to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposite(ResourceLocation majorOpposite) {
            majorOpposites.add(majorOpposite);
            return this;
        }

        /**
         * Adds minor opposites to this builder.
         *
         * @param minorOpposite The minor opposites to add.
         * @return This builder, for chaining.
         */
        public Builder addMinorOpposites(ResourceLocation... minorOpposite) {
            minorOpposites.addAll(Arrays.asList(minorOpposite));
            return this;
        }

        /**
         * Adds major opposites to this builder.
         *
         * @param majorOpposite The major opposites to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposites(ResourceLocation... majorOpposite) {
            majorOpposites.addAll(Arrays.asList(majorOpposite));
            return this;
        }

        /**
         * Sets the direct opposite for this builder.
         *
         * @param directOpposite The direct opposite to set.
         * @return This builder, for chaining.
         */
        public Builder setDirectOpposite(ResourceLocation directOpposite) {
            this.directOpposite = directOpposite;
            return this;
        }

        /**
         * Builds the affinity.
         *
         * @return The affinity from this builder.
         * @throws IllegalStateException If the color or direct opposite is not set.
         */
        public Affinity build() {
            if (color == null) throw new IllegalStateException("color is required");
            if (directOpposite == null) throw new IllegalStateException("directOpposite is required");
            return new Affinity(color, minorOpposites, majorOpposites, directOpposite);
        }
    }
}
