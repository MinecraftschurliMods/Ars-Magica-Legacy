package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class WizardsChalkItem extends BlockItem {
    public WizardsChalkItem() {
        super(AMBlocks.WIZARDS_CHALK.get(), new Item.Properties().stacksTo(64).durability(100));
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        if (!pContext.canPlace()) return InteractionResult.FAIL;
        BlockPlaceContext context = updatePlacementContext(pContext);
        if (context == null) return InteractionResult.FAIL;
        BlockState placement = getPlacementState(context);
        if (placement == null || !placeBlock(context, placement)) return InteractionResult.FAIL;
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        BlockState state = level.getBlockState(blockpos);
        if (state.is(placement.getBlock())) {
            state = updateBlockStateFromTag(blockpos, level, itemstack, state);
            updateCustomBlockEntityTag(blockpos, level, player, itemstack, state);
            state.getBlock().setPlacedBy(level, blockpos, state, player, itemstack);
            level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
            if (player != null) {
                SoundType soundtype = state.getSoundType(level, blockpos, pContext.getPlayer());
                level.playSound(player, blockpos, getPlaceSound(state, level, blockpos, player), SoundSource.BLOCKS, (soundtype.getVolume() + 1F) / 2F, soundtype.getPitch() * 0.8F);
                itemstack.hurtAndBreak(1, player, pContext.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
                }
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState) {
        BlockItemStateProperties blockitemstateproperties = pStack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
        if (blockitemstateproperties.isEmpty()) {
            return pState;
        } else {
            BlockState blockstate = blockitemstateproperties.apply(pState);
            if (blockstate != pState) {
                pLevel.setBlock(pPos, blockstate, 2);
            }

            return blockstate;
        }
    }
}
