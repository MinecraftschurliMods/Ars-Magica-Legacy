package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/// Represents a spell's grammar. All fields are immutable by contract.
///
/// @param parts      A [List] of all parts. Used mainly for serialization, use [SpellGrammar#components] for gameplay.
/// @param components A view of [SpellGrammar#parts] that lists the parts as [SpellComponent]s with their associated [SpellModifier]s.
public record SpellGrammar(List<SpellPart> parts, List<Pair<SpellComponent, List<SpellModifier>>> components) {
    public static final int MAX_PARTS = 8;
    public static final SpellGrammar EMPTY = new SpellGrammar(List.of(), List.of());
    public static final Codec<SpellGrammar> CODEC = AMRegistries.SPELL_PARTS.byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").xmap(SpellGrammar::of, SpellGrammar::parts).codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellGrammar> STREAM_CODEC = ByteBufCodecs.registry(AMRegistries.Keys.SPELL_PART).apply(ByteBufCodecs.list()).map(SpellGrammar::of, SpellGrammar::parts);

    /// @deprecated Use [SpellGrammar#of(List)] instead.
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public SpellGrammar {
    }

    /// Validates the given [List] of [SpellPart]s and constructs a [SpellGrammar] from it.
    ///
    /// @param parts The [List] of [SpellPart]s.
    /// @return A new [SpellGrammar], or [SpellGrammar#EMPTY] if validation failed.
    public static SpellGrammar of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isComponent()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        List<Pair<SpellComponent, List<SpellModifier>>> components = new ArrayList<>();
        SpellComponent currentComponent = null;
        List<SpellModifier> currentModifiers = new ArrayList<>();
        for (SpellPart part : parts) {
            if (part.isModifier()) {
                currentModifiers.add((SpellModifier) part);
            } else if (part.isComponent() && components.stream().noneMatch(pair -> pair.getFirst() == part)) {
                if (currentComponent != null) {
                    components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
                    currentModifiers = new ArrayList<>();
                }
                currentComponent = (SpellComponent) part;
            }
        }
        components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
        return new SpellGrammar(parts, components);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || getClass() == o.getClass() && parts.equals(((SpellGrammar) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    /// @return Whether the spell grammar is considered empty.
    public boolean isEmpty() {
        return parts.isEmpty();
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The combined mana cost of the spell grammar.
    public double getManaCost(RegistryAccess registryAccess) {
        return components.stream()
            .mapToDouble(pair -> pair.getFirst().getData(registryAccess).mana() * pair.getSecond()
                .stream()
                .mapToDouble(e -> e.getData(registryAccess).mana())
                .reduce(1, (a, b) -> a * b))
            .sum();
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The combined burnout cost of the spell grammar.
    public double getBurnoutCost(RegistryAccess registryAccess) {
        return components.stream()
            .mapToDouble(pair -> pair.getFirst().getData(registryAccess).burnoutOrGenerated())
            .sum();
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return A [Map] of combined [Affinity] shifts of the spell grammar.
    public Map<Holder<Affinity>, Double> affinityShifts(RegistryAccess registryAccess) {
        return components.stream()
            .map(Pair::getFirst)
            .map(part -> part.getData(registryAccess))
            .map(SpellPartData::affinityShifts)
            .map(Map::entrySet)
            .flatMap(Set::stream)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The key of the primary [Affinity] of the spell grammar.
    public ResourceKey<Affinity> primaryAffinity(RegistryAccess registryAccess) {
        return affinityShifts(registryAccess).entrySet()
            .stream()
            .max(Comparator.comparingDouble(Map.Entry::getValue))
            .map(Map.Entry::getKey)
            .map(Holder::getKey)
            .orElse(Affinity.NONE);
    }
}
