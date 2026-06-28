package com.github.minecraftschurlimods.arsmagicalegacy.common.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class PlacePredicateSlot extends Slot {
    private final Predicate<ItemStack> predicate;

    public PlacePredicateSlot(Container container, int slot, int x, int y, Predicate<ItemStack> predicate) {
        super(container, slot, x, y);
        this.predicate = predicate;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return predicate.test(stack);
    }
}
