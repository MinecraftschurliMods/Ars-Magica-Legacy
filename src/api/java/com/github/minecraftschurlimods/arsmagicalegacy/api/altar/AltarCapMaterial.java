package com.github.minecraftschurlimods.arsmagicalegacy.api.altar;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public record AltarCapMaterial(Block cap, int power) {
    public static final ResourceKey<Registry<AltarCapMaterial>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "altar/cap"));
    public static final Codec<AltarCapMaterial> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("cap").forGetter(AltarCapMaterial::cap),
            Codec.INT.fieldOf("power").forGetter(AltarCapMaterial::power)
    ).apply(inst, AltarCapMaterial::new));
}
