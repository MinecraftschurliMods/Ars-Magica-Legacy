package com.github.minecraftschurli.arsmagicalegacy.common.block;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.arsmagicalegacy.common.init.AMStats;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class OcculusBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    /**
     * Creates a new OcculusBlock. Sets the properties and default state.
     */
    public OcculusBlock() {
        super(Properties.of(Material.STONE).explosionResistance(5).destroyTime(3));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.isSecondaryUseActive()) return InteractionResult.PASS;
        if (pLevel.isClientSide) {
            var api = ArsMagicaAPI.get();
            if (!api.getMagicHelper().knowsMagic(pPlayer)) {
                pPlayer.sendMessage(new TranslatableComponent("message.%s.occulus.prevent".formatted(ArsMagicaAPI.MOD_ID)), pPlayer.getUUID());
            } else {
                api.openOcculusGui(pPlayer);
            }
            return InteractionResult.SUCCESS;
        } else {
            pPlayer.awardStat(AMStats.INTERACT_WITH_OCCULUS);
            return InteractionResult.CONSUME;
        }
    }
}
