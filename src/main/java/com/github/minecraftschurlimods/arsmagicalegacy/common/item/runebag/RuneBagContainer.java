package com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/**
 * Mostly taken from McJty's tutorials and the Botania mod.
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemBackedInventory.java}
 */
public class RuneBagContainer extends SimpleContainer {
    private final ItemStack stack;

    public RuneBagContainer(ItemStack stack) {
        super(DyeColor.values().length);
        this.stack = stack;
        ItemContainerContents contents = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        contents.copyInto(getItems());
    }

    @Override
    public void setChanged() {
        super.setChanged();
        this.stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(getItems()));
    }
}
