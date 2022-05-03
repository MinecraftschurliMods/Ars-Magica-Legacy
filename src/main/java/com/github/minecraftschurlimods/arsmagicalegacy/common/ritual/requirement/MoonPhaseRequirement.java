package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.Ritual;
import com.github.minecraftschurlimods.arsmagicalegacy.common.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

/**
 *
 */
public record MoonPhaseRequirement(MinMaxBounds.Ints phase) implements Ritual.RitualRequirement {
    public static final Codec<MoonPhaseRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(AMUtil.INT_MIN_MAX_BOUNDS.fieldOf("phase").forGetter(MoonPhaseRequirement::phase)).apply(inst, MoonPhaseRequirement::new));

    @Override
    public Codec<? extends Ritual.RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(final Player player, final ServerLevel serverLevel, final BlockPos pos) {
        return phase.matches(serverLevel.getMoonPhase());
    }
}
