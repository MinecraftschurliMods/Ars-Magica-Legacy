package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;

public interface IPrefabSpell {
    /**
     * @return The encoded spell of this prefab spell.
     */
    DataResult<JsonElement> getEncodedSpell();

    /**
     * @return A spell item stack built from this prefab spell.
     */
    ItemStack makeSpell();
}
