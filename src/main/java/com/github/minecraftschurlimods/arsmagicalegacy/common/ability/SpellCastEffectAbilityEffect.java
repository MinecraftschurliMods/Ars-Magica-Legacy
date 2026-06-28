package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.SpellCastEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

public record SpellCastEffectAbilityEffect(Holder<MobEffect> effect, int duration, int amplifier, boolean visible, double chance) implements EventTriggeredAbilityEffect<SpellCastEvent.Post> {
    public static final MapCodec<SpellCastEffectAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MobEffect.CODEC.fieldOf("effect").forGetter(SpellCastEffectAbilityEffect::effect),
        Codec.INT.fieldOf("duration").forGetter(SpellCastEffectAbilityEffect::duration),
        Codec.INT.optionalFieldOf("amplifier", 0).forGetter(SpellCastEffectAbilityEffect::amplifier),
        Codec.BOOL.optionalFieldOf("visible", false).forGetter(SpellCastEffectAbilityEffect::visible),
        Codec.DOUBLE.fieldOf("chance").forGetter(SpellCastEffectAbilityEffect::chance)
    ).apply(inst, SpellCastEffectAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(SpellCastEvent.Post event, Player player, Holder<Ability> ability) {
        if (player instanceof ServerPlayer serverPlayer && serverPlayer.getRandom().nextDouble() < chance) {
            player.addEffect(new MobEffectInstance(effect, duration, amplifier, false, visible, visible));
        }
    }
}
