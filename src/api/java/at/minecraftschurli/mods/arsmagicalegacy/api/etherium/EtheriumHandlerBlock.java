package at.minecraftschurli.mods.arsmagicalegacy.api.etherium;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

/// Represents a block that has an [EtheriumHandler] [BlockEntity] attached. Methods in this class mainly serve selection purposes.
public interface EtheriumHandlerBlock {
    /// Returns the [BlockEntity] at the given position. If this is a block larger than 1x1x1,
    /// this should return the [BlockEntity] that actually controls the logic. If an actual block entity is not present, null should be returned.
    ///
    /// @param level The [Level] to use.
    /// @param pos   The [BlockPos] to use.
    /// @param state The [BlockState] to use.
    /// @return The [BlockEntity] at the given position.
    @Nullable
    BlockEntity getBlockEntity(Level level, BlockPos pos, BlockState state);
}
