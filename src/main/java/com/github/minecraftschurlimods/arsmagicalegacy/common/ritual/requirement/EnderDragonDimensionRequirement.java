package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.IRitualRequirement;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record EnderDragonDimensionRequirement() implements IRitualRequirement {
    public static final Codec<EnderDragonDimensionRequirement> CODEC = Codec.unit(EnderDragonDimensionRequirement::new);

    @Override
    public Codec<? extends IRitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(final Player player, final ServerLevel serverLevel, final BlockPos pos) {
        return serverLevel.dimensionType().createDragonFight();
    }
}
