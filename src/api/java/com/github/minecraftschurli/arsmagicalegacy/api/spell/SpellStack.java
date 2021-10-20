package com.github.minecraftschurli.arsmagicalegacy.api.spell;

import com.github.minecraftschurli.arsmagicalegacy.api.ArsMagicaAPI;
import com.github.minecraftschurli.codeclib.CodecHelper;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public record SpellStack(List<ISpellPart> parts, List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers) {
    public static final Codec<SpellStack> CODEC = CodecHelper.forRegistry(ArsMagicaAPI.get()::getSpellPartRegistry)
                                                             .listOf()
                                                             .xmap(SpellStack::of, SpellStack::parts);

    public static final SpellStack EMPTY = SpellStack.of(List.of());

    public static SpellStack of(List<ISpellPart> parts) {
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
                if (currentMods != null) {
                    throw new MalformedSpellStackException("A shape can not come after the first component!", parts);
                }
                currentMods = new ArrayList<>();
                partsWithModifiers.add(Pair.of(shape, Collections.unmodifiableList(currentMods)));
            }
            if (part instanceof ISpellModifier modifier) {
                Objects.requireNonNullElse(currentMods, globalMods).add(modifier);
            }
        }
        return new SpellStack(parts, partsWithModifiers);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<ISpellPart> parts() {
        return Collections.unmodifiableList(this.parts);
    }

    @UnmodifiableView
    @Contract(pure = true)
    @Override
    public List<Pair<ISpellPart, List<ISpellModifier>>> partsWithModifiers() {
        return Collections.unmodifiableList(this.partsWithModifiers);
    }

    public boolean isEmpty() {
        return parts().isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SpellStack that = (SpellStack) o;
        return (this.isEmpty() && that.isEmpty()) || this.parts().equals(that.parts());
    }

    @Override
    public int hashCode() {
        return parts().hashCode();
    }

    public static class MalformedSpellStackException extends RuntimeException {
        private final List<ISpellPart> parts;

        public MalformedSpellStackException(String message, List<ISpellPart> parts) {
            super(message);
            this.parts = parts;
        }

        public List<ISpellPart> getParts() {
            return this.parts;
        }
    }
}
