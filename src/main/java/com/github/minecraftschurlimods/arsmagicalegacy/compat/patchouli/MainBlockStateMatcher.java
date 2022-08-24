package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarStructureMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class MainBlockStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    MainBlockStateMatcher() {
        this.predicate = (blockGetter, blockPos, state) -> PatchouliCompat.getRegistry(blockGetter, AltarStructureMaterial.REGISTRY_KEY).stream().anyMatch(mat -> state.is(mat.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        Registry<AltarStructureMaterial> registry = PatchouliCompat.getRegistry(null, AltarStructureMaterial.REGISTRY_KEY);
        AltarStructureMaterial mat = registry.stream().toArray(AltarStructureMaterial[]::new)[Math.toIntExact(ticks / 20) % registry.size()];
        if (mat == null) return Blocks.AIR.defaultBlockState();
        return mat.block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
