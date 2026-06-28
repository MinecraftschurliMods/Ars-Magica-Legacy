package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;

import java.util.Set;

/// Represents a spell part.
public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    /// @return Whether the spell part is a shape (primary or secondary).
    public final boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    /// @return Whether the spell part is a primary shape.
    public abstract boolean isPrimaryShape();

    /// @return Whether the spell part is a secondary shape.
    public abstract boolean isSecondaryShape();

    /// @return Whether the spell part is a component.
    public abstract boolean isComponent();

    /// @return Whether the spell part is a modifier.
    public abstract boolean isModifier();

    /// If the spell part is a modifier, returns the [SpellStat]s the modifier modifies. Otherwise, returns the [SpellStat]s the spell part uses.
    ///
    /// @return A [Set] of [SpellStat]s.
    public abstract Set<SpellStat> getStats();

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The spell part's datapack-defined data.
    public SpellPartData getData(RegistryAccess registryAccess) {
        return AMRegistries.spellPartData(registryAccess).getOptional(AMRegistries.SPELL_PARTS.getKey(this)).orElse(SpellPartData.DEFAULT);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The spell part's datapack-defined data.
    public SpellPartData getData(HolderLookup.Provider registries) {
        Identifier key = AMRegistries.SPELL_PARTS.getKey(this);
        return key == null ? SpellPartData.DEFAULT : registries.lookupOrThrow(AMRegistries.Keys.SPELL_PART_DATA)
            .get(ResourceKey.create(AMRegistries.Keys.SPELL_PART_DATA, key))
            .map(Holder::value)
            .orElse(SpellPartData.DEFAULT);
    }

    /// @return The [DataComponentType] the spell part uses for additional data storage, or null if it does not use a [DataComponentType].
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return null;
    }
}
