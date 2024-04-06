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
import net.minecraft.world.level.dimension.DimensionType;

public record DimensionTypeRequirement(HolderSet<DimensionType> dimensionType) implements RitualRequirement {
    public static final Codec<DimensionTypeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registries.DIMENSION_TYPE).fieldOf("dimension_type").forGetter(DimensionTypeRequirement::dimensionType)
    ).apply(inst, DimensionTypeRequirement::new));

    public static DimensionTypeRequirement tag(HolderGetter<DimensionType> holderGetter, TagKey<DimensionType> dimensionTypeTag) {
        return new DimensionTypeRequirement(holderGetter.getOrThrow(dimensionTypeTag));
    }

    public static DimensionTypeRequirement simple(HolderGetter<DimensionType> holderGetter, ResourceKey<DimensionType> dimensionType) {
        return any(holderGetter.getOrThrow(dimensionType));
    }

    @SafeVarargs
    public static DimensionTypeRequirement any(Holder<DimensionType>... holders) {
        return new DimensionTypeRequirement(HolderSet.direct(holders));
    }

    @Override
    public boolean test(Player player, ServerLevel level, BlockPos pos) {
        return dimensionType().contains(level.dimensionTypeRegistration());
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
