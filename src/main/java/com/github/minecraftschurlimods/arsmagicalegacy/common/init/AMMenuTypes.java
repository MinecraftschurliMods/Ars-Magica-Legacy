package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift.RiftMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMMenuTypes {
    RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = AMRegistries.MENU_TYPES.register("inscription_table", () -> IForgeMenuType.create(InscriptionTableMenu::new));
    RegistryObject<MenuType<RiftMenu>>             RIFT_1            = AMRegistries.MENU_TYPES.register("rift_1",            () -> IForgeMenuType.create(RiftMenu::rift1));
    RegistryObject<MenuType<RiftMenu>>             RIFT_2            = AMRegistries.MENU_TYPES.register("rift_2",            () -> IForgeMenuType.create(RiftMenu::rift2));
    RegistryObject<MenuType<RiftMenu>>             RIFT_3            = AMRegistries.MENU_TYPES.register("rift_3",            () -> IForgeMenuType.create(RiftMenu::rift3));
    RegistryObject<MenuType<RiftMenu>>             RIFT_4            = AMRegistries.MENU_TYPES.register("rift_4",            () -> IForgeMenuType.create(RiftMenu::rift4));
    RegistryObject<MenuType<RiftMenu>>             RIFT_5            = AMRegistries.MENU_TYPES.register("rift_5",            () -> IForgeMenuType.create(RiftMenu::rift5));
    RegistryObject<MenuType<RiftMenu>>             RIFT_6            = AMRegistries.MENU_TYPES.register("rift_6",            () -> IForgeMenuType.create(RiftMenu::rift6));
    RegistryObject<MenuType<RuneBagMenu>>          RUNE_BAG          = AMRegistries.MENU_TYPES.register("rune_bag",          () -> IForgeMenuType.create(RuneBagMenu::new));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
