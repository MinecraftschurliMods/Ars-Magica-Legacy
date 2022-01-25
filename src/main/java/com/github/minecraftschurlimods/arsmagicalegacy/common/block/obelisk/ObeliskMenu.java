package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ObeliskMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;

    public ObeliskMenu(int id, Inventory inv) {
        this(id, inv, new SimpleContainer(1), new SimpleContainerData(4));
    }

    public ObeliskMenu(int containerId, Inventory inventory, Container container, ContainerData data) {
        super(AMMenuTypes.OBELISK.get(), containerId);
        this.container = container;
        this.data = data;
        addDataSlots(data);
        addSlot(new ObeliskFuelSlot(container, 79, 47));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public int getLitProgress() {
        int i = data.get(1);
        if (i == 0) {
            i = 200;
        }
        return data.get(0) * 13 / i;
    }

    public boolean isLit() {
        return data.get(0) > 0;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();
            if (index == 0) {
                if (!moveItemStackTo(stack, 1, 37, true)) return ItemStack.EMPTY;
                slot.onQuickCraft(stack, itemstack);
            } else {
                if (getSlot(0).mayPlace(stack)) {
                    if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
                } else if (index < 28) {
                    if (!moveItemStackTo(stack, 28, 37, false)) return ItemStack.EMPTY;
                } else if (index < 37 && !moveItemStackTo(stack, 1, 28, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stack.getCount() == itemstack.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, stack);
        }
        return itemstack;
    }
}
