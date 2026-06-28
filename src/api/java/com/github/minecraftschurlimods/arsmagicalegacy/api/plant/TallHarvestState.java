package com.github.minecraftschurlimods.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

/// Represents the harvesting transition of a tall plant, used by some [GrowthType]s.
///
/// @param lowerFrom The old lower [BlockState].
/// @param upperFrom The old upper [BlockState].
/// @param lowerTo   The new lower [BlockState].
/// @param upperTo   The new upper [BlockState].
public record TallHarvestState(BlockState lowerFrom, BlockState upperFrom, BlockState lowerTo, BlockState upperTo) {
    public static final Codec<TallHarvestState> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BlockState.CODEC.fieldOf("lower_from").forGetter(TallHarvestState::lowerFrom),
        BlockState.CODEC.fieldOf("upper_from").forGetter(TallHarvestState::upperFrom),
        BlockState.CODEC.fieldOf("lower_to").forGetter(TallHarvestState::lowerTo),
        BlockState.CODEC.fieldOf("upper_to").forGetter(TallHarvestState::upperTo)
    ).apply(inst, TallHarvestState::new));
}
