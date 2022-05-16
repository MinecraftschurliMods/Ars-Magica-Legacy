package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

/**
 * Utility class for default spell part stat modifiers.
 */
public final class DefaultSpellPartStatModifier {
    public static final ISpellPartStatModifier NOOP = (base, modified, spell, caster, target) -> modified;
    public static final ISpellPartStatModifier COUNTING = (base, modified, spell, caster, target) -> modified + 1;

    /**
     * @param value The value to add.
     * @return A new spell part stat modifier that adds the given value to the modified value.
     */
    public static ISpellPartStatModifier add(float value) {
        return (base, modified, spell, caster, target) -> modified + value;
    }

    /**
     * @param value The value to subtract.
     * @return A new spell part stat modifier that subtracts the given value from the modified value.
     */
    public static ISpellPartStatModifier subtract(float value) {
        return (base, modified, spell, caster, target) -> modified - value;
    }

    /**
     * @param value The value to multiply with.
     * @return A new spell part stat modifier that multiplies the given value with the modified value.
     */
    public static ISpellPartStatModifier multiply(float value) {
        return (base, modified, spell, caster, target) -> modified * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A new spell part stat modifier that adds the base value, multiplied with the given value, to the modified value.
     */
    public static ISpellPartStatModifier addMultipliedBase(float value) {
        return (base, modified, spell, caster, target) -> modified + base * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A new spell part stat modifier that subtracts the base value, multiplied with the given value, from the modified value.
     */
    public static ISpellPartStatModifier subtractMultipliedBase(float value) {
        return (base, modified, spell, caster, target) -> modified - base * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A new spell part stat modifier that adds the modified value, multiplied with the given value, to the modified value.
     */
    public static ISpellPartStatModifier addMultiplied(float value) {
        return (base, modified, spell, caster, target) -> modified + modified * value;
    }

    /**
     * @param value The value to multiply with.
     * @return A new spell part stat modifier that subtracts the modified value, multiplied with the given value, from the modified value.
     */
    public static ISpellPartStatModifier subtractMultiplied(float value) {
        return (base, modified, spell, caster, target) -> modified - modified * value;
    }
}
