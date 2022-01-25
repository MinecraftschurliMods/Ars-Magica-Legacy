package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class MainBlockStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    MainBlockStateMatcher() {
        predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getStructureMaterial(state.getBlock()).map(AltarStructureMaterial::block).filter(state::is).isPresent();
    }

    @Override
    public BlockState getDisplayedState(int ticks) {
        AltarStructureMaterial mat = AltarMaterialManager.instance().getRandomStructureMaterial(ticks / 20);
        if (mat == null) return Blocks.AIR.defaultBlockState();
        return mat.block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
