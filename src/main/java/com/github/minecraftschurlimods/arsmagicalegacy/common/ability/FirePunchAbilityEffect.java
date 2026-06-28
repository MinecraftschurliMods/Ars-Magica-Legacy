package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record FirePunchAbilityEffect(int min, int max) implements AbilityEffect {
    public static final MapCodec<FirePunchAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.INT.fieldOf("min").forGetter(FirePunchAbilityEffect::min),
        Codec.INT.fieldOf("max").forGetter(FirePunchAbilityEffect::max)
    ).apply(inst, FirePunchAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }
}
