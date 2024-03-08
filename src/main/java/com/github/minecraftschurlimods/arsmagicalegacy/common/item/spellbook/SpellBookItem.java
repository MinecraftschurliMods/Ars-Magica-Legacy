package com.github.minecraftschurlimods.arsmagicalegacy.common.item.spellbook;

import com.github.minecraftschurlimods.arsmagicalegacy.client.renderer.item.SpellItemRenderProperties;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SpellBookItem extends Item implements DyeableLeatherItem {
    public static final int ACTIVE_SPELL_SLOTS = 8;
    public static final int BACKUP_SPELL_SLOTS = 32;
    public static final int TOTAL_SPELL_SLOTS = ACTIVE_SPELL_SLOTS + BACKUP_SPELL_SLOTS;
    public static final String SELECTED_SLOT_KEY = "SelectedSlot";
    public static final String SPELLS_KEY = "Spells";

    public SpellBookItem() {
        super(AMItems.ITEM_1);
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
    public int getUseDuration(ItemStack stack) {
        return getSelectedSpell(stack).getUseDuration();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        ItemStack selectedSpell = getSelectedSpell(stack);
        selectedSpell.getItem().appendHoverText(selectedSpell, level, tooltipComponents, isAdvanced);
    }

    public static IItemHandler getItemCapability(ItemStack stack, Void v) {
        return new InvWrapper(getContainer(stack));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new SpellItemRenderProperties());
    }

    public static int getSelectedSlot(ItemStack stack) {
        return stack.getOrCreateTag().getInt(SELECTED_SLOT_KEY);
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
        stack.getOrCreateTag().putInt(SELECTED_SLOT_KEY, slot);
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
