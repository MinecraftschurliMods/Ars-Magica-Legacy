package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class StairBlockStateMatcher implements IStateMatcher {
    private final Direction direction;
    private final Half half;
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public StairBlockStateMatcher(Direction direction, Half half) {
        this.direction = direction;
        this.half = half;
        this.predicate = (blockGetter, blockPos, state) -> PatchouliCompat.getRegistry(blockGetter, AltarStructureMaterial.REGISTRY_KEY).stream().anyMatch(mat -> state.is(mat.stair())) && state.getValue(StairBlock.FACING) == direction && state.getValue(StairBlock.HALF) == half;
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        Registry<AltarStructureMaterial> registry = PatchouliCompat.getRegistry(null, AltarStructureMaterial.REGISTRY_KEY);
        AltarStructureMaterial mat = registry.stream().toArray(AltarStructureMaterial[]::new)[Math.toIntExact(ticks / 20) % registry.size()];
        if (mat == null) return Blocks.AIR.defaultBlockState();
        return mat.stair().defaultBlockState().setValue(StairBlock.FACING, direction).setValue(StairBlock.HALF, half);
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
