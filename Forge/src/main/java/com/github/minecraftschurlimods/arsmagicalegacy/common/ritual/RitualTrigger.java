package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public interface RitualTrigger {
    Codec<RitualTrigger> CODEC = ResourceLocation.CODEC.dispatch("type", RitualManager::getRitualTriggerType, RitualManager::getRitualTriggerCodec);

    void register(Ritual ritual);

    boolean trigger(Player player, ServerLevel level, BlockPos pos, Context ctx);

    Codec<? extends RitualTrigger> codec();
}
