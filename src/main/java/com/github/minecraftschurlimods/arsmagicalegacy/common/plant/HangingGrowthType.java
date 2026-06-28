package com.github.minecraftschurlimods.arsmagicalegacy.common.plant;

import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public record HangingGrowthType(int minHeight, int maxHeight, Block head, Block body) implements ReplantableGrowthType {
    public static final MapCodec<HangingGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height", 1).forGetter(HangingGrowthType::minHeight),
        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_height", 0).forGetter(HangingGrowthType::maxHeight),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head").forGetter(HangingGrowthType::head),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body").forGetter(HangingGrowthType::body)
    ).apply(inst, HangingGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        List<BlockPos> column = AMUtil.getHangingColumn(context, head, body);
        if (maxHeight > 0 && column.size() >= maxHeight) return false;
        ServerLevel level = context.level();
        BlockPos last = column.getLast();
        return level.getBlockState(last.below()).canBeReplaced() && level.getBlockState(last).is(head);
    }

    @Override
    public void grow(GrowthContext context) {
        BlockState state = context.state();
        ServerLevel level = context.level();
        if (state.getBlock() instanceof BonemealableBlock block) {
            block.performBonemeal(level, level.getRandom(), context.pos(), state);
        } else {
            BlockPos last = AMUtil.getHangingColumn(context, head, body).getLast();
            level.setBlockAndUpdate(last, body.defaultBlockState());
            level.setBlockAndUpdate(last.below(), head.defaultBlockState());
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return AMUtil.getHangingColumn(context, head, body).size() > minHeight;
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        List<BlockPos> column = AMUtil.getHangingColumn(context, head, body);
        List<ItemStack> drops = new ArrayList<>();
        ServerLevel level = context.level();
        while (column.size() > minHeight) {
            BlockPos last = column.getLast();
            drops.addAll(AMUtil.destroyBlockAndGetDrops(level, last, level.getBlockState(last), context.player(), context.tool()));
            column.removeLast();
        }
        return drops;
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return false;
    }

    @Override
    public void replant(GrowthContext context) {
    }
}
