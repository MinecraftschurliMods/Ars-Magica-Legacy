package com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMLootModifiers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.List;

public record HasLootContextParamCondition(List<ResourceLocation> params) implements LootItemCondition {
    public static final MapCodec<HasLootContextParamCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ResourceLocation.CODEC.listOf().fieldOf("params").forGetter(HasLootContextParamCondition::params)
    ).apply(inst, HasLootContextParamCondition::new));

    public static HasLootContextParamCondition of(List<LootContextParam<?>> params) {
        return new HasLootContextParamCondition(params.stream().map(LootContextParam::getName).toList());
    }

    @Override
    public LootItemConditionType getType() {
        return AMLootModifiers.HAS_LOOT_CONTEXT_PARAM.get();
    }

    @Override
    public boolean test(LootContext context) {
        for (ResourceLocation param : params) {
            if (!context.hasParam(new LootContextParam<>(param))) {
                return false;
            }
        }
        return true;
    }
}
