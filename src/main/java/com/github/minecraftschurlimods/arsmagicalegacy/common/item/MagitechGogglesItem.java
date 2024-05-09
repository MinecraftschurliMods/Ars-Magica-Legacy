package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMArmorMaterials;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.UUID;

public class MagitechGogglesItem extends ArmorItem {
    public static final UUID UUID = java.util.UUID.fromString("8f3b29af-ee24-4f49-88fe-3d71bdb1d2c0");

    public MagitechGogglesItem() {
        super(AMArmorMaterials.MAGITECH, Type.HELMET, new Item.Properties()
                .stacksTo(1)
                .attributes(ItemAttributeModifiers.builder().add(AMAttributes.MAGIC_VISION, new AttributeModifier(UUID, "magic_vision", 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
