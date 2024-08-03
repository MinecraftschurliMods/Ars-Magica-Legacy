package com.github.minecraftschurlimods.arsmagicalegacy.api.affinity;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.ITranslatable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @param color          The color for this affinity.
 * @param minorOpposites The minor opposing affinities for this affinity.
 * @param majorOpposites The major opposing affinities for this affinity.
 * @param directOpposite The direct opposing affinity for this affinity.
 * @param castSound      The sound to play when casting a spell with this affinity.
 * @param loopSound      The sound to play when casting a continuous spell with this affinity.
 * @param particle       The particle type associated with this affinity.
 */
public record Affinity(int color, HolderSet<Affinity> minorOpposites, HolderSet<Affinity> majorOpposites, Holder<Affinity> directOpposite, @Nullable Holder<SoundEvent> castSound, @Nullable Holder<SoundEvent> loopSound, Supplier<? extends ParticleOptions> particle) implements Comparable<Affinity>, ITranslatable {
    public static final String AFFINITY = "affinity";
    public static final ResourceKey<Registry<Affinity>> REGISTRY_KEY = ResourceKey.createRegistryKey(ArsMagicaAPI.resource(AFFINITY));

    public static final ResourceKey<Affinity> NONE      = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("none"));
    public static final ResourceKey<Affinity> ARCANE    = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("arcane"));
    public static final ResourceKey<Affinity> WATER     = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("water"));
    public static final ResourceKey<Affinity> FIRE      = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("fire"));
    public static final ResourceKey<Affinity> EARTH     = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("earth"));
    public static final ResourceKey<Affinity> AIR       = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("air"));
    public static final ResourceKey<Affinity> LIGHTNING = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("lightning"));
    public static final ResourceKey<Affinity> ICE       = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("ice"));
    public static final ResourceKey<Affinity> NATURE    = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("nature"));
    public static final ResourceKey<Affinity> LIFE      = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("life"));
    public static final ResourceKey<Affinity> ENDER     = ResourceKey.create(REGISTRY_KEY, ArsMagicaAPI.resource("ender"));

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
    public HolderSet<Affinity> minorOpposites() {
        return minorOpposites;
    }

    /**
     * @return The major opposing affinities for this affinity.
     */
    @Unmodifiable
    @Override
    public HolderSet<Affinity> majorOpposites() {
        return majorOpposites;
    }

    /**
     * @return The adjacent affinities for this affinity.
     */
    public HolderSet<Affinity> getAdjacentAffinities() {
        return HolderSet.direct(ArsMagicaAPI.get().getAffinityRegistry().holders().filter(iAffinity -> !minorOpposites().contains(iAffinity) && !majorOpposites().contains(iAffinity) && !directOpposite().is(iAffinity)).toList());
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
        private final Set<ResourceKey<Affinity>> minorOpposites = new HashSet<>();
        private final Set<ResourceKey<Affinity>> majorOpposites = new HashSet<>();
        private Integer color;
        private ResourceKey<Affinity> directOpposite;
        @Nullable
        private Holder<SoundEvent> castSound;
        @Nullable
        private Holder<SoundEvent> loopSound;
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
        public Builder addMinorOpposite(ResourceKey<Affinity> minorOpposite) {
            minorOpposites.add(minorOpposite);
            return this;
        }

        /**
         * @param majorOpposite The major opposite to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposite(ResourceKey<Affinity> majorOpposite) {
            majorOpposites.add(majorOpposite);
            return this;
        }

        /**
         * @param minorOpposite The minor opposite(s) to add.
         * @return This builder, for chaining.
         */
        public Builder addMinorOpposites(ResourceKey<Affinity>... minorOpposite) {
            minorOpposites.addAll(Arrays.asList(minorOpposite));
            return this;
        }

        /**
         * @param majorOpposite The major opposite(s) to add.
         * @return This builder, for chaining.
         */
        public Builder addMajorOpposites(ResourceKey<Affinity>... majorOpposite) {
            majorOpposites.addAll(Arrays.asList(majorOpposite));
            return this;
        }

        /**
         * @param directOpposite The direct opposite to set.
         * @return This builder, for chaining.
         */
        public Builder setDirectOpposite(ResourceKey<Affinity> directOpposite) {
            this.directOpposite = directOpposite;
            return this;
        }

        /**
         * @param castSound The cast sound to set.
         * @return This builder, for chaining.
         */
        public Builder setCastSound(Holder<SoundEvent> castSound) {
            this.castSound = castSound;
            return this;
        }

        /**
         * @param loopSound The loop sound to set.
         * @return This builder, for chaining.
         */
        public Builder setLoopSound(Holder<SoundEvent> loopSound) {
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
            return new Affinity(color, HolderSet.direct(DeferredHolder::create, minorOpposites), HolderSet.direct(DeferredHolder::create, majorOpposites), DeferredHolder.create(directOpposite), castSound, loopSound, particle);
        }
    }
}
