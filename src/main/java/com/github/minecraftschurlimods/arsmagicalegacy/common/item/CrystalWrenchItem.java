package com.github.minecraftschurlimods.arsmagicalegacy.common.item;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMCapabilities;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumHandler;
import com.github.minecraftschurlimods.arsmagicalegacy.api.etherium.EtheriumHandlerBlock;
import com.github.minecraftschurlimods.arsmagicalegacy.common.init.AMDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrystalWrenchItem extends Item {
    public CrystalWrenchItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = state.getBlock() instanceof EtheriumHandlerBlock block ? block.getBlockEntity(level, pos, state) : level.getBlockEntity(pos);
        if (blockEntity == null) return super.useOn(context);
        EtheriumHandler handler = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, blockEntity.getBlockPos(), null);
        if (handler == null) return super.useOn(context);
        if (handler.canHaveConnectedPositions() && stack.has(AMDataComponents.STORED_POSITIONS)) {
            List<BlockPos> list = stack.get(AMDataComponents.STORED_POSITIONS)
                .stream()
                .filter(e -> e.dimension() == level.dimension())
                .map(GlobalPos::pos)
                .filter(e -> !e.equals(blockEntity.getBlockPos()))
                .toList();
            if (list.isEmpty()) return super.useOn(context);
            if (handler.getConnectedPositions().containsAll(list)) {
                list.forEach(handler::removeConnectedPosition);
            } else {
                list.forEach(handler::addConnectedPosition);
            }
            stack.set(AMDataComponents.STORED_POSITIONS, List.of());
        } else {
            List<GlobalPos> list = stack.has(AMDataComponents.STORED_POSITIONS) ? new ArrayList<>(stack.get(AMDataComponents.STORED_POSITIONS)) : new ArrayList<>();
            GlobalPos globalPos = new GlobalPos(level.dimension(), blockEntity.getBlockPos());
            if (!list.contains(globalPos)) {
                list.add(globalPos);
            }
            stack.set(AMDataComponents.STORED_POSITIONS, list);
        }
        return InteractionResult.SUCCESS;
    }
}
