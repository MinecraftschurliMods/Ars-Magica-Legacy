package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record SpellBookContainer(ItemStack stack, SimpleContainer active, SimpleContainer back) implements Container {
    public SpellBookContainer(ItemStack stack, int active, int back) {
        this(stack, new SimpleContainer(active), new SimpleContainer(back));
    }

    public SpellBookContainer(ItemStack stack, SimpleContainer active, SimpleContainer back) {
        this.stack = stack;
        this.active = active;
        this.back = back;
        deserializeTag(stack.getOrCreateTag());
    }

    private void deserializeTag(CompoundTag tag) {
        if (!tag.contains(SpellBookItem.SPELLS_KEY)) return;
        ListTag spells = tag.getList(SpellBookItem.SPELLS_KEY, Tag.TAG_COMPOUND);
        if (spells.size() != active.getContainerSize() + back.getContainerSize()) return;
        List<Tag> active = spells.subList(0, this.active.getContainerSize());
        for (int i = 0; i < active.size(); i++) {
            this.active.setItem(i, ItemStack.of((CompoundTag) active.get(i)));
        }
        List<Tag> back = spells.subList(this.active.getContainerSize(), this.active.getContainerSize() + this.back.getContainerSize());
        for (int i = 0; i < back.size(); i++) {
            this.back.setItem(i, ItemStack.of((CompoundTag) back.get(i)));
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
        stack.getOrCreateTag().put(SpellBookItem.SPELLS_KEY, createTag());
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

    public ListTag createTag() {
        ListTag listtag = new ListTag();
        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                listtag.add(itemstack.save(new CompoundTag()));
            } else {
                listtag.add(new CompoundTag());
            }
        }
        return listtag;
    }
}
