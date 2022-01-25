package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
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

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private final Set<ResourceLocation> minorOpposites = new HashSet<>();
        private final Set<ResourceLocation> majorOpposites = new HashSet<>();
        private Integer color;
        private ResourceLocation directOpposite;

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder addMinorOpposite(ResourceLocation minorOpposite) {
            minorOpposites.add(minorOpposite);
            return this;
        }

        public Builder addMajorOpposite(ResourceLocation majorOpposite) {
            majorOpposites.add(majorOpposite);
            return this;
        }

        public Builder addMinorOpposites(ResourceLocation... minorOpposite) {
            minorOpposites.addAll(Arrays.asList(minorOpposite));
            return this;
        }

        public Builder addMajorOpposites(ResourceLocation... majorOpposite) {
            majorOpposites.addAll(Arrays.asList(majorOpposite));
            return this;
        }

        public Builder setDirectOpposite(ResourceLocation directOpposite) {
            this.directOpposite = directOpposite;
            return this;
        }

        public Affinity build() {
            if (color == null) throw new IllegalStateException("An affinity needs a color!");
            if (directOpposite == null) throw new IllegalStateException("An affinity needs a direct opposite!");
            return new Affinity(color, minorOpposites, majorOpposites, directOpposite);
        }
    }
}
