package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;
import java.util.function.DoublePredicate;

/**
 * A double range with inclusive upper and lower bounds.
 */
public record Range(Optional<Double> min, Optional<Double> max) implements DoublePredicate {
    public static Codec<Range> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Codec.DOUBLE.optionalFieldOf("min").forGetter(Range::min),
            Codec.DOUBLE.optionalFieldOf("max").forGetter(Range::max)
    ).apply(inst, Range::new));

    /**
     * Creates a range with a lower and upper bound.
     *
     * @param min the minimum value for the range.
     * @param max the maximum value for the range.
     * @return A range with a lower bound of min and an upper bound of max.
     */
    public static Range ofBounds(double min, double max) {
        return new Range(Optional.of(min), Optional.of(max));
    }

    /**
     * Creates a range with a lower bound.
     *
     * @param min the minimum value for the range.
     * @return A range with a lower bound of min and no upper bound.
     */
    public static Range ofLowerBound(double min) {
        return new Range(Optional.of(min), Optional.empty());
    }

    /**
     * Creates a range with an upper bound.
     *
     * @param max the maximum value for the range.
     * @return A range with an upper bound of max and no lower bound.
     */
    public static Range ofUpperBound(double max) {
        return new Range(Optional.empty(), Optional.of(max));
    }

    public boolean hasLowerBound() {
        return min.isPresent();
    }

    public boolean hasUpperBound() {
        return max.isPresent();
    }

    @Override
    public boolean test(double value) {
        return min().map(min -> value >= min).orElse(true) && max().map(max -> value <= max).orElse(true);
    }
}
