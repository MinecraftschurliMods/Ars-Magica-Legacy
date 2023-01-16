package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public interface IRitualEffect {
    Codec<IRitualEffect> CODEC = ResourceLocation.CODEC.dispatch("type", ArsMagicaAPI.get().getRitualManager()::getRitualEffectType, ArsMagicaAPI.get().getRitualManager()::getRitualEffectCodec);

    boolean performEffect(Player player, ServerLevel level, BlockPos pos);

    Codec<? extends IRitualEffect> codec();
}
