package at.minecraftschurli.mods.arsmagicalegacy.plant;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ChorusGrowthType() implements GrowthType {
    public static final MapCodec<ChorusGrowthType> CODEC = MapCodec.unit(ChorusGrowthType::new);

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        BlockState state = context.state();
        return state.is(Blocks.CHORUS_FLOWER) && state.getValue(BlockStateProperties.AGE_5) < 5;
    }

    @Override
    public void grow(GrowthContext context) {
        ServerLevel level = context.level();
        ChorusFlowerBlock.generatePlant(level, context.pos(), level.getRandom(), 10 - context.state().getValue(BlockStateProperties.AGE_5) * 2);
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return !canGrow(context);
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context, boolean replant) {
        Set<BlockPos> set = new HashSet<>();
        ServerLevel level = context.level();
        ServerPlayer player = context.player();
        ItemStack tool = context.tool();
        getTree(level, context.pos(), set);
        List<ItemStack> drops = new ArrayList<>();
        Set<BlockPos> toRemove = new HashSet<>();
        for (BlockPos pos : set) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.CHORUS_FLOWER)) {
                drops.addAll(AMUtil.destroyBlockAndGetDrops(level, pos, state, player, tool));
                toRemove.add(pos);
            }
        }
        set.removeAll(toRemove);
        List<BlockPos> list = set.stream()
            .sorted(Comparator.comparing(BlockPos::getY))
            .toList();
        BlockPos ground = null;
        for (BlockPos pos : list) {
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.CHORUS_PLANT)) {
                drops.addAll(AMUtil.destroyBlockAndGetDrops(level, pos, state, player, tool));
            }
            if (level.getBlockState(pos.below()).is(Blocks.END_STONE)) {
                ground = pos;
            }
        }
        if (replant && ground != null) {
            level.setBlockAndUpdate(ground, Blocks.CHORUS_FLOWER.defaultBlockState());
        }
        return drops;
    }

    private void getTree(ServerLevel level, BlockPos pos, Set<BlockPos> set) {
        BlockState state = level.getBlockState(pos);
        if (state.is(Blocks.CHORUS_FLOWER)) {
            set.add(pos);
        } else if (state.is(Blocks.CHORUS_PLANT)) {
            set.add(pos);
            addPos(level, pos.north(), state, BlockStateProperties.NORTH, set);
            addPos(level, pos.east(), state, BlockStateProperties.EAST, set);
            addPos(level, pos.south(), state, BlockStateProperties.SOUTH, set);
            addPos(level, pos.west(), state, BlockStateProperties.WEST, set);
            addPos(level, pos.above(), state, BlockStateProperties.UP, set);
            addPos(level, pos.below(), state, BlockStateProperties.DOWN, set);
        }
    }

    private void addPos(ServerLevel level, BlockPos pos, BlockState state, BooleanProperty property, Set<BlockPos> set) {
        if (state.getValue(property) && !set.contains(pos)) {
            getTree(level, pos, set);
        }
    }
}
