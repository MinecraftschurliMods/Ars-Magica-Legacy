package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.RitualRequirement;
import com.github.minecraftschurlimods.arsmagicalegacy.compat.patchouli.PatchouliCompat;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

public record RitualStructureRequirement(ResourceLocation structure) implements RitualRequirement {
    public static final Codec<RitualStructureRequirement> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceLocation.CODEC.fieldOf("structure").forGetter(RitualStructureRequirement::structure)
    ).apply(inst, RitualStructureRequirement::new));

    @Override
    public boolean test(final Player player, final ServerLevel level, final BlockPos pos) {
        return PatchouliCompat.getMultiblockMatcher(structure).test(level, pos);
    }

    @Override
    public Codec<? extends RitualRequirement> codec() {
        return CODEC;
    }
}
