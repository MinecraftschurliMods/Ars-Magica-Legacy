package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.Config;
import com.github.minecraftschurlimods.arsmagicalegacy.common.block.inscriptiontable.InscriptionTableBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class InscriptionTableUpgradeItem extends Item {
    private final int tier;

    public InscriptionTableUpgradeItem(Properties pProperties, int tier) {
        super(pProperties);
        this.tier = tier;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (!Config.SERVER.ENABLE_INSCRIPTION_TABLE_IN_WORLD_UPGRADING.get()) return super.useOn(pContext);
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() == AMBlocks.INSCRIPTION_TABLE.get() && state.getValue(InscriptionTableBlock.TIER) == tier - 1) {
            if (state.getValue(InscriptionTableBlock.HALF) == InscriptionTableBlock.Half.LEFT) {
                pos = pos.relative(state.getValue(InscriptionTableBlock.FACING).getClockWise());
                state = level.getBlockState(pos);
            }
            level.setBlock(pos, state.setValue(InscriptionTableBlock.TIER, tier), Block.UPDATE_ALL);
            if (pContext.getPlayer() != null && !pContext.getPlayer().isCreative()) {
                pContext.getItemInHand().shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return super.useOn(pContext);
    }
}
