package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpellBookSlot extends Slot {
    public SpellBookSlot(Container container, int index, int x, int y) {
        super(container, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ISpellItem;
    }
}
