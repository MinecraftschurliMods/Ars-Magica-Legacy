package at.minecraftschurli.mods.arsmagicalegacy.menu;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.container.SpellBookContainer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMMenus;
import at.minecraftschurli.mods.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.mods.arsmagicalegacy.slot.PlacePredicateSlot;
import at.minecraftschurli.mods.arsmagicalegacy.slot.ViewSlot;
import at.minecraftschurli.mods.arsmagicalegacy.util.QuickMoveStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class SpellBookMenu extends AbstractContainerMenu implements QuickMoveStack {
    private static final Predicate<ItemStack> PREDICATE = stack -> stack.is(AMItems.SPELL);
    private final InteractionHand hand;

    public SpellBookMenu(int containerId, Inventory inventory, InteractionHand hand) {
        super(AMMenus.SPELL_BOOK.get(), containerId);
        this.hand = hand;
        Container container = new SpellBookContainer(inventory.player.getItemInHand(hand));
        for (int i = 0; i < SpellBookItem.HOTBAR_SLOTS; i++) {
            addSlot(new PlacePredicateSlot(container, i, 18, 5 + (i * 18), PREDICATE));
        }
        for (int i = 0; i < SpellBookItem.INVENTORY_SLOTS / SpellBookItem.HOTBAR_SLOTS; i++) {
            for (int j = 0; j < SpellBookItem.HOTBAR_SLOTS; j++) {
                addSlot(new PlacePredicateSlot(container, (i + 1) * SpellBookItem.HOTBAR_SLOTS + j, 138 + (i * 26), 5 + (j * 18), PREDICATE));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlot(i == inventory.getSelectedSlot() ? new ViewSlot(inventory, i, 48 + i * 18, 229) : new Slot(inventory, i, 48 + i * 18, 229));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(inventory, i * 9 + j + 9, 48 + j * 18, 171 + i * 18));
            }
        }
    }

    public SpellBookMenu(int containerId, Inventory inventory, FriendlyByteBuf buf) {
        this(containerId, inventory, buf.readEnum(InteractionHand.class));
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand).is(AMItems.SPELL_BOOK) && ArsMagicaApi.magicHelper().knowsMagic(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return QuickMoveStack.super.quickMoveStack(player, index);
    }

    @Override
    public int getSlotCount() {
        return SpellBookItem.TOTAL_SLOTS;
    }

    @Override
    public boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        return super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
    }
}
