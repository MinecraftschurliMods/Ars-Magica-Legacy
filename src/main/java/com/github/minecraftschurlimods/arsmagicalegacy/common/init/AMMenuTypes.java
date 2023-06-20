package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk.ObeliskMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook.SpellBookMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.magic.rift.RiftMenu;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

@NonExtendable
public interface AMMenuTypes {
    RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = AMRegistries.MENU_TYPES.register("inscription_table", () -> IForgeMenuType.create(InscriptionTableMenu::new));
    RegistryObject<MenuType<ObeliskMenu>>          OBELISK           = AMRegistries.MENU_TYPES.register("obelisk",           () -> new MenuType<>(ObeliskMenu::new, FeatureFlags.DEFAULT_FLAGS));
    RegistryObject<MenuType<RiftMenu>>             RIFT              = AMRegistries.MENU_TYPES.register("rift",              () -> IForgeMenuType.create(RiftMenu::rift));
    RegistryObject<MenuType<RuneBagMenu>>          RUNE_BAG          = AMRegistries.MENU_TYPES.register("rune_bag",          () -> IForgeMenuType.create(RuneBagMenu::new));
    RegistryObject<MenuType<SpellBookMenu>>        SPELL_BOOK        = AMRegistries.MENU_TYPES.register("spell_book",        () -> new MenuType<>(SpellBookMenu::new, FeatureFlags.DEFAULT_FLAGS));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
