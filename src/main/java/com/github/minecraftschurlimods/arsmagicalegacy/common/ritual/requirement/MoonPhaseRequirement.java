package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record MoonPhaseRequirement(MinMaxBounds.Ints phase) implements RitualRequirement {
    public static final Codec<MoonPhaseRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.INT_MIN_MAX_BOUNDS.fieldOf("phase").forGetter(MoonPhaseRequirement::phase)).apply(inst, MoonPhaseRequirement::new));

    public static MoonPhaseRequirement any() {
        return new MoonPhaseRequirement(MinMaxBounds.Ints.ANY);
    }

    public static MoonPhaseRequirement exactly(int phase) {
        return new MoonPhaseRequirement(MinMaxBounds.Ints.exactly(phase));
    }

    public static MoonPhaseRequirement between(int min, int max) {
        return new MoonPhaseRequirement(MinMaxBounds.Ints.between(min, max));
    }

    public static MoonPhaseRequirement atLeast(int min) {
        return new MoonPhaseRequirement(MinMaxBounds.Ints.atLeast(min));
    }

    public static MoonPhaseRequirement atMost(int max) {
        return new MoonPhaseRequirement(MinMaxBounds.Ints.atMost(max));
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(Player player, ServerLevel serverLevel, BlockPos pos) {
        return phase.matches(serverLevel.getMoonPhase());
    }
}
