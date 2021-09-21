package com.github.minecraftschurli.arsmagicalegacy.common.item.runebag;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMContainers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class RuneBagContainer extends AbstractContainerMenu {
    public RuneBagContainer(int pContainerId) {
        super(AMContainers.RUNE_BAG.get(), pContainerId);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
