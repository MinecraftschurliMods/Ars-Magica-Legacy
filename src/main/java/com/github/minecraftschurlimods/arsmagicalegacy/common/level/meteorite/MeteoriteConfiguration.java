package com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MeteoriteConfiguration(BlockState baseState, BlockState rareState, BlockState fluidState, int width, int height, float rareChance) implements FeatureConfiguration {
    public static final Codec<MeteoriteConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("base_state").forGetter(MeteoriteConfiguration::baseState),
            BlockState.CODEC.fieldOf("rare_state").forGetter(MeteoriteConfiguration::rareState),
            BlockState.CODEC.fieldOf("fluid_state").forGetter(MeteoriteConfiguration::fluidState),
            Codec.intRange(1, 64).fieldOf("width").forGetter(MeteoriteConfiguration::width),
            Codec.intRange(1, 64).fieldOf("height").forGetter(MeteoriteConfiguration::height),
            Codec.floatRange(0f, 1f).fieldOf("rare_chance").forGetter(MeteoriteConfiguration::rareChance)
    ).apply(inst, MeteoriteConfiguration::new));
}
