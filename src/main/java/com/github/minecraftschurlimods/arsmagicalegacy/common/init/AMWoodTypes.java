package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public interface AMWoodTypes {
    BlockSetType WITCHWOOD_BLOCK_SET_TYPE = BlockSetType.register(new BlockSetType("witchwood"));
    WoodType WITCHWOOD = WoodType.register(new WoodType(ArsMagicaAPI.MOD_ID + ":witchwood", WITCHWOOD_BLOCK_SET_TYPE));
}
