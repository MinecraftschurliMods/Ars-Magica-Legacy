package at.minecraftschurli.mods.arsmagicalegacy.plant;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public record BushGrowthType(List<BlockState> harvestStates) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<BushGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BlockState.CODEC.listOf().fieldOf("harvest_states").forGetter(BushGrowthType::harvestStates)
    ).apply(inst, BushGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        BlockState state = context.state();
        return harvestStates.stream().anyMatch(e -> e == state);
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        BlockPos pos = context.pos();
        Block.beginCapturingDrops();
        context.state().useItemOn(context.tool(), context.level(), context.player(), InteractionHand.MAIN_HAND, new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, true));
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
