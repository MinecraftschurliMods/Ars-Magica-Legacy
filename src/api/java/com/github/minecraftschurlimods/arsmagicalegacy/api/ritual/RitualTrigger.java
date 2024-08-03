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

public interface RitualTrigger {
    ResourceKey<Registry<MapCodec<? extends RitualTrigger>>> REGISTRY_KEY = ResourceKey.createRegistryKey(ArsMagicaAPI.resource("ritual_trigger_type"));
    Codec<RitualTrigger> CODEC = Codec.lazyInitialized(() -> ArsMagicaAPI.get().getRitualTriggerTypeRegistry().byNameCodec()).dispatch(RitualTrigger::codec, Function.identity());

    void register(Ritual ritual);

    boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx);

    MapCodec<? extends RitualTrigger> codec();
}
