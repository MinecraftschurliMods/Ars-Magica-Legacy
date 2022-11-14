package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.spell.ISpellItem;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpellBookMenu extends AbstractContainerMenu {
    public SpellBookMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(SpellBookItem.ACTIVE_SPELL_SLOTS + SpellBookItem.BACKUP_SPELL_SLOTS));
    }

    public SpellBookMenu(int containerId, Inventory playerInventory, ItemStack stack) {
        this(containerId, playerInventory, new SpellBookContainer(stack, SpellBookItem.ACTIVE_SPELL_SLOTS, SpellBookItem.BACKUP_SPELL_SLOTS));
    }

    private SpellBookMenu(int containerId, Inventory playerInventory, Container inventory) {
        super(AMMenuTypes.SPELL_BOOK.get(), containerId);
        int slotIndex = 0;
        for (int i = 0; i < SpellBookItem.ACTIVE_SPELL_SLOTS; i++) {
            addSlot(new SpellSlot(inventory, slotIndex++, 18, 5 + (i * 18)));
        }
        for (int i = 0; i < SpellBookItem.BACKUP_SPELL_SLOTS / SpellBookItem.ACTIVE_SPELL_SLOTS; i++) {
            for (int j = 0; j < SpellBookItem.ACTIVE_SPELL_SLOTS; j++) {
                addSlot(new SpellSlot(inventory, slotIndex++, 138 + (i * 26), 5 + (j * 18)));
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, j + i * 9 + 9, 48 + j * 18, 171 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 48 + i * 18, 229));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return ArsMagicaAPI.get().getMagicHelper().knowsMagic(player);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack is = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            is = stack.copy();
            if (pIndex < SpellBookItem.TOTAL_SPELL_SLOTS) {
                if (!moveItemStackTo(stack, SpellBookItem.TOTAL_SPELL_SLOTS, SpellBookItem.TOTAL_SPELL_SLOTS + 36, true))
                    return ItemStack.EMPTY;
            } else if (stack.getItem() instanceof ISpellItem) {
                if (!moveItemStackTo(stack, 0, SpellBookItem.TOTAL_SPELL_SLOTS, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stack.getCount() == is.getCount()) return ItemStack.EMPTY;
            slot.onTake(pPlayer, stack);
        }
        return is;
    }

    private static class SpellSlot extends Slot {
        public SpellSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.getItem() instanceof ISpellItem;
        }
    }
}
