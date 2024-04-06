package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record HeightRequirement(MinMaxBounds.Ints range) implements RitualRequirement {
    public static final Codec<HeightRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.INT_MIN_MAX_BOUNDS.fieldOf("height").forGetter(HeightRequirement::range)).apply(inst, HeightRequirement::new));

    public static HeightRequirement any() {
        return new HeightRequirement(MinMaxBounds.Ints.ANY);
    }

    public static HeightRequirement atLeast(int min) {
        return new HeightRequirement(MinMaxBounds.Ints.atLeast(min));
    }

    public static HeightRequirement atMost(int max) {
        return new HeightRequirement(MinMaxBounds.Ints.atMost(max));
    }

    public static HeightRequirement between(int min, int max) {
        return new HeightRequirement(MinMaxBounds.Ints.between(min, max));
    }

    public static HeightRequirement exactly(int value) {
        return new HeightRequirement(MinMaxBounds.Ints.exactly(value));
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(Player player, ServerLevel serverLevel, BlockPos pos) {
        return range.matches(pos.getY());
    }
}
