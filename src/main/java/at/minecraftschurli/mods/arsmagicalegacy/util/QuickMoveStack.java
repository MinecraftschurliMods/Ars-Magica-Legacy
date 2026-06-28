package at.minecraftschurli.mods.arsmagicalegacy.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface QuickMoveStack {
    default ItemStack quickMoveStack(Player player, int index) {
        Slot slot = getSlot(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;
        int slotCount = getSlotCount();
        ItemStack stack = slot.getItem();
        ItemStack originalStack = stack.copy();
        if (index < slotCount) { // If slot is a BE slot
            // Try moving to the hotbar
            if (!moveItemStackTo(stack, slotCount, slotCount + 9, true)
                // Try moving to the inventory
                && !moveItemStackTo(stack, slotCount + 9, slotCount + 36, true))
                return ItemStack.EMPTY;
        } else if (index < slotCount + 9) { // If slot is a hotbar slot
            // Try moving to the BE
            if (!moveItemStackTo(stack, 0, slotCount, false)
                // Try moving to the inventory
                && !moveItemStackTo(stack, slotCount + 9, slotCount + 36, false))
                return ItemStack.EMPTY;
        } else if (index < slotCount + 36) { // If slot is an inventory slot
            // Try moving to the BE
            if (!moveItemStackTo(stack, 0, slotCount, false)
                // Try moving to the hotbar
                && !moveItemStackTo(stack, slotCount, slotCount + 9, false))
                return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        return originalStack;
    }

    int getSlotCount();

    Slot getSlot(int index);

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
