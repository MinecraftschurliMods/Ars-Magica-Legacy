package at.minecraftschurli.mods.arsmagicalegacy.api.ritual;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;

/// Represents a ritual effect that does something when the ritual is successfully performed.
public interface RitualEffect {
    Codec<RitualEffect> CODEC = Codec.lazyInitialized(() -> AMRegistries.RITUAL_EFFECTS.byNameCodec().dispatch(RitualEffect::codec, Function.identity()));

    /// @return The registered [MapCodec].
    MapCodec<? extends RitualEffect> codec();

    /// Performs the effect.
    ///
    /// @param player The [Player] that triggered the ritual.
    /// @param level  The [Level] the ritual was triggered in.
    /// @param vec    The [Vec3] the ritual was triggered at.
    void perform(@Nullable Player player, Level level, Vec3 vec);
}
