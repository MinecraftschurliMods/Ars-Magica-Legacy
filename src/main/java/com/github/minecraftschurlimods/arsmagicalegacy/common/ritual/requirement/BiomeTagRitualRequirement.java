package com.github.minecraftschurlimods.arsmagicalegacy.common.ritual.requirement;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record BiomeTagRitualRequirement(TagKey<Biome> tag) implements RitualRequirement {
    public static final MapCodec<BiomeTagRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        TagKey.codec(Registries.BIOME).fieldOf("tag").forGetter(BiomeTagRitualRequirement::tag)
    ).apply(inst, BiomeTagRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return level.getBiome(BlockPos.containing(vec)).is(tag);
    }
}
