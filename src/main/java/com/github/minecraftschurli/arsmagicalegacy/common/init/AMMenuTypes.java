package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.MENU_TYPES;

@NonExtendable
public interface AMMenuTypes {
    RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = MENU_TYPES.register("inscription_table", () -> IForgeMenuType.create(InscriptionTableMenu::new));
    RegistryObject<MenuType<RuneBagMenu>>          RUNE_BAG          = MENU_TYPES.register("rune_bag",          () -> IForgeMenuType.create(RuneBagMenu::new));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
