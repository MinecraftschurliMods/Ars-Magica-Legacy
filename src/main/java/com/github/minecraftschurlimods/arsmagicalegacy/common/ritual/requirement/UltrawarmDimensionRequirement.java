package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/**
 *
 */
public record UltrawarmDimensionRequirement() implements Ritual.RitualRequirement {
    public static final Codec<UltrawarmDimensionRequirement> CODEC = Codec.unit(UltrawarmDimensionRequirement::new);

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return level.dimensionType().ultraWarm();
    }

    @Override
    public Codec<? extends Ritual.RitualRequirement> codec() {
        return CODEC;
    }
}
