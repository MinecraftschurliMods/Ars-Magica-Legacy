package com.github.minecraftschurlimods.arsmagicalegacy.common.item.runebag;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

/**
 * Mostly taken from McJty's tutorials and the Botania mod.
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemFlowerBag.java}
 */
public class RuneBagItem extends Item {
    public RuneBagItem(Properties pProperties) {
        super(pProperties);
    }

    public static IItemHandler getItemCapability(ItemStack stack, Void v) {
        return new InvWrapper(new RuneBagContainer(stack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pLevel.isClientSide()) return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        if (pPlayer instanceof ServerPlayer sp) {
            sp.openMenu(new SimpleMenuProvider((id, inv, player) -> new RuneBagMenu(id, inv, player.getItemInHand(pUsedHand)), Component.empty()), buf -> buf.writeEnum(pUsedHand));
        }
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }
}
