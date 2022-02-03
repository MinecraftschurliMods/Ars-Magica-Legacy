package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public class MageArmorItem extends ArmorItem {
    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    public static final ArmorMaterial MAGE_ARMOR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForSlot(EquipmentSlot pSlot) {
            return 8 * HEALTH_PER_SLOT[pSlot.getIndex()];
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot pSlot) {
            return switch (pSlot) {
                case MAINHAND, OFFHAND -> 0;
                case HEAD, FEET -> 2;
                case LEGS -> 4;
                case CHEST -> 6;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 15;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(AMItems.BLANK_RUNE.get());
        }

        @Override
        public String getName() {
            return "mage";
        }

        @Override
        public float getToughness() {
            return 0.5f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };
    public static final ArmorMaterial BATTLEMAGE_ARMOR_MATERIAL = new ArmorMaterial() {
        @Override
        public int getDurabilityForSlot(EquipmentSlot pSlot) {
            return 12 * HEALTH_PER_SLOT[pSlot.getIndex()];
        }

        @Override
        public int getDefenseForSlot(EquipmentSlot pSlot) {
            return switch (pSlot) {
                case MAINHAND, OFFHAND -> 0;
                case HEAD, FEET -> 3;
                case LEGS -> 6;
                case CHEST -> 8;
            };
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_NETHERITE;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(AMItems.BLANK_RUNE.get());
        }

        @Override
        public String getName() {
            return "battlemage";
        }

        @Override
        public float getToughness() {
            return 1f;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };
    private final float mana;

    public MageArmorItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, float mana) {
        super(pMaterial, pSlot, AMItems.ITEM_1);
        this.mana = mana;
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof LivingEntity living && pStack.isDamaged()) {
            var helper = ArsMagicaAPI.get().getManaHelper();
            if (helper.getMana(living) > mana) {
                pStack.setDamageValue(pStack.getDamageValue() - 1);
                helper.decreaseMana(living, mana);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return ArsMagicaAPI.MOD_ID + ":textures/models/armor/%s_layer_%d.png".formatted(getMaterial().getName(), slot == EquipmentSlot.LEGS ? 2 : 1);
    }
}
