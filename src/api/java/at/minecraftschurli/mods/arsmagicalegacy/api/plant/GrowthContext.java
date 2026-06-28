package at.minecraftschurli.mods.arsmagicalegacy.api.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/// A context record used for most methods in [GrowthType].
///
/// @param plant  The [Plant] to use.
/// @param player The [ServerPlayer] to use.
/// @param level  The [ServerLevel] to use.
/// @param pos    The [BlockPos] to use.
/// @param state  The [BlockState] to use.
/// @param tool   The [ItemStack] to use.
public record GrowthContext(Plant plant, ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state, ItemStack tool) {
}
