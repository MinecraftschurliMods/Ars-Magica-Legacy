package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

public record BiomeRequirement(HolderSet<Biome> biome) implements RitualRequirement {
    public static final Codec<BiomeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).fieldOf("biome").forGetter(BiomeRequirement::biome)
    ).apply(inst, BiomeRequirement::new));

    public BiomeRequirement(TagKey<Biome> biomeTag) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.BIOME_REGISTRY).getOrCreateTag(biomeTag));
    }

    public BiomeRequirement(ResourceKey<Biome> biome) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.BIOME_REGISTRY).getOrCreateHolder(biome).getOrThrow(false, s -> {}));
    }

    @SafeVarargs
    public BiomeRequirement(Holder<Biome>... holders) {
        this(HolderSet.direct(holders));
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
