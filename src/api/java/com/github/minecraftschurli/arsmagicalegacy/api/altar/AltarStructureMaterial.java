package com.github.minecraftschurli.arsmagicalegacy.api.altar;

import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public record AltarStructureMaterial(Block block, StairBlock stair, int power) {
    public static final Codec<AltarStructureMaterial> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                    CodecHelper.forRegistry(() -> ForgeRegistries.BLOCKS).fieldOf("block").forGetter(AltarStructureMaterial::block),
                    CodecHelper.forRegistry(() -> ForgeRegistries.BLOCKS).xmap(block1 -> block1 instanceof StairBlock stairBlock ? stairBlock : null, Function.identity()).fieldOf("stair").forGetter(AltarStructureMaterial::stair),
                    Codec.INT.fieldOf("power").forGetter(AltarStructureMaterial::power)
            ).apply(inst, AltarStructureMaterial::new));
}
