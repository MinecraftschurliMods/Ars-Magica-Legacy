package com.github.minecraftschurlimods.arsmagicalegacy.common.block;

import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumHandlerBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jspecify.annotations.Nullable;

public abstract class EtheriumGeneratorBlock extends Block implements EntityBlock, EtheriumHandlerBlock {
    private static final BlockEntityTicker<?> TICKER = (level, pos, state, blockEntity) -> {
        if (blockEntity instanceof EtheriumGeneratorBlockEntity etheriumGeneratorBlockEntity) {
            etheriumGeneratorBlockEntity.tick(level, pos, state);
        }
    };

    public EtheriumGeneratorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return (BlockEntityTicker<T>) TICKER;
    }
}
