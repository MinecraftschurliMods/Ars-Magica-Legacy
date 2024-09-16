package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.AddConditionsModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.AddPoolToTableModifier;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.HasLootContextParamCondition;
import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.HasSummonOwnerCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Supplier;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.GLOBAL_LOOT_MODIFIERS;
import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.LOOT_CONDITION_TYPES;

public interface AMLootModifiers {
    Supplier<MapCodec<AddPoolToTableModifier>> ADD_POOL_TO_TABLE = GLOBAL_LOOT_MODIFIERS.register("add_pool_to_table", () -> AddPoolToTableModifier.CODEC);
    Supplier<MapCodec<AddConditionsModifier>>  ADD_CONDITIONS    = GLOBAL_LOOT_MODIFIERS.register("add_conditions",    () -> AddConditionsModifier.CODEC);

    Supplier<LootItemConditionType> HAS_LOOT_CONTEXT_PARAM = LOOT_CONDITION_TYPES.register("has_loot_context_param", () -> new LootItemConditionType(HasLootContextParamCondition.CODEC));
    Supplier<LootItemConditionType> HAS_SUMMON_OWNER       = LOOT_CONDITION_TYPES.register("has_summon_owner",       () -> new LootItemConditionType(MapCodec.unit(new HasSummonOwnerCondition())));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
