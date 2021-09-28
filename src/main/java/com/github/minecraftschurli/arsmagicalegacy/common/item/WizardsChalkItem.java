package com.github.minecraftschurli.arsmagicalegacy.common.item;

import com.github.minecraftschurli.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class WizardsChalkItem extends BlockItem {
    public WizardsChalkItem(Properties pProperties) {
        super(AMBlocks.WIZARDS_CHALK.get(), pProperties);
    }

    @NotNull
    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        if (!pContext.canPlace()) return InteractionResult.FAIL;
        BlockPlaceContext context = this.updatePlacementContext(pContext);
        if (context == null) return InteractionResult.FAIL;
        BlockState placementState = this.getPlacementState(context);
        if (placementState == null || !this.placeBlock(context, placementState)) return InteractionResult.FAIL;
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        ItemStack itemstack = context.getItemInHand();
        BlockState state = level.getBlockState(blockpos);
        if (state.is(placementState.getBlock())) {
            state = this.updateBlockStateFromTag(blockpos, level, itemstack, state);
            this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, state);
            state.getBlock().setPlacedBy(level, blockpos, state, player, itemstack);
            if (player instanceof ServerPlayer)
                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
        }
        level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
        SoundType soundtype = state.getSoundType(level, blockpos, pContext.getPlayer());
        if (player != null) {
            level.playSound(player, blockpos, this.getPlaceSound(state, level, blockpos, player), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            if (!player.getAbilities().instabuild) itemstack.shrink(1);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState) {
        BlockState blockstate = pState;
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = pState.getBlock().getStateDefinition();
            for (String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }
        if (blockstate != pState) pLevel.setBlock(pPos, blockstate, 2);
        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
        return pProperty.getValue(pValueIdentifier).map((p_40592_) -> pState.setValue(pProperty, p_40592_)).orElse(pState);
    }
}
