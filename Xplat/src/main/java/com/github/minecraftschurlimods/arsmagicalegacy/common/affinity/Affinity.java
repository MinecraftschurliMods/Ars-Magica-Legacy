package com.github.minecraftschurlimods.arsmagicalegacy.common.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.IAffinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.IRegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Affinity implements IAffinity {
    private final int color;
    private final Set<ResourceLocation> minorOpposites;
    private final Set<ResourceLocation> majorOpposites;
    private final ResourceLocation directOpposite;
    private final Supplier<SoundEvent> castSound;
    private final Supplier<SoundEvent> loopSound;

    public Affinity(int color, Set<ResourceLocation> minorOpposites, Set<ResourceLocation> majorOpposites, ResourceLocation directOpposite, Supplier<SoundEvent> castSound, Supplier<SoundEvent> loopSound) {
        this.color = color;
        this.minorOpposites = minorOpposites;
        this.majorOpposites = majorOpposites;
        this.directOpposite = directOpposite;
        this.castSound = castSound;
        this.loopSound = loopSound;
    }

    /**
     * @return A new affinity builder.
     */
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
        return ArsMagicaAPI.get().getAffinityRegistry().stream().filter(iAffinity -> !getMinorOpposingAffinities().contains(iAffinity.getRegistryName()) && !getMajorOpposingAffinities().contains(iAffinity.getRegistryName()) && !getDirectOpposingAffinity().equals(iAffinity.getRegistryName())).map(IRegistryEntry::getRegistryName).collect(Collectors.toSet());
    }

    @Override
    public ResourceLocation getDirectOpposingAffinity() {
        return directOpposite;
    }

    @Override
    public SoundEvent getCastSound() {
        return castSound.get();
    }

    @Override
    public SoundEvent getLoopSound() {
        return loopSound.get();
    }

    public static class Builder {
        private final Set<ResourceLocation> minorOpposites = new HashSet<>();
        private final Set<ResourceLocation> majorOpposites = new HashSet<>();
        private Integer color;
        private ResourceLocation directOpposite;
        private Supplier<SoundEvent> castSound;
        private Supplier<SoundEvent> loopSound;

        /**
         * @param color The color to set.
         * @return This builder, for chaining.
         */
        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        /**
         * @param minorOpposite The minor opposite to add.
         * @return This builder, for chaining.
         */
        public Builder addMinorOpposite(ResourceLocation minorOpposite) {
            minorOpposites.add(minorOpposite);
            return this;
        }

        /**
         * @param majorOpposite The major opposite to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposite(ResourceLocation majorOpposite) {
            majorOpposites.add(majorOpposite);
            return this;
        }

        /**
         * @param minorOpposite The minor opposite(s) to add.
         * @return This builder, for chaining.
         */
        public Builder addMinorOpposites(ResourceLocation... minorOpposite) {
            minorOpposites.addAll(Arrays.asList(minorOpposite));
            return this;
        }

        /**
         * @param majorOpposite The major opposite(s) to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposites(ResourceLocation... majorOpposite) {
            majorOpposites.addAll(Arrays.asList(majorOpposite));
            return this;
        }

        /**
         * @param directOpposite The direct opposite to set.
         * @return This builder, for chaining.
         */
        public Builder setDirectOpposite(ResourceLocation directOpposite) {
            this.directOpposite = directOpposite;
            return this;
        }

        /**
         * @param castSound The cast sound to set.
         * @return This builder, for chaining.
         */
        public Builder setCastSound(Supplier<SoundEvent> castSound) {
            this.castSound = castSound;
            return this;
        }

        /**
         * @param loopSound The loop sound to set.
         * @return This builder, for chaining.
         */
        public Builder setLoopSound(Supplier<SoundEvent> loopSound) {
            this.loopSound = loopSound;
            return this;
        }

        /**
         * @return The affinity created from this builder.
         */
        public Affinity build() {
            if (color == null) throw new IllegalStateException("An affinity needs a color!");
            if (directOpposite == null) throw new IllegalStateException("An affinity needs a direct opposite!");
            return new Affinity(color, minorOpposites, majorOpposites, directOpposite, castSound, loopSound);
        }
    }
}
