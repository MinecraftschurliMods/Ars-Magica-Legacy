package com.github.minecraftschurlimods.arsmagicalegacy.api.data;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.IAbility;
import com.github.minecraftschurlimods.arsmagicalegacy.api.util.Range;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.SerializationException;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

public class AbilityBuilder {
    private final ResourceLocation id;
    private ResourceLocation affinity;
    private Range range;

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
     * Sets the range for this ability.
     *
     * @param range The range to set.
     * @return This builder, for chaining.
     */
    public AbilityBuilder setRange(Range range) {
        this.range = range;
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
        if (range == null) throw new SerializationException("An ability needs a range!");
        JsonObject range = new JsonObject();
        if (this.range.hasLowerBound()) {
            range.addProperty("min", this.range.min().get());
        }
        if (this.range.hasUpperBound()) {
            range.addProperty("max", this.range.max().get());
        }
        json.add("range", range);
        return json;
    }
}
