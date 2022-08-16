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
import net.minecraft.world.level.dimension.DimensionType;

public record DimensionTypeRequirement(HolderSet<DimensionType> dimensionType) implements RitualRequirement {
    public static final Codec<DimensionTypeRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            RegistryCodecs.homogeneousList(Registry.DIMENSION_TYPE_REGISTRY).fieldOf("dimension_type").forGetter(DimensionTypeRequirement::dimensionType)
    ).apply(inst, DimensionTypeRequirement::new));

    public DimensionTypeRequirement(TagKey<DimensionType> dimensionTypeTag) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getOrCreateTag(dimensionTypeTag));
    }

    public DimensionTypeRequirement(ResourceKey<DimensionType> dimensionType) {
        this(RegistryAccess.BUILTIN.get().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY).getOrCreateHolder(dimensionType).getOrThrow(false, s -> {}));
    }

    @SafeVarargs
    public DimensionTypeRequirement(Holder<DimensionType>... holders) {
        this(HolderSet.direct(holders));
    }

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return dimensionType().contains(level.dimensionTypeRegistration());
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
