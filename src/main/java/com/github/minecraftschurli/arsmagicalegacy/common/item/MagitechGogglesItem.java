package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class MagitechGogglesItem extends ArmorItem {
    private static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        public static final String NAME = ArsMagicaAPI.MOD_ID + ":magitech";

        @Override
        public int getDurabilityForSlot(EquipmentSlot pSlot) {
            return 0;
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot pSlot) {
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
        super(MATERIAL, EquipmentSlot.HEAD, AMItems.ITEM_1);
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
