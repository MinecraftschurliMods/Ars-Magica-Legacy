package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarCapMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

import java.util.Objects;

public final class AltarCapStateMatcher implements IStateMatcher {
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarCapStateMatcher() {
        predicate = (level, _, state) -> AMRegistries.altarCapMaterials(level instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess(false))
            .stream()
            .anyMatch(material -> state.is(material.block()));
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarCapMaterial material = AMUtil.getByTick(AMRegistries.altarCapMaterials(true)
            .stream()
            .toArray(AltarCapMaterial[]::new), (int) ticks / 20);
        return Objects.requireNonNull(material).block().defaultBlockState();
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
