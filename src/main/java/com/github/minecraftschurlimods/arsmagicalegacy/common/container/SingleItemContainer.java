package com.github.minecraftschurlimods.arsmagicalegacy.common.container;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SingleItemContainer implements Container {
    protected ItemStack stack;

    public SingleItemContainer(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot != 0 || amount <= 0) return ItemStack.EMPTY;
        ItemStack result = stack;
        stack = ItemStack.EMPTY;
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) return;
        this.stack = stack;
        setChanged();
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
        setChanged();
    }
}
