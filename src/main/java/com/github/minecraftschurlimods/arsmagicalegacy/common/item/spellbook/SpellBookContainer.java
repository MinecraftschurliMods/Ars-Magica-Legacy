package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

import java.util.ArrayList;
import java.util.List;

public record SpellBookContainer(ItemStack stack, SimpleContainer active, SimpleContainer back) implements Container {
    public SpellBookContainer(ItemStack stack, int active, int back) {
        this(stack, new SimpleContainer(active), new SimpleContainer(back));
    }

    public SpellBookContainer(ItemStack stack, SimpleContainer active, SimpleContainer back) {
        this.stack = stack;
        this.active = active;
        this.back = back;
        deserializeTag(stack);
    }

    private void deserializeTag(ItemStack stack) {
        if (!stack.has(AMDataComponents.SPELLS)) return;
        ItemContainerContents spells = stack.getOrDefault(AMDataComponents.SPELLS, ItemContainerContents.EMPTY);
        List<ItemStack> list = spells.stream().toList();
        if (list.isEmpty()) return;
        int loadedSize = list.size();
        int activeSize = this.active.getContainerSize();
        List<ItemStack> active = list.subList(0, Math.min(activeSize, loadedSize));
        for (int i = 0; i < active.size(); i++) {
            this.active.setItem(i, active.get(i));
        }
        if (activeSize < loadedSize) {
            int backSize = this.back.getContainerSize();
            List<ItemStack> back = list.subList(activeSize, Math.min(activeSize + backSize, loadedSize));
            for (int i = 0; i < back.size(); i++) {
                this.back.setItem(i, back.get(i));
            }
        }
    }

    @Override
    public int getContainerSize() {
        return active.getContainerSize() + back.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return active.isEmpty() && back.isEmpty();
    }

    @Override
    public ItemStack getItem(final int pIndex) {
        return pIndex < active.getContainerSize() ? active.getItem(pIndex) : back.getItem(pIndex - active.getContainerSize());
    }

    @Override
    public ItemStack removeItem(final int pIndex, final int pCount) {
        return pIndex < active.getContainerSize() ? active.removeItem(pIndex, pCount) : back.removeItem(pIndex - active.getContainerSize(), pCount);
    }

    @Override
    public ItemStack removeItemNoUpdate(final int pIndex) {
        return pIndex < active.getContainerSize() ? active.removeItemNoUpdate(pIndex) : back.removeItemNoUpdate(pIndex - active.getContainerSize());
    }

    @Override
    public void setItem(final int pIndex, final ItemStack pStack) {
        if (pIndex < active.getContainerSize()) {
            active.setItem(pIndex, pStack);
        } else {
            back.setItem(pIndex - active.getContainerSize(), pStack);
        }
    }

    @Override
    public void setChanged() {
        stack.set(AMDataComponents.SPELLS, createTag());
    }

    @Override
    public boolean stillValid(final Player pPlayer) {
        return active.stillValid(pPlayer) && back.stillValid(pPlayer);
    }

    @Override
    public void clearContent() {
        active.clearContent();
        back.clearContent();
    }

    public ItemContainerContents createTag() {
        List<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < this.getContainerSize(); i++) {
            list.add(this.getItem(i));
        }
        return ItemContainerContents.fromItems(list);
    }
}
