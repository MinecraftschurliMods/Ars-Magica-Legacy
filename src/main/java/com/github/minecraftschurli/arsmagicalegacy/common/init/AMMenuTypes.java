package com.github.minecraftschurli.arsmagicalegacy.common.init;

import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlockEntity;
import com.github.minecraftschurli.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableMenu;
import com.github.minecraftschurli.arsmagicalegacy.common.item.runebag.RuneBagMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import static com.github.minecraftschurli.arsmagicalegacy.common.init.AMRegistries.MENU_TYPES;

@NonExtendable
public interface AMMenuTypes {
    RegistryObject<MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = MENU_TYPES.register("inscription_table", () -> IForgeContainerType.create(InscriptionTableMenu::new));
    RegistryObject<MenuType<RuneBagMenu>>          RUNE_BAG          = MENU_TYPES.register("rune_bag",          () -> IForgeContainerType.create(RuneBagMenu::new));

    /**
     * Empty method that is required for classloading
     */
    @Internal
    static void register() {}
}
