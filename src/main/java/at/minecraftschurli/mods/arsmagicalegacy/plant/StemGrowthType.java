package at.minecraftschurli.mods.arsmagicalegacy.plant;

import at.minecraftschurli.mods.arsmagicalegacy.api.plant.BonemealableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.ReplantableGrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmlandBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;
import java.util.Optional;

public record StemGrowthType(RuleTest stem, Block attachedStem, BlockState fruit, String ageProperty, int maxAge) implements BonemealableGrowthType, ReplantableGrowthType {
    public static final MapCodec<StemGrowthType> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        RuleTest.CODEC.fieldOf("stem").forGetter(StemGrowthType::stem),
        BuiltInRegistries.BLOCK.byNameCodec().fieldOf("attached_stem").forGetter(StemGrowthType::attachedStem),
        BlockState.CODEC.fieldOf("fruit").forGetter(StemGrowthType::fruit),
        Codec.STRING.fieldOf("age_property").forGetter(StemGrowthType::ageProperty),
        Codec.INT.fieldOf("max_age").forGetter(StemGrowthType::maxAge)
    ).apply(inst, StemGrowthType::new));

    @Override
    public MapCodec<? extends GrowthType> codec() {
        return CODEC;
    }

    @Override
    public boolean canGrow(GrowthContext context) {
        return AMUtil.doRuleTest(stem, context.state());
    }

    @Override
    public void grow(GrowthContext context) {
        BlockState state = context.state();
        Optional<Property<?>> optional = state.getProperties()
            .stream()
            .filter(e -> e.getName().equals(ageProperty))
            .findFirst();
        if (optional.isEmpty()) return;
        if (AMUtil.getPropertyValueName(optional.get(), state).equals(String.valueOf(maxAge))) {
            ServerLevel level = context.level();
            BlockPos pos = context.pos();
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(level.getRandom());
            Direction initialDirection = direction;
            do {
                BlockPos fruitPos = pos.relative(direction);
                BlockState soil = level.getBlockState(fruitPos.below());
                if (level.isEmptyBlock(fruitPos) && (soil.getBlock() instanceof FarmlandBlock || soil.is(BlockTags.DIRT))) {
                    level.setBlockAndUpdate(fruitPos, fruit);
                    level.setBlockAndUpdate(pos, attachedStem.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction));
                    break;
                }
                direction = direction.getClockWise();
            } while (direction != initialDirection);
        } else {
            BonemealableGrowthType.super.grow(context);
        }
    }

    @Override
    public boolean canHarvest(GrowthContext context) {
        BlockState state = context.state();
        if (state == fruit) return true;
        if (state.is(attachedStem)) return false;
        if (!state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) return false;
        return context.level().getBlockState(context.pos().offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getUnitVec3i())) == fruit;
    }

    @Override
    public List<ItemStack> harvest(GrowthContext context) {
        BlockState state = context.state();
        BlockPos pos = context.pos();
        if (state.is(attachedStem) && state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            pos = pos.offset(state.getValue(BlockStateProperties.HORIZONTAL_FACING).getUnitVec3i());
        }
        return AMUtil.destroyBlockAndGetDrops(context.level(), pos, state, context.player(), context.tool());
    }

    @Override
    public boolean canReplant(GrowthContext context) {
        return false;
    }

    @Override
    public void replant(GrowthContext context) {
    }
}
