package com.github.minecraftschurlimods.arsmagicalegacy.api.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.Function;

public record AltarStructureMaterial(Block block, StairBlock stair, int power) {
    public static final ResourceKey<Registry<AltarStructureMaterial>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "altar/structure"));
    public static final Codec<AltarStructureMaterial> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(AltarStructureMaterial::block),
            BuiltInRegistries.BLOCK.byNameCodec().xmap(block1 -> block1 instanceof StairBlock stairBlock ? stairBlock : null, Function.identity()).fieldOf("stair").forGetter(AltarStructureMaterial::stair),
            Codec.INT.fieldOf("power").forGetter(AltarStructureMaterial::power)
    ).apply(inst, AltarStructureMaterial::new));
}
