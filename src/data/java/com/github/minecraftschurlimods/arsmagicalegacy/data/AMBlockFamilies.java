package com.github.minecraftschurlimods.arsmagicalegacy.data;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.data.BlockFamily;

public interface AMBlockFamilies {
    BlockFamily WITCHWOOD = new BlockFamily.Builder(AMBlocks.WITCHWOOD_PLANKS.get()).button(AMBlocks.WITCHWOOD_BUTTON.get()).fence(AMBlocks.WITCHWOOD_FENCE.get()).fenceGate(AMBlocks.WITCHWOOD_FENCE_GATE.get()).pressurePlate(AMBlocks.WITCHWOOD_PRESSURE_PLATE.get()).sign(AMBlocks.WITCHWOOD_SIGN.get(), AMBlocks.WITCHWOOD_WALL_SIGN.get()).slab(AMBlocks.WITCHWOOD_SLAB.get()).stairs(AMBlocks.WITCHWOOD_STAIRS.get()).door(AMBlocks.WITCHWOOD_DOOR.get()).trapdoor(AMBlocks.WITCHWOOD_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
}
