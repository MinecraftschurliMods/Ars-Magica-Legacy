package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.clock.ClockManager;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.timeline.Timeline;
import net.minecraft.world.timeline.Timelines;

public record LightHealthModifierAbilityEffect(double min, double max, int maxLight) implements AbilityEffect {
    public static final MapCodec<LightHealthModifierAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.DOUBLE.fieldOf("min").forGetter(LightHealthModifierAbilityEffect::min),
        Codec.DOUBLE.fieldOf("max").forGetter(LightHealthModifierAbilityEffect::max),
        Codec.INT.fieldOf("max_light").forGetter(LightHealthModifierAbilityEffect::maxLight)
    ).apply(inst, LightHealthModifierAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick(Player player, Holder<Ability> ability) {
        AttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute == null) return;
        Identifier identifier = ability.getKey().identifier();
        attribute.removeModifier(identifier);
        ClockManager clockManager = player.level().clockManager();
        Timeline timeline = player.level().registryAccess().getOrThrow(Timelines.OVERWORLD_DAY).value();
        // TODO use time marker
        if (timeline.getCurrentTicks(clockManager) >= 12000 || player.level().getBrightness(LightLayer.SKY, player.blockPosition()) <= maxLight) return;
        attribute.addTransientModifier(new AttributeModifier(identifier, ArsMagicaApi.abilityHelper().scaleToDepth(player, ability.value(), min, max), AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
    }
}
