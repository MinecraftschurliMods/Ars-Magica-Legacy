package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMAttributes;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.UUID;

public class MagitechGogglesItem extends ArmorItem {
    public static final UUID UUID = java.util.UUID.fromString("8f3b29af-ee24-4f49-88fe-3d71bdb1d2c0");
    private static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        public static final String NAME = ArsMagicaAPI.MOD_ID + ":magitech";

        @Override
        public int getDurabilityForType(Type p_266807_) {
            return 0;
        }

        @Override
        public int getDefenseForType(Type p_267168_) {
            return 0;
        }

        @Override
        public int getEnchantmentValue() {
            return 0;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        @Override
        public String getName() {
            return NAME;
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    public MagitechGogglesItem() {
        super(MATERIAL, Type.HELMET, AMItems.ITEM_1);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot != EquipmentSlot.HEAD) return ImmutableMultimap.of();
        return ImmutableMultimap.of(AMAttributes.MAGIC_VISION.value(), new AttributeModifier(UUID, "magic_vision", 1, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
