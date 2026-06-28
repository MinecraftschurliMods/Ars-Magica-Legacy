package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.Nullable;

public class AltarCoreBlock extends Block implements EntityBlock {
    public static final BooleanProperty FORMED = BooleanProperty.create("formed");
    private static final BlockEntityTicker<?> TICKER = (level, pos, state, blockEntity) -> {
        if (blockEntity instanceof AltarCoreBlockEntity altar) {
            altar.tick(level, pos, state);
        }
    };

    public AltarCoreBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FORMED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FORMED);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AltarCoreBlockEntity(pos, state);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == AMBlockEntities.ALTAR_CORE.get() ? (BlockEntityTicker<T>) TICKER : null;
    }
}
