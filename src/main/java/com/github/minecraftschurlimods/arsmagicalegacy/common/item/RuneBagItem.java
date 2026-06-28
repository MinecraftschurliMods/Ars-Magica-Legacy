package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.common.container.ItemStackContainer;
import com.github.minecraftschurlimods.arsmagicalegacy.common.menu.RuneBagMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;

public class RuneBagItem extends Item {
    public RuneBagItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("unused")
    public static ResourceHandler<ItemResource> getItemHandler(ItemStack stack, ItemAccess access) {
        return VanillaContainerWrapper.of(new ItemStackContainer(stack, DyeColor.values().length));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new SimpleMenuProvider((id, inventory, _) -> new RuneBagMenu(id, inventory, hand), Component.empty()), buf -> buf.writeEnum(hand));
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(player.getItemInHand(hand));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !ItemStack.isSameItem(oldStack, newStack);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
