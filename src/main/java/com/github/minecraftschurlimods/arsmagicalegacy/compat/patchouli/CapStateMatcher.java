package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.altar.AltarMaterialManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class CapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    CapStateMatcher() {
        this.predicate = (blockGetter, blockPos, state) -> AltarMaterialManager.instance().getCapMaterial(state.getBlock()).map(AltarCapMaterial::cap).filter(state::is).isPresent();
    }

    @Override
    public BlockState getDisplayedState(int ticks) {
        AltarCapMaterial mat = AltarMaterialManager.instance().getRandomCapMaterial(ticks / 20);
        if (mat == null) return Blocks.AIR.defaultBlockState();
        return mat.cap().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
