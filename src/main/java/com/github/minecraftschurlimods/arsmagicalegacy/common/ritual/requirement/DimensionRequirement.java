package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record DimensionRequirement(HolderSet<Level> dimension) implements IRitualRequirement {
    public static final Codec<DimensionRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registry.DIMENSION_REGISTRY).fieldOf("dimension").forGetter(DimensionRequirement::dimension)
    ).apply(inst, DimensionRequirement::new));

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return dimension().contains(level.getServer().registryAccess().registryOrThrow(Registry.DIMENSION_REGISTRY).getOrCreateHolder(level.dimension()));
    }

    @Override
    public Codec<? extends IRitualRequirement> codec() {
        return CODEC;
    }
}
