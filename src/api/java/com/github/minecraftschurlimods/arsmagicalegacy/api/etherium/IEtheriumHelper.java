package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;

/**
 * TODO doc
 */
public interface IEtheriumHelper {
    boolean hasEtheriumProvider(BlockEntity blockEntity);

    boolean hasEtheriumConsumer(BlockEntity blockEntity);

    boolean hasEtheriumProvider(Level level, BlockPos pos);

    boolean hasEtheriumConsumer(Level level, BlockPos pos);

    LazyOptional<IEtheriumProvider> getEtheriumProvider(BlockEntity blockEntity);

    LazyOptional<IEtheriumConsumer> getEtheriumConsumer(BlockEntity blockEntity);

    LazyOptional<IEtheriumProvider> getEtheriumProvider(Level level, BlockPos pos);

    LazyOptional<IEtheriumConsumer> getEtheriumConsumer(Level level, BlockPos pos);
}
