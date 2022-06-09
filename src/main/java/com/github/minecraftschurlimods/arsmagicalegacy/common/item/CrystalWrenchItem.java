package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaLegacy;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.TranslationConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
        if (context.getLevel().isClientSide()) return InteractionResult.PASS;
        var helper = ArsMagicaAPI.get().getEtheriumHelper();
        if (context.isSecondaryUseActive()) {
            if (helper.hasEtheriumProvider(context.getLevel(), context.getClickedPos())) {
                savePos(context.getItemInHand(), context.getClickedPos());
                return InteractionResult.SUCCESS;
            }
        } else if (helper.hasEtheriumConsumer(context.getLevel(), context.getClickedPos())) {
            if (Math.sqrt(context.getClickedPos().distSqr(getSavedPos(context.getItemInHand()))) > 32) {
                Player player = context.getPlayer();
                if (player != null) {
                    player.displayClientMessage(Component.translatable(TranslationConstants.CRYSTAL_WRENCH_TOO_FAR).withStyle(ChatFormatting.RED), true);
                }
                return InteractionResult.FAIL;
            }
            helper.getEtheriumConsumer(context.getLevel(), context.getClickedPos()).ifPresent(consumer -> consumer.bindProvider(getSavedPos(context.getItemInHand())));
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
