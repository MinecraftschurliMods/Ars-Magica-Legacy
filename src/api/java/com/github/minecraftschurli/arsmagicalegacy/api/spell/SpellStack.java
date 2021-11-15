package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record SpellStack(List<ISpellPart> parts, List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers) {
    public static final Codec<SpellStack> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellPartRegistry).listOf().xmap(SpellStack::of, SpellStack::parts);
    public static final SpellStack EMPTY = of(List.of());

    /**
     * Create a spell stack from alist of parts.
     *
     * @param parts the list of parts to construct the spell stack with
     * @return the created spell stack
     * @throws MalformedSpellStackException when a modifier is the first element of the parts list
     */
    public static SpellStack of(ISpellPart... parts) throws MalformedSpellStackException {
        return of(List.of(parts));
    }

    /**
     * Create a spell stack from alist of parts.
     *
     * @param parts the list of parts to construct the spell stack with
     * @return the created spell stack
     * @throws MalformedSpellStackException when a modifier is the first element of the parts list
     */
    public static SpellStack of(List<ISpellPart> parts) throws MalformedSpellStackException {
        List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers = new ArrayList<>();
        List<ISpellModifier> globalMods = new ArrayList<>();
        List<ISpellModifier> currentMods = null;
        partsWithModifiers.add(Pair.of(null, globalMods));
        for (ISpellPart part : parts) {
            if (part instanceof ISpellComponent component) {
                currentMods = new ArrayList<>();
                partsWithModifiers.add(Pair.of(component, Collections.unmodifiableList(currentMods)));
            }
            if (part instanceof ISpellShape shape) {
                if (currentMods != null)
                    throw new MalformedSpellStackException("A shape can not come after the first component!", parts);
                currentMods = new ArrayList<>();
                partsWithModifiers.add(Pair.of(shape, Collections.unmodifiableList(currentMods)));
            }
            if (part instanceof ISpellModifier modifier) {
                Objects.requireNonNullElse(currentMods, globalMods).add(modifier);
            }
        }
        return new SpellStack(parts, partsWithModifiers);
    }

    /**
     * Get the unmodifiable list of parts in this spell stack.
     *
     * @return the unmodifiable list of parts in this spell stack
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(parts);
    }

    /**
     * Get the unmodifiable list of parts with modifiers in this spell stack.
     *
     * @return the unmodifiable list of parts with modifiers in this spell stack
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        return Collections.unmodifiableList(partsWithModifiers);
    }

    /**
     * Check if this shape group is empty.
     *
     * @return true, if this shape group is empty
     */
    public boolean isEmpty() {
        return parts().isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SpellStack that = (SpellStack) o;
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
         * Get the list of parts that caused the exception.
         *
         * @return the list of parts that caused the exception
         */
        public List<ISpellPart> getParts() {
            return parts;
        }
    }
}
