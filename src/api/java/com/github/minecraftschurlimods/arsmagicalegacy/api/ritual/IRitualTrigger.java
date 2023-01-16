package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public interface IRitualTrigger {
    Codec<IRitualTrigger> CODEC = ResourceLocation.CODEC.dispatch("type", ArsMagicaAPI.get().getRitualManager()::getRitualTriggerType, ArsMagicaAPI.get().getRitualManager()::getRitualTriggerCodec);

    void register(Ritual ritual);

    boolean trigger(Player player, ServerLevel level, BlockPos pos, IContext ctx);

    Codec<? extends IRitualTrigger> codec();
}
