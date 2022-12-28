package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.effect;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record PlaceBlockRitualEffect(BlockState state, BlockPos offset) implements RitualEffect {
    public static final Codec<PlaceBlockRitualEffect> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(PlaceBlockRitualEffect::state),
            BlockPos.CODEC.optionalFieldOf("offset", BlockPos.ZERO).forGetter(PlaceBlockRitualEffect::offset)
    ).apply(inst, PlaceBlockRitualEffect::new));

    public PlaceBlockRitualEffect(BlockState state) {
        this(state, BlockPos.ZERO);
    }

    @Override
    public boolean performEffect(Player player, ServerLevel level, BlockPos pos) {
        return level.setBlock(pos.offset(offset), state, Block.UPDATE_ALL);
    }

    @Override
    public Codec<? extends RitualEffect> codec() {
        return CODEC;
    }
}
