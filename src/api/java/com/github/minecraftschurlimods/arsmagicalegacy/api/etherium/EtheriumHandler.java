package com.github.minecraftschurlimods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.SequencedSet;

/// Represents an etherium capability handler.
public interface EtheriumHandler {
    /// @return The [EtheriumType]s supported by the handler.
    List<Holder<EtheriumType>> getEtheriumTypes();

    /// @param type The [EtheriumType] to get the stored amount for.
    /// @return The amount of the [EtheriumType] stored in the handler.
    int getAmount(Holder<EtheriumType> type);

    /// @param type The [EtheriumType] to get the maximum amount for.
    /// @return The maximum amount of the [EtheriumType] that can be stored in the handler.
    int getMaxAmount(Holder<EtheriumType> type);

    /// Sets the stored amount for the given [EtheriumType].
    ///
    /// @param type   The [EtheriumType] to set the stored amount for.
    /// @param amount The stored amount to set.
    void setAmount(Holder<EtheriumType> type, int amount);

    /// Adds the given amount to the stored amount for the given [EtheriumType].
    ///
    /// Returns the amount left to be added. For example, if all etherium was successfully added,
    /// 0 will be returned. If the handler is full, the amount will be returned.
    ///
    /// @param type   The [EtheriumType] to add the given amount for.
    /// @param amount The amount to add.
    /// @return The amount left to be added.
    int addAmount(Holder<EtheriumType> type, int amount);

    /// Subtracts the given amount to the stored amount for the given [EtheriumType].
    ///
    /// Returns the amount left to be subtracted. For example, if all etherium was successfully subtracted,
    /// 0 will be returned. If the handler is full, the amount will be returned.
    ///
    /// @param type   The [EtheriumType] to subtract the given amount for.
    /// @param amount The amount to subtract.
    /// @return The amount left to be subtracted.
    int subtractAmount(Holder<EtheriumType> type, int amount);

    /// Returns the block outline [AABB] to render when the Magitech Goggles are equipped. If the handler is not in a block context, this method should return null.
    /// If this is a block larger than 1x1x1, only the part that actually controls the logic should return an [AABB], all other parts should return null.
    ///
    /// @param level The [Level] to use.
    /// @param pos   The [BlockPos] to use.
    /// @param state The [BlockState] to use.
    /// @return The block outline [AABB].
    @Nullable
    AABB getOutline(Level level, BlockPos pos, BlockState state);

    /// Returns the block outline color, in ARGB format.
    ///
    /// @param level The [Level] to use.
    /// @param pos   The [BlockPos] to use.
    /// @param state The [BlockState] to use.
    /// @return The block outline color.
    int getOutlineColor(Level level, BlockPos pos, BlockState state);

    /// @return Whether the handler is allowed to have connections to other handlers.
    boolean canHaveConnectedPositions();

    /// @return A [SequencedSet] of connections to other handlers. If [#canHaveConnectedPositions()] returns false, this is expected to be always empty.
    SequencedSet<BlockPos> getConnectedPositions();

    /// Adds a [BlockPos] to connect. If [#canHaveConnectedPositions()] returns false, this method should do nothing.
    ///
    /// @param pos The [BlockPos] to add.
    void addConnectedPosition(BlockPos pos);

    /// Removes a [BlockPos] to connect. If [#canHaveConnectedPositions()] returns false, this method should do nothing.
    ///
    /// @param pos The [BlockPos] to remove.
    void removeConnectedPosition(BlockPos pos);
}
