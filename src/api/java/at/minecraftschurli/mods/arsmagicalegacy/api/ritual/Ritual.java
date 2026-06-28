package at.minecraftschurli.mods.arsmagicalegacy.api.ritual;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.List;

/// Represents a ritual. A ritual can be triggered, in which case it performs the ritual effect.
///
/// @param requirements The passive requirements of the ritual.
/// @param trigger      The active trigger of the ritual. May itself contain requirements.
/// @param effects      The effects to perform when the ritual is successfully triggered.
/// @param <T>          The trigger context type.
public record Ritual<T>(List<RitualRequirement> requirements, RitualTrigger<T> trigger, List<RitualEffect> effects) {
    public static final Codec<Ritual<?>> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        RitualRequirement.CODEC.listOf().fieldOf("requirements").forGetter(Ritual::requirements),
        RitualTrigger.CODEC.fieldOf("trigger").forGetter(Ritual::trigger),
        RitualEffect.CODEC.listOf().fieldOf("effects").forGetter(Ritual::effects)
    ).apply(inst, Ritual::new));
    public static final Codec<Holder<Ritual<?>>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.RITUAL, DIRECT_CODEC);

    /// Performs the ritual.
    ///
    /// @param player  The [Player] triggering the ritual.
    /// @param level   The [Level] the ritual is triggered in.
    /// @param vec     The [Vec3] the ritual is triggered at.
    /// @param context The trigger context to use.
    public void perform(@Nullable Player player, Level level, Vec3 vec, T context) {
        if (!trigger.test(player, level, vec, context)) return;
        Vec3 adjustedVec = trigger.adjustPosition(player, level, vec, context);
        if (requirements.stream().allMatch(e -> e.test(player, level, adjustedVec))) {
            trigger.consume(player, level, adjustedVec, context);
            requirements.forEach(e -> e.consume(player, level, adjustedVec));
            effects.forEach(e -> e.perform(player, level, adjustedVec));
        }
    }

    /// Performs all rituals of the given codec type.
    /// @param codec   The [MapCodec] to check.
    /// @param player  The [Player] triggering the rituals.
    /// @param level   The [Level] the rituals are triggered in.
    /// @param vec     The [Vec3] the rituals are triggered at.
    /// @param context The trigger context to use.
    /// @param <T>     The trigger context type.
    @SuppressWarnings("unchecked")
    public static <T> void perform(MapCodec<? extends T> codec, @Nullable Player player, Level level, Vec3 vec, T context) {
        AMRegistries.rituals(level.registryAccess())
            .listElements()
            .map(Holder::value)
            .filter(e -> e.trigger().codec() == codec)
            .map(e -> (Ritual<T>) e)
            .forEach(e -> e.perform(player, level, vec, context));
    }
}
