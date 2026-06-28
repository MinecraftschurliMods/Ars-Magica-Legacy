package com.github.minecraftschurlimods.arsmagicalegacy.common.container;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class ItemStackContainer extends SimpleContainer {
    protected final ItemStack stack;

    public ItemStackContainer(ItemStack stack, int size) {
        super(size);
        this.stack = stack;
        stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(getItems());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(getItems()));
    }
}
