package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 *
 */
public interface ISpellTransformationManager {
    /**
     * @param block     The block to check the transition for.
     * @param level     The level to check the transition for.
     * @param spellPart The spell part to check the transition for.
     * @return The transitioned block state for the given block and spell part or empty.
     */
    Optional<BlockState> getTransformationFor(BlockState block, Level level, ResourceLocation spellPart);
}
