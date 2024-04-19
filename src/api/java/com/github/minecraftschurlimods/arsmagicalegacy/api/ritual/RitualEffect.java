package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public interface RitualEffect {
    ResourceKey<Registry<Codec<? extends RitualEffect>>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ritual_effect_type"));
    Codec<RitualEffect> CODEC = ExtraCodecs.lazyInitializedCodec(() -> ArsMagicaAPI.get().getRitualEffectTypeRegistry().byNameCodec()).dispatch(RitualEffect::codec, Function.identity());

    boolean performEffect(Player player, ServerLevel level, BlockPos pos);

    Codec<? extends RitualEffect> codec();
}
