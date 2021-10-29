package com.github.minecraftschurli.arsmagicalegacy.common.item.runebag;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/**
 * Mostly taken from the Botania mod.
 *
 * @see
 * <a href="https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemBackedInventory.java">
 *     https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemBackedInventory.java
 * </a>
 */
public class RuneBagContainer extends SimpleContainer {
    private final ItemStack stack;

    public RuneBagContainer(ItemStack stack) {
        super(DyeColor.values().length);
        this.stack = stack;
        ListTag tag = !stack.isEmpty() && stack.getOrCreateTag().contains("Items")
                ? stack.getOrCreateTag().getList("Items", 10)
                : new ListTag();
        for (int i = 0; i < DyeColor.values().length && i < tag.size(); i++) {
            setItem(i, ItemStack.of(tag.getCompound(i)));
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        ListTag tag = new ListTag();
        for (int i = 0; i < DyeColor.values().length; i++) {
            tag.add(getItem(i).save(new CompoundTag()));
        }
        stack.getOrCreateTag().put("Items", tag);
    }
}