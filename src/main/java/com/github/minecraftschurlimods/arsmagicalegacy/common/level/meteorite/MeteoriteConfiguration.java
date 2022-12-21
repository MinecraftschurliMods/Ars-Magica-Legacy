package com.github.minecraftschurlimods.arsmagicalegacy.common.level.meteorite;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MeteoriteConfiguration(BlockState baseState, BlockState rareState, BlockState fluidState, int meteoriteRadius, int meteoriteHeight, int craterRadius, int craterHeight, float rareChance, boolean placeCrater, float placeLake) implements FeatureConfiguration {
    public static final Codec<MeteoriteConfiguration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("base_state").forGetter(MeteoriteConfiguration::baseState),
            BlockState.CODEC.fieldOf("rare_state").forGetter(MeteoriteConfiguration::rareState),
            BlockState.CODEC.fieldOf("fluid_state").forGetter(MeteoriteConfiguration::fluidState),
            Codec.intRange(1, 64).fieldOf("meteorite_radius").forGetter(MeteoriteConfiguration::meteoriteRadius),
            Codec.intRange(1, 64).fieldOf("meteorite_height").forGetter(MeteoriteConfiguration::meteoriteHeight),
            Codec.intRange(1, 64).fieldOf("crater_radius").forGetter(MeteoriteConfiguration::craterRadius),
            Codec.intRange(1, 64).fieldOf("crater_height").forGetter(MeteoriteConfiguration::craterHeight),
            Codec.floatRange(0f, 1f).fieldOf("rare_chance").forGetter(MeteoriteConfiguration::rareChance),
            Codec.BOOL.optionalFieldOf("place_crater", true).forGetter(MeteoriteConfiguration::placeCrater),
            Codec.FLOAT.optionalFieldOf("place_lake", 1f).forGetter(MeteoriteConfiguration::placeLake)
    ).apply(inst, MeteoriteConfiguration::new));
}
