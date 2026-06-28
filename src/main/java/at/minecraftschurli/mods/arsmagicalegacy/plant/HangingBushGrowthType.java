package at.minecraftschurli.mods.arsmagicalegacy.plant;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record HangingBushGrowthType(List<BlockState> harvestStates, int minHeight, int maxHeight, Block head, Block body) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<HangingBushGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BlockState.CODEC.listOf().fieldOf("harvest_states").forGetter(HangingBushGrowthType::harvestStates),
        ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height", 1).forGetter(HangingBushGrowthType::minHeight),
        ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("max_height", 0).forGetter(HangingBushGrowthType::maxHeight),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("head").forGetter(HangingBushGrowthType::head),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("body").forGetter(HangingBushGrowthType::body)
    ).apply(inst, HangingBushGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        Plant plant = context.plant();
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        ItemStack tool = context.tool();
        List<BlockPos> column = AMUtil.getHangingColumn(context, head, body);
        for (BlockPos pos : column) {
            if (BonemealableGrowthType.super.canGrow(new GrowthContext(plant, player, level, pos, level.getBlockState(pos), tool))) return true;
        }
        if (maxHeight > 0 && column.size() >= maxHeight) return false;
        BlockPos last = column.getLast();
        return level.getBlockState(last.below()).canBeReplaced() && level.getBlockState(last).is(head);
    }

    @Override
    public void grow(GrowthContext context) {
        Plant plant = context.plant();
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        ItemStack tool = context.tool();
        boolean bonemealed = false;
        List<BlockPos> column = AMUtil.getHangingColumn(context, head, body);
        for (BlockPos pos : column) {
            BlockState current = level.getBlockState(pos);
            if (BonemealableGrowthType.super.canGrow(new GrowthContext(plant, player, level, pos, current, tool)) && current.getBlock() instanceof BonemealableBlock block) {
                block.performBonemeal(level, level.getRandom(), pos, current);
                bonemealed = true;
            }
        }
        if (bonemealed) return;
        BlockPos last = AMUtil.getHangingColumn(context, head, body).getLast();
        level.setBlockAndUpdate(last, body.defaultBlockState());
        level.setBlockAndUpdate(last.below(), head.defaultBlockState());
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        ServerLevel level = context.level();
        return AMUtil.getHangingColumn(context, head, body).stream().anyMatch(pos -> {
            BlockState state = level.getBlockState(pos);
            return harvestStates.stream().anyMatch(e -> e == state);
        });
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        List<BlockPos> column = AMUtil.getHangingColumn(context, head, body);
        ServerLevel level = context.level();
        ServerPlayer player = context.player();
        ItemStack tool = context.tool();
        Block.beginCapturingDrops();
        for (BlockPos lastPos : column) {
            BlockState lastState = level.getBlockState(lastPos);
            BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(lastPos), Direction.UP, lastPos, true);
            lastState.useItemOn(tool, level, player, InteractionHand.MAIN_HAND, hitResult);
        }
        return Block.stopCapturingDrops()
            .stream()
            .map(ItemEntity::getItem)
            .toList();
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return false;
    }

    @Override
    public void replant(GrowthContext context) {
    }
}
