package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMArmorMaterials;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class MagitechGogglesItem extends ArmorItem {
    public static final ResourceLocation ID = ArsMagicaAPI.resource("magitech_goggles_magic_vision");

    public MagitechGogglesItem() {
        super(AMArmorMaterials.MAGITECH, Type.HELMET, new Item.Properties()
                .stacksTo(1)
                .attributes(ItemAttributeModifiers.builder().add(AMAttributes.MAGIC_VISION, new AttributeModifier(ID, 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.HEAD).build()));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
