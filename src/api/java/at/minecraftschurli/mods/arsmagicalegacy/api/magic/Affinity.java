package at.minecraftschurli.mods.arsmagicalegacy.api.magic;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Util;
import net.neoforged.neoforge.common.extensions.IHolderExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/// Represents an affinity.
///
/// @param directOpposite The direct opposite affinity. When shifting into an affinity, one will also shift away by a large amount from the direct opposite.
/// @param majorOpposites The major opposite affinities. When shifting into an affinity, one will also shift away by a moderate amount from the major opposites.
/// @param minorOpposites The minor opposite affinities. When shifting into an affinity, one will also shift away by a small amount from the minor opposites.
/// @param adjacents      The adjacent affinities. When shifting into an affinity, one will also shift towards the adjacents by a small amount.
/// @param color          The color of the affinity.
/// @param index          The index of the affinity when displaying in the occulus. The built-in affinities use int values 1-10, use floating point values to insert your affinities between them. Use values < 0 to not display the affinity in the occulus.
/// @param castSound      The [SoundEvent] to use for casting [Spell]s with the affinity.
/// @param loopSound      The [SoundEvent] to use for casting continuous [Spell]s with the affinity.
/// @param particle       The [ParticleOptions] to associate with the affinity.
public record Affinity(Holder<Affinity> directOpposite, List<Holder<Affinity>> majorOpposites, List<Holder<Affinity>> minorOpposites, List<Holder<Affinity>> adjacents, int color, double index, Optional<Holder<SoundEvent>> castSound, Optional<Holder<SoundEvent>> loopSound, ParticleOptions particle) {
    public static final Codec<Affinity> DIRECT_CODEC = Util.make(() -> {
        Codec<Holder<Affinity>> codec = Codec.lazyInitialized(() -> Affinity.CODEC);
        return RecordCodecBuilder.create(inst -> inst.group(
            codec.fieldOf("direct_opposite").forGetter(Affinity::directOpposite),
            codec.listOf().fieldOf("major_opposites").forGetter(Affinity::majorOpposites),
            codec.listOf().fieldOf("minor_opposites").forGetter(Affinity::minorOpposites),
            codec.listOf().fieldOf("adjacents").forGetter(Affinity::adjacents),
            Codec.INT.fieldOf("color").forGetter(Affinity::color),
            Codec.DOUBLE.fieldOf("index").forGetter(Affinity::index),
            BuiltInRegistries.SOUND_EVENT.holderByNameCodec().optionalFieldOf("cast_sound").forGetter(Affinity::castSound),
            BuiltInRegistries.SOUND_EVENT.holderByNameCodec().optionalFieldOf("loop_sound").forGetter(Affinity::loopSound),
            ParticleTypes.CODEC.fieldOf("particle").forGetter(Affinity::particle)
        ).apply(inst, Affinity::new));
    });
    public static final Codec<Holder<Affinity>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.AFFINITY, DIRECT_CODEC);
    public static final ResourceKey<Affinity> NONE = ResourceKey.create(AMRegistries.Keys.AFFINITY, ArsMagicaApi.id("none"));

    /// @param directOpposite The direct opposite affinity.
    /// @param majorOpposites The major opposite affinities.
    /// @param minorOpposites The minor opposite affinities.
    /// @param adjacents      The adjacent affinities.
    /// @param color          The color of the affinity.
    /// @param index          The index of the affinity when displaying in the occulus.
    /// @param castSound      The [SoundEvent] to use for casting spells with the affinity.
    /// @param loopSound      The [SoundEvent] to use for casting continuous spells with the affinity.
    /// @param particle       The [ParticleOptions] to associate with the affinity.
    public Affinity(Holder<Affinity> directOpposite, List<Holder<Affinity>> majorOpposites, List<Holder<Affinity>> minorOpposites, List<Holder<Affinity>> adjacents, int color, double index, Holder<SoundEvent> castSound, Holder<SoundEvent> loopSound, ParticleOptions particle) {
        this(directOpposite, majorOpposites, minorOpposites, adjacents, color, index, Optional.of(castSound), Optional.of(loopSound), particle);
    }

    @Override
    public String toString() {
        return "Affinity{" +
            "directOpposite=" + Objects.requireNonNull(directOpposite.getKey()).identifier() +
            ", majorOpposites=[" + majorOpposites.stream().map(IHolderExtension::getKey).filter(Objects::nonNull).map(ResourceKey::identifier).map(Identifier::toString).collect(Collectors.joining(",")) +
            "], minorOpposites=[" + minorOpposites.stream().map(IHolderExtension::getKey).filter(Objects::nonNull).map(ResourceKey::identifier).map(Identifier::toString).collect(Collectors.joining(",")) +
            "], adjacents=[" + adjacents.stream().map(IHolderExtension::getKey).filter(Objects::nonNull).map(ResourceKey::identifier).map(Identifier::toString).collect(Collectors.joining(",")) +
            "], color=" + color +
            ", index=" + index +
            ", castSound=" + castSound +
            ", loopSound=" + loopSound +
            ", particle=" + particle +
            '}';
    }

    /// @param holder The affinity [Holder] to query.
    /// @return The display name of the given affinity.
    @SuppressWarnings("DataFlowIssue")
    public static MutableComponent getName(Holder<Affinity> holder) {
        return Component.translatable(Util.makeDescriptionId("affinity", holder.getKey().identifier()));
    }
}
