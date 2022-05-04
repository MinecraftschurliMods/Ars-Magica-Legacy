package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualRequirement;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/**
 *
 */
public record MoonPhaseRequirement(MinMaxBounds.Ints phase) implements RitualRequirement {
    public static final Codec<MoonPhaseRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(CodecHelper.INT_MIN_MAX_BOUNDS.fieldOf("phase").forGetter(MoonPhaseRequirement::phase)).apply(inst, MoonPhaseRequirement::new));

    public MoonPhaseRequirement(int phase) {
        this(MinMaxBounds.Ints.exactly(phase));
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(final Player player, final ServerLevel serverLevel, final BlockPos pos) {
        return phase.matches(serverLevel.getMoonPhase());
    }
}
