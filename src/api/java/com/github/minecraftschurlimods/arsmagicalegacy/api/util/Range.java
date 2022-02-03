package com.github.minecraftschurlimods.arsmagicalegacy.api.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.function.DoublePredicate;

/**
 * A double range with inclusive upper and lower bounds.
 */
public record Range(Double min, Double max) implements DoublePredicate {
    public static Codec<Range> CODEC = RecordCodecBuilder.create(inst -> inst.group(Codec.DOUBLE.optionalFieldOf("min", null).forGetter(Range::min), Codec.DOUBLE.optionalFieldOf("max", null).forGetter(Range::max)).apply(inst, Range::new));

    /**
     * Creates a range with a lower and upper bound.
     *
     * @param min the minimum value for the range.
     * @param max the maximum value for the range.
     * @return A range with a lower bound of min and an upper bound of max.
     */
    public static Range ofBounds(double min, double max) {
        return new Range(min, max);
    }

    /**
     * Creates a range with a lower bound.
     *
     * @param min the minimum value for the range.
     * @return A range with a lower bound of min and no upper bound.
     */
    public static Range ofLowerBound(double min) {
        return new Range(min, null);
    }

    /**
     * Creates a range with an upper bound.
     *
     * @param max the maximum value for the range.
     * @return A range with an upper bound of max and no lower bound.
     */
    public static Range ofUpperBound(double max) {
        return new Range(null, max);
    }

    @Override
    public Double min() {
        return min == null ? 0 : min;
    }

    @Override
    public Double max() {
        return max == null ? 0 : max;
    }

    public boolean hasLowerBound() {
        return min != null;
    }

    public boolean hasUpperBound() {
        return max != null;
    }

    @Override
    public boolean test(double value) {
        return (!hasLowerBound() || value >= min()) && (!hasUpperBound() || value <= max());
    }
}
