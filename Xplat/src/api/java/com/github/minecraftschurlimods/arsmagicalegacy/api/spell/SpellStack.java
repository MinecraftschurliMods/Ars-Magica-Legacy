package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record SpellStack(List<ISpellPart> parts, List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers) {
    public static final Codec<SpellStack> CODEC = ArsMagicaAPI.get().getSpellPartRegistry().byNameCodec().listOf().xmap(SpellStack::of, SpellStack::parts);
    public static final SpellStack EMPTY = of(List.of());

    /**
     * Creates a spell stack from a list of parts.
     *
     * @param parts The list of parts to create the spell stack from.
     * @return The newly created spell stack.
     * @throws ShapeGroup.MalformedShapeGroupException If a modifier is the first element of the parts list.
     */
    public static SpellStack of(ISpellPart... parts) throws MalformedSpellStackException {
        return of(List.of(parts));
    }

    /**
     * Creates a spell stack from a list of parts.
     *
     * @param parts The list of parts to create the spell stack from.
     * @return The newly created spell stack.
     * @throws ShapeGroup.MalformedShapeGroupException If a modifier is the first element of the parts list.
     */
    public static SpellStack of(List<ISpellPart> parts) throws MalformedSpellStackException {
        List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers = new ArrayList<>();
        List<ISpellModifier> globalMods = new ArrayList<>();
        List<ISpellModifier> currentMods = null;
        partsWithModifiers.add(Pair.of(null, globalMods));
        boolean locked = false;
        for (ISpellPart part : parts) {
            if (part instanceof ISpellComponent component) {
                currentMods = new ArrayList<>();
                partsWithModifiers.add(Pair.of(component, Collections.unmodifiableList(currentMods)));
            }
            if (part instanceof ISpellShape shape) {
                if (currentMods != null)
                    throw new MalformedSpellStackException("A shape cannot come after a component!", parts);
                if (locked)
                    throw new MalformedSpellStackException("A shape cannot come after an end shape!", parts);
                currentMods = new ArrayList<>();
                partsWithModifiers.add(Pair.of(shape, Collections.unmodifiableList(currentMods)));
                if (shape.isEndShape()) {
                    locked = true;
                }
            }
            if (part instanceof ISpellModifier modifier) {
                Objects.requireNonNullElse(currentMods, globalMods).add(modifier);
            }
        }
        return new SpellStack(parts, partsWithModifiers);
    }

    /**
     * @return An unmodifiable list of all spell parts in this spell stack.
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(parts);
    }

    /**
     * @return An unmodifiable list of all spell parts with their associated modifiers in this spell stack.
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        return Collections.unmodifiableList(partsWithModifiers);
    }

    /**
     * @return Whether this spell stack is empty or not.
     */
    public boolean isEmpty() {
        return parts().isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpellStack that = (SpellStack) o;
        return (isEmpty() && that.isEmpty()) || parts().equals(that.parts());
    }

    @Override
    public int hashCode() {
        return parts().hashCode();
    }

    /**
     * Exception thrown when the spell stack is malformed.
     */
    public static class MalformedSpellStackException extends RuntimeException {
        private final List<ISpellPart> parts;

        public MalformedSpellStackException(String message, List<ISpellPart> parts) {
            super(message);
            this.parts = parts;
        }

        /**
         * @return A list of spell parts that caused the exception.
         */
        public List<ISpellPart> getParts() {
            return parts;
        }
    }
}
