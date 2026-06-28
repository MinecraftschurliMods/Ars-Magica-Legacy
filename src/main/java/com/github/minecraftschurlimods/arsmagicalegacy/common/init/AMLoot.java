package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.loot.AddConditionsModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.loot.EnchantmentLevelFromItemProvider;
import com.github.minecraftschurlimods.arsmagicalegacy.common.loot.IsSummonCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public interface AMLoot {
    DeferredRegister<MapCodec<? extends LootItemCondition>> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, ArsMagicaApi.MOD_ID);
    DeferredHolder<MapCodec<? extends LootItemCondition>, MapCodec<IsSummonCondition>> IS_SUMMON = LOOT_CONDITIONS.register("is_summon", () -> IsSummonCondition.CODEC);

    DeferredRegister<MapCodec<? extends NumberProvider>> NUMBER_PROVIDERS = DeferredRegister.create(Registries.LOOT_NUMBER_PROVIDER_TYPE, ArsMagicaApi.MOD_ID);
    DeferredHolder<MapCodec<? extends NumberProvider>, MapCodec<EnchantmentLevelFromItemProvider>> ENCHANTMENT_LEVEL_FROM_ITEM = NUMBER_PROVIDERS.register("enchantment_level_from_item", () -> EnchantmentLevelFromItemProvider.CODEC);

    DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ArsMagicaApi.MOD_ID);
    DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddConditionsModifier>> ADD_CONDITIONS = GLOBAL_LOOT_MODIFIERS.register("add_conditions", () -> AddConditionsModifier.CODEC);
}
