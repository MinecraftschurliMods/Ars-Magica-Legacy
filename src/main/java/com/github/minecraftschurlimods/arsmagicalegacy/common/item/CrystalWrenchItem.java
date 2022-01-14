package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.IEtheriumHelper;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

public class CrystalWrenchItem extends Item {
    private static final String SAVED_POS_KEY = ArsMagicaAPI.MOD_ID + ":saved_pos";

    public CrystalWrenchItem() {
        super(AMItems.ITEM_1);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        IEtheriumHelper etheriumHelper = ArsMagicaAPI.get().getEtheriumHelper();
        if (context.isSecondaryUseActive()) {
            if (etheriumHelper.hasEtheriumProvider(context.getLevel(), context.getClickedPos())) {
                savePos(context.getItemInHand(), context.getClickedPos());
                return InteractionResult.SUCCESS;
            }
        } else if (etheriumHelper.hasEtheriumConsumer(context.getLevel(), context.getClickedPos())) {
            etheriumHelper.getEtheriumConsumer(context.getLevel(), context.getClickedPos()).ifPresent(consumer -> consumer.bindProvider(getSavedPos(context.getItemInHand())));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void savePos(ItemStack stack, BlockPos pos) {
        stack.getOrCreateTag().put(SAVED_POS_KEY, BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn));
    }

    private BlockPos getSavedPos(ItemStack stack) {
        return BlockPos.CODEC.decode(NbtOps.INSTANCE, stack.getOrCreateTag().get(SAVED_POS_KEY)).map(Pair::getFirst).getOrThrow(false, ArsMagicaLegacy.LOGGER::warn);
    }
}
