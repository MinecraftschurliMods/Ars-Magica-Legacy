package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.container.SpellBookContainer;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.menu.SpellBookMenu;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;

import java.util.function.Consumer;

public class SpellBookItem extends Item {
    public static final int INVENTORY_SLOTS = 32;
    public static final int HOTBAR_SLOTS = 8;
    public static final int TOTAL_SLOTS = INVENTORY_SLOTS + HOTBAR_SLOTS;

    public SpellBookItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!player.isSecondaryUseActive()) return getSelectedSpell(stack).use(level, player, usedHand);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, _) -> new SpellBookMenu(id, inventory, usedHand), Component.empty()), buf -> buf.writeEnum(usedHand));
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return getSelectedSpell(stack).getUseDuration(entity);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        getSelectedSpell(stack).onUseTick(level, livingEntity, remainingUseDuration);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        getSelectedSpell(stack).releaseUsing(level, livingEntity, timeCharged);
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        ItemStack spell = getSelectedSpell(stack);
        if (spell.isEmpty()) {
            builder.accept(AMTranslations.SPELL_BOOK_NO_SPELL_SELECTED.copy().withStyle(ChatFormatting.GRAY));
        } else {
            builder.accept(Component.translatable(AMTranslations.SPELL_BOOK_SELECTED_SPELL_KEY, spell.getHoverName()).withStyle(ChatFormatting.GRAY));
            spell.getItem().appendHoverText(spell, context, display, builder, tooltipFlag);
        }
    }

    @SuppressWarnings("unused")
    public static ResourceHandler<ItemResource> getItemHandler(ItemStack stack, ItemAccess access) {
        return VanillaContainerWrapper.of(new SpellBookContainer(stack));
    }

    public static void scroll(ItemStack stack, boolean backwards) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, 0);
        index += backwards ? HOTBAR_SLOTS - 1 : 1;
        index %= HOTBAR_SLOTS;
        stack.set(AMDataComponents.SELECTED_INDEX, index);
        updateSpell(stack);
    }

    public static void updateSpell(ItemStack stack) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        ItemContainerContents container = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (index >= 0 && index < Math.min(container.getSlots(), SpellBookItem.HOTBAR_SLOTS)) {
            ItemStack item = container.getStackInSlot(index);
            Spell spell = item.get(AMDataComponents.SPELL);
            if (spell != null && !spell.isEmpty()) {
                stack.set(AMDataComponents.SPELL, spell);
                return;
            }
        }
        stack.remove(AMDataComponents.SPELL);
    }

    private static ItemStack getSelectedSpell(ItemStack stack) {
        int index = stack.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index < 0 || index >= HOTBAR_SLOTS) return ItemStack.EMPTY;
        ItemContainerContents container = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        return index < container.getSlots() ? container.getStackInSlot(index) : ItemStack.EMPTY;
    }
}
