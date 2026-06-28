package com.github.minecraftschurlimods.arsmagicalegacy.common.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class MeteoriteFeature extends Feature<MeteoriteFeature.Configuration> {
    public MeteoriteFeature() {
        super(Configuration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<Configuration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        Configuration config = context.config();
        while (origin.getY() > level.getMinY() + config.height()) {
            if (!level.isEmptyBlock(origin.below())) {
                BlockState state = level.getBlockState(origin.below());
                if (state.is(BlockTags.DIRT) || state.is(BlockTags.BASE_STONE_OVERWORLD)) break;
            }
            origin = origin.below();
        }
        if (origin.getY() <= level.getMinY() + config.height()) return false;
        for (int i = 0; i < config.height(); i++) {
            int x = random.nextInt(config.width());
            int y = random.nextInt(config.width());
            int z = random.nextInt(config.width());
            float f = (float) (x + y + z) * 0.333f + 0.5f;
            for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-x, -y, -z), origin.offset(x, y, z))) {
                if (pos.distSqr(origin) <= f * f) {
                    level.setBlock(pos, random.nextDouble() < config.rareChance() ? config.rareState() : config.baseState(), Block.UPDATE_CLIENTS);
                }
            }
            origin = origin.offset(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
        }
        return true;
    }

    public record Configuration(BlockState baseState, BlockState rareState, int width, int height, float rareChance) implements FeatureConfiguration {
        public static final Codec<Configuration> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("base_state").forGetter(Configuration::baseState),
            BlockState.CODEC.fieldOf("rare_state").forGetter(Configuration::rareState),
            Codec.intRange(1, 64).fieldOf("width").forGetter(Configuration::width),
            Codec.intRange(1, 64).fieldOf("height").forGetter(Configuration::height),
            Codec.floatRange(0f, 1f).fieldOf("rare_chance").forGetter(Configuration::rareChance)
        ).apply(inst, Configuration::new));
    }
}
