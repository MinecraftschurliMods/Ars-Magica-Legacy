package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.AddPoolToTableModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.GLOBAL_LOOT_MODIFIERS;

public interface AMLootModifiers {
    DeferredHolder<Codec<? extends IGlobalLootModifier>, Codec<AddPoolToTableModifier>> ADD_POOL_TO_TABLE = GLOBAL_LOOT_MODIFIERS.register("add_pool_to_table", () -> AddPoolToTableModifier.CODEC);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
