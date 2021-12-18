package com.github.minecraftschurli.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurli.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurli.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class StairBlockStateMatcher implements IStateMatcher {
    private final Direction                                       direction;
    private final Half                                            half;
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public StairBlockStateMatcher(Direction direction, Half half) {
        this.direction = direction;
        this.half = half;
        this.predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getStructureMaterial(state.getBlock()).map(AltarStructureMaterial::stair).filter(state::is).isPresent() && state.getValue(StairBlock.FACING) == this.direction && state.getValue(StairBlock.HALF) == this.half;
    }

    @Override
    public BlockState getDisplayedState(int ticks) {
        AltarStructureMaterial mat = AltarMaterialManager.instance().getRandomStructureMaterial(ticks / 20);
        return mat.stair().defaultBlockState().setValue(StairBlock.FACING, direction).setValue(StairBlock.HALF, half);
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return this.predicate;
    }
}
