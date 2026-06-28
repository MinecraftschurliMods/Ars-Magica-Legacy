package at.minecraftschurli.mods.arsmagicalegacy.ritual.trigger;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record SetBlockStateRitualTrigger(RuleTest test, BlockPos offset) implements RitualTrigger<BlockState> {
    public static final MapCodec<SetBlockStateRitualTrigger> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        RuleTest.CODEC.fieldOf("test").forGetter(SetBlockStateRitualTrigger::test),
        BlockPos.CODEC.fieldOf("offset").forGetter(SetBlockStateRitualTrigger::offset)
    ).apply(inst, SetBlockStateRitualTrigger::new));

    @Override
    public MapCodec<? extends RitualTrigger<BlockState>> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec, BlockState context) {
        return AMUtil.doRuleTest(test, level.getBlockState(BlockPos.containing(vec)));
    }

    @Override
    public Vec3 adjustPosition(@Nullable Player player, Level level, Vec3 vec, BlockState context) {
        return RitualTrigger.super.adjustPosition(player, level, vec, context).add(Vec3.atLowerCornerOf(offset.multiply(-1)));
    }
}
