package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

/**
 *
 */
public interface RitualEffect {
    Codec<RitualEffect> CODEC = ResourceLocation.CODEC.dispatch("type", RitualManager::getRitualEffectType, RitualManager::getRitualEffectCodec);

    boolean performEffect(ServerLevel level, BlockPos pos);

    Codec<? extends RitualEffect> codec();
}
