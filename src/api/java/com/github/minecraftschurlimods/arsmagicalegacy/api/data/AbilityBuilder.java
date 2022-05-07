package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.Contract;

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
    public AbilityBuilder setAffinity(ResourceLocation affinity) {
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
    public AbilityBuilder setAffinity(ForgeRegistryEntry<IAbility> affinity) {
        return setAffinity(affinity.getRegistryName());
    }

    /**
     * Sets the bounds for this ability.
     *
     * @param bounds The bounds to set.
     * @return This builder, for chaining.
     */
    public AbilityBuilder setBounds(MinMaxBounds.Doubles bounds) {
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
    JsonObject serialize() {
        JsonObject json = new JsonObject();
        if (affinity == null) throw new SerializationException("An ability needs an affinity!");
        json.addProperty("affinity", this.affinity.toString());
        if (bounds == null) throw new SerializationException("An ability needs bounds!");
        json.add("bounds", this.bounds.serializeToJson());
        return json;
    }
}