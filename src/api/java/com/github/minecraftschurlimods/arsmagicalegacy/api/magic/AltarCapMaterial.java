package com.github.minecraftschurlimods.arsmagicalegacy.api.magic;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.block.Block;

/// Represents an altar's cap material.
///
/// @param block The [Block] of the cap material.
/// @param power The power of the material.
public record AltarCapMaterial(Block block, int power) {
    public static final Codec<AltarCapMaterial> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(AltarCapMaterial::block),
        Codec.INT.fieldOf("power").forGetter(AltarCapMaterial::power)
    ).apply(inst, AltarCapMaterial::new));
    public static final Codec<Holder<AltarCapMaterial>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.ALTAR_CAP_MATERIAL, DIRECT_CODEC);
}
