package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record UltrawarmDimensionRequirement() implements RitualRequirement {
    public static final UltrawarmDimensionRequirement INSTANCE = new UltrawarmDimensionRequirement();
    public static final Codec<UltrawarmDimensionRequirement> CODEC = Codec.unit(INSTANCE);

    @Override
    public boolean test(Player player, ServerLevel level, BlockPos pos) {
        return level.dimensionType().ultraWarm();
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
