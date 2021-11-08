package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class InscriptionTableMenu extends AbstractContainerMenu {
    public InscriptionTableMenu(int pContainerId, Inventory inventory) {
        super(AMMenuTypes.INSCRIPTION_TABLE.get(), pContainerId);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 30 + j * 18, 170 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(inventory, i, 30 + i * 18, 228));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
