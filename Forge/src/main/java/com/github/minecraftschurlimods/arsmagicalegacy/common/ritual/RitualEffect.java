package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public interface RitualEffect {
    Codec<RitualEffect> CODEC = ResourceLocation.CODEC.dispatch("type", RitualManager::getRitualEffectType, RitualManager::getRitualEffectCodec);

    boolean performEffect(Player player, ServerLevel level, BlockPos pos);

    Codec<? extends RitualEffect> codec();
}
