package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public record DimensionRequirement(HolderSet<Level> dimension) implements RitualRequirement {
    public static final Codec<DimensionRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registries.DIMENSION).fieldOf("dimension").forGetter(DimensionRequirement::dimension)
    ).apply(inst, DimensionRequirement::new));

    @Override
    public boolean test(Player player, ServerLevel level, BlockPos pos) {
        return dimension().contains(level.registryAccess().registryOrThrow(Registries.DIMENSION).getHolderOrThrow(level.dimension()));
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
