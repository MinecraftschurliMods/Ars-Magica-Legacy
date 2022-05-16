package com.github.minecraftschurlimods.arsmagicalegacy.api.altar;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;

public record AltarCapMaterial(Block cap, int power) {
    public static final Codec<AltarCapMaterial> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Registry.BLOCK.byNameCodec().fieldOf("cap").forGetter(AltarCapMaterial::cap),
            Codec.INT.fieldOf("power").forGetter(AltarCapMaterial::power)
    ).apply(inst, AltarCapMaterial::new));
}
