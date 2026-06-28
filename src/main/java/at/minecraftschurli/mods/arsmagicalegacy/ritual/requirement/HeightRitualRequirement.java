package at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record HeightRitualRequirement(MinMaxBounds.Doubles bounds) implements RitualRequirement {
    public static final MapCodec<HeightRitualRequirement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        MinMaxBounds.Doubles.CODEC.fieldOf("bounds").forGetter(HeightRitualRequirement::bounds)
    ).apply(inst, HeightRitualRequirement::new));

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return bounds.matches(vec.y());
    }
}
