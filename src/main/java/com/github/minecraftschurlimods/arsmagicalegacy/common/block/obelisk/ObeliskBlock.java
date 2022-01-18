package com.github.minecraftschurlimods.arsmagicalegacy.common.block.obelisk;

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
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.function.BiPredicate;

public class ObeliskBlock extends AbstractFurnaceBlock {
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private final BiPredicate<Level, BlockPos> OBELISK_CHALK = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.OBELISK_CHALK);
    private final BiPredicate<Level, BlockPos> OBELISK_PILLARS = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.OBELISK_PILLARS);

    public ObeliskBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE).lightLevel(ObeliskBlock::getLightLevel));
        registerDefaultState(defaultBlockState().setValue(PART, Part.LOWER));
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(PART);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() || state.getValue(PART) != Part.LOWER ? null : createTickerHelper(blockEntityType, AMBlockEntities.OBELISK.get(), (level1, pos, state1, blockEntity) -> blockEntity.tick(level1, pos, state1));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.LOWER ? AMBlockEntities.OBELISK.get().create(pos, state) : null;
    }

    private static int getLightLevel(BlockState state) {
        return state.getValue(PART) == Part.LOWER && state.getValue(LIT) ? 11 : 0;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos.below(level.getBlockState(pos).getValue(PART).ordinal()));
        if (blockentity instanceof ObeliskBlockEntity obeliskBlockEntity) {
            player.openMenu(obeliskBlockEntity);
            player.awardStat(AMStats.INTERACT_WITH_OBELISK);
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
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (state.getValue(PART) == Part.LOWER) {
            level.setBlock(pos.above(), defaultBlockState().setValue(PART, Part.MIDDLE).setValue(FACING, state.getValue(FACING)), UPDATE_ALL);
            level.setBlock(pos.above(2), defaultBlockState().setValue(PART, Part.UPPER).setValue(FACING, state.getValue(FACING)), UPDATE_ALL);
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

    public int getTier(BlockState state, Level world, BlockPos pos) {
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
        LOWER,MIDDLE,UPPER;

        @Override
        public String getSerializedName() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
