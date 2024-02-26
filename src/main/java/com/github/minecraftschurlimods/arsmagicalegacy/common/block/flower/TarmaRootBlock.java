package com.github.minecraftschurlimods.arsmagicalegacy.common.block.flower;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.PlantType;
import net.neoforged.neoforge.common.Tags;

public class TarmaRootBlock extends FlowerBlock {
    public TarmaRootBlock() {
        super(() -> MobEffects.DIG_SLOWDOWN, 7, Properties.ofLegacyCopy(Blocks.POPPY));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState block = level.getBlockState(pos.below());
        return block.is(Tags.Blocks.SAND) || block.is(BlockTags.DIRT) || block.is(BlockTags.STONE_ORE_REPLACEABLES) || block.is(BlockTags.DEEPSLATE_ORE_REPLACEABLES) || block.is(Blocks.CLAY) || block.is(Blocks.GRAVEL);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.CAVE;
    }
}
