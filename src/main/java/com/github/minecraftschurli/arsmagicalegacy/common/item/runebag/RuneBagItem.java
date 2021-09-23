package com.github.minecraftschurli.arsmagicalegacy.common.item.runebag;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mostly taken from the Botania mod.
 * {@see https://github.com/VazkiiMods/Botania/blob/master/src/main/java/vazkii/botania/common/item/ItemFlowerBag.java}
 */
public class RuneBagItem extends Item {
    public RuneBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return new InvProvider(stack);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        if (pLevel.isClientSide) return InteractionResultHolder.success(pPlayer.getItemInHand(pUsedHand));
        if (pPlayer instanceof ServerPlayer sp)
            NetworkHooks.openGui(sp, new SimpleMenuProvider((id, inv, player) -> new RuneBagContainer(id, inv, player.getItemInHand(pUsedHand)), TextComponent.EMPTY), buf -> buf.writeBoolean(pUsedHand == InteractionHand.MAIN_HAND));
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    public static final class InvProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> lazy;

        public InvProvider(ItemStack stack) {
            this.lazy = LazyOptional.of(() -> new InvWrapper(new RuneBagInventory(stack)));
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, lazy);
        }
    }
}
