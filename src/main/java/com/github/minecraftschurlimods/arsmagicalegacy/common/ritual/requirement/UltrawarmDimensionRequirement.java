package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record UltrawarmDimensionRequirement() implements RitualRequirement {
    public static final UltrawarmDimensionRequirement INSTANCE = new UltrawarmDimensionRequirement();
    public static final MapCodec<UltrawarmDimensionRequirement> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean test(Player player, ServerLevel level, BlockPos pos) {
        return level.dimensionType().ultraWarm();
    }

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
