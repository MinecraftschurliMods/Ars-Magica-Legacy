package com.github.minecraftschurli.arsmagicalegacy.common.block;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author Minecraftschurli
 * @version 2021-07-30
 */
public final class Blocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArsMagicaAPI.MOD_ID);
    private Blocks() {}
}
