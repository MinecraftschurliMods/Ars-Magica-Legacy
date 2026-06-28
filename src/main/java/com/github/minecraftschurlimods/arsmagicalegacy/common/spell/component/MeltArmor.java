package com.github.minecraftschurlimods.arsmagicalegacy.common.spell.component;

import com.github.minecraftschurlimods.arsmagicalegacy.common.AMServerConfig;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.Spell;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellCastContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponent;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellComponentCastResult;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class MeltArmor extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(List<SpellModifier> modifiers, SpellCastContext context, EntityHitResult hitResult) {
        Spell spell = context.spell();
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        for (EquipmentSlot slot : EquipmentSlotGroup.ARMOR) {
            ItemStack stack = entity.getItemBySlot(slot);
            if (stack.isDamageableItem()) {
                int damage = stack.getMaxDamage() - stack.getDamageValue();
                stack.setDamageValue((int) (damage * AMServerConfig.MELT_ARMOR_FACTOR.get()));
            }
        }
        return SpellComponentCastResult.success(spell);
    }
}
