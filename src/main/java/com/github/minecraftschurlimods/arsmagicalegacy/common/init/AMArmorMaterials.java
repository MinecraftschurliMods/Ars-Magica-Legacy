package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.EnumMap;
import java.util.List;

import static com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMRegistries.ARMOR_MATERIALS;

@NonExtendable
public interface AMArmorMaterials {
    Holder<ArmorMaterial> MAGITECH = ARMOR_MATERIALS.register("magitech", () -> new ArmorMaterial(new EnumMap<>(ArmorItem.Type.class), 0, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.EMPTY, List.of(new ArmorMaterial.Layer(new ResourceLocation(ArsMagicaAPI.MOD_ID, "magitech"))), 0, 0));
    Holder<ArmorMaterial> MAGE = ARMOR_MATERIALS.register("mage", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 2);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.LEGGINGS, 4);
        map.put(ArmorItem.Type.BOOTS, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, () -> Ingredient.of(AMItems.BLANK_RUNE.get()), List.of(), 0.5f, 0));
    Holder<ArmorMaterial> BATTLEMAGE = ARMOR_MATERIALS.register("battlemage", () -> new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
        map.put(ArmorItem.Type.HELMET, 3);
        map.put(ArmorItem.Type.CHESTPLATE, 8);
        map.put(ArmorItem.Type.LEGGINGS, 6);
        map.put(ArmorItem.Type.BOOTS, 3);
    }), 10, SoundEvents.ARMOR_EQUIP_NETHERITE, () -> Ingredient.of(AMItems.BLANK_RUNE.get()), List.of(), 1f, 0));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
