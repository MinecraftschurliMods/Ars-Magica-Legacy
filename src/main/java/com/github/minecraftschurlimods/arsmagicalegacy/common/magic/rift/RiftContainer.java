package com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public record RiftContainer(IItemHandlerModifiable handler) implements Container {

    @Override
    public int getContainerSize() {
        return handler.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < handler.getSlots(); i++) {
            if (!handler.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(final int index) {
        return handler.getStackInSlot(index);
    }

    @Override
    public ItemStack removeItem(final int index, final int count) {
        ItemStack stack = handler.getStackInSlot(index);
        return stack.isEmpty() ? ItemStack.EMPTY : stack.split(count);
    }

    @Override
    public ItemStack removeItemNoUpdate(final int index) {
        ItemStack s = getItem(index);
        if (s.isEmpty()) {
            return ItemStack.EMPTY;
        }
        setItem(index, ItemStack.EMPTY);
        return s;
    }

    @Override
    public void setItem(final int index, final ItemStack stack) {
        handler.setStackInSlot(index, stack);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(final Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < handler.getSlots(); i++) {
            handler.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}
