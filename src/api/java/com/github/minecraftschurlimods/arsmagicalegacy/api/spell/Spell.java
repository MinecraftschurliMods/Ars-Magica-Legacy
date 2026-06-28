package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

/// Represents a spell.
///
/// @param name             The name of the spell.
/// @param icon             The icon of the spell.
/// @param shapeGroups      The [SpellShapeGroup]s of the spell. Immutable by contract.
/// @param activeShapeGroup The index of the currently active [SpellShapeGroup]. Immutable by contract.
/// @param grammar          The [SpellGrammar] of the spell. Immutable by contract.
/// @param dataComponents   The data components of the spell. To modify, call [Spell#updateDataComponents(UnaryOperator)].
public record Spell(Optional<Component> name, Optional<Identifier> icon, List<SpellShapeGroup> shapeGroups, int activeShapeGroup, SpellGrammar grammar, SpellDataComponentMap dataComponents) {
    public static final int MAX_SHAPE_GROUPS = 5;
    public static final Spell EMPTY = new Spell(Optional.empty(), Optional.empty(), List.of(SpellShapeGroup.EMPTY), 0, SpellGrammar.EMPTY, SpellDataComponentMap.EMPTY);
    public static final Codec<Spell> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(Spell::name),
        Identifier.CODEC.optionalFieldOf("icon").forGetter(Spell::icon),
        SpellShapeGroup.CODEC.listOf(0, MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(Spell::shapeGroups),
        ExtraCodecs.intRange(0, MAX_SHAPE_GROUPS - 1).fieldOf("active_shape_group").forGetter(Spell::activeShapeGroup),
        SpellGrammar.CODEC.fieldOf("grammar").forGetter(Spell::grammar),
        SpellDataComponentMap.CODEC.fieldOf("components").forGetter(Spell::dataComponents)
    ).apply(inst, Spell::new));
    public static final Codec<Holder<Spell>> PREFAB_CODEC = RegistryFileCodec.create(AMRegistries.Keys.SPELL_PREFAB, CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Spell> STREAM_CODEC = StreamCodec.composite(
        ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), Spell::name,
        Identifier.STREAM_CODEC.apply(ByteBufCodecs::optional), Spell::icon,
        SpellShapeGroup.STREAM_CODEC.apply(ByteBufCodecs.list()), Spell::shapeGroups,
        ByteBufCodecs.INT, Spell::activeShapeGroup,
        SpellGrammar.STREAM_CODEC, Spell::grammar,
        SpellDataComponentMap.STREAM_CODEC, Spell::dataComponents,
        Spell::new);

    /// @param name The new name to set.
    /// @return A new spell with the new name set.
    public Spell setName(Component name) {
        return new Spell(Optional.of(name), icon, shapeGroups, activeShapeGroup, grammar, dataComponents);
    }

    /// @return A new spell with no name set.
    public Spell clearName() {
        return new Spell(Optional.empty(), icon, shapeGroups, activeShapeGroup, grammar, dataComponents);
    }

    /// @param icon The new icon to set.
    /// @return A new spell with the new icon set.
    public Spell setIcon(Identifier icon) {
        return new Spell(name, Optional.of(icon), shapeGroups, activeShapeGroup, grammar, dataComponents);
    }

    /// @return A new spell with no icon set.
    public Spell clearIcon() {
        return new Spell(name, Optional.empty(), shapeGroups, activeShapeGroup, grammar, dataComponents);
    }

    /// @return The spell, with the next shape group set as active.
    public Spell nextShapeGroup() {
        Spell spell = this;
        do {
            spell = new Spell(name, icon, shapeGroups, spell.activeShapeGroup < shapeGroups.size() - 1 ? spell.activeShapeGroup + 1 : 0, grammar, dataComponents);
        } while (spell.currentShapeGroup().isEmpty());
        return spell;
    }

    /// @return The spell, with the previous shape group set as active.
    public Spell prevShapeGroup() {
        Spell spell = this;
        do {
            spell = new Spell(name, icon, shapeGroups, spell.activeShapeGroup > 0 ? spell.activeShapeGroup - 1 : shapeGroups.size() - 1, grammar, dataComponents);
        } while (spell.currentShapeGroup().isEmpty());
        return spell;
    }

    /// @param activeShapeGroup The active shape group index to set.
    /// @return The spell, with the given shape group index set as active.
    public Spell setActiveShapeGroup(int activeShapeGroup) {
        return new Spell(name, icon, shapeGroups, activeShapeGroup, grammar, dataComponents);
    }

    /// @param operator The modifications to apply to the data components.
    /// @return A new spell with the modifications to the data components applied.
    public Spell updateDataComponents(UnaryOperator<SpellDataComponentMap> operator) {
        return new Spell(name, icon, shapeGroups, activeShapeGroup, grammar, operator.apply(dataComponents));
    }

    /// @return The currently active [SpellShapeGroup].
    public SpellShapeGroup currentShapeGroup() {
        return shapeGroups.get(activeShapeGroup);
    }

    /// @return Whether the spell is considered empty.
    public boolean isEmpty() {
        return grammar.isEmpty() || shapeGroups.isEmpty() || shapeGroups.stream().allMatch(SpellShapeGroup::isEmpty);
    }

    /// @return Whether the spell is continuous, i.e., can be cast by holding down the spell.
    public boolean isContinuous() {
        return currentShapeGroup().isContinuous();
    }

    /// @return Whether the spell is malformed, i.e., does not fulfill basic requirements to the spell's structure.
    public boolean isMalformed() {
        return currentShapeGroup().primaryShape() == null || grammar.components().isEmpty();
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The combined mana cost of the spell.
    public double getManaCost(RegistryAccess registryAccess) {
        return currentShapeGroup().getManaCost(registryAccess) * grammar.getManaCost(registryAccess);
    }
}
