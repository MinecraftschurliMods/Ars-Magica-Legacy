package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

import com.github.minecraftschurlimods.arsmagicalegacy.common.block.ITierCheckingBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMStats;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.Locale;
import java.util.function.BiPredicate;

public class ObeliskBlock extends AbstractFurnaceBlock implements ITierCheckingBlock {
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private final BiPredicate<Level, BlockPos> OBELISK_CHALK = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.OBELISK_CHALK);
    private final BiPredicate<Level, BlockPos> OBELISK_PILLARS = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.OBELISK_PILLARS);

    public ObeliskBlock() {
        super(BlockBehaviour.Properties.of().noOcclusion().lightLevel(state -> state.getValue(PART) == Part.LOWER && state.getValue(LIT) ? 11 : 1));
        registerDefaultState(defaultBlockState().setValue(PART, Part.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() || state.getValue(PART) != Part.LOWER ? null : createTickerHelper(blockEntityType, AMBlockEntities.OBELISK.get(), (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? AMBlockEntities.OBELISK.get().create(pos, state) : null;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos.below(level.getBlockState(pos).getValue(PART).ordinal()));
        if (blockentity instanceof ObeliskBlockEntity obeliskBlockEntity) {
            player.openMenu(obeliskBlockEntity);
            player.awardStat(AMStats.INTERACT_WITH_OBELISK.get());
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getLevel().isOutsideBuildHeight(pContext.getClickedPos())) return null;
        if (pContext.getLevel().isOutsideBuildHeight(pContext.getClickedPos().above())) return null;
        if (pContext.getLevel().isOutsideBuildHeight(pContext.getClickedPos().above(2))) return null;
        if (!pContext.getLevel().getBlockState(pContext.getClickedPos()).canBeReplaced(pContext)) return null;
        if (!pContext.getLevel().getBlockState(pContext.getClickedPos().above()).canBeReplaced(pContext)) return null;
        if (!pContext.getLevel().getBlockState(pContext.getClickedPos().above(2)).canBeReplaced(pContext)) return null;
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(PART) == Part.LOWER) {
            level.setBlock(pos.above(), defaultBlockState().setValue(PART, Part.MIDDLE).setValue(FACING, state.getValue(FACING)), UPDATE_ALL);
            level.setBlock(pos.above(2), defaultBlockState().setValue(PART, Part.UPPER).setValue(FACING, state.getValue(FACING)), UPDATE_ALL);
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        Block block = pState.getBlock();
        if (block == pNewState.getBlock() && pState.getValue(PART) == pNewState.getValue(PART)) return;
        BlockPos above1 = pPos.above();
        BlockPos above2 = pPos.above(2);
        BlockPos below1 = pPos.below();
        BlockPos below2 = pPos.below(2);
        switch (pState.getValue(PART)) {
            case LOWER -> {
                if (pLevel.getBlockState(above1).getBlock() == block) {
                    pLevel.removeBlock(above1, false);
                }
                if (pLevel.getBlockState(above2).getBlock() == block) {
                    pLevel.removeBlock(above2, false);
                }
            }
            case MIDDLE -> {
                if (pLevel.getBlockState(below1).getBlock() == block) {
                    pLevel.removeBlock(below1, false);
                }
                if (pLevel.getBlockState(above1).getBlock() == block) {
                    pLevel.removeBlock(above1, false);
                }
            }
            case UPPER -> {
                if (pLevel.getBlockState(below1).getBlock() == block) {
                    pLevel.removeBlock(below1, false);
                }
                if (pLevel.getBlockState(below2).getBlock() == block) {
                    pLevel.removeBlock(below2, false);
                }
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (state.getBlock() == this) {
            Part part = state.getValue(PART);
            BlockPos pos1;
            BlockPos pos2;
            switch (part) {
                case LOWER -> {
                    pos1 = pos.above();
                    pos2 = pos.above(2);
                }
                case MIDDLE -> {
                    pos1 = pos.above();
                    pos2 = pos.below();
                }
                case UPPER -> {
                    pos1 = pos.below();
                    pos2 = pos.below(2);
                }
                default -> pos1 = pos2 = pos;
            }
            level.setBlock(pos1, Blocks.AIR.defaultBlockState(), UPDATE_SUPPRESS_DROPS | UPDATE_ALL);
            level.setBlock(pos2, Blocks.AIR.defaultBlockState(), UPDATE_SUPPRESS_DROPS | UPDATE_ALL);
            level.levelEvent(player, 2001, pos1, Block.getId(level.getBlockState(pos1)));
            level.levelEvent(player, 2001, pos2, Block.getId(level.getBlockState(pos2)));
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    /**
     * @param world The world this block is in.
     * @param pos   The position of the core block.
     * @return The tier of the surrounding multiblock.
     */
    @Override
    public int getTier(Level world, BlockPos pos) {
        int tier = 0;
        if (OBELISK_CHALK.test(world, pos)) {
            tier = 1;
            if (OBELISK_PILLARS.test(world, pos)) {
                tier = 2;
            }
        }
        return tier;
    }

    public enum Part implements StringRepresentable {
        LOWER, MIDDLE, UPPER;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
