package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/// Represents a spell's shape group. One spell may have up to [Spell#MAX_SHAPE_GROUPS] different shape groups. All fields are immutable by contract.
///
/// @param parts              A [List] of all parts. Used mainly for serialization, use the other fields for gameplay.
/// @param primaryShape       The [PrimarySpellShape] of the shape group.
/// @param primaryModifiers   A [List] of [SpellModifier]s for the [PrimarySpellShape].
/// @param secondaryShape     The [SecondarySpellShape] of the shape group.
/// @param secondaryModifiers A [List] of [SpellModifier]s for the [SecondarySpellShape].
public record SpellShapeGroup(List<SpellPart> parts, @Nullable PrimarySpellShape primaryShape, List<SpellModifier> primaryModifiers, @Nullable SecondarySpellShape secondaryShape, List<SpellModifier> secondaryModifiers) {
    public static final int MAX_PARTS = 4;
    public static final SpellShapeGroup EMPTY = new SpellShapeGroup(List.of(), null, List.of(), null, List.of());
    public static final Codec<SpellShapeGroup> CODEC = AMRegistries.SPELL_PARTS.byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").xmap(SpellShapeGroup::of, SpellShapeGroup::parts).codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellShapeGroup> STREAM_CODEC = ByteBufCodecs.registry(AMRegistries.Keys.SPELL_PART).apply(ByteBufCodecs.list()).map(SpellShapeGroup::of, SpellShapeGroup::parts);

    /// @deprecated Use [SpellShapeGroup#of(List)] instead.
    @Deprecated
    public SpellShapeGroup {
    }

    /// Validates the given [List] of [SpellPart]s and constructs a [SpellShapeGroup] from it.
    ///
    /// @param parts The [List] of [SpellPart]s.
    /// @return A new [SpellShapeGroup], or [SpellShapeGroup#EMPTY] if validation failed.
    public static SpellShapeGroup of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isPrimaryShape()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        PrimarySpellShape primary = (PrimarySpellShape) parts.getFirst();
        List<SpellModifier> primaryModifiers = new ArrayList<>();
        SecondarySpellShape secondary = null;
        List<SpellModifier> secondaryModifiers = new ArrayList<>();
        for (int i = 1; i < parts.size(); i++) {
            SpellPart part = parts.get(i);
            if (part.isModifier()) {
                if (secondary == null) {
                    primaryModifiers.add((SpellModifier) part);
                } else {
                    secondaryModifiers.add((SpellModifier) part);
                }
            } else if (part.isSecondaryShape() && secondary == null) {
                secondary = (SecondarySpellShape) part;
            }
        }
        return new SpellShapeGroup(parts, primary, primaryModifiers, secondary, secondaryModifiers);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || getClass() == o.getClass() && parts.equals(((SpellShapeGroup) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    /// @return Whether the spell shape group is considered empty.
    public boolean isEmpty() {
        return parts.isEmpty();
    }

    /// @return Whether this spell shape group is continuous, i.e., can be cast by holding down the spell.
    public boolean isContinuous() {
        return primaryShape != null && primaryShape.isContinuous();
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The combined mana cost of the spell shape group.
    public double getManaCost(RegistryAccess registryAccess) {
        if (primaryShape == null) return 0;
        double cost = primaryShape.getData(registryAccess).mana() * primaryModifiers
            .stream()
            .mapToDouble(e -> e.getData(registryAccess).mana())
            .reduce(1, (a, b) -> a * b);
        return secondaryShape == null ? cost : cost + secondaryShape.getData(registryAccess).mana() * secondaryModifiers
            .stream()
            .mapToDouble(e -> e.getData(registryAccess).mana())
            .reduce(1, (a, b) -> a * b);
    }
}
