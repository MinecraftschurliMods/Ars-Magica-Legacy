package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import java.util.List;

public class SpellBookItem extends Item {
    public static final int ACTIVE_SPELL_SLOTS = 8;
    public static final int BACKUP_SPELL_SLOTS = 32;
    public static final int TOTAL_SPELL_SLOTS = ACTIVE_SPELL_SLOTS + BACKUP_SPELL_SLOTS;

    public SpellBookItem() {
        super(new Item.Properties()
                      .stacksTo(1)
                      .component(AMDataComponents.SPELLS, ItemContainerContents.EMPTY)
        );
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        getSelectedSpell(stack).onUseTick(level, entity, remainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        getSelectedSpell(stack).releaseUsing(level, livingEntity, timeCharged);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemInHand = player.getItemInHand(usedHand);
        if (player.isCrouching()) {
            if (!player.isLocalPlayer()) {
                player.openMenu(new SimpleMenuProvider((containerId, inventory, $) -> new SpellBookMenu(containerId, inventory, itemInHand), getName(itemInHand)));
            }
            return InteractionResultHolder.sidedSuccess(itemInHand, player.isLocalPlayer());
        }
        InteractionResultHolder<ItemStack> use = getSelectedSpell(itemInHand).use(level, player, usedHand);
        getContainer(itemInHand).setItem(getSelectedSlot(itemInHand), use.getObject());
        return new InteractionResultHolder<>(use.getResult(), itemInHand);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack itemInHand = context.getItemInHand();
        ItemStack selectedSpell = getSelectedSpell(itemInHand);
        Player player = context.getPlayer();
        if (player != null && player.isCrouching()) {
            if (!player.isLocalPlayer()) {
                player.openMenu(new SimpleMenuProvider((containerId, inventory, $) -> new SpellBookMenu(containerId, inventory, itemInHand), getName(itemInHand)));
            }
            return InteractionResult.sidedSuccess(player.isLocalPlayer());
        }
        return selectedSpell.getItem().useOn(context);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getSelectedSpell(stack).getUseDuration(entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        ItemStack selectedSpell = getSelectedSpell(stack);
        selectedSpell.getItem().appendHoverText(selectedSpell, context, tooltip, flag);
    }

    public static IItemHandler getItemCapability(ItemStack stack, Void $) {
        return new InvWrapper(getContainer(stack));
    }

    public static int getSelectedSlot(ItemStack stack) {
        return stack.getOrDefault(AMDataComponents.SELECTED_SLOT, 0);
    }

    public static void prevSelectedSlot(ItemStack stack) {
        int slot = (getSelectedSlot(stack) - 1) % getContainer(stack).active().getContainerSize();
        if (slot < 0) {
            slot += getContainer(stack).active().getContainerSize();
        }
        setSelectedSlot(stack, slot);
    }

    public static void nextSelectedSlot(ItemStack stack) {
        int slot = (getSelectedSlot(stack) + 1) % getContainer(stack).active().getContainerSize();
        if (slot > getContainer(stack).active().getContainerSize()) {
            slot -= getContainer(stack).active().getContainerSize();
        }
        setSelectedSlot(stack, slot);
    }

    private static void setSelectedSlot(ItemStack stack, int slot) {
        stack.set(AMDataComponents.SELECTED_SLOT, slot);
    }

    public static ItemStack getSelectedSpell(ItemStack stack) {
        return getSpell(stack, getSelectedSlot(stack));
    }

    public static SpellBookContainer getContainer(ItemStack stack) {
        return new SpellBookContainer(stack, ACTIVE_SPELL_SLOTS, BACKUP_SPELL_SLOTS);
    }

    public static ItemStack getSpell(ItemStack stack, int slot) {
        if (slot < 0) return ItemStack.EMPTY;
        SpellBookContainer container = getContainer(stack);
        if (slot > container.getContainerSize()) return ItemStack.EMPTY;
        return container.getItem(slot);
    }
}
