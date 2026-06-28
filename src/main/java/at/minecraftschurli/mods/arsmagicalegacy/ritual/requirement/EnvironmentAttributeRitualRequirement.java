package at.minecraftschurli.mods.arsmagicalegacy.ritual.requirement;

import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public record EnvironmentAttributeRitualRequirement<T>(EnvironmentAttributePair<T> value) implements RitualRequirement {
    public static final MapCodec<EnvironmentAttributeRitualRequirement<?>> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        EnvironmentAttributePair.CODEC.fieldOf("environment_attribute").forGetter(EnvironmentAttributeRitualRequirement::value)
    ).apply(inst, EnvironmentAttributeRitualRequirement::new));

    public EnvironmentAttributeRitualRequirement(EnvironmentAttribute<T> type, T value) {
        this(new EnvironmentAttributePair<>(type, value));
    }

    @Override
    public MapCodec<? extends RitualRequirement> codec() {
        return CODEC;
    }

    @Override
    public boolean test(@Nullable Player player, Level level, Vec3 vec) {
        return Objects.equals(level.environmentAttributes().getValue(value.type, vec), value.value);
    }

    public record EnvironmentAttributePair<T>(EnvironmentAttribute<T> type, T value) {
        private static final Codec<EnvironmentAttributePair<?>> CODEC = EnvironmentAttributes.CODEC.fieldOf("type").dispatch(EnvironmentAttributePair::type, EnvironmentAttributePair::valueCodec);

        private static <T> MapCodec<EnvironmentAttributePair<T>> valueCodec(EnvironmentAttribute<T> type) {
            return type.valueCodec().fieldOf("value").xmap(v -> new EnvironmentAttributePair<>(type, v), EnvironmentAttributePair::value);
        }
    }
}
