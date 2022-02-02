package com.github.minecraftschurlimods.arsmagicalegacy.common.block.blackaurem;

import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlockEntities;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiPredicate;

public class BlackAuremBlock extends BaseEntityBlock {
    private static final VoxelShape BOX = Block.box(6, 6, 6, 10, 10, 10);
    private static final BiPredicate<Level, BlockPos> CHALK = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.BLACK_AUREM_CHALK);
    private static final BiPredicate<Level, BlockPos> PILLAR1 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.BLACK_AUREM_PILLAR1);
    private static final BiPredicate<Level, BlockPos> PILLAR2 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.BLACK_AUREM_PILLAR2);
    private static final BiPredicate<Level, BlockPos> PILLAR3 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.BLACK_AUREM_PILLAR3);
    private static final BiPredicate<Level, BlockPos> PILLAR4 = PatchouliCompat.getMultiblockMatcher(PatchouliCompat.BLACK_AUREM_PILLAR4);

    public BlackAuremBlock() {
        super(BlockBehaviour.Properties.of(Material.AIR).color(MaterialColor.COLOR_RED).lightLevel(value -> 2).noOcclusion().noCollission());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AMBlockEntities.BLACK_AUREM.get().create(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOX;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null : createTickerHelper(blockEntityType, AMBlockEntities.BLACK_AUREM.get(), (pLevel, pPos, pState, pBlockEntity) -> pBlockEntity.tick(pLevel, pPos, pState));
    }

    public int getTier(BlockState state, Level world, BlockPos pos) {
        int tier = 0;
        if (CHALK.test(world, pos)) {
            if (PILLAR1.test(world, pos)) {
                tier = 2;
            } else if (PILLAR2.test(world, pos)) {
                tier = 3;
            } else if (PILLAR3.test(world, pos)) {
                tier = 4;
            } else if (PILLAR4.test(world, pos)) {
                tier = 5;
            } else {
                tier = 1;
            }
        }
        return tier;
    }
}
