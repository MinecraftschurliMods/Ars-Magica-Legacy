package com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.AltarMaterial;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import vazkii.patchouli.api.IStateMatcher;
import vazkii.patchouli.api.TriPredicate;

import java.util.Objects;

public final class AltarStairStateMatcher implements IStateMatcher {
    private final Direction direction;
    private final Half half;
    private final TriPredicate<BlockGetter, BlockPos, BlockState> predicate;

    public AltarStairStateMatcher(Direction direction, Half half) {
        this.direction = direction;
        this.half = half;
        predicate = (level, _, state) -> AMRegistries.altarMaterials(level instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess(false))
            .stream()
            .anyMatch(material -> state.is(material.stair()) && state.getValue(StairBlock.FACING) == direction && state.getValue(StairBlock.HALF) == half);
    }

    @Override
    public BlockState getDisplayedState(long ticks) {
        AltarMaterial material = AMUtil.getByTick(AMRegistries.altarMaterials(true)
            .stream()
            .toArray(AltarMaterial[]::new), (int) ticks / 20);
        return Objects.requireNonNull(material).stair().defaultBlockState().setValue(StairBlock.FACING, direction).setValue(StairBlock.HALF, half);
    }

    @Override
    public TriPredicate<BlockGetter, BlockPos, BlockState> getStatePredicate() {
        return predicate;
    }
}
