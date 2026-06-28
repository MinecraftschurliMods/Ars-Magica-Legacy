package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.menu.InscriptionTableMenu;
import at.minecraftschurli.mods.arsmagicalegacy.menu.RiftMenu;
import at.minecraftschurli.mods.arsmagicalegacy.menu.RuneBagMenu;
import at.minecraftschurli.mods.arsmagicalegacy.menu.SpellBookMenu;
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
