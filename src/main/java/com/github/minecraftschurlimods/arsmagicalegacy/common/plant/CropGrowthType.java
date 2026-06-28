package com.github.minecraftschurlimods.arsmagicalegacy.common.plant;

import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.BonemealableGrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthContext;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.GrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.HarvestState;
import com.github.minecraftschurlimods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record CropGrowthType(List<HarvestState> harvestStates) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<CropGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        HarvestState.CODEC.listOf().fieldOf("harvest_states").forGetter(CropGrowthType::harvestStates)
    ).apply(inst, CropGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        BlockState state = context.state();
        return harvestStates.stream().map(HarvestState::from).anyMatch(e -> e == state);
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        return AMUtil.destroyBlockAndGetDrops(context.level(), context.pos(), context.state(), context.player(), context.tool());
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return context.plant().seed().isPresent();
    }

    @Override
    public void replant(GrowthContext context) {
        BlockState state = context.state();
        harvestStates.stream()
            .filter(e -> e.from() == state)
            .findFirst()
            .ifPresent(e -> context.level().setBlockAndUpdate(context.pos(), e.to()));
    }
}
