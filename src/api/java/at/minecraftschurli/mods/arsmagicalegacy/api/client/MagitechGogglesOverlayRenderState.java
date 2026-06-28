package at.minecraftschurli.mods.arsmagicalegacy.api.client;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.ApiStatus;

/// Represents a render state for the Magitech Goggles overlay. Also contains methods for extracting and submitting.
@ApiStatus.NonExtendable
public interface MagitechGogglesOverlayRenderState {
    /// Clears the internal state of the render state.
    void clear();

    /// Extracts the render state from the given [BlockEntity]. This requires the [BlockEntity] to expose the [AMCapabilities#BLOCK_ETHERIUM] capability.
    ///
    /// @param blockEntity The [BlockEntity] to extract from.
    void extract(BlockEntity blockEntity);

    /// Stores a line between two [BlockPos]es in the render state.
    ///
    /// @param pos1  The first [BlockPos].
    /// @param pos2  The second [BlockPos].
    /// @param width The width of the line.
    /// @param color The color of the line.
    void extractLine(BlockPos pos1, BlockPos pos2, float width, int color);

    /// Stores an [AABB] in the render state.
    ///
    /// @param aabb  The [AABB] to store.
    /// @param width The width of the line.
    /// @param color The color of the line.
    void extractBox(AABB aabb, float width, int color);

    /// Submits the state to the given [SubmitNodeCollector].
    ///
    /// @param stack     The [PoseStack] to use.
    /// @param collector The [SubmitNodeCollector] to submit to.
    void submit(PoseStack stack, SubmitNodeCollector collector);
}
