package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public interface RitualEffect {
    ResourceKey<Registry<MapCodec<? extends RitualEffect>>> REGISTRY_KEY = ResourceKey.createRegistryKey(ArsMagicaAPI.resource("ritual_effect_type"));
    Codec<RitualEffect> CODEC = Codec.lazyInitialized(() -> ArsMagicaAPI.get().getRitualEffectTypeRegistry().byNameCodec()).dispatch(RitualEffect::codec, Function.identity());

    boolean performEffect(Player player, ServerLevel level, BlockPos pos);

    MapCodec<? extends RitualEffect> codec();
}
