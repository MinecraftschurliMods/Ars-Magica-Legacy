package com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;

public class RiftMenu extends ChestMenu {
    private RiftMenu(int id, Inventory inv, Container container, int rows) {
        super(AMMenuTypes.RIFT.get(), id, inv, container, rows);
    }

    /**
     * @param windowId The window id to use.
     * @param inv      The inventory to use.
     * @param buf      The buffer to read the uuid and the rift size from.
     * @return A rift menu.
     */
    public static RiftMenu rift(int windowId, Inventory inv, FriendlyByteBuf buf) {
        return rift(windowId, inv, inv.player.level.getPlayerByUUID(buf.readUUID()), buf.readInt());
    }

    /**
     * @param windowId The window id to use.
     * @param inv      The inventory to use.
     * @param player   The player this rift belongs to.
     * @param size     The size to use.
     * @return A rift menu.
     */
    public static RiftMenu rift(int windowId, Inventory inv, Player player, int size) {
        return new RiftMenu(windowId, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(player)), size);
    }
}
