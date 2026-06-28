package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;
import java.util.Map;

public interface AMArmorMaterials {
    ArmorMaterial MAGITECH_GOGGLES = register("magitech_goggles", 10, Map.of(ArmorType.HELMET, 0), 1, SoundEvents.ARMOR_EQUIP_LEATHER, AMTags.Items.MAGITECH_GOGGLES_REPAIR_ITEMS, 0);
    ArmorMaterial MAGE = register("mage", 8, defenseMap(2, 6, 4, 2), 15, SoundEvents.ARMOR_EQUIP_LEATHER, AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, 0.5f);
    ArmorMaterial BATTLEMAGE = register("battlemage", 12, defenseMap(3, 8, 6, 3), 10, SoundEvents.ARMOR_EQUIP_NETHERITE, AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, 1);

    private static ArmorMaterial register(String name, int durability, Map<ArmorType, Integer> defense, int enchantmentValue, Holder<SoundEvent> equipSound, TagKey<Item> repairItems, float toughness) {
        return new ArmorMaterial(durability, defense, enchantmentValue, equipSound, toughness, 0, repairItems, ResourceKey.create(EquipmentAssets.ROOT_ID, ArsMagicaApi.id(name)));
    }

    private static EnumMap<ArmorType, Integer> defenseMap(int helmet, int chestplate, int leggings, int boots) {
        EnumMap<ArmorType, Integer> map = new EnumMap<>(ArmorType.class);
        map.put(ArmorType.HELMET, helmet);
        map.put(ArmorType.CHESTPLATE, chestplate);
        map.put(ArmorType.LEGGINGS, leggings);
        map.put(ArmorType.BOOTS, boots);
        return map;
    }
}
