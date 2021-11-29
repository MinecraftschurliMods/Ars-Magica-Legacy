package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import java.util.Objects;

public class InscriptionTableMenu extends AbstractContainerMenu {
    private final InscriptionTableBlockEntity table;

    public InscriptionTableMenu(int pContainerId, Inventory inventory, InscriptionTableBlockEntity table) {
        super(AMMenuTypes.INSCRIPTION_TABLE.get(), pContainerId);
        this.table = table;
        addSlot(new Slot(table, 0, 102, 74));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 30 + j * 18, 170 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 30 + i * 18, 228));
        }
    }

    public InscriptionTableMenu(int id, Inventory inv, FriendlyByteBuf data) {
        this(id, inv, ((InscriptionTableBlockEntity) Objects.requireNonNull(inv.player.level.getBlockEntity(data.readBlockPos()))));
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.table.stillValid(pPlayer);
    }
}
