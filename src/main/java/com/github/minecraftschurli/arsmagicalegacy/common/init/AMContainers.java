package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.CONTAINERS;

public interface AMContainers {
    RegistryObject<MenuType<RuneBagContainer>> RUNE_BAG = CONTAINERS.register("rune_bag", () -> IForgeContainerType.create((windowId, inv, data) -> new RuneBagContainer(windowId, inv, inv.player.getItemInHand(data.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND))));

    /**
     * Empty method that is required for classloading
     */
    static void init() {}
}