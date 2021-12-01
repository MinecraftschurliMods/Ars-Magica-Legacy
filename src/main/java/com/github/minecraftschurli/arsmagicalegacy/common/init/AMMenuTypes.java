package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.MENU_TYPES;

public interface AMMenuTypes {
    RegistryObject<MenuType<RuneBagMenu>> RUNE_BAG = MENU_TYPES.register("rune_bag", () -> IForgeMenuType.create((windowId, inv, data) -> new RuneBagMenu(windowId, inv, inv.player.getItemInHand(data.readEnum(InteractionHand.class)))));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {
    }
}
