package at.minecraftschurli.mods.arsmagicalegacy.block;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class OcculusBlock extends HorizontalDirectionalBlock {
    private static final MapCodec<OcculusBlock> CODEC = simpleCodec(OcculusBlock::new);
    private static final VoxelShape SOCKET = AMUtil.joinShapes(
        box(0, 0, 0, 16, 1, 16),
        box(1.5, 1, 1.5, 14.5, 2, 14.5),
        box(3, 2, 3, 13, 3, 13),
        box(4.5, 3, 4.5, 11.5, 4, 11.5),
        box(6, 4, 6, 10, 8, 10),
        box(5, 7, 6, 6, 9, 10),
        box(10, 7, 6, 11, 9, 10),
        box(6, 7, 5, 10, 9, 6),
        box(6, 7, 10, 10, 9, 11),
        box(7, 8, 11, 9, 10, 12),
        box(11, 8, 7, 12, 10, 9),
        box(4, 8, 7, 5, 10, 9),
        box(7, 8, 4, 9, 10, 5));
    private static final Map<Direction, VoxelShape> SHAPES = Map.of(
        Direction.NORTH, AMUtil.joinShapes(SOCKET,
            box(5, 11, 6.5, 11, 12, 9.5),
            box(5, 14, 6.5, 11, 15, 9.5),
            box(5, 12, 6.5, 6.5, 14, 9.5),
            box(9.5, 12, 6.5, 11, 14, 9.5),
            box(4, 11, 7.5, 12, 15, 8.5),
            box(5, 10, 7.5, 11, 16, 8.5),
            box(6.5, 12, 8.5, 9.5, 14, 9.5)),
        Direction.EAST, AMUtil.joinShapes(SOCKET,
            box(6.5, 11, 5, 9.5, 12, 11),
            box(6.5, 14, 5, 9.5, 15, 11),
            box(6.5, 12, 9.5, 9.5, 14, 11),
            box(6.5, 12, 5, 9.5, 14, 6.5),
            box(7.5, 11, 4, 8.5, 15, 12),
            box(7.5, 10, 5, 8.5, 16, 11),
            box(6.5, 12, 6.5, 7.5, 14, 9.5)),
        Direction.SOUTH, AMUtil.joinShapes(SOCKET,
            box(5, 10, 7.5, 11, 16, 8.5),
            box(4, 11, 7.5, 12, 15, 8.5),
            box(5, 11, 6.5, 11, 12, 9.5),
            box(5, 14, 6.5, 11, 15, 9.5),
            box(5, 12, 6.5, 6.5, 14, 9.5),
            box(9.5, 12, 6.5, 11, 14, 9.5),
            box(6.5, 12, 6.5, 9.5, 14, 7.5)),
        Direction.WEST, AMUtil.joinShapes(SOCKET,
            box(6.5, 11, 5, 9.5, 12, 11),
            box(6.5, 14, 5, 9.5, 15, 11),
            box(6.5, 12, 5, 9.5, 14, 6.5),
            box(6.5, 12, 9.5, 9.5, 14, 11),
            box(7.5, 11, 4, 8.5, 15, 12),
            box(7.5, 10, 5, 8.5, 16, 11),
            box(8.5, 12, 6.5, 9.5, 14, 9.5)));

    public OcculusBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<OcculusBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) return InteractionResult.SUCCESS;
        if (ArsMagicaApi.magicHelper().knowsMagic(player)) {
            AMClientUtil.setOcculusScreen();
        } else {
            player.sendOverlayMessage(AMTranslations.PREVENT_BLOCK);
        }
        return InteractionResult.SUCCESS;
    }
}
