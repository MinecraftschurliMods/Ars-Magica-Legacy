package at.minecraftschurli.mods.arsmagicalegacy.plant;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.TallHarvestState;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.ArrayList;
import java.util.List;

public record TallCropGrowthType(List<TallHarvestState> harvestStates, RuleTest lower, RuleTest upper) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<TallCropGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        TallHarvestState.CODEC.listOf().fieldOf("harvest_states").forGetter(TallCropGrowthType::harvestStates),
        RuleTest.CODEC.fieldOf("lower").forGetter(TallCropGrowthType::lower),
        RuleTest.CODEC.fieldOf("upper").forGetter(TallCropGrowthType::upper)
    ).apply(inst, TallCropGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        return BonemealableGrowthType.super.canGrow(lower(context)) || BonemealableGrowthType.super.canGrow(upper(context));
    }

    @Override
    public void grow(GrowthContext context) {
        if (BonemealableGrowthType.super.canGrow(lower(context))) {
            BonemealableGrowthType.super.grow(lower(context));
        } else if (BonemealableGrowthType.super.canGrow(upper(context))) {
            BonemealableGrowthType.super.grow(upper(context));
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        BlockState lower = lower(context).state();
        BlockState upper = upper(context).state();
        return harvestStates.stream().anyMatch(e -> e.lowerFrom() == lower && e.upperFrom() == upper);
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        ServerPlayer player = context.player();
        ServerLevel level = context.level();
        ItemStack tool = context.tool();
        GrowthContext lower = lower(context);
        GrowthContext upper = upper(context);
        List<ItemStack> result = new ArrayList<>();
        result.addAll(AMUtil.destroyBlockAndGetDrops(level, lower.pos(), lower.state(), player, tool));
        result.addAll(AMUtil.destroyBlockAndGetDrops(level, upper.pos(), upper.state(), player, tool));
        return result;
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return context.plant().seed().isPresent();
    }

    @Override
    public void replant(GrowthContext context) {
        BlockState state = context.state();
        BlockPos pos = context.pos();
        TallHarvestState harvestState = null;
        for (TallHarvestState e : harvestStates) {
            if (e.lowerFrom() == state) {
                harvestState = e;
                break;
            }
            if (e.upperFrom() == state) {
                harvestState = e;
                pos = pos.below();
                break;
            }
        }
        if (harvestState == null) return;
        ServerLevel level = context.level();
        level.setBlockAndUpdate(pos, harvestState.lowerTo());
        level.setBlockAndUpdate(pos.above(), harvestState.upperTo());
    }

    private GrowthContext lower(GrowthContext context) {
        if (AMUtil.doRuleTest(lower, context.state())) return context;
        ServerLevel level = context.level();
        BlockPos below = context.pos().below();
        return new GrowthContext(context.plant(), context.player(), level, below, level.getBlockState(below), context.tool());
    }

    private GrowthContext upper(GrowthContext context) {
        if (AMUtil.doRuleTest(upper, context.state())) return context;
        ServerLevel level = context.level();
        BlockPos above = context.pos().above();
        return new GrowthContext(context.plant(), context.player(), level, above, level.getBlockState(above), context.tool());
    }
}
