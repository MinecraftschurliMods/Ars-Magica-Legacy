package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WizardsChalkBlock extends Block {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 15);
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 0.1, 14);

    public WizardsChalkBlock() {
        super(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().sound(SoundType.GRAVEL));
        registerDefaultState(getStateDefinition().any().setValue(VARIANT, 0).setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.below()).isSolidRender(pLevel, pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(VARIANT, BlockStateProperties.HORIZONTAL_FACING);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return new ItemStack(AMItems.WIZARDS_CHALK.get());
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.DESTROY;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(VARIANT, pContext.getLevel().random.nextInt(16)).setValue(BlockStateProperties.HORIZONTAL_FACING, pContext.getHorizontalDirection());
    }
}
