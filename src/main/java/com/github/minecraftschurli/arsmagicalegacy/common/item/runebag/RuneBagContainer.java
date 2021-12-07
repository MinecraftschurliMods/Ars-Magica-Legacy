package com.github.minecraftschurli.arsmagicalegacy.common.item.runebag;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

/**
 * Mostly taken from the Botania mod.
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemBackedInventory.java}
 */
public class RuneBagContainer extends SimpleContainer {
    private static final String KEY = "Items";
    private final ItemStack stack;

    /**
     * Creates a new RuneBagContainer.
     *
     * @param stack The ItemStack to create the container for.
     */
    public RuneBagContainer(ItemStack stack) {
        super(DyeColor.values().length);
        this.stack = stack;
        ListTag tag = !stack.isEmpty() && stack.getOrCreateTag().contains(KEY) ? stack.getOrCreateTag().getList(KEY, 10) : new ListTag();
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
        stack.getOrCreateTag().put(KEY, tag);
    }
}
