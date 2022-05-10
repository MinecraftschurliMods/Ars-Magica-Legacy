package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SpellBookMenu extends AbstractContainerMenu {
    public SpellBookMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(SpellBookItem.ACTIVE_SPELL_SLOTS + SpellBookItem.BACKUP_SPELL_SLOTS));
    }

    public SpellBookMenu(int containerId, Inventory playerInventory, ItemStack stack) {
        this(containerId, playerInventory, new SpellBookContainer(stack, SpellBookItem.ACTIVE_SPELL_SLOTS, SpellBookItem.BACKUP_SPELL_SLOTS));
    }

    private SpellBookMenu(int containerId, Inventory playerInventory, Container inventory) {
        super(AMMenuTypes.SPELL_BOOK.get(), containerId);
        int slotIndex = 0;
        //Spell Book Pages - active spells
        for (int i = 0; i < SpellBookItem.ACTIVE_SPELL_SLOTS; i++) {
            addSlot(new SpellBookSlot(inventory, slotIndex++, 18, 5 + (i * 18)));
        }
        //Spell Book Pages - reserve spells
        for (int i = 0; i < SpellBookItem.BACKUP_SPELL_SLOTS / SpellBookItem.ACTIVE_SPELL_SLOTS; i++) {
            for (int k = 0; k < SpellBookItem.ACTIVE_SPELL_SLOTS; k++) {
                addSlot(new SpellBookSlot(inventory, slotIndex++, 138 + (i * 26), 5 + (k * 18)));
            }
        }
        //display player inventory
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 9; k++) {
                addSlot(new Slot(playerInventory, k + i * 9 + 9, 48 + k * 18, 171 + i * 18));
            }
        }
        //display player action bar
        for (int j1 = 0; j1 < 9; j1++) {
            addSlot(new Slot(playerInventory, j1, 48 + j1 * 18, 229));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return ArsMagicaAPI.get().getMagicHelper().knowsMagic(player);
    }
}
