package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public interface AMEnchantments {
    ResourceKey<Enchantment> DISMEMBERING = ResourceKey.create(Registries.ENCHANTMENT, ArsMagicaApi.id("dismembering"));
}
