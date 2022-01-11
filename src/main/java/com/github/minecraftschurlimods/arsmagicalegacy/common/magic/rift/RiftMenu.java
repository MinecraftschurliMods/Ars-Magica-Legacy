package com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

public class RiftMenu extends ChestMenu {
    private RiftMenu(MenuType<?> type, int id, Inventory inv, Container container, int rows) {
        super(type, id, inv, container, rows);
    }

    public static RiftMenu rift1(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_1.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 1);
    }

    public static RiftMenu rift1(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_1.get(), id, inv, container, 1);
    }

    public static RiftMenu rift2(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_2.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 2);
    }

    public static RiftMenu rift2(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_2.get(), id, inv, container, 2);
    }

    public static RiftMenu rift3(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_3.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 3);
    }

    public static RiftMenu rift3(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_3.get(), id, inv, container, 3);
    }

    public static RiftMenu rift4(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_4.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 4);
    }

    public static RiftMenu rift4(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_4.get(), id, inv, container, 4);
    }

    public static RiftMenu rift5(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_5.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 5);
    }

    public static RiftMenu rift5(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_5.get(), id, inv, container, 5);
    }

    public static RiftMenu rift6(int id, Inventory inv, FriendlyByteBuf buf) {
        return new RiftMenu(AMMenuTypes.RIFT_6.get(), id, inv, new RiftContainer(ArsMagicaAPI.get().getRiftHelper().getRift(inv.player.level.getPlayerByUUID(buf.readUUID()))), 6);
    }

    public static RiftMenu rift6(int id, Inventory inv, Container container) {
        return new RiftMenu(AMMenuTypes.RIFT_6.get(), id, inv, container, 6);
    }
}
