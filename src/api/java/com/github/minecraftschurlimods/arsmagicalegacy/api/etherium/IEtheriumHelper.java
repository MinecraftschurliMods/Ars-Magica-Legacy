package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Interface for etherium related helper methods.
 */
public interface IEtheriumHelper {
    /**
     * @param blockEntity The block entity to check.
     * @return Whether the given block entity has an etherium provider or not.
     */
    boolean hasEtheriumProvider(BlockEntity blockEntity);

    /**
     * @param blockEntity The block entity to check.
     * @return Whether the given block entity has an etherium consumer or not.
     */
    boolean hasEtheriumConsumer(BlockEntity blockEntity);

    /**
     * @param level The level to use.
     * @param pos   The position to use.
     * @return Whether the block entity at the given position in the given level has an etherium provider or not.
     */
    boolean hasEtheriumProvider(Level level, BlockPos pos);

    /**
     * @param level The level to use.
     * @param pos   The position to use.
     * @return Whether the block entity at the given position in the given level has an etherium consumer or not.
     */
    boolean hasEtheriumConsumer(Level level, BlockPos pos);

    /**
     * @param blockEntity The block entity to use.
     * @return The etherium provider of the given block entity.
     */
    LazyOptional<IEtheriumProvider> getEtheriumProvider(BlockEntity blockEntity);

    /**
     * @param blockEntity The block entity to use.
     * @return The etherium consumer of the given block entity.
     */
    LazyOptional<IEtheriumConsumer> getEtheriumConsumer(BlockEntity blockEntity);

    /**
     * @param level The level to use.
     * @param pos   The position to use.
     * @return The etherium provider of the block entity at the given position in the given level.
     */
    LazyOptional<IEtheriumProvider> getEtheriumProvider(Level level, BlockPos pos);

    /**
     * @param level The level to use.
     * @param pos   The position to use.
     * @return The etherium consumer of the block entity at the given position in the given level.
     */
    LazyOptional<IEtheriumConsumer> getEtheriumConsumer(Level level, BlockPos pos);
}
