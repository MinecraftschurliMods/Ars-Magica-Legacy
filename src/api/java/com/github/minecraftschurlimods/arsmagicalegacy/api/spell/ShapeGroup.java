package com.github.minecraftschurlimods.arsmagicalegacy.api.spell;

import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurlimods.codeclib.CodecHelper;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A shape group is the part of a spell that can be exchanged to change the casting but not the effect of a spell.
 */
public record ShapeGroup(List<ISpellPart> parts, List<Pair<ISpellShape, List<ISpellModifier>>> shapesWithModifiers) {
    public static final Codec<ShapeGroup> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellPartRegistry).listOf().xmap(ShapeGroup::of, ShapeGroup::parts);
    public static final ShapeGroup EMPTY = of(List.of());

    /**
     * Create a shape group from alist of parts.
     *
     * @param parts the list of parts to construct the shape group with
     * @return the created shape group
     * @throws MalformedShapeGroupException when there are components present in the parts list or a modifier is the first element of the parts list
     */
    public static ShapeGroup of(ISpellPart... parts) throws MalformedShapeGroupException {
        return of(List.of(parts));
    }

    /**
     * Create a shape group from alist of parts.
     *
     * @param parts the list of parts to construct the shape group with
     * @return the created shape group
     * @throws MalformedShapeGroupException when there are components present in the parts list or a modifier is the first element of the parts list
     */
    public static ShapeGroup of(List<ISpellPart> parts) throws MalformedShapeGroupException {
        List<Pair<ISpellShape, List<ISpellModifier>>> map = new ArrayList<>();
        List<ISpellModifier> currentMods = null;
        boolean locked = false;
        boolean first = true;
        for (ISpellPart part : parts) {
            if (part instanceof ISpellComponent)
                throw new MalformedShapeGroupException("Shape groups can not contain components!", parts);
            if (part instanceof ISpellShape shape) {
                if (locked)
                    throw new MalformedShapeGroupException("A shape can not come after a terminus shape!", parts);
                if (first && shape.needsPrecedingShape())
                    throw new MalformedShapeGroupException("A non beginn shape can not be first!", parts);
                first = false;
                currentMods = new ArrayList<>();
                map.add(Pair.of(shape, Collections.unmodifiableList(currentMods)));
                if (shape.isEndShape()) {
                    locked = true;
                }
            }
            if (part instanceof ISpellModifier modifier) {
                if (currentMods == null)
                    throw new MalformedShapeGroupException("A Modifier can not come before a shape in a shape group!", parts);
                currentMods.add(modifier);
            }
        }
        return new ShapeGroup(parts, map);
    }

    /**
     * Get the unmodifiable list of parts in this shape group.
     *
     * @return the unmodifiable list of parts in this shape group
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(parts);
    }

    /**
     * Get the unmodifiable list of shapes with modifiers in this shape group.
     *
     * @return the unmodifiable list of shapes with modifiers in this shape group
     */
    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<Pair<ISpellShape, List<ISpellModifier>>> shapesWithModifiers() {
        return Collections.unmodifiableList(shapesWithModifiers);
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
        final ShapeGroup that = (ShapeGroup) o;
        return (isEmpty() && that.isEmpty()) || parts().equals(that.parts());
    }

    @Override
    public int hashCode() {
        return parts().hashCode();
    }

    /**
     * Exception thrown when the shape group is malformed.
     */
    public static class MalformedShapeGroupException extends RuntimeException {
        private final List<ISpellPart> parts;

        public MalformedShapeGroupException(String message, List<ISpellPart> parts) {
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
