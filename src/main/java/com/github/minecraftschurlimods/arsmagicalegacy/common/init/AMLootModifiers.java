package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.level.loot.AddPoolToTableModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.GLOBAL_LOOT_MODIFIERS;

public interface AMLootModifiers {
    RegistryObject<Codec<AddPoolToTableModifier>> ADD_POOL_TO_TABLE = GLOBAL_LOOT_MODIFIERS.register("add_pool_to_table", () -> AddPoolToTableModifier.CODEC);

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
