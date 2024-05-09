package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMArmorMaterials;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MageArmorItem extends ArmorItem {
    public MageArmorItem(Holder<ArmorMaterial> pMaterial, Type pSlot, float mana) {
        super(pMaterial, pSlot, new Item.Properties()
                .stacksTo(1)
                .durability(pSlot.getDurability(pMaterial.is(AMArmorMaterials.MAGE) ? 8 : pMaterial.is(AMArmorMaterials.BATTLEMAGE) ? 12 : 1))
                .component(AMDataComponents.MANA_REPAIR_COST, mana)
        );
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof LivingEntity living && pStack.isDamaged()) {
            var helper = ArsMagicaAPI.get().getManaHelper();
            float cost = pStack.getOrDefault(AMDataComponents.MANA_REPAIR_COST, 1f);
            if (helper.getMana(living) > cost) {
                pStack.setDamageValue(pStack.getDamageValue() - 1);
                helper.decreaseMana(living, cost);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
}
