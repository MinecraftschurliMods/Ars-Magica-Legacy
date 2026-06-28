package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public interface AMEnchantments {
    ResourceKey<Enchantment> DISMEMBERING = ResourceKey.create(Registries.ENCHANTMENT, ArsMagicaApi.id("dismembering"));
}
