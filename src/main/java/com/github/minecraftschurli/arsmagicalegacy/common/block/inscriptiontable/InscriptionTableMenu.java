package com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class InscriptionTableMenu extends AbstractContainerMenu {
    public InscriptionTableMenu(int pContainerId) {
        super(AMMenuTypes.INSCRIPTION_TABLE.get(), pContainerId);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
