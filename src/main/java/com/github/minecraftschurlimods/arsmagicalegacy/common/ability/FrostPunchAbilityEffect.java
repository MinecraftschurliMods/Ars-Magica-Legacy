package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FrostPunchAbilityEffect(int min, int max) implements AbilityEffect {
    public static final MapCodec<FrostPunchAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.INT.fieldOf("min").forGetter(FrostPunchAbilityEffect::min),
        Codec.INT.fieldOf("max").forGetter(FrostPunchAbilityEffect::max)
    ).apply(inst, FrostPunchAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }
}
