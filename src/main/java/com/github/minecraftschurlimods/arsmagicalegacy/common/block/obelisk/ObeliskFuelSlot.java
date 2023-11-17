package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ObeliskFuelSlot extends Slot {
    public ObeliskFuelSlot(Container container, int x, int y) {
        super(container, 0, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return ObeliskFuelManager.isFuel(AMUtil.getRegistryAccess(), stack);
    }
}
