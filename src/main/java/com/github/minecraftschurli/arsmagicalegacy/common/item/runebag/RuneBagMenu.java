package com.github.minecraftschurli.arsmagicalegacy.common.item.runebag;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import com.github.minecraftschurli.arsmagicalegacy.common.item.ColoredRuneItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/**
 * Mostly taken from McJty's tutorials and the Botania mod.
 * {@see https://github.com/McJty/YouTubeModding14/blob/1.17/src/main/java/com/mcjty/mytutorial/blocks/FirstBlockContainer.java}
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/client/gui/bag/ContainerFlowerBag.java}
 */
public class RuneBagMenu extends AbstractContainerMenu {
    /**
     * Creates a new RuneBagMenu. Sets all slots.
     * @param pContainerId    The container id from the supplier.
     * @param playerInventory The player inventory from the supplier.
     * @param stack           The item stack from the supplier.
     */
    public RuneBagMenu(int pContainerId, Inventory playerInventory, ItemStack stack) {
        super(AMMenuTypes.RUNE_BAG.get(), pContainerId);
        Container inventory = new RuneBagContainer(stack);
        {
            int i = 0;
            for (DyeColor color : DyeColor.values()) {
                int x = i % 8;
                int y = i / 8;
                addSlot(new RuneSlot(inventory, i, 8 + x * 18, 8 + y * 18, color));
                i++;
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, i * 9 + j + 9, 8 + j * 18, 68 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 126));
        }
    }

    public RuneBagMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, inv.player.getItemInHand(data.readEnum(InteractionHand.class)));
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack is = ItemStack.EMPTY;
        Slot slot = slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            is = stack.copy();
            if (pIndex < 16) {
                if (!moveItemStackTo(stack, 16, 52, true)) return ItemStack.EMPTY;
            } else if (stack.getItem() instanceof ColoredRuneItem) {
                int color = ((ColoredRuneItem) stack.getItem()).getDyeColor().getId();
                Slot invSlot = slots.get(color);
                if (invSlot.mayPlace(is) && !moveItemStackTo(stack, color, color + 1, true)) return ItemStack.EMPTY;
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

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    private static class RuneSlot extends Slot {
        private final DyeColor color;

        public RuneSlot(Container container, int index, int x, int y, DyeColor color) {
            super(container, index, x, y);
            this.color = color;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return pStack.getItem() instanceof ColoredRuneItem item && color == item.getDyeColor();
        }
    }
}
