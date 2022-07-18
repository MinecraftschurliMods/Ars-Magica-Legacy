package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.altar.AltarCapMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

class CapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    CapStateMatcher() {
        this.predicate = (blockGetter, blockPos, state) -> RegistryAccess.BUILTIN.get().registryOrThrow(AltarCapMaterial.REGISTRY_KEY).stream().anyMatch(mat -> state.is(mat.cap()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        Registry<AltarCapMaterial> registry = RegistryAccess.BUILTIN.get().registryOrThrow(AltarCapMaterial.REGISTRY_KEY);
        AltarCapMaterial mat = registry.stream().toArray(AltarCapMaterial[]::new)[Math.toIntExact(ticks / 20) % registry.size()];
        if (mat == null) return Blocks.AIR.defaultBlockState();
        return mat.cap().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
