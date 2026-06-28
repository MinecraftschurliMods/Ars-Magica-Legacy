package com.github.minecraftschurlimods.arsmagicalegacy.common.menu;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.blockentity.InscriptionTableBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenus;
import com.github.minecraftschurlimods.arsmagicalegacy.common.slot.InscriptionTableSlot;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.QuickMoveStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InscriptionTableMenu extends AbstractContainerMenu implements QuickMoveStack {
    private final InscriptionTableBlockEntity blockEntity;

    public InscriptionTableMenu(int containerId, Inventory inventory, InscriptionTableBlockEntity blockEntity) {
        super(AMMenus.INSCRIPTION_TABLE.get(), containerId);
        this.blockEntity = blockEntity;
        blockEntity.startOpen(inventory.player);
        addSlot(new InscriptionTableSlot(blockEntity, 102, 74));
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 30 + i * 18, 228));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 30 + j * 18, 170 + i * 18));
            }
        }
    }

    @SuppressWarnings("DataFlowIssue")
    public InscriptionTableMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, (InscriptionTableBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return QuickMoveStack.super.quickMoveStack(player, index);
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        blockEntity.stopOpen(player);
    }

    public InscriptionTableBlockEntity getBlockEntity() {
        return blockEntity;
    }

    public int getShapeGroups() {
        return blockEntity.getBlockState().getValue(InscriptionTableBlock.TIER) + 2;
    }
}
