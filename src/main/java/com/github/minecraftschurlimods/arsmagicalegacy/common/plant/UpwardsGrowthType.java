package com.github.minecraftschurlimods.arsmagicalegacy.common.plant;

import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public record UpwardsGrowthType(int minHeight, int maxHeight, Block head, Block body, boolean headRequired) implements ReplantableGrowthType {
    public static final MapCodec<UpwardsGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height", 1).forGetter(UpwardsGrowthType::minHeight),
        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_height", 0).forGetter(UpwardsGrowthType::maxHeight),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head").forGetter(UpwardsGrowthType::head),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body").forGetter(UpwardsGrowthType::body),
        Codec.BOOL.optionalFieldOf("head_required", true).forGetter(UpwardsGrowthType::headRequired)
    ).apply(inst, UpwardsGrowthType::new));

    public UpwardsGrowthType(int minHeight, int maxHeight, Block head, Block body) {
        this(minHeight, maxHeight, head, body, true);
    }

    public UpwardsGrowthType(int minHeight, int maxHeight, Block block) {
        this(minHeight, maxHeight, block, block, true);
    }

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        List<BlockPos> column = getColumn(context);
        if (maxHeight > 0 && column.size() >= maxHeight) return false;
        ServerLevel level = context.level();
        BlockPos last = column.getLast();
        return level.getBlockState(last.above()).canBeReplaced() && (!headRequired || level.getBlockState(last).is(head));
    }

    @Override
    public void grow(GrowthContext context) {
        BlockState state = context.state();
        ServerLevel level = context.level();
        if (state.getBlock() instanceof BonemealableBlock block) {
            block.performBonemeal(level, level.getRandom(), context.pos(), state);
        } else {
            BlockPos last = getColumn(context).getLast();
            level.setBlockAndUpdate(last, body.defaultBlockState());
            level.setBlockAndUpdate(last.above(), head.defaultBlockState());
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        return getColumn(context).size() > minHeight;
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        List<BlockPos> column = getColumn(context);
        List<ItemStack> drops = new ArrayList<>();
        ServerLevel level = context.level();
        ServerPlayer player = context.player();
        ItemStack tool = context.tool();
        while (column.size() > minHeight) {
            BlockPos last = column.getLast();
            drops.addAll(AMUtil.destroyBlockAndGetDrops(level, last, level.getBlockState(last), player, tool));
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

    private List<BlockPos> getColumn(GrowthContext context) {
        ServerLevel level = context.level();
        BlockPos originalPos = context.pos();
        List<BlockPos> list = new ArrayList<>();
        list.add(originalPos);
        BlockPos pos = originalPos.below();
        while (isHeadOrBody(level.getBlockState(pos))) {
            list.addFirst(pos);
            pos = pos.below();
        }
        pos = originalPos.above();
        while (isHeadOrBody(level.getBlockState(pos))) {
            list.add(pos);
            pos = pos.above();
        }
        return list;
    }

    private boolean isHeadOrBody(BlockState state) {
        return state.is(head) || state.is(body);
    }
}
