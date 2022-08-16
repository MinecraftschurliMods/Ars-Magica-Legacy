package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public interface RitualTrigger {
    ResourceKey<Registry<Codec<? extends RitualTrigger>>> REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(ArsMagicaAPI.MOD_ID, "ritual_trigger_type"));
    Codec<RitualTrigger> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getRitualTriggerTypeRegistry).dispatch(RitualTrigger::codec, Function.identity());

    void register(Ritual ritual);

    boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx);

    Codec<? extends RitualTrigger> codec();
}
