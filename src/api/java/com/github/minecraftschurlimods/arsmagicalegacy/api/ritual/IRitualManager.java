package com.github.minecraftschurlimods.arsmagicalegacy.api.ritual;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;

public interface IRitualManager {
    void registerRitualRequirement(ResourceLocation id, Codec<? extends IRitualRequirement> codec);

    void registerRitualEffect(ResourceLocation id, Codec<? extends IRitualEffect> codec);

    void registerRitualTrigger(ResourceLocation id, Codec<? extends IRitualTrigger> codec);

    ResourceLocation getRitualRequirementType(IRitualRequirement ritualRequirement);

    Codec<? extends IRitualRequirement> getRitualRequirementCodec(ResourceLocation resourceLocation);

    ResourceLocation getRitualTriggerType(IRitualTrigger ritualTrigger);

    Codec<? extends IRitualTrigger> getRitualTriggerCodec(ResourceLocation resourceLocation);

    ResourceLocation getRitualEffectType(IRitualEffect ritualEffect);

    Codec<? extends IRitualEffect> getRitualEffectCodec(ResourceLocation resourceLocation);
}
