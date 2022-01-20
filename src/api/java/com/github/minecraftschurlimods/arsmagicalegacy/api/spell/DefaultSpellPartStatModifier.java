package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

/**
 * Utility class for default spell part stat modifiers.
 */
public final class DefaultSpellPartStatModifier {
    public static final ISpellPartStatModifier NOOP = (base, modified, spell, caster, target) -> modified;
    public static final ISpellPartStatModifier COUNTING = (base, modified, spell, caster, target) -> modified + 1;

    public static ISpellPartStatModifier add(final float value) {
        return (base, modified, spell, caster, target) -> modified + value;
    }

    public static ISpellPartStatModifier subtract(final float value) {
        return (base, modified, spell, caster, target) -> modified - value;
    }

    public static ISpellPartStatModifier multiply(final float by) {
        return (base, modified, spell, caster, target) -> modified * by;
    }

    public static ISpellPartStatModifier addMultipliedBase(final float by) {
        return (base, modified, spell, caster, target) -> modified + base * by;
    }

    public static ISpellPartStatModifier subtractMultipliedBase(final float by) {
        return (base, modified, spell, caster, target) -> modified - base * by;
    }

    public static ISpellPartStatModifier addMultiplied(final float by) {
        return (base, modified, spell, caster, target) -> modified + modified * by;
    }

    public static ISpellPartStatModifier subtractMultiplied(final float by) {
        return (base, modified, spell, caster, target) -> modified - modified * by;
    }
}
