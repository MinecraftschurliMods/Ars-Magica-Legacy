package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.dimension.DimensionType;

/**
 *
 */
public record DimensionTypeRequirement(HolderSet<DimensionType> dimensionType) implements RitualRequirement {
    public static final Codec<DimensionTypeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registry.DIMENSION_TYPE_REGISTRY).fieldOf("dimension_type").forGetter(DimensionTypeRequirement::dimensionType)
    ).apply(inst, DimensionTypeRequirement::new));

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return dimensionType().contains(level.dimensionTypeRegistration());
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
