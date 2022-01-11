package com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class RiftContainer extends SimpleContainer {
    private final ItemStackHandler handler;

    public RiftContainer(ItemStackHandler handler) {
        super(54);
        this.handler = handler;
    }

    @Override
    public void fromTag(ListTag list) {
        for (int i = 0; i < getContainerSize(); i++) {
            setItem(i, ItemStack.EMPTY);
        }
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            int slot = tag.getByte("Slot") & 255;
            if (slot < getContainerSize()) {
                setItem(slot, ItemStack.of(tag));
            }
        }
    }

    @Override
    public ListTag createTag() {
        ListTag list = new ListTag();
        for (int i = 0; i < getContainerSize(); i++) {
            ItemStack stack = getItem(i);
            if (!stack.isEmpty()) {
                CompoundTag tag = new CompoundTag();
                tag.putByte("Slot", (byte) i);
                stack.save(tag);
                list.add(tag);
            }
        }
        return list;
    }

    @Override
    public void startOpen(Player pPlayer) {
        fromTag(handler.serializeNBT().getList("Items", ListTag.TAG_COMPOUND));
        super.startOpen(pPlayer);
    }

    @Override
    public void stopOpen(Player pPlayer) {
        CompoundTag tag = new CompoundTag();
        ListTag list = createTag();
        tag.put("Items", list);
        tag.putInt("Size", list.size());
        handler.deserializeNBT(tag);
        super.stopOpen(pPlayer);
    }
}
