package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class WizardsChalkItem extends BlockItem {
    public WizardsChalkItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (!getBlock().isEnabled(context.getLevel().enabledFeatures()) || !context.canPlace()) return InteractionResult.FAIL;
        BlockPlaceContext updatedContext = updatePlacementContext(context);
        if (updatedContext == null) return InteractionResult.FAIL;
        BlockState state = getPlacementState(updatedContext);
        if (state == null || !placeBlock(updatedContext, state)) return InteractionResult.FAIL;
        BlockPos pos = updatedContext.getClickedPos();
        Level level = updatedContext.getLevel();
        Player player = updatedContext.getPlayer();
        ItemStack stack = updatedContext.getItemInHand();
        BlockState oldState = level.getBlockState(pos);
        if (oldState.is(state.getBlock())) {
            BlockItemStateProperties properties = stack.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            if (!properties.isEmpty()) {
                BlockState newState = properties.apply(oldState);
                if (newState != oldState) {
                    level.setBlock(pos, newState, Block.UPDATE_CLIENTS);
                }
                oldState = newState;
            }
            updateCustomBlockEntityTag(pos, level, player, stack, oldState);
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity != null) {
                blockentity.applyComponentsFromItemStack(stack);
                blockentity.setChanged();
            }
            oldState.getBlock().setPlacedBy(level, pos, oldState, player, stack);
            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, pos, stack);
            }
        }
        SoundType soundtype = oldState.getSoundType(level, pos, player);
        if (player != null) {
            level.playSound(player, pos, getPlaceSound(oldState, level, pos, player), SoundSource.BLOCKS, (soundtype.getVolume() + 1) / 2f, soundtype.getPitch() * 0.8f);
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(player, oldState));
            stack.hurtAndBreak(1, player, context.getHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        }
        return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
    }
}
