package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record SetBlockRitualEffect(BlockState state, BlockPos offset) implements RitualEffect {
    public static final MapCodec<SetBlockRitualEffect> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        BlockState.CODEC.fieldOf("state").forGetter(SetBlockRitualEffect::state),
        BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(SetBlockRitualEffect::offset)
    ).apply(inst, SetBlockRitualEffect::new));

    @Override
    public MapCodec<? extends RitualEffect> codec() {
        return CODEC;
    }

    @Override
    public void perform(@Nullable Player player, Level level, Vec3 vec) {
        level.setBlockAndUpdate(BlockPos.containing(vec).offset(offset), state);
    }
}
