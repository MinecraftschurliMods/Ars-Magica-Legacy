package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

/**
 *
 */
public record BiomeRequirement(HolderSet<Biome> biome) implements Ritual.RitualRequirement {
    public static final Codec<BiomeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registry.BIOME_REGISTRY).fieldOf("biome").forGetter(BiomeRequirement::biome)
    ).apply(inst, BiomeRequirement::new));

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return biome().contains(level.getBiome(pos));
    }

    @Override
    public Codec<? extends Ritual.RitualRequirement> codec() {
        return CODEC;
    }
}
