package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMMenuTypes {
    RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = AMRegistries.MENU_TYPES.register("inscription_table", () -> IForgeMenuType.create(InscriptionTableMenu::new));
    RegistryObject<MenuType<RuneBagMenu>>          RUNE_BAG          = AMRegistries.MENU_TYPES.register("rune_bag",          () -> IForgeMenuType.create(RuneBagMenu::new));
    RegistryObject<MenuType<ObeliskMenu>>          OBELISK           = AMRegistries.MENU_TYPES.register("obelisk",           () -> new MenuType<>(ObeliskMenu::new));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
