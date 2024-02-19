package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @param color          The color for this affinity.
 * @param minorOpposites The minor opposing affinities for this affinity.
 * @param majorOpposites The major opposing affinities for this affinity.
 * @param directOpposite The direct opposing affinity for this affinity.
 * @param castSound      The sound to play when casting a spell with this affinity.
 * @param loopSound      The sound to play when casting a continuous spell with this affinity.
 * @param particle       The particle type associated with this affinity.
 */
public record Affinity(int color, Set<ResourceLocation> minorOpposites, Set<ResourceLocation> majorOpposites, ResourceLocation directOpposite, Supplier<SoundEvent> castSound, Supplier<SoundEvent> loopSound, Supplier<? extends ParticleOptions> particle) implements Comparable<Affinity>, ITranslatable {
    public static final String AFFINITY = "affinity";
    public static final ResourceKey<Registry<Affinity>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, AFFINITY));

    public static final ResourceLocation NONE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "none");
    public static final ResourceLocation ARCANE    = new ResourceLocation(ArsMagicaAPI.MOD_ID, "arcane");
    public static final ResourceLocation WATER     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "water");
    public static final ResourceLocation FIRE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "fire");
    public static final ResourceLocation EARTH     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "earth");
    public static final ResourceLocation AIR       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "air");
    public static final ResourceLocation LIGHTNING = new ResourceLocation(ArsMagicaAPI.MOD_ID, "lightning");
    public static final ResourceLocation ICE       = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ice");
    public static final ResourceLocation NATURE    = new ResourceLocation(ArsMagicaAPI.MOD_ID, "nature");
    public static final ResourceLocation LIFE      = new ResourceLocation(ArsMagicaAPI.MOD_ID, "life");
    public static final ResourceLocation ENDER     = new ResourceLocation(ArsMagicaAPI.MOD_ID, "ender");

    /**
     * @return A new affinity builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * @return The minor opposing affinities for this affinity.
     */
    @Unmodifiable
    @Override
    public Set<ResourceLocation> minorOpposites() {
        return Collections.unmodifiableSet(minorOpposites);
    }

    /**
     * @return The major opposing affinities for this affinity.
     */
    @Unmodifiable
    @Override
    public Set<ResourceLocation> majorOpposites() {
        return Collections.unmodifiableSet(majorOpposites);
    }

    /**
     * @return The adjacent affinities for this affinity.
     */
    public Set<ResourceLocation> getAdjacentAffinities() {
        return ArsMagicaAPI.get().getAffinityRegistry().getValues().stream().filter(iAffinity -> !minorOpposites().contains(iAffinity.getId()) && !majorOpposites().contains(iAffinity.getId()) && !directOpposite().equals(iAffinity.getId())).map(Affinity::getId).collect(Collectors.toSet());
    }

    /**
     * @return The sound that should be played when casting a spell with this affinity.
     */
    @Nullable
    public SoundEvent getCastSound() {
        return castSound().get();
    }

    /**
     * @return The sound that should be played when casting a continuous spell with this affinity.
     */
    @Nullable
    public SoundEvent getLoopSound() {
        return loopSound().get();
    }

    /**
     * @return The sound that should be played when casting a continuous spell with this affinity.
     */
    @Nullable
    public ParticleOptions getParticle() {
        return particle().get();
    }

    @Override
    public String getType() {
        return AFFINITY;
    }

    @Override
    public ResourceLocation getId() {
        return Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getKey(this));
    }

    @Override
    public int compareTo(Affinity o) {
        return Comparator.comparing(ArsMagicaAPI.get().getAffinityRegistry()::getKey).compare(this, o);
    }

    public static class Builder {
        private final Set<ResourceLocation> minorOpposites = new HashSet<>();
        private final Set<ResourceLocation> majorOpposites = new HashSet<>();
        private Integer color;
        private ResourceLocation directOpposite;
        private Supplier<SoundEvent> castSound;
        private Supplier<SoundEvent> loopSound;
        private Supplier<? extends ParticleOptions> particle;

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
         * @param particle The particle type to set.
         * @return This builder, for chaining.
         */
        public Builder setParticle(Supplier<? extends ParticleOptions> particle) {
            this.particle = particle;
            return this;
        }

        /**
         * @return The affinity created from this builder.
         */
        public Affinity build() {
            if (color == null) {
                throw new IllegalStateException("An affinity needs a color!");
            }
            if (directOpposite == null) {
                throw new IllegalStateException("An affinity needs a direct opposite!");
            }
            return new Affinity(color, minorOpposites, majorOpposites, directOpposite, castSound, loopSound, particle);
        }
    }
}
