package at.minecraftschurli.mods.arsmagicalegacy.util;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public final class ArmorItemUtil {
    public static final ResourceKey<EquipmentAsset> MAGE_ASSET_ID = createAssetId("mage");
    public static final ResourceKey<EquipmentAsset> BATTLEMAGE_ASSET_ID = createAssetId("battlemage");
    public static final ResourceKey<EquipmentAsset> MAGITECH_GOGGLES_ASSET_ID = createAssetId("magitech_goggles");

    public static ResourceKey<EquipmentAsset> createAssetId(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, ArsMagicaApi.id(name));
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId) {
        return properties.stacksTo(1).component(DataComponents.EQUIPPABLE, Equippable.builder(slot).setEquipSound(equipSound).setAsset(assetId).build());
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, ItemAttributeModifiers attributes) {
        return armorProperties(properties, slot, equipSound, assetId).attributes(attributes);
    }

    public static Item.Properties armorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        Identifier modifierId = Identifier.withDefaultNamespace("armor." + slot.getName());
        if (defense > 0) {
            builder.add(Attributes.ARMOR, new AttributeModifier(modifierId, defense, AttributeModifier.Operation.ADD_VALUE), group);
        }
        if (toughness > 0) {
            builder.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(modifierId, toughness, AttributeModifier.Operation.ADD_VALUE), group);
        }
        return armorProperties(properties, slot, equipSound, assetId, builder.build());
    }

    public static Item.Properties manaArmorProperties(Item.Properties properties, EquipmentSlot slot, Holder<SoundEvent> equipSound, ResourceKey<EquipmentAsset> assetId, int defense, float toughness, int durability, int enchantmentValue, TagKey<Item> repairItems, double manaRepairCost) {
        return armorProperties(properties, slot, equipSound, assetId, defense, toughness).durability(durability).enchantable(enchantmentValue).repairable(repairItems).component(AMDataComponents.MANA_REPAIR_COST, manaRepairCost);
    }

    public static Item.Properties mageArmor(Item.Properties properties, ArmorType type, int defense) {
        return manaArmorProperties(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_LEATHER, MAGE_ASSET_ID, defense, 0.5f, type.getDurability(8), 15, AMTags.Items.MAGE_ARMOR_REPAIR_ITEMS, 2.);
    }

    public static Item.Properties battlemageArmor(Item.Properties properties, ArmorType type, int defense) {
        return manaArmorProperties(properties, type.getSlot(), SoundEvents.ARMOR_EQUIP_NETHERITE, BATTLEMAGE_ASSET_ID, defense, 1f, type.getDurability(12), 10, AMTags.Items.BATTLEMAGE_ARMOR_REPAIR_ITEMS, 4.);
    }
}
