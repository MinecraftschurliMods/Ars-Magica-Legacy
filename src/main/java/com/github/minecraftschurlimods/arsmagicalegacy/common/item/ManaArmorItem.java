package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.ManaHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class ManaArmorItem extends Item {
    public ManaArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (owner instanceof LivingEntity living && itemStack.isDamaged()) {
            ManaHelper helper = ArsMagicaApi.manaHelper();
            double cost = itemStack.getOrDefault(AMDataComponents.MANA_REPAIR_COST, 1.);
            if (helper.getMana(living) > cost) {
                itemStack.setDamageValue(itemStack.getDamageValue() - 1);
                helper.decreaseMana(living, cost);
            }
        }
        super.inventoryTick(itemStack, level, owner, slot);
    }
}
