package com.github.minecraftschurli.arsmagicalegacy.data;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.api.data.AltarStructureMaterialProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.StairBlock;

public class AMAltarStructureMaterialProvider extends AltarStructureMaterialProvider {
    public AMAltarStructureMaterialProvider(DataGenerator generator) {
        super(ArsMagicaAPI.MOD_ID, generator);
    }

    @Override
    protected void createStructureMaterials() {
        addCapMaterial("diamond", Blocks.DIAMOND_BLOCK, 9);
        addStructureMaterial("dark_oak", Blocks.DARK_OAK_PLANKS, (StairBlock)Blocks.DARK_OAK_STAIRS, 6);
    }
}
