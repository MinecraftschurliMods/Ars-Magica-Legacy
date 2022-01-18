package com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

public class BlackAuremBlock extends BaseEntityBlock {
    public BlackAuremBlock() {
        super(BlockBehaviour.Properties.of(Material.AIR).air().color(MaterialColor.COLOR_RED).lightLevel(value -> 2).noOcclusion());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final BlockPos pos, final BlockState state) {
        return AMBlockEntities.BLACK_AUREM.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(final Level level, final BlockState state, final BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, AMBlockEntities.BLACK_AUREM.get(), (pLevel, pPos, pState, pBlockEntity) -> pBlockEntity.tick(pLevel, pPos, pState));
    }
}
