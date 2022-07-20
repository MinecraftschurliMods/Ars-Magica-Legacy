package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.affinity.Affinity;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Consumer;

public class AbilityBuilder {
    private final ResourceLocation id;
    private ResourceLocation affinity;
    private MinMaxBounds.Doubles bounds;

    protected AbilityBuilder(ResourceLocation id) {
        this.id = id;
    }

    /**
     * @param id The id of the ability.
     * @return A builder for an ability.
     */
    public static AbilityBuilder create(ResourceLocation id) {
        return new AbilityBuilder(id);
    }

    /**
     * @return The id of the ability.
     */
    public ResourceLocation getId() {
        return id;
    }

    /**
     * Sets the affinity for this ability.
     *
     * @param affinity The id of the affinity to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public AbilityBuilder withAffinity(ResourceLocation affinity) {
        this.affinity = affinity;
        return this;
    }

    /**
     * Sets the affinity for this ability.
     *
     * @param affinity The affinity to set.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public AbilityBuilder withAffinity(Affinity affinity) {
        return withAffinity(Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getKey(affinity)));
    }

    /**
     * Sets the bounds for this ability.
     *
     * @param bounds The bounds to set.
     * @return This builder, for chaining.
     */
    public AbilityBuilder withBounds(MinMaxBounds.Doubles bounds) {
        this.bounds = bounds;
        return this;
    }

    /**
     * Builds the ability.
     *
     * @param consumer The consumer that will consume the builder.
     * @return This builder, for chaining.
     */
    @Contract("_ -> this")
    public AbilityBuilder build(Consumer<AbilityBuilder> consumer) {
        consumer.accept(this);
        return this;
    }

    /**
     * @return The serialized ability.
     */
    Ability build() {
        return new Ability(Objects.requireNonNull(ArsMagicaAPI.get().getAffinityRegistry().getValue(this.affinity)), this.bounds);
    }
}
