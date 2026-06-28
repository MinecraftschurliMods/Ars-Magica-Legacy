package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record EffectResistanceAbilityEffect(List<Holder<MobEffect>> effects) implements AbilityEffect {
    public static final MapCodec<EffectResistanceAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MobEffect.CODEC.listOf().fieldOf("effects").forGetter(EffectResistanceAbilityEffect::effects)
    ).apply(inst, EffectResistanceAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void tick(Player player, Holder<Ability> ability) {
        effects.forEach(player::removeEffect);
    }
}
