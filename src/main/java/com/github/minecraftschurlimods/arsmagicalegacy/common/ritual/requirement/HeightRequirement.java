package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record HeightRequirement(MinMaxBounds.Ints range) implements RitualRequirement {
    public static final Codec<HeightRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.INT_MIN_MAX_BOUNDS.fieldOf("height").forGetter(HeightRequirement::range)).apply(inst, HeightRequirement::new));

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(final Player player, final ServerLevel serverLevel, final BlockPos pos) {
        return range.matches(pos.getY());
    }
}
