package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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

    /**
     * @return The icon of the spell.
     */
    ResourceLocation icon();

    /**
     * @return The name of the spell.
     */
    Component name();

    /**
     * @return The spell.
     */
    ISpell spell();
}
