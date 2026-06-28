package com.github.minecraftschurlimods.arsmagicalegacy.common.init;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.InscriptionTableMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RiftMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RuneBagMenu;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.SpellBookMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMMenus {
    DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<MenuType<?>, MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = MENUS.register("inscription_table", () -> IMenuTypeExtension.create(InscriptionTableMenu::new));
    DeferredHolder<MenuType<?>, MenuType<RiftMenu>>             RIFT              = MENUS.register("rift",              () -> IMenuTypeExtension.create(RiftMenu::new));
    DeferredHolder<MenuType<?>, MenuType<RuneBagMenu>>          RUNE_BAG          = MENUS.register("rune_bag",          () -> IMenuTypeExtension.create(RuneBagMenu::new));
    DeferredHolder<MenuType<?>, MenuType<SpellBookMenu>>        SPELL_BOOK        = MENUS.register("spell_book",        () -> IMenuTypeExtension.create(SpellBookMenu::new));
    // @formatter:on
}
