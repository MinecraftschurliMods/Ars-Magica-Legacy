package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class AMFlowerBlock extends FlowerBlock {
    private final TagKey<Block> soil;

    public AMFlowerBlock(Holder<MobEffect> effect, float seconds, TagKey<Block> soil, Properties properties) {
        super(effect, seconds, properties);
        this.soil = soil;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(soil);
    }
}
