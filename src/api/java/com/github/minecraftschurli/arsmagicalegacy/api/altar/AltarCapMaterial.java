package com.github.minecraftschurli.arsmagicalegacy.api.altar;

import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * TODO: doc
 */
public record AltarCapMaterial(Block cap, int power) {
    public static final Codec<AltarCapMaterial> CODEC =
            RecordCodecBuilder.create(inst -> inst.group(
                    CodecHelper.forRegistry(() -> ForgeRegistries.BLOCKS).fieldOf("cap").forGetter(AltarCapMaterial::cap),
                    Codec.INT.fieldOf("power").forGetter(AltarCapMaterial::power)
            ).apply(inst, AltarCapMaterial::new));
}
