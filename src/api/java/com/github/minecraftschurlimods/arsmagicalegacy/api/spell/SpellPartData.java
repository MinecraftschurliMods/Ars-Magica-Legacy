package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaApi;
import com.github.minecraftschurlimods.arsmagicalegacy.api.constants.AMRegistries;
import com.github.minecraftschurlimods.arsmagicalegacy.api.magic.Affinity;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/// Represents the datapack-supplied properties of a [SpellPart]. All fields are immutable by contract.
///
/// @param mana           The mana cost of the [SpellPart].
/// @param burnout        The burnout cost of the [SpellPart]. If empty, will be calculated from the mana cost.
/// @param affinityShifts A [Map] of [Affinity]s to doubles, representing the affinity shifts when casting the [SpellPart].
/// @param recipe         A [List] of [SpellIngredient]s required to craft the [SpellPart].
public record SpellPartData(double mana, Optional<Double> burnout, Map<Holder<Affinity>, Double> affinityShifts, List<SpellIngredient> recipe) {
    public static final SpellPartData DEFAULT = new SpellPartData(0, Optional.empty(), Map.of(), List.of());
    public static final Codec<SpellPartData> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.DOUBLE.fieldOf("mana").forGetter(SpellPartData::mana),
        Codec.DOUBLE.optionalFieldOf("burnout").forGetter(SpellPartData::burnout),
        Codec.unboundedMap(Affinity.CODEC, Codec.DOUBLE).fieldOf("affinity_shifts").forGetter(SpellPartData::affinityShifts),
        SpellIngredient.CODEC.listOf().fieldOf("recipe").forGetter(SpellPartData::recipe)
    ).apply(inst, SpellPartData::new));
    public static final Codec<Holder<SpellPartData>> CODEC = RegistryFileCodec.create(AMRegistries.Keys.SPELL_PART_DATA, DIRECT_CODEC);

    /// @return The burnout value. Will use [SpellPartData#burnout] or, if that is empty, calculate the value from [SpellPartData#mana].
    public double burnoutOrGenerated() {
        return burnout.orElse(mana * ArsMagicaApi.spellHelper().getManaToBurnoutRatio());
    }
}
