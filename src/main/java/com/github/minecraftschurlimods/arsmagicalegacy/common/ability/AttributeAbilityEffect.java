package com.github.minecraftschurlimods.arsmagicalegacy.common.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.Ability;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityEffect;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ability.AbilityHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.LinearAttributeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public record AttributeAbilityEffect(Map<Holder<Attribute>, LinearAttributeModifier> modifiers) implements AbilityEffect {
    public static final MapCodec<AttributeAbilityEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Codec.unboundedMap(Attribute.CODEC, LinearAttributeModifier.CODEC).fieldOf("modifiers").forGetter(AttributeAbilityEffect::modifiers)
    ).apply(inst, AttributeAbilityEffect::new));

    @Override
    public MapCodec<? extends AbilityEffect> codec() {
        return CODEC;
    }

    @Override
    public void shiftInto(Player player, Holder<Ability> ability) {
        AbilityHelper abilityHelper = ArsMagicaApi.abilityHelper();
        for (Map.Entry<Holder<Attribute>, LinearAttributeModifier> entry : this.modifiers.entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                LinearAttributeModifier value = entry.getValue();
                attribute.addOrUpdateTransientModifier(new AttributeModifier(value.id(), abilityHelper.scaleToDepth(player, ability.value(), value.min(), value.max()), value.operation()));
            }
        }
    }

    @Override
    public void shiftOutOf(Player player, Holder<Ability> ability) {
        for (Map.Entry<Holder<Attribute>, LinearAttributeModifier> entry : this.modifiers.entrySet()) {
            AttributeInstance attribute = player.getAttribute(entry.getKey());
            if (attribute != null) {
                attribute.removeModifier(entry.getValue().id());
            }
        }
    }
}
