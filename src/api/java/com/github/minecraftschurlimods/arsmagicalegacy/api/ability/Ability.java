package com.github.minecraftschurlimods.arsmagicalegacy.api.ability;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.MagicAttachment;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Predicate;

/// Represents an affinity ability.
///
/// @param affinity The [Affinity] to associate the ability with.
/// @param bounds   The [MinMaxBounds.Doubles] within which the ability becomes active. Should overlap with the range [0, 1].
/// @param negative Whether the ability should be considered negative or not.
/// @param effects  A list of [AbilityEffect]s that this ability applies.
public record Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, List<AbilityEffect> effects) implements Predicate<Player> {
    public static final Codec<Ability> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Affinity.CODEC.fieldOf("affinity").forGetter(Ability::affinity),
        MinMaxBounds.Doubles.CODEC.fieldOf("bounds").forGetter(Ability::bounds),
        Codec.BOOL.optionalFieldOf("negative", false).forGetter(Ability::negative),
        AbilityEffect.CODEC.listOf().optionalFieldOf("effects", List.of()).forGetter(Ability::effects)
    ).apply(inst, Ability::new));
    public static final Codec<Holder<Ability>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.ABILITY, Ability.DIRECT_CODEC);

    /// @param affinity The [Affinity] to associate the ability with.
    /// @param bounds   The [MinMaxBounds.Doubles] within which the ability becomes active. Should overlap with the range [0, 1].
    /// @param effects  A list of [AbilityEffect]s that this ability applies.
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, List<AbilityEffect> effects) {
        this(affinity, bounds, false, effects);
    }

    /// @param affinity The [Affinity] to associate the ability with.
    /// @param bounds   The [MinMaxBounds.Doubles] within which the ability becomes active. Should overlap with the range [0, 1].
    /// @param negative Whether the ability should be considered negative or not.
    /// @param effect   The [AbilityEffect] that this ability applies.
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, boolean negative, AbilityEffect effect) {
        this(affinity, bounds, negative, List.of(effect));
    }

    /// @param affinity The [Affinity] to associate the ability with.
    /// @param bounds   The [MinMaxBounds.Doubles] within which the ability becomes active. Should overlap with the range [0, 1].
    /// @param effect   The [AbilityEffect] that this ability applies.
    public Ability(Holder<Affinity> affinity, MinMaxBounds.Doubles bounds, AbilityEffect effect) {
        this(affinity, bounds, false, List.of(effect));
    }

    /// @param player The [Player] to query.
    /// @return Whether the given [Player] matches the bounds.
    @Override
    public boolean test(Player player) {
        return bounds.matches(ArsMagicaApi.magicHelper().getAffinityDepth(player, affinity));
    }

    /// @param data The [MagicAttachment] to query.
    /// @return Whether the given [MagicAttachment] matches the bounds.
    public boolean test(MagicAttachment data) {
        return bounds.matches(data.affinityShifts().getOrDefault(affinity, 0.));
    }

    /// @param holder The ability [Holder] to query.
    /// @return The display name of the given ability.
    @SuppressWarnings("DataFlowIssue")
    public static MutableComponent getName(Holder<Ability> holder) {
        return Component.translatable(Util.makeDescriptionId("ability", holder.getKey().identifier()));
    }
}
