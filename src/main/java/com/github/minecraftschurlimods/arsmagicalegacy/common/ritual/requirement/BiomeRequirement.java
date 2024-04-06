package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

public record BiomeRequirement(HolderSet<Biome> biome) implements RitualRequirement {
    public static final Codec<BiomeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biome").forGetter(BiomeRequirement::biome)
    ).apply(inst, BiomeRequirement::new));

    public static BiomeRequirement tag(HolderGetter<Biome> holderGetter, TagKey<Biome> biomeTag) {
        return new BiomeRequirement(holderGetter.getOrThrow(biomeTag));
    }

    public static BiomeRequirement single(HolderGetter<Biome> holderGetter, ResourceKey<Biome> biome) {
        return any(holderGetter.getOrThrow(biome));
    }

    @SafeVarargs
    public static BiomeRequirement any(Holder<Biome>... holders) {
        return new BiomeRequirement(HolderSet.direct(holders));
    }

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return biome().contains(level.getBiome(pos));
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
