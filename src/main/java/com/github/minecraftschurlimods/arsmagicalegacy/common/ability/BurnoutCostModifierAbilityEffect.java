package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.EventTriggeredAbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.event.ManaBurnoutCostEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;

public record BurnoutCostModifierAbilityEffect(double min, double max) implements EventTriggeredAbilityEffect<ManaBurnoutCostEvent> {
    public static final MapCodec<BurnoutCostModifierAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(BurnoutCostModifierAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(BurnoutCostModifierAbilityEffect::max)
    ).apply(inst, BurnoutCostModifierAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void apply(ManaBurnoutCostEvent event, Player player, Holder<Ability> ability) {
        event.setBurnout(event.getBurnout() * ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max));
    }
}
