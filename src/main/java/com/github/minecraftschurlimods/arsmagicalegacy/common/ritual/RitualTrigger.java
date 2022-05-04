package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

/**
 *
 */
public interface RitualTrigger {
    Codec<RitualTrigger> CODEC = ResourceLocation.CODEC.dispatch("type", RitualManager::getRitualTriggerType, RitualManager::getRitualTriggerCodec);

    void register(Ritual ritual);

    boolean trigger(ServerLevel level, BlockPos pos, Context ctx);

    Codec<? extends RitualTrigger> codec();
}
