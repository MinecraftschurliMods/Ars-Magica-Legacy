package at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record DimensionRitualRequirement(ResourceKey<Level> dimension) implements RitualRequirement {
    public static final MapCodec<DimensionRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(DimensionRitualRequirement::dimension)
    ).apply(inst, DimensionRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return level.dimension() == dimension;
    }
}
