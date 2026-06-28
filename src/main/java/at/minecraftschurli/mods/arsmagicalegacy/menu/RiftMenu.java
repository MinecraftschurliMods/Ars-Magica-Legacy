package at.minecraftschurli.mods.arsmagicalegacy.menu;

import at.minecraftschurli.mods.arsmagicalegacy.container.RiftContainer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.mods.arsmagicalegacy.util.QuickMoveStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class RiftMenu extends AbstractContainerMenu implements QuickMoveStack {
    private final int size;
    private final LivingEntity entity;

    public RiftMenu(int containerId, Inventory inventory, int entityId, int size) {
        super(AMMenus.RIFT.get(), containerId);
        this.size = size;
        entity = (LivingEntity) Objects.requireNonNull(inventory.player.level().getEntity(entityId));
        RiftContainer container = new RiftContainer(entity, size);
        int rows = Math.ceilDiv(container.getContainerSize(), 9);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 9; j++) {
                int index = i * 9 + j;
                if (index < size) {
                    addSlot(new Slot(container, index, j * 18 + 8, i * 18 + 18));
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, i * 18 + 8, rows * 18 + 90));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 8 + j * 18, rows * 18 + i * 18 + 32));
            }
        }
    }

    public RiftMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readInt(), buf.readInt());
    }

    @Override
    public boolean stillValid(Player player) {
        return entity.isAlive() && player.distanceTo(entity) < 64;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return QuickMoveStack.super.quickMoveStack(player, index);
    }

    @Override
    public int getSlotCount() {
        return size;
    }

    @Override
    public boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
    }
}
